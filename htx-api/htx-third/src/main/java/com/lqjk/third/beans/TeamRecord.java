package com.lqjk.third.beans;

import java.math.BigDecimal;
import java.util.Date;

public class TeamRecord {
    private Long id;

    private Long ownerId;

    private Long comId;

    private Integer travelType;

    private Integer travelTimes;

    private BigDecimal travalCarbon;

    private Integer status;

    private Date lastUpdate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public Long getComId() {
        return comId;
    }

    public void setComId(Long comId) {
        this.comId = comId;
    }

    public Integer getTravelType() {
        return travelType;
    }

    public void setTravelType(Integer travelType) {
        this.travelType = travelType;
    }

    public Integer getTravelTimes() {
        return travelTimes;
    }

    public void setTravelTimes(Integer travelTimes) {
        this.travelTimes = travelTimes;
    }

    public BigDecimal getTravalCarbon() {
        return travalCarbon;
    }

    public void setTravalCarbon(BigDecimal travalCarbon) {
        this.travalCarbon = travalCarbon;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}