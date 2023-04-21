package com.zacboot.admin.beans.vos.request;

import com.zacboot.system.core.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "PermissionUpdateRequest", description = "权限更新")
public class PermissionUpdateRequest extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "父级uuid")
    private String parentUuid;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "菜单/权限编码")
    private String code;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "组件路径")
    private String component;

    @ApiModelProperty(value = "组件请求url")
    private String url;

    @ApiModelProperty(value = "是否跳转到第三方网页")
    private String redirect;

    @ApiModelProperty(value = "排序")
    private Integer sortNo;

    @ApiModelProperty(value = "菜单类型0=父级菜单1=子菜单2=权限按钮")
    private Integer menuType;

    @ApiModelProperty(value = "是否隐藏")
    private Boolean hidden;

}
