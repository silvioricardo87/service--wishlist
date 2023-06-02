package com.silvioricardo.wishlist.adapter.in.web;

import com.silvioricardo.wishlist.adapter.in.web.dto.ClienteDto;
import com.silvioricardo.wishlist.adapter.in.web.dto.ClienteUpdateDto;
import com.silvioricardo.wishlist.domain.Cliente;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@SecurityRequirement(name = "X-API-KEY")
@Tag(name = "Clientes", description = "Definição de endpoints para clientes")
@RequestMapping(value = "/clientes", produces = "application/json")
public interface ClienteContract {

  @Operation(summary = "Cria um novo cliente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso", content = @Content(schema = @Schema(implementation = ClienteDto.class))),
      @ApiResponse(responseCode = "400", description = "Solicitação inválida"),
      @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  ClienteDto criarCliente(@Valid @RequestBody ClienteDto clienteDto);

  @Operation(summary = "Busca um cliente por ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Sucesso", content = @Content(schema = @Schema(implementation = Cliente.class))),
      @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
      @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  @GetMapping("/{id}")
  Optional<Cliente> obterCliente(@PathVariable String id);

  @Operation(summary = "Atualiza um cliente existente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso", content = @Content(schema = @Schema(implementation = Cliente.class))),
      @ApiResponse(responseCode = "400", description = "Solicitação inválida"),
      @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
      @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  @PutMapping("/{id}")
  ClienteDto atualizarCliente(@PathVariable String id, @Valid @RequestBody ClienteUpdateDto clienteUpdateDto);

  @Operation(summary = "Lista todos os clientes")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Sucesso", content = @Content(schema = @Schema(implementation = List.class))),
      @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  @GetMapping
  List<Cliente> obterTodosClientes();

  @Operation(summary = "Deleta um cliente existente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Cliente deletado com sucesso"),
      @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
      @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}")
  void deletarCliente(@PathVariable String id);
}