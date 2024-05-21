package br.com.fiap.msclientes.service;

import br.com.fiap.msclientes.model.Cliente;
import br.com.fiap.msclientes.repository.ClienteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @Test
    void salvarCliente_DeveRetornarClienteSalvo() {
        Cliente cliente = new Cliente();
        cliente.setNome("Victor");
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente salvo = clienteService.salvarCliente(cliente);

        assertThat(salvo).isNotNull();
        verify(clienteRepository).save(cliente);
    }

    @Test
    void buscarPorId_DeveRetornarCliente_QuandoClienteExistir() {
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Cliente encontrado = clienteService.buscarPorId(1L);

        assertThat(encontrado).isNotNull();
        assertThat(encontrado.getId()).isEqualTo(1L);
        verify(clienteRepository).findById(1L);
    }

    @Test
    public void testAtualizarCliente() {
        Cliente clienteExistente = new Cliente();
        Cliente clienteAtualizado = new Cliente();
        clienteAtualizado.setNome("Nome Atualizado");

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteExistente));
        when(clienteRepository.save(any(Cliente.class))).thenReturn(clienteExistente);

        Cliente result = clienteService.atualizarCliente(1L, clienteAtualizado);

        assertEquals("Nome Atualizado", result.getNome());
        verify(clienteRepository).save(clienteExistente);
    }

    @Test
    public void testDeletarCliente() {
        doNothing().when(clienteRepository).deleteById(1L);

        clienteService.deletarCliente(1L);

        verify(clienteRepository).deleteById(1L);
    }

    @Test
    public void testListarTodos() {
        Cliente cliente1 = new Cliente();
        cliente1.setNome("Cliente 1");
        Cliente cliente2 = new Cliente();
        cliente2.setNome("Cliente 2");
        List<Cliente> expectedClientes = Arrays.asList(cliente1, cliente2);

        when(clienteRepository.findAll()).thenReturn(expectedClientes);

        List<Cliente> actualClientes = clienteService.listarTodos();

        assertEquals(expectedClientes, actualClientes);
        verify(clienteRepository).findAll();
    }

}
