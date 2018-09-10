package co.com.bancolombia.mvccrud.repositories;

import co.com.bancolombia.mvccrud.models.ClientModel;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository <ClientModel, String> {}
