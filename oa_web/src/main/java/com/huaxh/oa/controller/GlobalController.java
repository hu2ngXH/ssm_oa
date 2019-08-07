package com.huaxh.oa.controller;

import com.huaxh.oa.biz.GlobalBiz;
import com.huaxh.oa.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class GlobalController {

    @Autowired
    private GlobalBiz globalBiz;


    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }


    @RequestMapping("/login")
    //@RequestParam 表示这是用户提交过来的信息 多个的情况使用 参数名必须和前端传回来的一致
    public String login(HttpSession session, @RequestParam String sn, @RequestParam String password) {
        Employee employee = globalBiz.login(sn, password);
        if (employee == null) {
            return "redirect:to_login";
        }
        session.setAttribute("employee", employee);
        return "redirect:self";
    }

    @RequestMapping("/self")
    public String self() {
        return "self";
    }

    @RequestMapping("/quit")
    public String quit(HttpSession session) {
        session.setAttribute("employee", null);
        return "redirect:self";
    }

    @RequestMapping("/to_change_password")
    public String toChangePassword() {
        return "change_password";
    }

    @RequestMapping("/changePassword")
    public String changePassword(HttpSession session, @RequestParam String old, @RequestParam String new1, @RequestParam String new2) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee.getPassword().equals(old)) {
            if (new1.equals(new2)) {
                employee.setPassword(new1);
                globalBiz.changePassword(employee);
                return "redirect:self";
            }
        }
        return "redirect:to_change_password";
    }
}
