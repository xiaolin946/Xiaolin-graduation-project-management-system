package com.example.controller;

import com.example.common.Result;
import com.example.dao.UserDao;
import com.example.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody User user){
        Result result = new Result();
        if(user.getStatus()==null || user.getUsername()==null || user.getPassword()==null){
            return result.error("参数错误");
        }
        if(userDao.login(user.getStatus(), user.getUsername(), user.getPassword())!=null){
            return result.success(userDao.login(user.getStatus(), user.getUsername(), user.getPassword()));
        }else{
            return result.error("密码错误");
        }
    }

    @GetMapping("/findStatusAll/{status}")
    public Result findStatusAll(@PathVariable String status){
        Result result = new Result();
        if(status==null){
            return result.error("参数错误");
        }
        return result.success(userDao.findStatusAll(status));
    }

    @PostMapping("/insert")
    public Result insert(@RequestBody User user){
        Result result = new Result();
        if(user.getStatus()==null || user.getUsername()==null || user.getPassword()==null){
            return result.error("参数错误");
        }
        return result.success(userDao.insert(user)==1);
    }

    @PutMapping("/update")
    public Result update(@RequestBody User user){
        Result result = new Result();
        if(user.getId()==null || user.getUsername()==null){
            return result.error("参数错误");
        }
        return result.success(userDao.update(user)==1);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestParam String status,@RequestParam String username){
        Result result = new Result();
        if(status==null || username==null){
            return result.error("参数错误");
        }
        return result.success(userDao.delete(status, username)==1);
    }

    @GetMapping("/findPage")
    public Result findPage(@RequestParam Integer currentPage,@RequestParam Integer pageSize,@RequestParam String status){
        Result result = new Result();
        if(currentPage==null || pageSize==null || status==null){
            return result.error("参数错误");
        }
        Integer pageNum = (currentPage-1) * pageSize;
        long total = userDao.count(status);
        List<User> users = userDao.findPage(pageNum, pageSize, status);
        Map<String, Object> map = new HashMap<>();
        map.put("data", users);
        map.put("total", total);

        return result.success(map);
    }

    //个人信息----
    @GetMapping("/myxx")
    public Result myxx(@RequestParam String status,@RequestParam String username){
        Result result = new Result();
        if(status==null || username==null){
            return result.error("参数错误");
        }
        return result.success(userDao.myxx(status, username));
    }

    //修改密码----
    @PutMapping("/uppsd")
    public Result uppsd(@RequestBody psd psd){
        Result result = new Result();
        if(psd.getStatus()==null || psd.getUsername()==null || psd.getPassword()==null || psd.getNew_password()==null || psd.getNew_password2()==null){
            return result.error("参数错误");
        }
        return result.success(userDao.uppsd(psd.getStatus(), psd.getUsername(), psd.getNew_password2())==1);
    }
    @GetMapping("/mypsd")
    public Result mypsd(@RequestParam String status,@RequestParam String username){
        Result result = new Result();
        if(status==null || username==null){
            return result.error("参数错误");
        }
        return result.success(userDao.mypsd(status, username));
    }

}
