package com.micro.bbqentry.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.google.common.collect.Lists;
import com.micro.bbqentry.general.constant.OpenConstant;
import com.micro.bbqentry.model.entity.SysDict;
import com.micro.bbqentry.model.param.DictDTO;
import com.micro.bbqentry.model.param.DictVO;
import com.micro.bbqentry.repository.SysDictMapper;
import com.micro.bbqentry.service.ISysDictService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
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
    public SysDict queryById(String id) {
        return this.sysDictMapper.selectById(id);
    }


    /**
     * 通过字典类型查询字典集合
     *
     * @param type 字典类型
     * @return 字典的实例对象集合
     */
    @Override
    public List<DictVO> getDictsByType(String type) {
        LambdaQueryWrapper<SysDict> queryWrapper=new LambdaQueryWrapper();
        queryWrapper.eq(SysDict::getType,type);
        List<SysDict> list = sysDictMapper.selectList(queryWrapper);
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
        SysDict entity =   param.asEntity();
        entity.setCreateBy(OpenConstant.SUPPER_ADMIN);
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
        SysDict entity = param.asEntity();
        entity.setUpdateBy(OpenConstant.SUPPER_ADMIN);
        entity.setUpdateTime(LocalDateTime.now());
        return this.sysDictMapper.updateById(entity) > 0;
    }

    /**
     * 软删除
     *
     * @param id 主键
     * @return 是否软删除成功
     */
    @Override
    public boolean softDelete(String id) {
        LambdaUpdateWrapper<SysDict> updateWrapper=new LambdaUpdateWrapper<>();
        updateWrapper.set(SysDict::getDelFlag,OpenConstant.DELETED_YES)
                .set(SysDict::getUpdateBy, OpenConstant.SUPPER_ADMIN)
                .set(SysDict::getUpdateTime,LocalDateTime.now())
                .eq(SysDict::getId,id);
        return this.sysDictMapper.update(null,updateWrapper) > 0;
    }
}