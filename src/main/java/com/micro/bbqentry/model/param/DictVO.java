package com.micro.bbqentry.model.param;

import com.micro.bbqentry.model.entity.SysDict;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 字典的单条记录
 *
 * @author jockeys
 * @since 2020/2/4
 */
@Data
@AllArgsConstructor
public class DictVO {
    @NotNull(message = "id不能为空")
    private String id;
    @NotBlank(message = "字典的key键不能为空")
    private String key;
    @NotBlank(message = "字典的value值不能为空")
    private String value;

    /**
     * 从实体解析为视图对象
     */
    public static DictVO phaseByEntity(SysDict entity) {
        return new DictVO(
                entity.getId(),
                entity.getCode(),
                entity.getValue()
        );
    }
}

