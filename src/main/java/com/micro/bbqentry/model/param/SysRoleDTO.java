package com.micro.bbqentry.model.param;

import com.micro.bbqentry.model.entity.SysRoleEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jockeys
 * @since 2020/3/1
 */

@ApiModel
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysRoleDTO {

    @ApiModelProperty(value = "唯一id")
    private String roleId;

    @ApiModelProperty(value = "角色代码")
    private String roleCode;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色类型 U自定义 S系统预设")
    private String roleType;

    @ApiModelProperty(value = "备注")
    private String remark;

    public SysRoleDTO(SysRoleEntity entity) {
        this(
                entity.getRoleId(),
                entity.getRoleCode(),
                entity.getRoleName(),
                entity.getRoleType(),
                entity.getRemark()
        );
    }

}
