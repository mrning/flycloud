package com.zac.flycloud.sys.sysutils;

import com.zac.flycloud.dto.SysDeptDto;
import com.zac.flycloud.dto.TreeDto;
import com.zac.flycloud.tablemodel.SysDept;
import org.apache.commons.lang.StringUtils;

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
     * 该方法是s将SysDept类型的list集合转换成TreeDto类型的集合
     */
    public static List<TreeDto> wrapTreeDataToTreeList(List<SysDeptDto> recordList) {
        // 在该方法每请求一次,都要对全局list集合进行一次清理
    	List<SysDeptDto> idList = new ArrayList<>();
        List<TreeDto> records = new ArrayList<>();
        for (int i = 0; i < recordList.size(); i++) {
            SysDeptDto depart = recordList.get(i);
            records.add(new TreeDto(depart));
        }
        List<TreeDto> tree = findChildren(records, idList);
        setEmptyChildrenAsNull(tree);
        return tree;
    }

    /**
     * 获取 SysDeptDto
     * @param recordList
     * @return
     */
    public static List<SysDeptDto> wrapTreeDataToDepartIdTreeList(List<SysDept> recordList) {
        // 在该方法每请求一次,都要对全局list集合进行一次清理
        //idList.clear();
        List<SysDeptDto> idList = new ArrayList<>();
        List<TreeDto> records = new ArrayList<>();
        for (int i = 0; i < recordList.size(); i++) {
            SysDeptDto depart = recordList.get(i);
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
                                                         List<SysDeptDto> departIdList) {

        List<TreeDto> treeList = new ArrayList<>();
        for (int i = 0; i < recordList.size(); i++) {
            TreeDto branch = recordList.get(i);
            if (StringUtils.isEmpty(branch.getParentId())) {
                treeList.add(branch);
                SysDeptDto SysDeptDto = new SysDeptDto().convert(branch);
                departIdList.add(SysDeptDto);
            }
        }
        getGrandChildren(treeList,recordList,departIdList);
        
        //idList = departIdList;
        return treeList;
    }

    /**
     * queryTreeList的子方法====3====
     *该方法是找到顶级父类下的所有子节点集合并封装到TreeList集合
     */
    private static void getGrandChildren(List<TreeDto> treeList,List<TreeDto> recordList,List<SysDeptDto> idList) {

        for (int i = 0; i < treeList.size(); i++) {
            TreeDto treeDto = treeList.get(i);
            SysDeptDto idModel = idList.get(i);
            for (int i1 = 0; i1 < recordList.size(); i1++) {
                TreeDto m = recordList.get(i1);
                if (m.getParentId()!=null && m.getParentId().equals(treeDto.getId())) {
                    treeDto.getChildren().add(m);
                    SysDeptDto dim = new SysDeptDto().convert(m);
                    idModel.getChildren().add(dim);
                }
            }
            getGrandChildren(treeList.get(i).getChildren(), recordList, idList.get(i).getChildren());
        }

    }
    

    /**
     * queryTreeList的子方法 ====4====
     * 该方法是将子节点为空的List集合设置为Null值
     */
    private static void setEmptyChildrenAsNull(List<TreeDto> treeList) {

        for (int i = 0; i < treeList.size(); i++) {
            TreeDto model = treeList.get(i);
            if (model.getChildren().size() == 0) {
                model.setChildren(null);
                model.setIsLeaf(true);
            }else{
                setEmptyChildrenAsNull(model.getChildren());
                model.setIsLeaf(false);
            }
        }
        // SysDeptTreeList = treeList;
    }
}
