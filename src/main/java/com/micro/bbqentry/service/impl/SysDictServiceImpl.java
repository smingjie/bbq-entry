package com.micro.bbqentry.service.impl;

import com.google.common.collect.Lists;
import com.micro.bbqentry.general.constant.OpenConstant;
import com.micro.bbqentry.model.entity.SysDictEntity;
import com.micro.bbqentry.model.param.DictDTO;
import com.micro.bbqentry.model.param.DictVO;
import com.micro.bbqentry.repository.SysDictMapper;
import com.micro.bbqentry.service.ISysDictService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 数据字典服务实现类
 *
 * @author makejava
 * @since 2020-02-03 22:34:26
 */
@Service
public class SysDictServiceImpl implements ISysDictService {
    @Resource
    private SysDictMapper sysDictMapper;

    /**
     * 通过主键id查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SysDictEntity queryById(String id) {
        return this.sysDictMapper.queryById(id);
    }


    /**
     * 通过字典类型查询字典集合
     *
     * @param type 字典类型
     * @return 字典的实例对象集合
     */
    @Override
    public List<DictVO> queryDictsByType(String type) {
        List<SysDictEntity> list = sysDictMapper.queryByType(type);
        // 推荐使用λ表达式方式，简洁
        return Lists.transform(list, DictVO::new);
    }

    /**
     * 新增一个字典记录
     *
     * @param param 字典记录必要信息
     * @return 是否新增成功
     */
    @Override
    public boolean save(DictDTO param) {
        SysDictEntity entity =   param.asEntity();
        //设置创建人
        entity.setCreateBy(OpenConstant.SUPPER_ADMIN);
        //设置创建时间
        entity.setCreateTime(LocalDateTime.now());
        return this.sysDictMapper.insert(entity) > 0;
    }


    /**
     * 修改数据
     *
     * @param param 字典的实例对象
     * @return 是否更新成功
     */
    @Override
    public boolean update(DictDTO param) {
        SysDictEntity entity = param.asEntity();
        //设置更新人
        entity.setUpdateBy(OpenConstant.SUPPER_ADMIN);
        //设置更新时间
        entity.setUpdateTime(LocalDateTime.now());
        return this.sysDictMapper.update(entity) > 0;
    }

    /**
     * 软删除
     *
     * @param id 主键
     * @return 是否软删除成功
     */
    @Override
    public boolean softDelete(String id) {
        String updUsr = OpenConstant.SUPPER_ADMIN;
        Date updTim = new Date();
        return this.sysDictMapper.updateDelFlagAsTrue(id, updUsr, updTim) > 0;
    }
}