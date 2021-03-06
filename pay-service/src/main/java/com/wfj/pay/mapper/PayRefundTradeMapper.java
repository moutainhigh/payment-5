package com.wfj.pay.mapper;

import com.wfj.pay.po.PayRefundTradePO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wjg on 2017/6/22.
 */
@Repository
public interface PayRefundTradeMapper {

    void insert(PayRefundTradePO payRefundTradePO);

    void update(PayRefundTradePO payRefundTradePO);

    List<PayRefundTradePO> selectRefundTradeByOrderTradeNo(String orderTradeNo);

    PayRefundTradePO selectByBpIdAndBpRefundOrderId(@Param("bpId") Long bpId,
                                        @Param("bpRefundOrderId") String bpRefundOrderId);

    PayRefundTradePO selectLastSuccessRefundTrade(String orderTradeNo);

    Integer selectRefundTradeCount(@Param("orderTradeNo") String orderTradeNo);

    PayRefundTradePO selectByRefundTradeNo(String refundTradeNo);
}
