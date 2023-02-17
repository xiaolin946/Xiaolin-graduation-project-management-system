package com.example.dao;

import com.example.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface UserDao {

    //登录验证-----
    User login(@Param("status") String status, @Param("username") String username, @Param("password") String password);

    //查询指定类型用户-----
    List<User> findStatusAll(String status);

    //用户增、删、更-----
    int insert(User user);
    int update(User user);
    int delete(@Param("status") String status,@Param("username") String username);

    //分页查询指定类型用户-----
    List<User> findPage(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize, @Param("status") String status);
    //查询指定类型用户个数
    int count(String status);

    //分页查询课题
    List<kt> kt_findPage(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);

    //定题
    List<dt_stu> dt_findPage(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);
    int dt_count();
    List<User> mdt_findPage(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);
    int mdt_count();

    //消息
    List<news> news_findPage(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize);
    int news_count();
    int news_insert(news news);
    int news_update(news news);
    int news_delete(@Param("xid") Integer xid);

    //个人信息
    User myxx(@Param("status") String status, @Param("username") String username);

    //修改密码
    int uppsd(@Param("status") String status, @Param("username") String username, @Param("password") String password);
    String mypsd(@Param("status") String status, @Param("username") String username);

    //定题申请
    List<dt> dt(String stuuname);
    int dt_insert(dt dt);
    int dt_delete(Integer id);
    int sdt_count(String stuuname);
    int sdt_ps(String stuuname);

    //课题列表
    List<kt> l_tid(String stuuname);

    //单课题
    kt kt(String tid);

    //条件消息
    List<news> snews_findPage(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize, @Param("status") String status);
    int snews_count(String status);

    //我的课题
    List<kt> mykt_findPage(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize, @Param("teaname") String teaname);
    int mykt_count(String teaname);
    int mykt_insert(kt kt);
    int mykt_update(kt kt);
    int mykt_delete(Integer id);

    //定题操作
    List<dt> dt_tfindPage(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize, @Param("teaname") String teaname);
    int dt_tfindPage_count(String teaname);
    int dt_t(Integer id);
    int dt_t_ps(String stuuname);
    int dt_f(Integer id);

    //(管理员、教师)发送的消息-----
    List<news> mynews_findPage(@Param("pageNum") Integer pageNum, @Param("pageSize") Integer pageSize, @Param("puser") String puser);
    int mynews_findPage_count(String puser);

}
