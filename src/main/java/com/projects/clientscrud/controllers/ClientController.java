package com.projects.clientscrud.controllers;

import com.projects.clientscrud.models.ClientModel;
import com.projects.clientscrud.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/clients")
public class ClientController {

    @Autowired
    ClientRepository clientRepository;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public Iterable<ClientModel> getAllClients () {
        return clientRepository.findAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Optional<ClientModel> getClientById (@PathVariable long id) {
        return clientRepository.findById(id);
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createClient(@RequestBody ClientModel clientModel) {
        clientRepository.save(clientModel);
    }
}
