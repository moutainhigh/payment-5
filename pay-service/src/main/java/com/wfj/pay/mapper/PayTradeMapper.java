package com.wfj.pay.mapper;

import com.wfj.pay.dto.OrderQueryReqDTO;
import com.wfj.pay.po.PayTradePO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by wjg on 2017/6/22.
 */
@Repository
public interface PayTradeMapper {

    void insert(PayTradePO payTradePO);

    void update(PayTradePO orderPO);

    PayTradePO selectByOrderTradeNo(@Param("orderTradeNo") String orderTradeNo);

    PayTradePO selectByBpIdAndBpOrderId(@Param("bpId") Long bpId, @Param("bpOrderId") String bpOrderId);

    PayTradePO selectByBpIdAndOrderTradeNo(@Param("bpId")Long bpId,@Param("orderTradeNo") String orderTradeNo);

    int updateOrderPayType(Map<String, Object> para);

    void updateOrderAfterPaySuccess(PayTradePO payTradePO);

    void updateOrderStatus(@Param("orderTradeNo") String orderTrade,@Param("status") Long status);

    List<PayTradePO> selectByTimeStamp(@Param("beginTimeStamp") long beginTimeStamp, @Param("endTimeStamp") long endTimeStamp);

	List<PayTradePO> selectAllOrderCompensate(OrderQueryReqDTO orderDTO);

	List<PayTradePO> selectAllOrderByStatus(OrderQueryReqDTO orderDTO);

	List<PayTradePO> tradeToES();
}
