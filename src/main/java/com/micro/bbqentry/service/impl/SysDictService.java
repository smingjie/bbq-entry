package com.micro.bbqentry.service.impl;

import com.google.common.collect.Lists;
import com.micro.bbqentry.general.constant.OpenConstant;
import com.micro.bbqentry.model.entity.SysDict;
import com.micro.bbqentry.model.param.DictDTO;
import com.micro.bbqentry.model.param.DictVO;
import com.micro.bbqentry.repository.SysDictMapper;
import com.micro.bbqentry.service.ISysDictService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 数据字典服务实现类
 *
 * @author makejava
 * @since 2020-02-03 22:34:26
 */
@Service
public class SysDictService implements ISysDictService {
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
        List<SysDict> list = sysDictMapper.queryByType(type);
        //集合转换，不用重新开辟内存空间(优秀的Google啊)
        // 推荐使用λ表达式方式，简洁
        List<DictVO> dicts = Lists.transform(list, o -> DictVO.phaseByEntity(o));

//        List<DictVO> dicts=Lists.transform(list, new Function<SysDict, DictVO>() {
//            @Override
//            public DictVO apply(SysDict sysDict) {
//                return DictVO.phaseByEntity(sysDict);
//            }
//        });

        return dicts;
    }

    /**
     * 新增一个字典记录
     *
     * @param param 字典记录必要信息
     * @return 是否新增成功
     */
    @Override
    public boolean save(DictDTO param) {
        SysDict entity = DictDTO.convertIntoEntity(param);
        //设置创建人
        entity.setCreatedBy(OpenConstant.SUPPER_ADMIN);
        //设置创建时间
        entity.setCreatedTime(new Date());
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
        SysDict entity = DictDTO.convertIntoEntity(param);
        //设置更新人
        entity.setUpdatedBy(OpenConstant.SUPPER_ADMIN);
        //设置更新时间
        entity.setUpdatedTime(new Date());
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
//
//    /**
//     * 通过主键删除数据
//     *
//     * @param id 主键
//     * @return 是否成功
//     */
//    @Override
//    public boolean deleteById(String id) {
//        return this.sysDictMapper.deleteById(id) > 0;
//    }
}