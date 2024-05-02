package br.com.fiap.msclientes.controller;

import br.com.fiap.msclientes.model.Cliente;
import br.com.fiap.msclientes.service.ClienteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.*;

@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void criarCliente_DeveRetornarClienteCriado() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setNome("Victor");
        given(clienteService.salvarCliente(any(Cliente.class))).willReturn(cliente);

        mockMvc.perform(post("/clientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value("Victor"));
    }

    @Test
    void listarClientes_DeveRetornarTodosClientes() throws Exception {
        Cliente cliente = new Cliente();
        cliente.setNome("Victor");
        given(clienteService.listarTodos()).willReturn(Arrays.asList(cliente));

        mockMvc.perform(get("/clientes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Victor"));
    }
}
