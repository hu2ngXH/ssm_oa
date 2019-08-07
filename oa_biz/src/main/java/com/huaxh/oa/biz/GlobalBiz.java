package com.huaxh.oa.biz;

import com.huaxh.oa.entity.Employee;

public interface GlobalBiz {
    Employee login(String sn, String password);
    // session的操作一般在表现层 而不在业务层

    //为了表现层方便 使用对象
    void changePassword(Employee employee);
}
