package com.micro.bbqentry.service;

import com.alibaba.fastjson.JSONArray;

/**
 * @author jockeys
 * @since 2020/3/2
 */
public interface ISysMenuService {
    /**
     * 根据用户id获取菜单树 JSONArray对象
     * @param userId 用户id
     * @return JSONArray对象
     */
    JSONArray getMenuTreeByUserId(String userId);
}
