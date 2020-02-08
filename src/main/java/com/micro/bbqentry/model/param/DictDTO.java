package com.micro.bbqentry.model.param;

import com.google.common.base.Strings;
import com.micro.bbqentry.general.constant.OpenConstant;
import com.micro.bbqentry.general.utils.GuidUtility;
import com.micro.bbqentry.model.entity.SysDict;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 数据字典数据传输对象
 *
 * @author jockeys
 * @since 2020/2/4
 */
@Data
@AllArgsConstructor
public class DictDTO {

    private String id;
    @NotNull(message = "字典名称不能为空")
    private String name;

    @NotNull(message = "字典类型不能为空")
    private String type;

    @NotBlank(message = "字典码不能为空")
    private String code;

    @NotBlank(message = "字典值不能为空")
    private String value;
    //排序码
    private Integer orderNum;
    //备注
    private String remark;

    /**
     * 从实体解析为传输对象
     */
    public static DictDTO phaseByEntity(SysDict entity) {
        return new DictDTO(
                entity.getId(),
                entity.getName(),
                entity.getType(),
                entity.getCode(),
                entity.getValue(),
                entity.getOrderNum(),
                entity.getRemark()
        );
    }

    /**
     * 从传输对象转换为实体对象
     */
    public static SysDict convertIntoEntity(DictDTO param) {
        SysDict entity = new SysDict();
        //id若为空 则生成一个guid(uuid)
        String id = Strings.isNullOrEmpty(param.getId()) ? GuidUtility.getIdKey() : param.getId();
        //填充
        entity.setId(id);
        entity.setName(param.getName());
        entity.setType(param.getType());
        entity.setCode(param.getCode());
        entity.setValue(param.getValue());
        entity.setOrderNum(param.getOrderNum());
        entity.setRemark(param.getRemark());
        entity.setDelFlag(OpenConstant.DELFLAG_NOT);// 设置删除标志位 正常
        return entity;
    }
}
