package com.micro.bbqentry.service;

import com.micro.bbqentry.model.entity.SysDict;
import com.micro.bbqentry.model.param.DictDTO;
import com.micro.bbqentry.model.param.DictVO;

import java.util.List;

/**
 * 数据字典服务接口
 *
 * @author jockeys
 * @since 2020/2/3
 */
public interface ISysDictService {
    /**
     * 通过主键id查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysDict queryById(String id);

    /**
     * 通过字典类型查询字典集合
     *
     * @param type 字典类型
     * @return 字典的实例对象集合
     */
    List<DictVO> getDictsByType(String type);

    /**
     * 新增一个字典记录
     *
     * @param param 字典记录必要信息
     * @return 是否新增成功
     */
    boolean save(DictDTO param);

    /**
     * 修改数据
     *
     * @param param 字典的实例对象
     * @return 是否更新成功
     */
    boolean update(DictDTO param);

    /**
     * 软删除
     *
     * @param id 主键
     * @return 是否软删除成功
     */
    boolean softDelete(String id);
}
