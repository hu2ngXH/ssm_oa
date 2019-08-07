package com.huaxh.oa.dao;

import com.huaxh.oa.entity.ClaimVoucher;

import java.util.List;

//报销单
public interface ClaimVoucherDao {
    void insert(ClaimVoucher claimVoucher);

    void update(ClaimVoucher claimVoucher);

    void delete(int id);

    ClaimVoucher select(int id);

    // 通过创建人查找报销单
    List<ClaimVoucher> selectByCreateSn(String csn);

    //查询能够处理的报销单
    List<ClaimVoucher> selectByNextDealSn(String dsn);
}
