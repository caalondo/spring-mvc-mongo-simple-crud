package com.projects.mvccrud.repository;

import com.projects.mvccrud.models.ClientModel;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository <ClientModel, String> {}
