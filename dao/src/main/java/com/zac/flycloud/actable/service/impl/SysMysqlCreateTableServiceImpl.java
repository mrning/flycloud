package com.zac.flycloud.actable.service.impl;

import com.zac.flycloud.actable.config.ActableConfig;
import com.zac.flycloud.actable.mapper.CreateMysqlTablesMapper;
import com.zac.flycloud.actable.service.SysMysqlCreateTableService;
import com.zac.flycloud.actable.table.ColumnFactory;
import com.zac.flycloud.actable.table.ColumnSql;
import com.zac.flycloud.actable.table.CommonColumn;
import com.zac.flycloud.actable.table.TableSql;
import com.zac.flycloud.actable.util.CamelCaseUtil;
import com.zac.flycloud.actable.util.ClassTools;
import com.zac.flycloud.annotation.AutoColumn;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.*;

/**
 * 项目启动时自动扫描配置的目录中的model，根据配置的规则自动创建或更新表 该逻辑只适用于mysql，其他数据库尚且需要另外扩展，因为sql的语法不同
 *
 * @author starmark
 * @version 2018年4月7日 下午5:58:12
 */
@Transactional
@Service
public class SysMysqlCreateTableServiceImpl implements SysMysqlCreateTableService {

    private static final Logger log = LoggerFactory.getLogger(SysMysqlCreateTableServiceImpl.class);

    @Autowired
    private CreateMysqlTablesMapper createMysqlTablesMapper;

    @Autowired
    private ActableConfig actableConfig;

