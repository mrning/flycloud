package com.lqjk.pay.constants;

public class PayUrlConstant {
    /**
     * 客户在商户APP想要拉起数字人民币APP支付，商户可以通过该接口发起支付请求。
     */
    public static final String PAY_UP = "/DCEPPayApp.do";
    /**
     * 商户向银行发起数字人民币支付请求后，可以通过该接口发起支付订单状态查询请求。
     */
    public static final String PAY_UP_QUERY = "/DCEPQueryPay.do";
    /**
     * 客户在商户网站进行数字人民币订单退款操作，商户可以通过该接口发起退款请求。
     */
    public static final String PAY_REFUND = "/DCEPRefund.do";
    /**
     * 商户向银行发起数字人民币退款请求后，可以通过该接口发起退款订单状态查询请求。
     */
    public static final String PAY_REFUND_QUERY = "/DCEPQueryRefund.do";
    /**
     * 客户未完成支付的订单，商户可以通过该接口发起关闭订单请求。
     */
    public static final String PAY_CLOSE = "/DCEPClose.do";
}
