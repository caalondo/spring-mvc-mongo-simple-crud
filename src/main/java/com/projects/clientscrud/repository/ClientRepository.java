package com.projects.clientscrud.repository;

import com.projects.clientscrud.models.ClientModel;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClientRepository extends CrudRepository <ClientModel, Long> {
    List<ClientModel> findByName(String lastName);
}
