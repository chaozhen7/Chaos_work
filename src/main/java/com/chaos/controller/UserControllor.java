package com.chaos.controller;

import com.chaos.model.User;
import com.chaos.model.response.UserResult;
import com.chaos.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by sina on 16/8/1.
 */

@Controller
@RequestMapping("/user")
public class UserControllor {

    @Resource
    private UserService userService;

    //为自动绑定数据准备model__ user
    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("user", new User());
        return "user/add_user";
    }

    //自动绑定数据user
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String addautobind(User user, HttpServletRequest request) {
        int user_id = userService.insertUser(user);
        if (user_id != 0) {
            return "redirect:show?id=" + user.getId();
        }
        return "user/add_user";
    }

    //非自动绑定，需要使用注解
    @RequestMapping(value = "addnotauto", method = RequestMethod.POST)
    public String addUser(@Validated User user, BindingResult br, HttpServletRequest request) {
        if (br.hasErrors()) {
            return "user/add_user";
        }
        int user_id = userService.insertUser(user);
        if (user_id != 0) {
            return "redirect:show/" + user.getId();
        } else
            return "user/add";
    }

    @RequestMapping("show/{id}")
    public String showUser(@PathVariable("id") Integer id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "user/show";
    }

    @RequestMapping("userlist")
    public String list(@RequestParam(value = "page", required = false) Integer pageNo,
                       @RequestParam(value = "u", required = false) String userName, Model model) {
        PageInfo<User> pages = userService.queryByPage(userName, pageNo, null);
        List<User> users = pages.getList();
        model.addAttribute("userlist", users);
        return "user/userlist";
    }

    @RequestMapping("del/{id}")
    @ResponseBody
    public UserResult del(@PathVariable("id") Integer id){
        boolean flag = userService.deleteById(id);
        UserResult result = new UserResult();
        if (flag){
            result.setCode(100000);
            result.setMsg("删除成功");
        }
        else{
            result.setCode(100001);
            result.setMsg("删除失败");
        }

        return result;
    }

}
