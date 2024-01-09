package com.authentication.controller;

import com.authentication.model.Ticket;
import com.authentication.payload.request.TicketRequest;
import com.authentication.payload.response.TicketResponse;
import com.authentication.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth/user")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/raise")
    public ResponseEntity<TicketResponse> raiseTicket(@Valid @RequestBody TicketRequest ticketRequest) {
        Ticket ticket = new Ticket();
        ticket.setSubject(ticketRequest.getSubject());
        ticket.setDescription(ticketRequest.getDescription());

        Ticket savedTicket = ticketService.raiseTicket(ticket);

        TicketResponse ticketResponse = new TicketResponse(savedTicket.getId(), savedTicket.getCreationDate(),
                savedTicket.getSubject(), savedTicket.getDescription(), savedTicket.isStatus());

        return ResponseEntity.ok(ticketResponse);
    }
}
