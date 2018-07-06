package com.projects.clientscrud.repository;

import com.projects.clientscrud.models.ClientModel;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository <ClientModel, Long> {}
