package com.huaxh.oa.biz;

import com.huaxh.oa.entity.ClaimVoucher;
import com.huaxh.oa.entity.ClaimVoucherItem;
import com.huaxh.oa.entity.DealRecord;

import java.util.List;

public interface ClaimVoucherBiz {
    // 保存报销单 报销单 报销单明细
    void save(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items);

    // 获取报销单
    ClaimVoucher get(int id);

    // 报销单明细
    List<ClaimVoucherItem> getItems(int cvid);

    // 获取报销单处理明细
    List<DealRecord> getRecords(int cvid);

    // 通过个人的角色 获取报销单
    List<ClaimVoucher> getForSelf(String sn);

    // 通过处理人的报销单
    List<ClaimVoucher> getForDeal(String sn);

    // 更新
    void update(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items);

    // 提交
    void submit(int id);

    // 处理
    void deal(DealRecord dealRecord);
}
