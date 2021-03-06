package com.micro.bbqentry.model.param;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.micro.bbqentry.model.entity.SysMenuEntity;
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
public class SysMenuDTO {

    @ApiModelProperty(value = "唯一id")
    private String menuId;

    @ApiModelProperty(value = "父菜单ID，一级菜单为0")
    private String parentId;

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "菜单URL")
    private String url;

    @ApiModelProperty(value = "类型 0：目录 1：菜单 2：按钮")
    private Integer type;

    @ApiModelProperty(value = "菜单图标")
    private String icon;


    public SysMenuDTO(SysMenuEntity entity) {
        this(
                entity.getMenuId(),
                entity.getParentId(),
                entity.getName(),
                entity.getUrl(),
                entity.getType(),
                entity.getIcon()
        );
    }

    public JSONObject toJSONObject() {
        String jsonStr = JSON.toJSONString(this, SerializerFeature.WriteMapNullValue);
        JSONObject result = (JSONObject) JSON.parse(jsonStr, Feature.OrderedField);
        result.remove("parentId");
        return result;
    }
}
