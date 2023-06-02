package com.silvioricardo.wishlist.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProdutoDto {

  @Schema(accessMode = AccessMode.READ_ONLY)
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String id;

  @NotBlank
  @Schema(example = "Macbook Pro")
  private String nome;

  @NotBlank
  @Schema(example = "Laptop da Apple com 16GB RAM e 512GB SSD")
  private String descricao;

  @DecimalMin("0.01")
  @Schema(example = "14999.90")
  private double preco;

  @NotBlank
  @Schema(example = "Eletrônicos")
  private String categoria;
}
