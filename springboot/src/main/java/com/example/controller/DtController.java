package com.example.controller;

import com.example.common.Result;
import com.example.dao.DtDao;
import com.example.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/Dt")
public class DtController {

    @Autowired
    private DtDao dtDao;

    //定题-----
    @GetMapping("/dt_findPage")
    public Result dt_findPage(@RequestParam Integer currentPage,@RequestParam Integer pageSize){
        Result result = new Result();
        if(currentPage==null || pageSize==null){
            return result.error("参数错误");
        }
        Integer pageNum = (currentPage-1) * pageSize;
        long total = dtDao.dt_count();
        List<dt_stu> dts = dtDao.dt_findPage(pageNum, pageSize);
        Map<String, Object> map = new HashMap<>();
        map.put("data", dts);
        map.put("total", total);

        return result.success(map);
    }
    @GetMapping("/mdt_findPage")
    public Result mdt_findPage(@RequestParam Integer currentPage,@RequestParam Integer pageSize){
        Result result = new Result();
        if(currentPage==null || pageSize==null){
            return result.error("参数错误");
        }
        Integer pageNum = (currentPage-1) * pageSize;
        long total = dtDao.mdt_count();
        List<User> mdts = dtDao.mdt_findPage(pageNum, pageSize);
        Map<String, Object> map = new HashMap<>();
        map.put("data", mdts);
        map.put("total", total);

        return result.success(map);
    }

    //定题申请----
    @GetMapping("/dt")
    public Result dt(@RequestParam String stuuname){
        Result result = new Result();
        if(stuuname==null){
            return result.error("参数错误");
        }
        return result.success(dtDao.dt(stuuname));
    }
    @PutMapping("/dt_insert")
    public Result dt_insert(@RequestBody dt dt){
        Result result = new Result();
        if(dt.getStuuname()==null || dt.getTid()==null){
            return result.error("参数错误");
        }
        return result.success(dtDao.dt_insert(dt)==1);
    }
    @DeleteMapping("/dt_delete")
    public Result dt_delete(@RequestParam Integer id){
        Result result = new Result();
        if(id==null){
            return result.error("参数错误");
        }
        return result.success(dtDao.dt_delete(id)==1);
    }
    @GetMapping("/sdt_count")
    public Result sdt_count(@RequestParam String stuuname){
        Result result = new Result();
        if(stuuname==null){
            return result.error("参数错误");
        }
        return result.success(dtDao.sdt_count(stuuname));
    }
    @GetMapping("/sdt_ps")
    public Result sdt_ps(@RequestParam String stuuname){
        Result result = new Result();
        if(stuuname==null){
            return result.error("参数错误");
        }
        return result.success(dtDao.sdt_ps(stuuname));
    }

    //定题操作----
    @GetMapping("/dt_tfindPage")
    public Result dt_tfindPage(@RequestParam Integer currentPage,@RequestParam Integer pageSize, @RequestParam String teaname){
        Result result = new Result();
        if(currentPage==null || pageSize==null || teaname==null){
            return result.error("参数错误");
        }
        Integer pageNum = (currentPage-1) * pageSize;
        long total = dtDao.dt_tfindPage_count(teaname);
        List<dt> dts = dtDao.dt_tfindPage(pageNum, pageSize, teaname);
        Map<String, Object> map = new HashMap<>();
        map.put("data", dts);
        map.put("total", total);

        return result.success(map);
    }
    @PutMapping("/dt_t")
    public Result dt_t(@RequestBody dt dt){
        Result result = new Result();
        if(dt.getId()==null || dt.getStuuname()==null || dt.getTid()==null){
            return result.error("参数错误");
        }
        return result.success(dtDao.dt_t(dt.getId())!=0 && dtDao.dt_t_ps(dt.getStuuname())!=0);
    }
    @PutMapping("/dt_f")
    public Result dt_f(@RequestBody dt dt){
        Result result = new Result();
        if(dt.getId()==null || dt.getStuuname()==null || dt.getTid()==null){
            return result.error("参数错误");
        }
        return result.success(dtDao.dt_f(dt.getId())==1);
    }

}
