package com.zac.mq.constants;

/**
 * @Description rabbitMq用的常量
 * @Author HZY
 * @Date 2019/10/21 15:40
 * @Version 1.0
 **/

public class MQConstant {

    private MQConstant(){}
    /***********----------信道------------**************/
    public static final String DEFAULT_EXCHANGE="default_exchange";
    //TTL QUEUE
    public static final String DEFAULT_DEAD_LETTER_QUEUE="default_dead_letter_queue";

    //碳账本保存用户授权信息队列
    public static final String CARBON_THIRD_SAVE_AUTH = "carbon_third_save_auth";

    //上海交通卡行程推送
    public static final String SHTRAFFIC_USER_TRAVEL_PUSH ="shTraffic_user_travel_push";

    //T3新能源车行程推送
    public static final String T3_USER_TRAVEL_PUSH ="T3_user_travel_push";

    //DLX repeat QUEUE 死信转发队列
    public static final String DEFAULT_REPEAT_TRADE_QUEUE="default_repeat_trade_queue";

    public static final String COMPANY_CARBON_ADD = "company_carbon_add";

    public static final String CREATE_AUTH_USER_HTX = "create_auth_user_htx";

    public static final String MAIL_USER_QUEUE="mail_user_queue";

}