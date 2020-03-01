package com.micro.bbqentry.model.param;

import com.google.common.base.Strings;
import com.micro.bbqentry.general.constant.OpenConstant;
import com.micro.bbqentry.general.utils.SequenceUtils;
import com.micro.bbqentry.model.entity.SysUserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

/**
 * 用户数据传输对象
 *
 * @author jockeys
 * @since 2020/2/4
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String userId;
    @NotBlank(message = "用户账号不能为空")
    private String username;
    @NotBlank(message = "用户密码不能为空")
    private String password;

    private String mobile;

    private String email;


    public UserDTO(SysUserEntity entity) {
        this(
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
    public SysUserEntity asEntity() {
        SysUserEntity entity = new SysUserEntity();
        //id若为空 则生成一个guid(uuid)
        String id = Strings.isNullOrEmpty(this.getUserId()) ? SequenceUtils.uuid36() : this.getUserId();
        //填充
        entity.setUserId(id);
        entity.setUsername(this.getUsername());
        entity.setPassword(this.getPassword());
        entity.setMobile(this.getMobile());
        entity.setEmail(this.getEmail());
        // 设置状态标志位 启用
        entity.setStatus(OpenConstant.ENABLED_TRUE);
        return entity;
    }
}
