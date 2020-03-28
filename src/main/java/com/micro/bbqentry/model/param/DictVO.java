package com.micro.bbqentry.model.param;

import com.micro.bbqentry.model.entity.SysDict;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 字典的单条记录
 *
 * @author jockeys
 * @since 2020/2/4
 */
@Data
@AllArgsConstructor
public class DictVO {
    @NotBlank(message = "字典的key键不能为空")
    private String key;
    @NotBlank(message = "字典的value值不能为空")
    private String value;

    public DictVO(SysDict entity) {
        this(
                entity.getCode(),
                entity.getValue()
        );
    }
}

