package com.example.dao;

import com.example.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface DtDao {
    //(管理员)定题查看-----
    //分页查询全部已通过定题学生
    List<dt_stu> dt_findPage(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);
    //已通过定题学生总数
    int dt_count();
    //分页查询未处理的定题学生
    List<User> mdt_findPage(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);
    //未处理的定题学生总数
    int mdt_count();

    //(学生)定题申请-----
    //我的全部定题查询
    List<dt> dt(String stuuname);
    //添加定题申请
    int dt_insert(dt dt);
    //删除定题申请
    int dt_delete(Integer id);
    //我发送的定题总数
    int sdt_count(String stuuname);
    //我通过的定题总数
    int sdt_ps(String stuuname);

    //(教师)定题操作-----
    //分页查询全部定题申请
    List<dt> dt_tfindPage(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize, @Param("teaname") String teaname);
    //定题申请总数
    int dt_tfindPage_count(String teaname);
    //通过该定题申请
    int dt_t(Integer id);
    //定题申请失效
    int dt_t_ps(String stuuname);
    //拒绝该定题申请
    int dt_f(Integer id);
}
