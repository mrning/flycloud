package com.zacboot.autocode.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.UUID;
import com.zacboot.autocode.bean.MybatisGeneratorRequest;
import com.zacboot.autocode.constants.MgtConstant;
import com.zacboot.autocode.service.MybatisGeneratorService;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.api.VerboseProgressCallback;
import org.mybatis.generator.config.*;
import org.mybatis.generator.exception.InvalidConfigurationException;
import org.mybatis.generator.internal.DefaultShellCallback;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MybatisGeneratorServiceImpl implements MybatisGeneratorService {
    @Value("${spring.datasource.url}")
    private String dbUrl;
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    @Value("${spring.datasource.username}")
    private String dbUserName;
    @Value("${spring.datasource.password}")
    private String dbPassword;
    public static final String API_PACKAGE = ".api.";

    @Override
    public String doDenerator(MybatisGeneratorRequest mybatisGeneratorRequest)  {
        Configuration config = new Configuration();

        // conditional:*这是默认值*,这个模型和下面的hierarchical类似，除了如果那个单独的类将只包含一个字段，将不会生成一个单独的类。
        // 因此,如果一个表的主键只有一个字段,那么不会为该字段生成单独的实体类,会将该字段合并到基本实体类中。
        // flat:该模型为每一张表只生成一个实体类。这个实体类包含表中的所有字段。**这种模型最简单，推荐使用。**
        // hierarchical:如果表有主键,那么该模型会产生一个单独的主键实体类,如果表还有BLOB字段，
        // 则会为表生成一个包含所有BLOB字段的单独的实体类,然后为所有其他的字段生成一个单独的实体类。 MBG会在所有生成的实体类之间维护一个继承关系。
        Context context = new Context(ModelType.FLAT);
        context.setId(UUID.randomUUID().toString(true));
        context.setTargetRuntime("MyBatis3");
        // 设置分隔符 前后分隔符 保留字符使用`分隔
        context.addProperty("autoDelimitKeywords","true");
        context.addProperty("beginningDelimiter","`");
        context.addProperty("endingDelimiter","`");
        context.addProperty("tableName", mybatisGeneratorRequest.getTableName());
        context.addProperty("schema", mybatisGeneratorRequest.getSchema());

        // 添加插件
        addPlugins(context,mybatisGeneratorRequest.getDesc(),mybatisGeneratorRequest.getPlatform().getValue());

        // 配置jdbc连接
        buildConnection(context);

        // 配置要生成代码的表
        buildTables(context);

        //生成注释 默认实现：DefaultCommentGenerator

        // 用来控制生成的实体类
        buildJavaModel(context);

        // 设置mapper接口的生成
        buildMapper(context);

        List<String> errorMsg = new ArrayList<>();
        try {
            config.addContext(context);
            // 允许覆盖生成的文件
            DefaultShellCallback callback = new DefaultShellCallback(true);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, errorMsg);
            ProgressCallback progressCallback = new VerboseProgressCallback();
            myBatisGenerator.generate(progressCallback);
            if (CollectionUtil.isNotEmpty(errorMsg)){
                errorMsg.forEach(log::error);
            }
        } catch (InvalidConfigurationException | SQLException | IOException | InterruptedException e) {
            e.printStackTrace();
            return e.getLocalizedMessage();
        }
        return "success";
    }

    private void addPlugins(Context context,String desc, String platform) {
        // controller生成插件
        PluginConfiguration controllerPlugin = new PluginConfiguration();
        controllerPlugin.setConfigurationType(MgtConstant.TARGETPACKAGE + ".genplugins.ControllerGenPlugin");
        controllerPlugin.addProperty("controllerPath", System.getProperty("user.dir") + "\\genDir\\");
        controllerPlugin.addProperty("controllerPackage", MgtConstant.TARGETPACKAGE + API_PACKAGE + platform);
        controllerPlugin.addProperty("controllerPlatform", platform);
        controllerPlugin.addProperty("controllerDesc", desc);
        context.addPluginConfiguration(controllerPlugin);
        // service生成插件
        PluginConfiguration servicePlugin = new PluginConfiguration();
        servicePlugin.setConfigurationType(MgtConstant.TARGETPACKAGE + ".genplugins.ServiceGenPlugin");
        servicePlugin.addProperty("servicePath", System.getProperty("user.dir") + "\\genDir\\");
        servicePlugin.addProperty("servicePackage", MgtConstant.TARGETPACKAGE_SERVICE);
        context.addPluginConfiguration(servicePlugin);
        // dao生成插件
        PluginConfiguration daoPlugin = new PluginConfiguration();
        daoPlugin.setConfigurationType(MgtConstant.TARGETPACKAGE + ".genplugins.DaoGenPlugin");
        daoPlugin.addProperty("daoPath", System.getProperty("user.dir") + "\\genDir\\");
        daoPlugin.addProperty("daoPackage", MgtConstant.TARGETPACKAGE_DAO);
        context.addPluginConfiguration(daoPlugin);
    }

    private void buildMapper(Context context) {
        JavaClientGeneratorConfiguration javaClientGeneratorConfiguration = new JavaClientGeneratorConfiguration();
        javaClientGeneratorConfiguration.setConfigurationType("ANNOTATEDMAPPER");
        javaClientGeneratorConfiguration.setTargetProject(System.getProperty("user.dir") + "\\genDir\\");
        javaClientGeneratorConfiguration.setTargetPackage(MgtConstant.TARGETPACKAGE_MAPPER);
        javaClientGeneratorConfiguration.addProperty("rootInterface","com.baomidou.mybatisplus.core.mapper.BaseMapper<"+context.getProperty("")+">");
        context.setJavaClientGeneratorConfiguration(javaClientGeneratorConfiguration);
    }

    private void buildJavaModel(Context context) {
        JavaModelGeneratorConfiguration javaModelGeneratorConfiguration = new JavaModelGeneratorConfiguration();
        javaModelGeneratorConfiguration.setTargetProject(System.getProperty("user.dir") + "\\genDir\\");
        javaModelGeneratorConfiguration.setTargetPackage(MgtConstant.TARGETPACKAGE_DTO);
        javaModelGeneratorConfiguration.addProperty("rootClass", "com.zacboot.system.core.entity.BaseEntity");
        javaModelGeneratorConfiguration.addProperty("exampleTargetPackage",javaModelGeneratorConfiguration.getTargetPackage()+".example");
        javaModelGeneratorConfiguration.addProperty("trimStrings","true");
        context.setJavaModelGeneratorConfiguration(javaModelGeneratorConfiguration);
    }

    private void buildTables(Context context) {
        TableConfiguration tableConfiguration = new TableConfiguration(context);
        tableConfiguration.setTableName(context.getProperty("tableName"));
        GeneratedKey generatedKey = new GeneratedKey("id","JDBC",true,"post");
        tableConfiguration.setGeneratedKey(generatedKey);
        tableConfiguration.addIgnoredColumn(new IgnoredColumn("id"));
        // 生成代码逻辑需要制定库名，否则会查找全部库的同名表然后被覆盖生成不需要的DTO
        tableConfiguration.setSchema(context.getProperty("schema"));
        tableConfiguration.setCatalog(context.getProperty("schema"));
        tableConfiguration.addProperty("ignoreQualifiersAtRuntime", "true");
        context.addTableConfiguration(tableConfiguration);
    }

    private void buildConnection(Context context) {
        JDBCConnectionConfiguration jdbcConnectionConfiguration = new JDBCConnectionConfiguration();
        jdbcConnectionConfiguration.setConnectionURL(dbUrl);
        jdbcConnectionConfiguration.setDriverClass(driverClassName);
        jdbcConnectionConfiguration.setUserId(dbUserName);
        jdbcConnectionConfiguration.setPassword(dbPassword);
        context.setJdbcConnectionConfiguration(jdbcConnectionConfiguration);
    }
}
