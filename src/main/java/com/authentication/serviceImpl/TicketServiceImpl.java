package com.authentication.serviceImpl;

import com.authentication.model.Ticket;
import com.authentication.repository.TicketRepository;
import com.authentication.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Access;

@Service
public class TicketServiceImpl implements TicketService {


    @Autowired
    private TicketRepository ticketRepository;

    public Ticket raiseTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }
}
