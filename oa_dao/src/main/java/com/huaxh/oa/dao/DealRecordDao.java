package com.huaxh.oa.dao;

import com.huaxh.oa.entity.DealRecord;

import java.util.List;

public interface DealRecordDao {
    void insert(DealRecord dealRecord);

    List<DealRecord> selectByClaimVoucher(int cvid);
}
