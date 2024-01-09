package com.authentication.controller;

import com.authentication.payload.request.TicketRequest;
import com.authentication.payload.response.TicketResponse;
import com.authentication.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/auth/user")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/raise")
    public ResponseEntity<TicketResponse> raiseTicket(@Valid @RequestBody TicketRequest ticketRequest) {
        try {
            TicketResponse ticketResponse = ticketService.raiseTicket(ticketRequest);
            return ResponseEntity.ok(ticketResponse);
        } catch (Exception e) {
            // Handle exception and return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TicketResponse(0L, new Date(), "Error", e.getMessage(), false));
        }
    }


    @GetMapping("/allTickets")
    public ResponseEntity<List<TicketResponse>> getAllTickets() {
        List<TicketResponse> allTickets = ticketService.getAllTickets();
        return ResponseEntity.ok(allTickets);
    }

    @GetMapping("/ticket")
    public ResponseEntity<TicketResponse> getTicketById(@RequestParam Long id) {
        try {
            TicketResponse ticket = ticketService.getTicketById(id);

            if (ticket != null) {
                return ResponseEntity.ok(ticket);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // Handle exception and return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TicketResponse(0L, new Date(), "Error", e.getMessage(), false));
        }
    }


    @PutMapping("/updateTicket")
    public ResponseEntity<TicketResponse> updateTicket(
            @RequestParam Long id,
            @Valid @RequestBody TicketRequest ticketRequest) {
        try {
            TicketResponse updatedTicket = ticketService.updateTicket(id, ticketRequest);

            if (updatedTicket != null) {
                return ResponseEntity.ok(updatedTicket);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // Handle exception and return an error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new TicketResponse(0L, new Date(), "Error", e.getMessage(), false));
        }
    }


    @DeleteMapping("/deleteTicket")
    public boolean deleteTicket(@RequestParam Long id) {

            boolean isDeleted = ticketService.deleteTicket(id);

            return isDeleted;
    }

}
