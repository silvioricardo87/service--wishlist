package com.silvioricardo.wishlist.adapter.in.web;

import com.silvioricardo.wishlist.domain.Cliente;
import com.silvioricardo.wishlist.domain.Produto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Set;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@SecurityRequirement(name = "X-API-KEY")
@Tag(name = "Wishlist", description = "Definição de endpoints para wishlist")
@RequestMapping(value = "/clientes/{clienteId}/wishlist",produces = "application/json")
public interface WishlistContract {

  @Operation(summary = "Adiciona um produto na Wishlist de um cliente")
  @ApiResponses(value = {
      @ApiResponse(responseCode =" 200", description = "Produto adicionado com sucesso"),
      @ApiResponse(responseCode =" 400", description = "Solicitação inválida"),
      @ApiResponse(responseCode =" 404", description = "Cliente ou Produto não encontrado"),
      @ApiResponse(responseCode =" 422", description = "Falha ao processar a entidade"),
      @ApiResponse(responseCode =" 500", description = "Erro interno do servidor")
  })
  @PostMapping("/{produtoId}")
  Cliente adicionarProdutoNaWishlist(@PathVariable String clienteId, @PathVariable String produtoId);

  @Operation(summary = "Remove um produto da Wishlist de um cliente")
  @ApiResponses(value = {
      @ApiResponse(responseCode =" 200", description = "Produto removido com sucesso"),
      @ApiResponse(responseCode =" 400", description = "Solicitação inválida"),
      @ApiResponse(responseCode =" 404", description = "Cliente ou Produto não encontrado"),
      @ApiResponse(responseCode =" 422", description = "Falha ao processar a entidade"),
      @ApiResponse(responseCode =" 500", description = "Erro interno do servidor")
  })
  @DeleteMapping("/{produtoId}")
  Cliente removerProdutoDaWishlist(@PathVariable String clienteId, @PathVariable String produtoId);

  @Operation(summary = "Consulta todos os produtos da Wishlist do cliente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Sucesso", content = @Content(schema = @Schema(implementation = Set.class))),
      @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
      @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  @GetMapping
  List<Produto> consultarWishlistDoCliente(@PathVariable String clienteId);

  @Operation(summary = "Consulta se um determinado produto está na Wishlist de um cliente")
  @ApiResponses(value = {
      @ApiResponse(responseCode =" 200", description = "Consulta realizada com sucesso",  content = @Content(schema = @Schema(implementation = Boolean.class))),
      @ApiResponse(responseCode =" 400", description = "Solicitação inválida"),
      @ApiResponse(responseCode =" 404", description = "Cliente ou Produto não encontrado"),
      @ApiResponse(responseCode =" 500", description = "Erro interno do servidor")
  })
  @GetMapping("/{produtoId}")
  Boolean produtoEstaNaWishlist(@PathVariable String clienteId, @PathVariable String produtoId);

  @Operation(summary = "Limpa a lista do cliente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Wishlist limpa com sucesso"),
      @ApiResponse(responseCode = "400", description = "Solicitação inválida"),
      @ApiResponse(responseCode = "404", description = "Cliente não encontrado"),
      @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  @DeleteMapping("/clear")
  void limpaWishlistDoCliente(@PathVariable String clienteId);
}
