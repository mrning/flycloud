package com.lqjk.admin.utils;


import com.lqjk.admin.beans.dtos.TreeDto;
import com.lqjk.admin.entity.SysDept;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <P>
 * 对应部门的表,处理并查找树级数据
 * <P>
 * 
 * @Author: zac
 * @Date: 2019-01-22
 */
public class FindsDeptsChildrenUtil {

    /**
     * queryTreeList的子方法 ====1====
     * 该方法是将SysDept类型的list集合转换成TreeDto类型的集合
     */
    public static List<TreeDto> wrapTreeDataToTreeList(List<SysDept> recordList) {
        List<TreeDto> records = new ArrayList<>();
        for (int i = 0; i < recordList.size(); i++) {
            SysDept depart = recordList.get(i);
            records.add(new TreeDto(depart));
        }
        List<TreeDto> tree = findChildren(records);
        return tree;
    }

    /**
     * queryTreeList的子方法 ====2=====
     * 该方法是找到并封装顶级父类的节点到TreeList集合
     */
    private static List<TreeDto> findChildren(List<TreeDto> recordList) {
        List<SysDept> departIdList = new ArrayList<>();
        List<TreeDto> treeList = new ArrayList<>();

        for (int i = 0; i < recordList.size(); i++) {
            TreeDto branch = recordList.get(i);
            if (StringUtils.isEmpty(branch.getParentUuid())) {
                treeList.add(branch);
                departIdList.add(branch.convertToDept());
            }
        }
        getGrandChildren(treeList,recordList,departIdList);
        
        return treeList;
    }

    /**
     * queryTreeList的子方法====3====
     *该方法是找到顶级父类下的所有子节点集合并封装到TreeList集合
     */
    private static void getGrandChildren(List<TreeDto> treeList,List<TreeDto> recordList,List<SysDept> idList) {

        for (int i = 0; i < treeList.size(); i++) {
            TreeDto treeDto = treeList.get(i);
            SysDept idModel = idList.get(i);
            for (int i1 = 0; i1 < recordList.size(); i1++) {
                TreeDto m = recordList.get(i1);
                if (StringUtils.isNotBlank(m.getParentUuid()) && m.getParentUuid().equals(treeDto.getKey())) {
                    if (treeDto.getChildren() == null){
                        treeDto.setChildren(new ArrayList<>());
                    }
                    treeDto.getChildren().add(m);
                    SysDept dim = m.convertToDept();
                    if (idModel.getChildren() == null){
                        idModel.setChildren(new ArrayList<>());
                    }
                    idModel.getChildren().add(dim);
                }
            }
            if (null != treeList.get(i).getChildren() && treeList.get(i).getChildren().size()>0){
                getGrandChildren(treeList.get(i).getChildren(), recordList, idList.get(i).getChildren());
            }
        }

    }
    

}