    /**
     * 读取配置文件的三种状态（创建表、更新表、不做任何事情）
     */
    public void createMysqlTable() {
        try {
            // 不做任何事情
            if ("none".equals(actableConfig.getTableAuto())) {
                log.info("配置mybatis.table.auto=none，不需要做任何事情");
                return;
            }

            // 从包package中获取所有的Class
            Set<Class<?>> classes = ClassTools.findPathMatchingResources(actableConfig.getPack());

            // 用于存需要创建的表名+结构
            Map<String, List<CommonColumn>> newTableMap = new HashMap<String, List<CommonColumn>>();


            // 用于存需要增加字段的表名+结构
            Map<String, List<CommonColumn>> addTableMap = new HashMap<String, List<CommonColumn>>();
            // 构建出全部表的增删改的map
            allTableMapConstruct(classes, newTableMap, addTableMap);

            if(newTableMap.isEmpty() && addTableMap.isEmpty()){
                log.info("不需要创建表 || 更新表。");
            }else{
                // 根据传入的map，分别去创建或修改表结构
                createOrModifyTableConstruct(newTableMap, addTableMap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取表名
     *
     * @param clas
     * @return
     */
    private String getTableName(Class clas) {
        String className = clas.getSimpleName();
        String tableName = CamelCaseUtil.humpToLine(className);
        //因为类名第一个肯定是大写的，所以去掉第一个_
        return tableName.substring(1);
    }

    /**
     * 构建出全部表的增删改的map
     *
     * @param classes     从包package中获取所有的Class
     * @param newTableMap 用于存需要创建的表名+结构
     * @param addTableMap 用于存需要增加字段的表名+结构
     */
    private void allTableMapConstruct(Set<Class<?>> classes,
                                      Map<String, List<CommonColumn>> newTableMap,
                                      Map<String, List<CommonColumn>> addTableMap) throws InstantiationException, IllegalAccessException {
        for (Class<?> clas : classes) {

            String tableName = this.getTableName(clas);
            // 用于存新增表的字段
            List<CommonColumn> newFieldList = new ArrayList<CommonColumn>();
            // 用于存新增的字段
            List<CommonColumn> addFieldList = new ArrayList<CommonColumn>();
            // 迭代出所有model的所有fields存到newFieldList中
            tableFieldsConstruct(clas, newFieldList);

            // 如果配置文件配置的是create，表示将所有的表删掉重新创建
            if ("create".equals(actableConfig.getTableAuto())) {
                createMysqlTablesMapper.dorpTableByName(tableName);
            }
            // 先查该表是否以存在
            int exist = createMysqlTablesMapper.findTableCountByTableName(tableName);
            // 不存在时
            if (exist == 0) {
                newTableMap.put(tableName, newFieldList);
            } else {
                // 已存在时理论上做修改的操作，这里查出该表的结构
                List<String> columnNames = createMysqlTablesMapper
                        .findTableEnsembleByTableName(tableName);

                // 验证对比从model中解析的fieldList与从数据库查出来的columnList
                // 1. 找出增加的字段

                buildAddFields(
                        newFieldList, addFieldList,
                        columnNames);
                if (addFieldList.size() > 0) {
                    addTableMap.put(tableName, addFieldList);
                }
            }
        }
    }

    /**
     * 构建增加的删除的修改的字段
     *
     * @param newFieldList 用于存新增表的字段
     * @param addFieldList 用于存新增的字段
     * @param columnNames  从sysColumns中取出我们需要比较的列的List
     */
    private void buildAddFields(List<CommonColumn> newFieldList, List<CommonColumn> addFieldList,
                                List<String> columnNames) {
        for (CommonColumn commonColumn : newFieldList) {
            if (!this.isExistField(columnNames, commonColumn)) {
                addFieldList.add(commonColumn);
            }
        }
    }

    /**
     * 判断字段是否已经存在了
     *
     * @param columnNames
     * @param commonColumn
     * @return
     */
    private boolean isExistField(List<String> columnNames, CommonColumn commonColumn) {
        for (String columnName : columnNames) {
            if (commonColumn.getName().equalsIgnoreCase(columnName)) {
                return true;
            }
        }

        return false;
    }


    /**
     * 迭代出所有model的所有fields存到newFieldList中
     *
     * @param clas         准备做为创建表依据的class
     * @param newFieldList 用于存新增表的字段
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private void tableFieldsConstruct(Class<?> clas,
                                      List<CommonColumn> newFieldList) throws IllegalAccessException, InstantiationException {
        Field[] fields = clas.getDeclaredFields();
        // 判断是否有父类，如果有拉取父类的field，这里只支持多层继承
        fields = recursionParents(clas, fields);
        for (Field field : fields) {
            if("serialVersionUID".equals(field.getName())){
                continue;
            }
            AutoColumn column = field.getAnnotation(AutoColumn.class);
            // 注解，不需要的字段
            if (column != null && column.isIgnore()) {
                continue;
            }
            Class<CommonColumn> commonColumnClass = ColumnFactory.getCommonColumn(field.getType().getName());
            CommonColumn commonColumn = commonColumnClass.newInstance();
            //设置字段
            if (column != null && StringUtils.isNoneEmpty(column.name())) {
                commonColumn.setName(column.name());
            } else {
                //获取字段名称
                String fieldName = CamelCaseUtil.humpToLine(field.getName());
                commonColumn.setName(fieldName);
            }
            //设置字段类型
            if (column != null && StringUtils.isNoneEmpty(column.type())) {
                commonColumn.setType(column.type());
            }

            //设置长度
            if (column != null && column.length() > 0) {
                commonColumn.setLength(column.length());
            }
            //设置长度
            if (column != null && column.length() > 0) {
                commonColumn.setDecimalLength(column.decimalLength());
            }
            //设置自增
            if (column != null && column.isAutoIncrement()) {
                commonColumn.setAutoIncrement(column.isAutoIncrement());
            }
            //是否为空
            if (column != null && !column.isNull()) {
                commonColumn.setNullValue(column.isNull());
            }
            //是否主键
            if (column != null && !column.isKey()) {
                commonColumn.setKey(column.isKey());
            }
            //设置字段类型
            if (column != null && StringUtils.isNoneEmpty(column.defaultValue())) {
                commonColumn.setDefaultValue(column.defaultValue());
            }
            newFieldList.add(commonColumn);

        }
    }

    /**
     * 递归扫描父类的fields
     *
     * @param clas   类
     * @param fields 属性
     */
    @SuppressWarnings("rawtypes")
    private Field[] recursionParents(Class<?> clas, Field[] fields) {
        if (clas.getSuperclass() != null) {
            Class clsSup = clas.getSuperclass();
            fields = ArrayUtils.addAll(fields, clsSup.getDeclaredFields());
            fields = recursionParents(clsSup, fields);
        }
        return fields;
    }

    /**
     * 根据传入的map创建或修改表结构
     *
     * @param newTableMap 用于存需要创建的表名+结构
     * @param addTableMap 用于存需要增加字段的表名+结构
     */
    private void createOrModifyTableConstruct(Map<String, List<CommonColumn>> newTableMap,
                                              Map<String, List<CommonColumn>> addTableMap) {
        // 1. 创建表
        createTableByMap(newTableMap);
        // 2. 添加新的字段
        addFieldsByMap(addTableMap);
    }


    /**
     * 根据map结构对表中添加新的字段
     *
     * @param addTableMap 用于存需要增加字段的表名+结构
     */
    private void addFieldsByMap(Map<String, List<CommonColumn>> addTableMap) {
        // 做增加字段操作
        if (addTableMap.size() > 0) {
            for (Map.Entry<String, List<CommonColumn>> entry : addTableMap.entrySet()) {
                List<CommonColumn> columnList = entry.getValue();
                for (CommonColumn column : columnList) {
                    //因为mysql一次只能执行一条sql
                    List<CommonColumn> fieldList = new ArrayList<CommonColumn>();
                    fieldList.add(column);
                    TableSql tableSql = this.getTableSql(entry.getKey(), fieldList);

                    log.info("开始为表" + entry.getKey() + "增加字段" + column.getName());
                    createMysqlTablesMapper.addTableField(tableSql);
                    log.info("完成为表" + entry.getKey() + "增加字段" + column.getName());
                }
            }
            log.info("所有表添加字段结束：数量为 " + addTableMap.size());
        }
    }


    /**
     * 根据map结构创建表
     *
     * @param newTableMap 用于存需要创建的表名+结构
     */
    private void createTableByMap(Map<String, List<CommonColumn>> newTableMap) {
        // 做创建表操作
        if (newTableMap.size() > 0) {
            for (Map.Entry<String, List<CommonColumn>> entry : newTableMap.entrySet()) {
                TableSql tableSql = this.getTableSql(entry.getKey(), entry.getValue());
                log.info("开始创建表：" + entry.getKey());
                createMysqlTablesMapper.createTable(tableSql);
                log.info("完成创建表：" + entry.getKey());
            }
            log.info("建表结束：数量为" + newTableMap.size());
        }
    }

    /**
     * 构造执行的SQL
     *
     * @param tableName
     * @param columns
     * @return
     */
    private TableSql getTableSql(String tableName, List<CommonColumn> columns) {
        TableSql tableSql = new TableSql();
        tableSql.setName(tableName);
        List<String> primaryKeys = this.getPrimaryKey(columns);
        List<ColumnSql> columnSqls = new ArrayList<ColumnSql>();
        for (CommonColumn column : columns) {
            columnSqls.add(new ColumnSql(column, primaryKeys));
        }
        tableSql.setPrimaryKey(StringUtils.join(primaryKeys, ","));
        tableSql.setColumnSqls(columnSqls);
        return tableSql;
    }

    /**
     * 获取主键
     *
     * @param columns
     * @return
     */
    private List<String> getPrimaryKey(List<CommonColumn> columns) {
        List<String> primaryKeys = new ArrayList<String>() {
        };
        for (CommonColumn column : columns) {
            if (column.isKey()) {
                primaryKeys.add(column.getName());
            }
        }
        if (primaryKeys.isEmpty()) {
            primaryKeys.add(actableConfig.getPrimarykey());
        }
        return primaryKeys;
    }
}
