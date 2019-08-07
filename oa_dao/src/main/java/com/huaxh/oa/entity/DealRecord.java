package com.huaxh.oa.entity;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 处理明细
 */
public class DealRecord {
    // 处理明细
    private Integer id;
    // 报销单编号
    private Integer claimVoucherId;
    // 处理人的编号
    private String dealSn;

    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    private Date dealTime;

    // 处理方式
    private String dealway;
    // 处理结果
    private String dealResult;
    // 评论
    private String comment;
    // 处理人
    private Employee dealer;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClaimVoucherId() {
        return claimVoucherId;
    }

    public void setClaimVoucherId(Integer claimVoucherId) {
        this.claimVoucherId = claimVoucherId;
    }

    public String getDealSn() {
        return dealSn;
    }

    public void setDealSn(String dealSn) {
        this.dealSn = dealSn;
    }

    public Date getDealTime() {
        return dealTime;
    }

    public void setDealTime(Date dealTime) {
        this.dealTime = dealTime;
    }

    public String getDealway() {
        return dealway;
    }

    public void setDealway(String dealway) {
        this.dealway = dealway;
    }

    public String getDealResult() {
        return dealResult;
    }

    public void setDealResult(String dealResult) {
        this.dealResult = dealResult;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Employee getDealer() {
        return dealer;
    }

    public void setDealer(Employee dealer) {
        this.dealer = dealer;
    }
}
