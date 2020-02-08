package com.micro.bbqentry.service;

import com.micro.bbqentry.model.entity.SysUser;
import com.micro.bbqentry.model.param.DictDTO;
import com.micro.bbqentry.model.param.UserDTO;

/**
 * 系统用户服务接口
 *
 * @author jockeys
 * @since 2020/2/4
 */
public interface ISysUserService {
    /**
     * 通过主键id查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    UserDTO queryById(String id);
    /**
     * 通过username查询单条数据
     *
     * @param username 主键
     * @return 实例对象
     */
    UserDTO queryByUsername(String username);

    /**
     * 通过邮箱查询单条数据
     *
     * @param email 邮箱
     * @return 实例对象
     */
    UserDTO queryByEmail(String email);

    /**
     * 新增一个用户记录
     *
     * @param param 用户记录必要信息
     * @return 是否新增成功
     */
    boolean save(UserDTO param);

    /**
     * 修改数据
     *
     * @param param 用户的实例对象
     * @return 是否更新成功
     */
    boolean update(UserDTO param);


}
