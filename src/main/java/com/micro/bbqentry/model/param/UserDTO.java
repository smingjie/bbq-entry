package com.micro.bbqentry.model.param;

import com.google.common.base.Strings;
import com.micro.bbqentry.general.constant.OpenConstant;
import com.micro.bbqentry.general.utils.SequenceUtils;
import com.micro.bbqentry.model.entity.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 用户数据传输对象
 *
 * @author jockeys
 * @since 2020/2/4
 */
@Data
@AllArgsConstructor
public class UserDTO {
    private String userId;
    @NotBlank(message = "用户账号不能为空")
    private String username;
    @NotBlank(message = "用户密码不能为空")
    private String password;

    private String mobile;

    private String email;

    /**
     * 从实体解析为传输对象
     */
    public static UserDTO phaseByEntity(SysUser entity) {
        return new UserDTO(
                entity.getUserId(),
                entity.getUsername(),
                entity.getPassword(),
                entity.getMobile(),
                entity.getEmail()
        );
    }

    /**
     * 从传输对象转换为实体对象
     */
    public static SysUser convertIntoEntity(UserDTO param) {
        SysUser entity = new SysUser();
        //id若为空 则生成一个guid(uuid)
        String id = Strings.isNullOrEmpty(param.getUserId()) ? SequenceUtils.uuid36() : param.getUserId();
        //填充
        entity.setUserId(id);
        entity.setUsername(param.getUsername());
        entity.setPassword(param.getPassword());
        entity.setMobile(param.getMobile());
        entity.setEmail(param.getEmail());
        entity.setStatus(OpenConstant.ENABLED_TRUE);// 设置状态标志位 启用
        return entity;
    }
}
