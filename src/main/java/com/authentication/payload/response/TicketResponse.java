package com.authentication.payload.response;

import lombok.Data;

import java.util.Date;

@Data
public class TicketResponse {


    private int id;
    private Date creationDate;
    private String subject;
    private String description;
    private boolean status;

    public TicketResponse(int id, Date creationDate, String subject, String description, boolean status) {
    }
}
