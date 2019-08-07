package com.huaxh.oa.global;

import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;

/**
 * 常量类 用于包含常量
 */
public class Contant {
    // 职务
    public static final String POST_STAFF = "员工";
    public static final String POST_FM = "经理";
    public static final String POST_GM = "总经理";
    public static final String POST_CASHIER = "财务";

    public static List<String> getPost() {
        List<String> list = new ArrayList<String>();
        list.add(POST_STAFF);
        list.add(POST_FM);
        list.add(POST_GM);
        list.add(POST_CASHIER);
        return list;
    }

    //费用类别 不需要判断 直接返回
    public static List<String> getItem() {
        List<String> list = new ArrayList<String>();
        list.add("交通");
        list.add("餐饮");
        list.add("住宿");
        list.add("办公");
        return list;
    }

    // 报销单状态
    public static final String CLAIMVOURCHER_CREATE = "新创建";
    public static final String CLAIMVOURCHER_SUBMIT = "已提交";
    public static final String CLAIMVOURCHER_APPROVED = "已审核";
    public static final String CLAIMVOURCHER_BACK = "已打回";
    public static final String CLAIMVOURCHER_TERMINATED = "已终止";
    public static final String CLAIMVOURCHER_RECHECK = "待复审";
    public static final String CLAIMVOURCHER_PAID = "已打款";

    // 审核额度 可以放到配置文件中
    @Value("${limit}")
    public static double LIMIT;

    //报销单的处理方式
    public static final String DEAL_CREATE = "创建";
    public static final String DEAL_SUBMIT = "提交";
    public static final String DEAL_UPDATE = "修改";
    public static final String DEAL_BACK = "打回";
    public static final String DEAL_REJECT = "拒绝";
    public static final String DEAL_PASS = "通过";
    public static final String DEAL_PAID = "打款";
}
