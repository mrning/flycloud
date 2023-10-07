package com.lqjk.admin.beans.vos.response;

import com.lqjk.base.basebeans.BaseEntity;
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
@Schema(name = "PermissionPageResponse", description = "权限分页查询")
public class PermissionPageResponse extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @SchemaProperty(name = "父级uuid")
    private String parentUuid;

    @SchemaProperty(name = "名称")
    private String name;

    @SchemaProperty(name = "菜单/权限编码")
    private String code;

    @SchemaProperty(name = "图标")
    private String icon;

    @SchemaProperty(name = "组件路径")
    private String component;

    @SchemaProperty(name = "组件请求url")
    private String url;

    @SchemaProperty(name = "是否跳转到第三方网页")
    private String redirect;

    @SchemaProperty(name = "排序")
    private Integer sortNo;

    @SchemaProperty(name = "菜单类型0=父级菜单1=子菜单2=权限按钮")
    private Integer menuType;

    @SchemaProperty(name = "是否隐藏")
    private Boolean hidden;

}
