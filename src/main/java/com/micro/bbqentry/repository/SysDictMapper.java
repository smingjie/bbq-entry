package com.micro.bbqentry.repository;

import com.micro.bbqentry.model.entity.SysDict;
import com.micro.bbqentry.repository.sqlprovider.SysDictSqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * 数据字典表(SysDict)表数据库访问层
 *
 * @author makejava
 * @since 2020-02-03 22:34:24
 */
@Mapper
public interface SysDictMapper {

    /**
     * 通过主键id查询单条数据
     */
    @Select("select * from sys_dict where id=#{id} and del_flag=0")
    SysDict queryById(@Param("id") String id);

    /**
     * 通过字典类型查询字典集合
     */
    @Select("select * from sys_dict where type=#{type} and del_flag=0")
    List<SysDict> queryByType(@Param("type") String type);

    /**
     * 新增数据
     */
    @InsertProvider(type = SysDictSqlProvider.class, method = "insertEntity")
    int insert(SysDict entity);

    /**
     * 修改数据
     */
    @UpdateProvider(type = SysDictSqlProvider.class, method = "updateEntity")
    int update(SysDict entity);

    /**
     * 软删除，通过更新删除标记位为 -1
     */
//    @UpdateProvider(type = SysDictSqlProvider.class, method = "softDeleteEntity")
    @Update("update sys_dict " +
            "set del_flag=-1,updated_by=#{updatedBy},updated_time=#{updatedTime} " +
            "where id=#{id}")
    int updateDelFlagAsTrue(@Param("id") String id,
                            @Param("updatedBy")String updatedBy,
                            @Param("updatedTime") Date updatedTime
                            );

    /**
     * 删除数据：通过主键id
     */
    @Delete("delete * from sys_dict where id=#{id}")
    int deleteById(@Param("id")String id);

}