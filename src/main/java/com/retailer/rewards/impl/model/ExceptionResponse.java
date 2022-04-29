package com.retailer.rewards.impl.model;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * Model to save consolidated exception details and return to consumer.
 */
public class ExceptionResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy hh:mm:ss")
    private Date timeStamp;
    private String errorMessage;
    private String details;

    public ExceptionResponse(Date timeStamp, String errorMessage, String details){
        this.timeStamp = timeStamp;
        this.errorMessage = errorMessage;
        this.details = details;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
