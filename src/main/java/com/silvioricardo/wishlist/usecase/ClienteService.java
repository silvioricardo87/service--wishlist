package com.silvioricardo.wishlist.usecase;

import com.silvioricardo.wishlist.domain.Cliente;
import com.silvioricardo.wishlist.domain.repository.ClienteRepository;
import com.silvioricardo.wishlist.usecase.exception.BusinessException;
import java.util.List;
import java.util.Optional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

  private final ClienteRepository clienteRepository;

  public ClienteService(ClienteRepository clienteRepository) {
    this.clienteRepository = clienteRepository;
  }

  @CachePut(value = "cliente", key = "#cliente.id")
  public Cliente salvarCliente(Cliente cliente) {
    return clienteRepository.save(cliente);
  }

  @CachePut(value = "cliente", key = "#id")
  public Cliente atualizarCliente(String id, Cliente cliente) {
    Cliente clienteAtualizado = obterCliente(id)
        .orElseThrow(() -> new BusinessException("Cliente não encontrado."));

    if(cliente.getDataNascimento() != null)
      clienteAtualizado.setDataNascimento(cliente.getDataNascimento());

    if(cliente.getEmail() != null)
      clienteAtualizado.setEmail(cliente.getEmail());

    return clienteRepository.save(clienteAtualizado);
  }

  @Cacheable(value = "cliente", key = "#id")
  public Optional<Cliente> obterCliente(String id) {
    return clienteRepository.findById(id);
  }

  @Cacheable(value = "clientes")
  public List<Cliente> obterTodosClientes() {
    return clienteRepository.findAll();
  }

  @CacheEvict(value = "cliente", key = "#id")
  public void deletarCliente(String id) {
    Cliente cliente = obterCliente(id)
        .orElseThrow(() -> new BusinessException("Cliente não encontrado."));
    clienteRepository.delete(cliente);
  }
}
