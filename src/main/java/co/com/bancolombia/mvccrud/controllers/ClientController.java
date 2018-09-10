package co.com.bancolombia.mvccrud.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import co.com.bancolombia.mvccrud.models.ApiResponse;
import co.com.bancolombia.mvccrud.models.ClientModel;
import co.com.bancolombia.mvccrud.models.ITypeListResponse;
import co.com.bancolombia.mvccrud.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/clients")
public class ClientController {

    @Autowired
    ClientRepository clientRepository;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAllClients (HttpServletResponse http_resp) {
        List<ITypeListResponse> listR = Lists.newArrayList(clientRepository.findAll());
        ApiResponse res = new ApiResponse("200", "OK", listR);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            http_resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            return objectMapper.writeValueAsString(res);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            http_resp.setStatus(500);
            return "";
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Optional<ClientModel> getClientById (@PathVariable String id) {
        return clientRepository.findById(id);
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createClient(@RequestBody ClientModel clientModel) {
        clientRepository.save(clientModel);
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@RequestBody ClientModel clientModel) {
        clientRepository.save(clientModel);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String id) {
        clientRepository.deleteById(id);
    }
}
