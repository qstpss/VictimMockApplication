package com.qstpss.victimmockapplication.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class MockEvent {

    private MockEvent(){
    }

    public MockEvent(Type type) {
        if (type == null) {
            throw new UnsupportedOperationException();
        }
        this.type = type;
        this.status = Status.PENDING;
    }

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("type")
    @Expose
    private Type type;

    @SerializedName("status")
    @Expose
    private Status status;

    @SerializedName("registerTimestamp")
    @Expose
    private Date registerTimestamp;

    @SerializedName("startTimestamp")
    @Expose
    private Date startTimestamp;

    @SerializedName("endTimestamp")
    @Expose
    private Date endTimestamp;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getRegisterTimestamp() {
        return registerTimestamp;
    }

    public void setRegisterTimestamp(Date registerTimestamp) {
        this.registerTimestamp = registerTimestamp;
    }

    public Date getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(Date startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public Date getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(Date endTimestamp) {
        this.endTimestamp = endTimestamp;
    }
}
