package com.lqjk.third.beans;

import lombok.Data;

@Data
public class CustomerToken {

    private Long customer_id;
    private String token;
    private String customer_key;
    private String customer_pub;
    private String service_key;
    private String service_pub;
    private String customer_equipment;
    private String validity_time;

}
