package com.huaxh.oa.biz.impl;

import com.huaxh.oa.biz.EmployeeBiz;
import com.huaxh.oa.dao.EmployeeDao;
import com.huaxh.oa.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeBizImpl implements EmployeeBiz {
    @Autowired
    private EmployeeDao employeeDao;

    public void add(Employee employee) {
        /*有一个业务规则 业务层有它该有的代码 创建用户的时候初始化密码*/
        employee.setPassword("000000");
        employeeDao.insert(employee);
    }

    public void edit(Employee employee) {
        employeeDao.update(employee);
    }

    public void remove(String sn) {
        employeeDao.delete(sn);
    }

    public Employee get(String sn) {
        return employeeDao.select(sn);
    }

    public List<Employee> getAll() {
        return employeeDao.selectAll();
    }
}
