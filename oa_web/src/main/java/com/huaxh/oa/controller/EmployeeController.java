package com.huaxh.oa.controller;

import com.huaxh.oa.biz.DepartmentBiz;
import com.huaxh.oa.biz.EmployeeBiz;
import com.huaxh.oa.entity.Department;
import com.huaxh.oa.entity.Employee;
import com.huaxh.oa.global.Contant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private DepartmentBiz departmentBiz;

    @Autowired
    private EmployeeBiz employeeBiz;

    //把所有的部门信息给用户传递
    @RequestMapping("/list")
    public String list(Map<String, Object> map) {
        map.put("list", employeeBiz.getAll());
        return "employee_list";
    }

    // 去添加
    @RequestMapping("/to_add")
    public String toAdd(Map<String, Object> map) {
        map.put("employee", new Employee());
        // 添加员工时需要部门信息 所以需要把部门信息添加到map中
        map.put("dlist", departmentBiz.getAll());
        // 需要职位
        map.put("plist", Contant.getPost());
        return "employee_add";
    }

    @RequestMapping("/add")
    public String add(Employee employee) {
        employeeBiz.add(employee);
        return "redirect:list";
    }

    /*必须以sn为键 传递一个参数*/
    @RequestMapping(value = "/to_update", params = "sn")
    public String toUpdate(String sn, Map<String, Object> map) {
        map.put("employee", employeeBiz.get(sn));
        // 添加员工时需要部门信息 所以需要把部门信息添加到map中
        map.put("dlist", departmentBiz.getAll());
        // 需要职位
        map.put("plist", Contant.getPost());
        return "employee_update";
    }

    @RequestMapping("/update")
    public String update(Employee employee) {
        employeeBiz.edit(employee);
        return "redirect:list";
    }


    @RequestMapping(value = "/remove", params = "sn")
    public String remove(String sn) {
        employeeBiz.remove(sn);
        return "redirect:list";
    }
}
