package com.silvioricardo.wishlist.usecase;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.silvioricardo.wishlist.domain.Cliente;
import com.silvioricardo.wishlist.domain.repository.ClienteRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {
  @Mock
  private ClienteRepository clienteRepository;

  @MockBean
  private ClienteService clienteService;

  private final Cliente cliente = new Cliente();

  @BeforeEach
  void criaMockCliente() {
    cliente.setId("a1b2c3d4e5");
    cliente.setNome("João da Silva");
    cliente.setEmail("email@email.com");
    cliente.setCpf("61001347056");
    cliente.setDataNascimento(LocalDate.parse("2000-01-01"));
  }

  @Test
  void salvarCliente() {
    when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

    clienteService = new ClienteService(clienteRepository);

    Cliente savedCliente = clienteService.salvarCliente(cliente);
    assertEquals(cliente.getId(), savedCliente.getId());
  }

  @Test
  void atualizarCliente() {
    cliente.setEmail("cliente@email.com");

    when(clienteRepository.findById(anyString())).thenReturn(Optional.of(cliente));
    when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

    clienteService = new ClienteService(clienteRepository);

    Cliente updatedCliente = clienteService.atualizarCliente("a1b2c3d4e5", cliente);

    assertEquals(cliente.getId(), updatedCliente.getId());
    assertEquals(cliente.getEmail(), updatedCliente.getEmail());
  }

  @Test
  void obterCliente() {
    when(clienteRepository.findById(anyString())).thenReturn(Optional.of(cliente));

    clienteService = new ClienteService(clienteRepository);

    Optional<Cliente> obtainedCliente = clienteService.obterCliente("a1b2c3d4e5");

    assertTrue(obtainedCliente.isPresent());
    assertEquals(cliente.getId(), obtainedCliente.get().getId());
  }

  @Test
  void obterTodosClientes() {
    when(clienteRepository.findAll()).thenReturn(List.of(cliente));

    clienteService = new ClienteService(clienteRepository);

    List<Cliente> clientes = clienteService.obterTodosClientes();

    assertFalse(clientes.isEmpty());
    assertEquals(cliente.getId(), clientes.get(0).getId());
  }

  @Test
  void deletarCliente() {
    when(clienteRepository.findById(anyString())).thenReturn(Optional.of(cliente));
    doNothing().when(clienteRepository).delete(any(Cliente.class));

    clienteService = new ClienteService(clienteRepository);

    assertDoesNotThrow(() -> clienteService.deletarCliente("a1b2c3d4e5"));
  }
}
