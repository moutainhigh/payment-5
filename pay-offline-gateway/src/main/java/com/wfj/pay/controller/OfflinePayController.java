package com.wfj.pay.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.wfj.pay.constant.PayOrderTerminalEnum;
import com.wfj.pay.constant.PayServiceEnum;
import com.wfj.pay.constant.PayTypeEnum;
import com.wfj.pay.dto.OrderRequestDTO;
import com.wfj.pay.dto.OrderResponseDTO;
import com.wfj.pay.dubbo.IOfflinePayDubbo;
import com.wfj.pay.dubbo.ITestDubbo;
import com.wfj.pay.utils.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wjg on 2017/6/22.
 */
@Controller
public class OfflinePayController {
    private Logger logger = LoggerFactory.getLogger(OfflinePayController.class);

    @Reference(version = "1.0.0",cluster = "failfast")
    private IOfflinePayDubbo offlinePayDubbo;


    /**
     * 线下支付创建订单接口
     *
     * @param orderRequestDTO
     * @param result
     */
    @RequestMapping("front/order/shbOrderMsg")
    @ResponseBody
    public OrderResponseDTO createOrder(@RequestBody @Valid OrderRequestDTO orderRequestDTO, BindingResult result, HttpServletRequest request) {
        logger.info("接到线下支付的请求:" + JSON.toJSONString(orderRequestDTO));
        OrderResponseDTO orderResponseDTO = validate(orderRequestDTO,result);
        //校验失败直接返回
        if(orderResponseDTO!=null){
            return orderResponseDTO;
        }
        orderRequestDTO.setPayIp(WebUtil.getIpAddress(request));
        try{
            offlinePayDubbo.createOrder(orderRequestDTO);
        }catch (Exception e){
            //调用dubbo失败，可能是超时，也可能是服务方出错了
            logger.error(e.toString(),e);
            return new OrderResponseDTO("1","false","系统错误");
        }
        return new OrderResponseDTO("0","true","OK");
    }

    /**
     * 校验参数
     * @param result
     * @return
     */
    private OrderResponseDTO validate(OrderRequestDTO orderRequestDTO,BindingResult result){
        OrderResponseDTO orderResponseDTO =null;
        if (result.hasErrors()) {
            StringBuffer sb = new StringBuffer("校验错误：");
            List<FieldError> fieldErrors = result.getFieldErrors();
            fieldErrors.stream().forEach(fieldError -> {
                logger.info("校验错误：" + fieldError.getField()  + fieldError.getDefaultMessage());
                sb.append(fieldError.getField()  + fieldError.getDefaultMessage() + ",");
            });
            orderResponseDTO = new OrderResponseDTO("1", "false", sb.substring(0, sb.length() - 1));
            return orderResponseDTO;
        }
        if(!PayTypeEnum.isContain(orderRequestDTO.getPayType())){
            orderResponseDTO = new OrderResponseDTO("1", "false", "payType只接收WECHATPAY_OFFLINE或者ALIPAY_OFFLINE");
            return orderResponseDTO;
        }
        if(!PayServiceEnum.isContain(orderRequestDTO.getPayService())){
            orderResponseDTO = new OrderResponseDTO("1", "false", "payService只接收1或2或3");
            return orderResponseDTO;
        }
        if(!PayOrderTerminalEnum.isContain(orderRequestDTO.getInitOrderTerminal())){
            orderResponseDTO = new OrderResponseDTO("1", "false", "initOrderTerminal只接收01或02或03或04");
            return orderResponseDTO;
        }
        return null;
    }
}
