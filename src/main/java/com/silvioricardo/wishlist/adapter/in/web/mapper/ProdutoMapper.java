package com.silvioricardo.wishlist.adapter.in.web.mapper;

import com.silvioricardo.wishlist.adapter.in.web.dto.ProdutoDto;
import com.silvioricardo.wishlist.adapter.in.web.dto.ProdutoUpdateDto;
import com.silvioricardo.wishlist.domain.Produto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class ProdutoMapper {
  public Produto toDomain(ProdutoDto dto) {
    Produto produto = new Produto();

    BeanUtils.copyProperties(dto, produto);
    return produto;
  }

  public Produto toDomain(ProdutoUpdateDto dto) {
    Produto produto = new Produto();

    BeanUtils.copyProperties(dto, produto);
    return produto;
  }

  public ProdutoDto toDto(Produto produto) {
    ProdutoDto dto = new ProdutoDto();

    BeanUtils.copyProperties(produto, dto);
    return dto;
  }
}
