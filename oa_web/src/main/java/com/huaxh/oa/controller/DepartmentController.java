package com.huaxh.oa.controller;

import com.huaxh.oa.biz.DepartmentBiz;
import com.huaxh.oa.entity.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentBiz departmentBiz;

    //把所有的部门信息给用户传递
    @RequestMapping("/list")
    public String list(Map<String, Object> map) {
        map.put("list", departmentBiz.getAll());
        return "department_list";
    }

    // 去添加
    @RequestMapping("/to_add")
    public String toAdd(Map<String, Object> map) {
        map.put("department", new Department());
        return "department_add";
    }

    @RequestMapping("/add")
    public String add(Department department) {
        departmentBiz.add(department);
        return "redirect:list";
    }

    /*必须以sn为键 传递一个参数*/
    @RequestMapping(value = "/to_update", params = "sn")
    public String toUpdate(String sn, Map<String, Object> map) {
        map.put("department", departmentBiz.get(sn));
        return "department_update";
    }

    @RequestMapping("/update")
    public String update(Department department) {
        departmentBiz.edit(department);
        return "redirect:list";
    }


    @RequestMapping(value = "/remove", params = "sn")
    public String remove(String sn) {
        departmentBiz.remove(sn);
        return "redirect:list";
    }
}
