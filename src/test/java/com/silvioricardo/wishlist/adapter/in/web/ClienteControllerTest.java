package com.silvioricardo.wishlist.adapter.in.web;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.silvioricardo.wishlist.adapter.in.web.dto.ClienteDto;
import com.silvioricardo.wishlist.adapter.in.web.dto.ClienteUpdateDto;
import com.silvioricardo.wishlist.adapter.in.web.mapper.ClienteMapper;
import com.silvioricardo.wishlist.domain.Cliente;
import com.silvioricardo.wishlist.usecase.ClienteService;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ClienteService clienteService;

  @MockBean
  private ClienteMapper clienteMapper;

  private final Cliente cliente = new Cliente();
  private final ClienteDto clienteDto = new ClienteDto();
  private final String X_API_KEY_VALUE = "teste";

  void criaMockCliente() {
    cliente.setId("a1b2c3d4e5");
    cliente.setNome("João da Silva");
    cliente.setEmail("email@email.com");
    cliente.setCpf("61001347056");
    cliente.setDataNascimento(LocalDate.parse("2000-01-01"));
  }

  void criaMockClienteDto() {
    clienteDto.setNome("João da Silva");
    clienteDto.setEmail("email@email.com");
    clienteDto.setCpf("61001347056");
    clienteDto.setDataNascimento(LocalDate.parse("2000-01-01"));
  }

  @Test
  void deveCriarCliente() throws Exception {
    criaMockClienteDto();

    when(clienteMapper.toDomain(any(ClienteDto.class))).thenReturn(cliente);
    when(clienteService.salvarCliente(any(Cliente.class))).thenReturn(cliente);
    when(clienteMapper.toDto(any(Cliente.class))).thenReturn(clienteDto);

    mockMvc.perform(post("/clientes")
            .header("x-api-key", X_API_KEY_VALUE)
            .content(asJsonString(clienteDto))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated());

    verify(clienteMapper).toDomain(any(ClienteDto.class));
    verify(clienteService).salvarCliente(any(Cliente.class));
    verify(clienteMapper).toDto(any(Cliente.class));
  }

  @Test
  void deveAtualizarCliente() throws Exception {
    criaMockCliente();

    ClienteUpdateDto clienteUpdateDto = new ClienteUpdateDto();
    clienteUpdateDto.setEmail("novoemail@email.com");
    clienteUpdateDto.setDataNascimento(LocalDate.parse("2000-01-01"));

    when(clienteMapper.toDomain(clienteUpdateDto)).thenReturn(cliente);
    when(clienteService.atualizarCliente(cliente.getId(), cliente)).thenReturn(cliente);
    when(clienteMapper.toDto(cliente)).thenReturn(new ClienteDto());

    mockMvc.perform(put("/clientes/" + cliente.getId())
            .header("x-api-key", X_API_KEY_VALUE)
            .content(asJsonString(clienteUpdateDto))
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    verify(clienteMapper, times(1)).toDomain(any(ClienteUpdateDto.class));
    verify(clienteService, times(1)).atualizarCliente(anyString(), any(Cliente.class));
    verify(clienteMapper, times(1)).toDto(any(Cliente.class));
  }

  @Test
  void deveObterCliente() throws Exception {
    criaMockCliente();

    when(clienteService.obterCliente(cliente.getId())).thenReturn(Optional.of(cliente));

    mockMvc.perform(get("/clientes/" + cliente.getId())
            .header("X-API-KEY", X_API_KEY_VALUE))
        .andExpect(status().isOk());
  }

  @Test
  void deveObterTodosClientes() throws Exception {
    criaMockCliente();

    when(clienteService.obterTodosClientes()).thenReturn(Arrays.asList(cliente, cliente));
    mockMvc.perform(get("/clientes")
            .header("X-API-KEY", X_API_KEY_VALUE))
        .andExpect(status().isOk());
  }

  @Test
  void deveDeletarCliente() throws Exception {
    criaMockCliente();
    doNothing().when(clienteService).deletarCliente(cliente.getId());

    mockMvc.perform(delete("/clientes/" + cliente.getId())
            .header("X-API-KEY", X_API_KEY_VALUE))
        .andExpect(status().isNoContent());
  }

  @Test
  void deveRetornarNaoAutorizadoAoEnviarChaveNulaOuInvalida() throws Exception {
    criaMockCliente();

    mockMvc.perform(delete("/clientes/" + cliente.getId()))
        .andExpect(status().isUnauthorized());
  }

  private static String asJsonString(final Object obj) {
    try {
      ObjectMapper mapper = new ObjectMapper();
      mapper.registerModule(new JavaTimeModule());
      mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

      return mapper.writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
