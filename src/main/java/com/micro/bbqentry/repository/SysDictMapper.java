package com.micro.bbqentry.repository;

import com.micro.bbqentry.model.entity.SysDictEntity;
import com.micro.bbqentry.repository.sqlprovider.SysDictSqlProvider;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

/**
 * 数据字典表(SysDictEntity)表数据库访问层
 *
 * @author makejava
 * @since 2020-02-03 22:34:24
 */
public interface SysDictMapper extends Mapper<SysDictEntity> {

    /**
     * 通过主键id查询单条数据
     */
    @Select("select * from sys_dict where id=#{id} and del_flag=0")
    SysDictEntity queryById(@Param("id") String id);

    /**
     * 通过字典类型查询字典集合
     */
    @Select("select * from sys_dict where type=#{type} and del_flag=0")
    List<SysDictEntity> queryByType(@Param("type") String type);

    /**
     * 新增数据
     */
    @InsertProvider(type = SysDictSqlProvider.class, method = "insertEntity")
    int insert(SysDictEntity entity);

    /**
     * 修改数据
     */
    @UpdateProvider(type = SysDictSqlProvider.class, method = "updateEntity")
    int update(SysDictEntity entity);

    /**
     * 软删除，通过更新删除标记位为 -1
     */
    @Update("update sys_dict " +
            "set del_flag=-1,update_by=#{updateBy},update_time=#{updateTime} " +
            "where id=#{id}")
    int updateDelFlagAsTrue(@Param("id") String id,
                            @Param("updateBy")String updateBy,
                            @Param("updateTime") Date updateTime
                            );

    /**
     * 删除数据：通过主键id
     */
    @Delete("delete * from sys_dict where id=#{id}")
    int deleteById(@Param("id")String id);

}