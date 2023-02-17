package com.example.dao;

import com.example.entity.kt;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface KtDao {
    //查询指定类型数据个数-----
    int count(String status);

    //(ALL)分页查询全部课题-----
    List<kt> kt_findPage(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);

    //(学生)可选课题列表----
    List<kt> l_tid(String stuuname);

    //(学生)单课题消息(组合用)----
    kt kt(String tid);

    //(教师)我的课题----
    //分页查询教师的全部课题
    List<kt> mykt_findPage(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize, @Param("teaname") String teaname);
    //教师课题总数
    int mykt_count(String teaname);
    //添加课题
    int mykt_insert(kt kt);
    //更新课题
    int mykt_update(kt kt);
    //删除课题
    int mykt_delete(Integer id);
}
