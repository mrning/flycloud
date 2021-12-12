package com.zac.flycloud.utils;

import com.zac.flycloud.bean.dto.TreeDto;
import com.zac.flycloud.bean.tablemodel.SysDept;

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
     * queryTreeList的子方法 ====1=====
     * 该方法是将SysDept类型的list集合转换成TreeDto类型的集合
     */
    public static List<TreeDto> wrapTreeDataToTreeList(List<SysDept> recordList) {
        // 在该方法每请求一次,都要对全局list集合进行一次清理
    	List<SysDept> idList = new ArrayList<>();
        List<TreeDto> records = new ArrayList<>();
        for (int i = 0; i < recordList.size(); i++) {
            SysDept depart = recordList.get(i);
            records.add(new TreeDto(depart));
        }
        List<TreeDto> tree = findChildren(records, idList);
//        setEmptyChildrenAsNull(tree);
        return tree;
    }

    /**
     * 获取 SysDept
     * @param recordList
     * @return
     */
    public static List<SysDept> wrapTreeDataToDepartIdTreeList(List<SysDept> recordList) {
        // 在该方法每请求一次,都要对全局list集合进行一次清理
        //idList.clear();
        List<SysDept> idList = new ArrayList<>();
        List<TreeDto> records = new ArrayList<>();
        for (int i = 0; i < recordList.size(); i++) {
            SysDept depart = recordList.get(i);
            records.add(new TreeDto(depart));
        }
        findChildren(records, idList);
        return idList;
    }

    /**
     * queryTreeList的子方法 ====2=====
     * 该方法是找到并封装顶级父类的节点到TreeList集合
     */
    private static List<TreeDto> findChildren(List<TreeDto> recordList,
                                                         List<SysDept> departIdList) {

        List<TreeDto> treeList = new ArrayList<>();
//        for (int i = 0; i < recordList.size(); i++) {
//            TreeDto branch = recordList.get(i);
//            if (StringUtils.isEmpty(branch.getParentId())) {
//                treeList.add(branch);
//                SysDept sysDept = new SysDept();
//                sysDept.set
//                departIdList.add(sysDept);
//            }
//        }
//        getGrandChildren(treeList,recordList,departIdList);
        
        return treeList;
    }

    /**
     * queryTreeList的子方法====3====
     *该方法是找到顶级父类下的所有子节点集合并封装到TreeList集合
     */
    private static void getGrandChildren(List<TreeDto> treeList,List<TreeDto> recordList,List<SysDept> idList) {

//        for (int i = 0; i < treeList.size(); i++) {
//            TreeDto treeDto = treeList.get(i);
//            SysDept idModel = idList.get(i);
//            for (int i1 = 0; i1 < recordList.size(); i1++) {
//                TreeDto m = recordList.get(i1);
//                if (m.getParentId()!=null && m.getParentId().equals(treeDto.getId())) {
//                    treeDto.getChildren().add(m);
//                    SysDept dim = new SysDept().convert(m);
//                    idModel.getChildren().add(dim);
//                }
//            }
//            getGrandChildren(treeList.get(i).getChildren(), recordList, idList.get(i).getChildren());
//        }

    }
    

}
