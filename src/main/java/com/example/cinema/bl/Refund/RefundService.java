package com.example.cinema.bl.Refund;

import com.example.cinema.vo.RefundForm;
import com.example.cinema.vo.ResponseVO;

public interface RefundService {
    /**
     * 新增一个退票方案
     * @param refundForm
     * @return
     */
    ResponseVO addRefundMethod(RefundForm refundForm);

    /**
     * 根据id删除退票方案
     * @param RefundId
     * @return
     */
    ResponseVO deleteRefundMethod(int RefundId);

    /**、
     * 得到所有的退票方案
     * @return
     */
    ResponseVO getAllRefundMethods();

    /**
     * 更新退票方案
     * @param RefundId
     * @param refundForm
     * @return
     */
    ResponseVO updateRefundMethod(int RefundId,RefundForm refundForm);
}
