package com.authentication.service;

import com.authentication.payload.request.TicketRequest;
import com.authentication.payload.response.TicketResponse;

import java.util.List;


public interface TicketService {
    TicketResponse raiseTicket(TicketRequest ticketRequest);

    List<TicketResponse> getAllTickets();

    TicketResponse getTicketById(Long id);

    TicketResponse updateTicket(Long id, TicketRequest ticketRequest);

    boolean deleteTicket(Long id);
}
