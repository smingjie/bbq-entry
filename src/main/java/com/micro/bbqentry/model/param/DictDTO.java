package com.micro.bbqentry.model.param;

import com.google.common.base.Strings;
import com.micro.bbqentry.general.constant.OpenConstant;
import com.micro.bbqentry.general.utils.SequenceUtils;
import com.micro.bbqentry.model.entity.SysDictEntity;
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

    private Integer orderNum;

    private String remark;

    /**
     * 从实体解析为传输对象
     */
    public DictDTO(SysDictEntity entity) {
        this(
                entity.getId(),
                entity.getName(),
                entity.getType(),
                entity.getCode(),
                entity.getValue(),
                entity.getOrderNum(),
                entity.getRemark()
        );
    }


    public SysDictEntity asEntity() {
        SysDictEntity entity = new SysDictEntity();
        //id若为空 则生成一个guid(uuid)
        String uid = Strings.isNullOrEmpty(this.getId()) ? SequenceUtils.uuid36() : this.getId();
        //填充
        entity.setId(uid);
        entity.setName(this.getName());
        entity.setType(this.getType());
        entity.setCode(this.getCode());
        entity.setValue(this.getValue());
        entity.setOrderNum(this.getOrderNum());
        entity.setRemark(this.getRemark());
        // 设置删除标志位 正常
        entity.setDelFlag(OpenConstant.DELFLAG_NOT);
        return entity;
    }
}
