package com.silvioricardo.wishlist.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ProdutoUpdateDto {

  @Schema(example = "Este é um ótimo produto")
  private String descricao;

  @Schema(example = "99.99")
  private double preco;

  @Schema(example = "Eletrônicos")
  private String categoria;
}
