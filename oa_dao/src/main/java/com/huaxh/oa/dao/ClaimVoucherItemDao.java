package com.huaxh.oa.dao;

import com.huaxh.oa.entity.ClaimVoucherItem;
import org.springframework.stereotype.Repository;

import java.util.List;

// 报销单条目
@Repository
public interface ClaimVoucherItemDao {
    void insert(ClaimVoucherItem claimVoucherItem);

    void update(ClaimVoucherItem claimVoucherItem);

    void delete(int id);

    List<ClaimVoucherItem> selectByClaimVoucher(int cvid);
}
