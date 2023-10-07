package com.lqjk.base.bizentity;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lqjk.base.basebeans.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author zac
 * @since 2022-11-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sys_permission")
public class SysPermission extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String parentUuid;

    private String name;

    private String code;

    private String icon;

    private String component;

    private String url;

    private String redirect;

    private Integer sortNo;

    private Integer menuType;

    private Boolean hidden;

    public static <T> SysPermission convertByRequest(T request) {
        SysPermission sysPermission = new SysPermission();
        BeanUtil.copyProperties(request, sysPermission);
        return sysPermission;
    }

}
