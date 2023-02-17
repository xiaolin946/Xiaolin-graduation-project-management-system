package com.example.dao;

import com.example.entity.news;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NewsDao {
    //(管理员)消息-----
    //分页查询全部消息
    List<news> news_findPage(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);
    //全部消息总数
    int news_count();
    //添加新消息
    int news_insert(news news);
    //更新消息
    int news_update(news news);
    //删除消息
    int news_delete(@Param("xid") Integer xid);

    //条件接收消息-----
    //分页查询该用户类型的全部消息
    List<news> snews_findPage(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize, @Param("status") String status);
    //该用户类型的消息总数
    int snews_count(String status);

    //(管理员、教师)发送的消息-----
    //分页查询该用户发送的全部消息
    List<news> mynews_findPage(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize, @Param("puser") String puser);
    //该用户发送的消息总数
    int mynews_findPage_count(String puser);
}
