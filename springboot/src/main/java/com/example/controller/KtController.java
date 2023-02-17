package com.example.controller;

import com.example.common.Result;
import com.example.dao.KtDao;
import com.example.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Kt")
public class KtController {

    @Autowired
    private KtDao ktDao;

    //课题----
    @GetMapping("/kt_findPage")
    public Result kt_findPage(@RequestParam Integer currentPage,@RequestParam Integer pageSize,@RequestParam String status){
        Result result = new Result();
        if(currentPage==null || pageSize==null || status==null){
            return result.error("参数错误");
        }
        Integer pageNum = (currentPage-1) * pageSize;
        long total = ktDao.count(status);
        List<kt> kts = ktDao.kt_findPage(pageNum, pageSize);
        Map<String, Object> map = new HashMap<>();
        map.put("data", kts);
        map.put("total", total);

        return result.success(map);
    }

    //单课题----
    @GetMapping("/kt")
    public Result kt(@RequestParam String tid){
        Result result = new Result();
        if(tid==null){
            return result.error("参数错误");
        }
        return result.success(ktDao.kt(tid));
    }

    //课题列表----
    @GetMapping("l_tid")
    public Result l_tid(@RequestParam String stuuname){
        Result result = new Result();
        if(stuuname==null){
            return result.error("参数错误");
        }
        return result.success(ktDao.l_tid(stuuname));
    }

    //我的课题----
    @GetMapping("/mykt_findPage")
    public Result mykt_findPage(@RequestParam Integer currentPage,@RequestParam Integer pageSize, @RequestParam String teaname){
        Result result = new Result();
        if(currentPage==null || pageSize==null){
            return result.error("参数错误");
        }
        Integer pageNum = (currentPage-1) * pageSize;
        long total = ktDao.mykt_count(teaname);
        List<kt> kts = ktDao.mykt_findPage(pageNum, pageSize, teaname);
        Map<String, Object> map = new HashMap<>();
        map.put("data", kts);
        map.put("total", total);

        return result.success(map);
    }
    @PostMapping("/mykt_insert")
    public Result mykt_insert(@RequestBody kt kt){
        Result result = new Result();
        if(kt.getTid()==null || kt.getTeaname()==null || kt.getTname()==null || kt.getTt()==null || kt.getTtc()==null){
            return result.error("参数错误");
        }
        return result.success(ktDao.mykt_insert(kt)==1);
    }
    @PutMapping("/mykt_update")
    public Result mykt_update(@RequestBody kt kt){
        Result result = new Result();
        if(kt.getId()==null || kt.getTid()==null || kt.getTeaname()==null || kt.getTname()==null || kt.getTt()==null || kt.getTtc()==null){
            return result.error("参数错误");
        }
        return result.success(ktDao.mykt_update(kt)==1);
    }
    @DeleteMapping("/mykt_delete")
    public Result mykt_delete(@RequestParam Integer id){
        Result result = new Result();
        if(id==null){
            return result.error("参数错误");
        }
        return result.success(ktDao.mykt_delete(id)==1);
    }

}
