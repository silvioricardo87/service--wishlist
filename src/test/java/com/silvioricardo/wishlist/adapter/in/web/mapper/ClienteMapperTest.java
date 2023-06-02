package com.silvioricardo.wishlist.adapter.in.web.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.silvioricardo.wishlist.adapter.in.web.dto.ClienteDto;
import com.silvioricardo.wishlist.adapter.in.web.dto.ClienteUpdateDto;
import com.silvioricardo.wishlist.domain.Cliente;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class ClienteMapperTest {

  private final ClienteMapper mapper = new ClienteMapper();
  private final Cliente cliente = new Cliente();
  private final ClienteDto clienteDto = new ClienteDto();

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
  void deveMapearDtoToDomain() {
    criaMockClienteDto();

    Cliente cliente = mapper.toDomain(clienteDto);

    assertEquals(clienteDto.getId(), cliente.getId());
    assertEquals(clienteDto.getNome(), cliente.getNome());
    assertEquals(clienteDto.getDataNascimento(), cliente.getDataNascimento());
  }

  @Test
  void deveMapearUpdateDtoToDomain() {
    ClienteUpdateDto dto = new ClienteUpdateDto();
    dto.setEmail("email@email.com");
    dto.setDataNascimento(LocalDate.parse("2000-01-01"));

    Cliente cliente = mapper.toDomain(dto);

    assertEquals(dto.getEmail(), cliente.getEmail());
    assertEquals(dto.getDataNascimento(), cliente.getDataNascimento());
  }

  @Test
  void deveMapearDomainToDto() {
    criaMockCliente();

    ClienteDto dto = mapper.toDto(cliente);

    assertEquals(cliente.getId(), dto.getId());
    assertEquals(cliente.getNome(), dto.getNome());
    assertEquals(cliente.getDataNascimento(), dto.getDataNascimento());
  }
}
