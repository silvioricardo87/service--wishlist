package com.silvioricardo.wishlist.adapter.in.web;

import com.silvioricardo.wishlist.adapter.in.web.dto.ClienteDto;
import com.silvioricardo.wishlist.adapter.in.web.dto.ClienteUpdateDto;
import com.silvioricardo.wishlist.adapter.in.web.mapper.ClienteMapper;
import com.silvioricardo.wishlist.domain.Cliente;
import com.silvioricardo.wishlist.usecase.ClienteService;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ClienteController implements ClienteContract {

  private final ClienteMapper clienteMapper;
  private final ClienteService clienteService;

  public ClienteController(ClienteMapper clienteMapper, ClienteService clienteService) {
    this.clienteMapper = clienteMapper;
    this.clienteService = clienteService;
  }

  @Override
  public ClienteDto criarCliente(ClienteDto clienteDto) {
    Cliente cliente = clienteMapper.toDomain(clienteDto);
    return clienteMapper.toDto(clienteService.salvarCliente(cliente));
  }

  @Override
  public ClienteDto atualizarCliente(String id, ClienteUpdateDto clienteUpdateDto) {
    Cliente cliente = clienteMapper.toDomain(clienteUpdateDto);
    return clienteMapper.toDto(clienteService.atualizarCliente(id, cliente));
  }

  @Override
  public Optional<Cliente> obterCliente(String id) {
    return clienteService.obterCliente(id);
  }

  @Override
  public List<Cliente> obterTodosClientes() {
    return clienteService.obterTodosClientes();
  }

  @Override
  public void deletarCliente(String id) {
    clienteService.deletarCliente(id);
  }
}