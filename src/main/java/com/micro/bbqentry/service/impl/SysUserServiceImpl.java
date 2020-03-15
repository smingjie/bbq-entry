package com.micro.bbqentry.service.impl;

import com.micro.bbqentry.general.common.ResponseEnum;
import com.micro.bbqentry.general.constant.OpenConstant;
import com.micro.bbqentry.general.exception.BusinessException;
import com.micro.bbqentry.general.utils.SecurityUtils;
import com.micro.bbqentry.model.entity.SysUserEntity;
import com.micro.bbqentry.model.param.UserDTO;
import com.micro.bbqentry.repository.SysUserMapper;
import com.micro.bbqentry.security.model.MyUser;
import com.micro.bbqentry.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * 系统用户服务实现类
 *
 * @author jockeys
 * @since 2020/2/4
 */
@Slf4j
@Service
public class SysUserServiceImpl implements ISysUserService {

    @Resource
    private SysUserMapper sysUserMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * 通过主键id查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public UserDTO queryById(String id) {
        log.info("mybatis");
        SysUserEntity entity=  sysUserMapper.queryByUserId(id);
        //  SysUserEntity entity1 = this.sysUserMapper.queryByUserId(id);

        return new UserDTO(entity);
    }

    /**
     * 通过username查询单条数据
     *
     * @param username 主键
     * @return 实例对象
     */
    @Override
    public UserDTO queryByUsername(String username) {
        SysUserEntity entity = this.sysUserMapper.queryByUsername(username);
        if (entity == null) {
            throw new BusinessException(ResponseEnum.USER_NOT_EXIST);
        }
        if (entity.getStatus() == OpenConstant.ENABLED_FALSE) {
            throw new BusinessException(ResponseEnum.USER_FORBIDDEN);
        }
        return new UserDTO(entity);
    }


    /**
     * 通过邮箱查询单条数据
     *
     * @param email 邮箱
     * @return 实例对象
     */
    @Override
    public UserDTO queryByEmail(String email) {
        SysUserEntity entity = this.sysUserMapper.queryByEmail(email);
        if (entity == null) {
            throw new BusinessException(ResponseEnum.USER_NOT_EXIST);
        }
        if (entity.getStatus() == OpenConstant.ENABLED_FALSE) {
            throw new BusinessException(ResponseEnum.USER_FORBIDDEN);
        }
        return new UserDTO(entity);
    }

    /**
     * 新增一个用户记录
     *
     * @param param 用户记录必要信息
     * @return 是否新增成功
     */
    @Override
    public boolean save(UserDTO param) {
        //转换为实体
        SysUserEntity entity = param.asEntity();
        //对密码加密处理
        entity.setPassword(bCryptPasswordEncoder.encode(entity.getPassword()));
        //设置创建人
        entity.setCreateBy(SecurityUtils.getCurrUser().getUsername());
        //设置创建时间
        entity.setCreateTime(LocalDateTime.now());
        return this.sysUserMapper.insert(entity) > 0;

    }

    /**
     * 修改数据
     *
     * @param param 用户的实例对象
     * @return 是否更新成功
     */
    @Override
    public boolean update(UserDTO param) {
        //获取当前用户信息
        MyUser currUser = SecurityUtils.getCurrUser();
        SysUserEntity entity = param.asEntity();
        entity.setUpdateBy(currUser.getUsername());
        entity.setUpdateTime(LocalDateTime.now());
        return this.sysUserMapper.update(entity) > 0;
    }
}
