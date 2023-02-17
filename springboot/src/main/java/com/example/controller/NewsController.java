package com.example.controller;

import com.example.common.Result;
import com.example.dao.NewsDao;
import com.example.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/News")
public class NewsController {

    @Autowired
    private NewsDao newsDao;

    //消息----
    @GetMapping("/news_findPage")
    public Result news_findPage(@RequestParam Integer currentPage,@RequestParam Integer pageSize){
        Result result = new Result();
        if(currentPage==null || pageSize==null){
            return result.error("参数错误");
        }
        Integer pageNum = (currentPage-1) * pageSize;
        long total = newsDao.news_count();
        List<news> news = newsDao.news_findPage(pageNum, pageSize);
        Map<String, Object> map = new HashMap<>();
        map.put("data", news);
        map.put("total", total);

        return result.success(map);
    }
    @PostMapping("/news_insert")
    public Result news_insert(@RequestBody news news){
        Result result = new Result();
        if(news.getXstatus()==null || news.getPuser()==null || news.getGuser()==null || news.getTitle()==null){
            return result.error("参数错误");
        }
        return result.success(newsDao.news_insert(news)==1);
    }
    @PutMapping("/news_update")
    public Result news_update(@RequestBody news news){
        Result result = new Result();
        if(news.getXstatus()==null || news.getPuser()==null || news.getGuser()==null || news.getTitle()==null){
            return result.error("参数错误");
        }
        return result.success(newsDao.news_update(news)==1);
    }
    @DeleteMapping("/news_delete")
    public Result news_delete(@RequestParam Integer xid){
        Result result = new Result();
        if(xid==null){
            return result.error("参数错误");
        }
        return result.success(newsDao.news_delete(xid)==1);
    }

    //条件消息----
    @GetMapping("/snews_findPage")
    public Result snews_findPage(@RequestParam Integer currentPage,@RequestParam Integer pageSize,@RequestParam String status){
        Result result = new Result();
        if(currentPage==null || pageSize==null || status==null){
            return result.error("参数错误");
        }
        Integer pageNum = (currentPage-1) * pageSize;
        long total = newsDao.snews_count(status);
        List<news> news = newsDao.snews_findPage(pageNum, pageSize, status);
        Map<String, Object> map = new HashMap<>();
        map.put("data", news);
        map.put("total", total);

        return result.success(map);
    }

    //我的消息----
    @GetMapping("/mynews_findPage")
    public Result mynews_findPage(@RequestParam Integer currentPage,@RequestParam Integer pageSize, @RequestParam String puser){
        Result result = new Result();
        if(currentPage==null || pageSize==null || puser==null){
            return result.error("参数错误");
        }
        Integer pageNum = (currentPage-1) * pageSize;
        long total = newsDao.mynews_findPage_count(puser);
        List<news> news = newsDao.mynews_findPage(pageNum, pageSize, puser);
        Map<String, Object> map = new HashMap<>();
        map.put("data", news);
        map.put("total", total);

        return result.success(map);
    }
}
