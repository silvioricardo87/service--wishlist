package com.silvioricardo.wishlist.domain;

import java.util.Objects;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "produtos")
public class Produto {
  @Id
  private String id;

  private String nome;
  private String descricao;
  private double preco;
  private String categoria;

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null || getClass() != obj.getClass())
      return false;
    Produto produto = (Produto) obj;
    return Objects.equals(id, produto.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
