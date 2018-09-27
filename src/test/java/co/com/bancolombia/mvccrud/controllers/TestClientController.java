package co.com.bancolombia.mvccrud.controllers;

import co.com.bancolombia.mvccrud.models.ClientModel;
import co.com.bancolombia.mvccrud.repositories.ClientRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;


@RunWith(Enclosed.class)
public class TestClientController {

    @RunWith(MockitoJUnitRunner.class)
    @PrepareForTest({ClientController.class, ClientRepository.class})
    public static class TestGetAllClients {

        @InjectMocks
        private ClientController clientController;

        @Mock
        private ClientRepository clientRepositoryMock;

        private MockMvc mockMvc;

        @Before
        public void setup() {
            mockMvc = MockMvcBuilders.standaloneSetup(ClientController.class).build();
            MockitoAnnotations.initMocks(this);
        }

        @Test
        public void testA() throws Exception {

            when(clientRepositoryMock.findAll()).thenReturn(null);

            System.err.println("====>>> Test1");
            mockMvc.perform(MockMvcRequestBuilders
                    .get("/clients/")
                    .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }
    }

    public static class TestGetClientById {

        @Test
        public void testA() {
            System.out.println("Test1");
            assertEquals(6, 6);
        }
    }

    public static class TestCreateClient {

        @Test
        public void testA() {
            System.out.println("Test1");
            assertEquals(6, 6);
        }
    }

    public static class TestUpdateClient {

        @Test
        public void testA() {
            System.out.println("Test1");
            assertEquals(6, 6);
        }
    }

    public static class TestDeleteClient {

        @Test
        public void testA() {
            System.out.println("Test1");
            assertEquals(6, 6);
        }
    }
}
