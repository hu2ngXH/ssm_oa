package com.huaxh.oa.biz.impl;

import com.huaxh.oa.biz.ClaimVoucherBiz;
import com.huaxh.oa.dao.ClaimVoucherDao;
import com.huaxh.oa.dao.ClaimVoucherItemDao;
import com.huaxh.oa.dao.DealRecordDao;
import com.huaxh.oa.dao.EmployeeDao;
import com.huaxh.oa.entity.ClaimVoucher;
import com.huaxh.oa.entity.ClaimVoucherItem;
import com.huaxh.oa.entity.DealRecord;
import com.huaxh.oa.entity.Employee;
import com.huaxh.oa.global.Contant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class ClaimVoucherBizImpl implements ClaimVoucherBiz {
    @Autowired
    private ClaimVoucherDao claimVoucherDao;
    @Autowired
    private ClaimVoucherItemDao claimVoucherItemDao;
    @Autowired
    private DealRecordDao dealRecordDao;

    public void save(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items) {
        claimVoucher.setCreateTime(new Date());
        claimVoucher.setNextDealSn(claimVoucher.getCreateSn());
        claimVoucher.setStatus(Contant.CLAIMVOURCHER_CREATE);
        claimVoucherDao.insert(claimVoucher);
        //保存
        for (ClaimVoucherItem item : items) {
            item.setClaimVoucherId(claimVoucher.getId());
            claimVoucherItemDao.insert(item);
        }
    }

    public ClaimVoucher get(int id) {
        return claimVoucherDao.select(id);
    }

    public List<ClaimVoucherItem> getItems(int cvid) {
        return claimVoucherItemDao.selectByClaimVoucher(cvid);
    }

    public List<DealRecord> getRecords(int cvid) {
        return dealRecordDao.selectByClaimVoucher(cvid);
    }

    public List<ClaimVoucher> getForSelf(String sn) {
        return claimVoucherDao.selectByCreateSn(sn);
    }

    public List<ClaimVoucher> getForDeal(String sn) {
        return claimVoucherDao.selectByNextDealSn(sn);
    }

    public void update(ClaimVoucher claimVoucher, List<ClaimVoucherItem> items) {
        claimVoucher.setNextDealSn(claimVoucher.getCreateSn());
        claimVoucher.setStatus(Contant.CLAIMVOURCHER_CREATE);
        claimVoucherDao.update(claimVoucher);

        // 首先应该获取当前数据库中已经有的条目信息
        List<ClaimVoucherItem> olds = claimVoucherItemDao.selectByClaimVoucher(claimVoucher.getId());

        //迭代集合
        for (ClaimVoucherItem old : olds) {
            boolean isHave = false;
            for (ClaimVoucherItem item : items) {
                if (item.getId() == old.getId()) {
                    isHave = true;
                    break;
                }
            }
            if (!isHave) {
                claimVoucherItemDao.delete(old.getId());
            }
        }
        for (ClaimVoucherItem item : items) {
            item.setClaimVoucherId(claimVoucher.getId());
            if (item.getId() != null && item.getId() > 0) {
                claimVoucherItemDao.update(item);
            } else {
                claimVoucherItemDao.insert(item);
            }
        }
    }

    @Autowired
    private EmployeeDao employeeDao;

    // 提交申请
    public void submit(int id) {
        // 首先找到那个id的记录
        ClaimVoucher claimVoucher = claimVoucherDao.select(id);
        // 找到创建者
        Employee employee = employeeDao.select(claimVoucher.getCreateSn());
        // 设置状态为提交
        claimVoucher.setStatus(Contant.CLAIMVOURCHER_SUBMIT);
        // 设置下一个处理人 找到经理
        claimVoucher.setNextDealSn(employeeDao.selectByDepartmentAndPost(employee.getDepartmentSn(), Contant.POST_FM).get(0).getSn());
        // 然后更新处理单
        claimVoucherDao.update(claimVoucher);

        // 创建一个处理记录
        DealRecord dealRecord = new DealRecord();
        // 设置处理方式
        dealRecord.setDealway(Contant.DEAL_SUBMIT);
        // 设置处理人 就是当前的处理人
        dealRecord.setDealSn(employee.getSn());
        // 设置报销单
        dealRecord.setClaimVoucherId(id);
        // 处理方式 为提交
        dealRecord.setDealResult(Contant.CLAIMVOURCHER_SUBMIT);
        // 设置处理时间
        dealRecord.setDealTime(new Date());
        // 处理记录
        dealRecord.setComment("无");
        // 插入这条记录
        dealRecordDao.insert(dealRecord);
    }

    // 处理流程
    public void deal(DealRecord dealRecord) {
        // 传入的是处理记录
        // 获取处理的报销单
        ClaimVoucher claimVoucher = claimVoucherDao.select(dealRecord.getClaimVoucherId());
        // 获取处理人
        Employee employee = employeeDao.select(dealRecord.getDealSn());
        // 设置处理记录的时间
        dealRecord.setDealTime(new Date());
        // 如果处理方式是通过的话
        if (dealRecord.getDealway().equals(Contant.DEAL_PASS)) {
            // 处理的金额小于限制 并且处理人是经理
            if (claimVoucher.getTotalAmount() <= Contant.LIMIT || employee.getPost().equals(Contant.POST_GM)) {
                // 设置状态
                claimVoucher.setStatus(Contant.CLAIMVOURCHER_APPROVED);
                // 设置下一个处理的人 是尽力
                claimVoucher.setNextDealSn(employeeDao.selectByDepartmentAndPost(null, Contant.POST_CASHIER).get(0).getSn());
                // 处理记录是通过
                dealRecord.setDealResult(Contant.CLAIMVOURCHER_APPROVED);
            }
            // 大于限制
            else {
                claimVoucher.setStatus(Contant.CLAIMVOURCHER_RECHECK);
                claimVoucher.setNextDealSn(employeeDao.selectByDepartmentAndPost(null, Contant.POST_GM).get(0).getSn());
                dealRecord.setDealResult(Contant.CLAIMVOURCHER_RECHECK);
            }
        }
        // 打回
        else if (dealRecord.getDealway().equals(Contant.DEAL_BACK)) {
            claimVoucher.setStatus(Contant.CLAIMVOURCHER_BACK);
            claimVoucher.setNextDealSn(claimVoucher.getCreateSn());
            dealRecord.setDealResult(Contant.CLAIMVOURCHER_BACK);
        }
        // 拒绝
        else if (dealRecord.getDealway().equals(Contant.DEAL_REJECT)) {
            claimVoucher.setStatus(Contant.CLAIMVOURCHER_TERMINATED);
            claimVoucher.setNextDealSn(null);
            dealRecord.setDealResult(Contant.CLAIMVOURCHER_TERMINATED);
        }
        // 打钱
        else if (dealRecord.getDealway().equals(Contant.DEAL_PAID)) {
            claimVoucher.setStatus(Contant.CLAIMVOURCHER_PAID);
            claimVoucher.setNextDealSn(null);
            dealRecord.setDealResult(Contant.CLAIMVOURCHER_PAID);
        }
        // 更新报销单
        claimVoucherDao.update(claimVoucher);
        // 插入处理记录
        dealRecordDao.insert(dealRecord);
    }
}
