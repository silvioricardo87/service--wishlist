package com.silvioricardo.wishlist.adapter.in.web.mapper;

import com.silvioricardo.wishlist.adapter.in.web.dto.ClienteDto;
import com.silvioricardo.wishlist.adapter.in.web.dto.ClienteUpdateDto;
import com.silvioricardo.wishlist.domain.Cliente;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {
  public Cliente toDomain(ClienteDto dto) {
    Cliente cliente = new Cliente();

    BeanUtils.copyProperties(dto, cliente);
    return cliente;
  }

  public Cliente toDomain(ClienteUpdateDto dto) {
    Cliente cliente = new Cliente();

    BeanUtils.copyProperties(dto, cliente);
    return cliente;
  }

  public ClienteDto toDto(Cliente cliente) {
    ClienteDto dto = new ClienteDto();

    BeanUtils.copyProperties(cliente, dto);
    return dto;
  }
}
