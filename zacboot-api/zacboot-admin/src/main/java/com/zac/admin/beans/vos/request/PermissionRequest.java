package com.zac.admin.beans.vos.request;

import cn.hutool.db.Page;
import com.zac.base.basebeans.PageRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
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
@Schema(name = "SysPermission对象", description = "权限表")
public class PermissionRequest extends PageRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @SchemaProperty(name = "名称")
    private String name;

    @SchemaProperty(name = "菜单/权限编码")
    private String code;

    @SchemaProperty(name = "菜单类型0=父级菜单1=子菜单2=权限按钮")
    private Integer menuType;

}
