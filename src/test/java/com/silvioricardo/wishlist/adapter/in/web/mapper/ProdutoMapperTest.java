package com.silvioricardo.wishlist.adapter.in.web.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.silvioricardo.wishlist.adapter.in.web.dto.ProdutoDto;
import com.silvioricardo.wishlist.adapter.in.web.dto.ProdutoUpdateDto;
import com.silvioricardo.wishlist.domain.Produto;
import org.junit.jupiter.api.Test;

class ProdutoMapperTest {

  private final ProdutoMapper mapper = new ProdutoMapper();

  private final Produto produto = new Produto();
  private final ProdutoDto produtoDto = new ProdutoDto();

  void criaMockProduto(){
    produto.setId("a1b2c3d4e5");
    produto.setNome("iPhone 14 Pro");
    produto.setDescricao("iPhone 14 Pro 256GB");
    produto.setPreco(6499.99);
    produto.setCategoria("Celular");
  }

  void criaMockProdutoDto(){
    produtoDto.setNome("iPhone 14 Pro");
    produtoDto.setDescricao("iPhone 14 Pro 256GB AZUL");
    produtoDto.setPreco(5999.99);
    produtoDto.setCategoria("Smartphone");
  }

  @Test
  void deveMapearDtoToDomain() {
    criaMockProdutoDto();
    Produto produto = mapper.toDomain(produtoDto);

    assertEquals(produtoDto.getNome(), produto.getNome());
    assertEquals(produtoDto.getDescricao(), produto.getDescricao());
    assertEquals(produtoDto.getPreco(), produto.getPreco());
  }

  @Test
  void deveMapearUpdateDtoToDomain() {
    ProdutoUpdateDto dto = new ProdutoUpdateDto();
    dto.setCategoria("Vinhos");
    dto.setPreco(99.99);

    Produto produto = mapper.toDomain(dto);

    assertEquals(dto.getCategoria(), produto.getCategoria());
    assertEquals(dto.getPreco(), produto.getPreco());
  }

  @Test
  void deveMapearDomainToDto() {
    criaMockProduto();
    ProdutoDto dto = mapper.toDto(produto);

    assertEquals(produto.getId(), dto.getId());
    assertEquals(produto.getNome(), dto.getNome());
    assertEquals(produto.getPreco(), dto.getPreco());
  }
}
