package com.micro.bbqentry.general.utils;


import java.util.UUID;

/**
 * guid 生成器
 *
 * @author jockeys
 * @since 2020/2/4
 */
public class GuidUtility {

    /**
     *  java 工具类 uuid生成的id，36字符
     * @return uuid
     */
    public static String getIdKey() {
        return UUID.randomUUID().toString().toUpperCase();
    }


}
