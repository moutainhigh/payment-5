package com.wfj.pay.mapper;

import com.wfj.pay.po.PayLogPO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by wjg on 2017/6/22.
 */
@Repository
public interface PayLogMapper {

    void insert(PayLogPO payLogPO);

}
