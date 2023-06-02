package com.silvioricardo.wishlist.adapter.in.web;

import com.silvioricardo.wishlist.adapter.in.web.dto.ProdutoDto;
import com.silvioricardo.wishlist.adapter.in.web.dto.ProdutoUpdateDto;
import com.silvioricardo.wishlist.domain.Produto;
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
@Tag(name = "Produtos", description = "Definição de endpoints para produtos")
@RequestMapping(value = "/produtos", produces = "application/json")
public interface ProdutoContract {

  @Operation(summary = "Cria um novo produto")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Produto criado com sucesso", content = @Content(schema = @Schema(implementation = Produto.class))),
      @ApiResponse(responseCode = "400", description = "Solicitação inválida"),
      @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  ProdutoDto criarProduto(@Valid @RequestBody ProdutoDto produtoDto);

  @Operation(summary = "Busca um produto por ID")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Sucesso",  content = @Content(schema = @Schema(implementation = Produto.class))),
      @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
      @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  @GetMapping("/{id}")
  Optional<Produto> obterProduto(@PathVariable String id);

  @Operation(summary = "Atualiza um produto existente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso",  content = @Content(schema = @Schema(implementation = Produto.class))),
      @ApiResponse(responseCode = "400", description = "Solicitação inválida"),
      @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
      @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  @PutMapping("/{id}")
  ProdutoDto atualizarProduto(@PathVariable String id, @Valid @RequestBody ProdutoUpdateDto produtoUpdateDto);

  @Operation(summary = "Lista todos os produtos")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Sucesso",  content = @Content(schema = @Schema(implementation = List.class))),
      @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  @GetMapping
  List<Produto> obterTodosProdutos();

  @Operation(summary = "Deleta um produto existente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "204", description = "Produto deletado com sucesso"),
      @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
      @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
  })
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @DeleteMapping("/{id}")
  void deletarProduto(@PathVariable String id);
}
