package com.example.javafx.service;

import com.example.javafx.model.SearchContext;
import com.example.javafx.model.SearchFilter;
import com.example.javafx.model.Ticket;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MockTicketsService {
    private static List<Ticket> allTickets = new ArrayList<>();

    static {
        allTickets = Arrays.asList(
            new Ticket(10249249249L, "The printer failed", "Joebob", "TRANSFERRED", null, "M2CMCGODATAMGMT"),
            new Ticket(10049499423L, "Dog failed", "Jimmy", "TRANSFERRED", null, "M2CMCGOPNET4"),
            new Ticket(10432094223L, "I'm bored", "John", "ACKNOWLEDGED", "O44442", "M2CMCGOSUA"),
            new Ticket(14923904823L, "Cat is hungry", "George", "ACKNOWLEDGED", "H204929", "M2CMCGODATAMGMT"),
             new Ticket(23352, "The printer failed", "abc", "TRANSFERRED", null, "ABC3SUPPGP"),
            new Ticket(666, "Where is toilet!??", "frg", "TRANSFERRED", null, "M2CMCGOPNET4"),
            new Ticket(4444, "President is hungry", "hth", "ACKNOWLEDGED", "O44442", "M2CMCGOSUA"),
            new Ticket(322, "All world is hungry", "rtre", "RESOLVED", "H204929", "M3CMCGOSUA"),
             new Ticket(532, "The dworld failed", "yery", "CLOSED", null, "M3CMCGOPNET4"),
            new Ticket(100456269499423L, "eVERYTHING failed", "ery", "TRANSFERRED", null, "M2CMCGODATAMGMT"),
            new Ticket(23412, "I'm bored again", "ery", "CLOSED", "O44442", "M3CMCGODATAMGMT"),
            new Ticket(7856856, "Cat is still hungry", "346343", "ACKNOWLEDGED", "H204929", "M2CMCGOPNET4")
        );
    }

    public List<Ticket> findTickets() {
        SearchFilter filter = SearchContext.activeSearchFilter();
        if (filter != null) {
            return allTickets.stream()
                    .filter(t -> filter(t,filter))
                    .collect(Collectors.toList());
        } else {
            return allTickets;
        }
    }

    private boolean filter(Ticket ticket, SearchFilter filter) {
        return filter.getStatuses().contains(Ticket.IncidentStatus.fromText(ticket.getStatus()))
                && filter.getQueues().contains(ticket.getAssignedGroup());
    }
}
