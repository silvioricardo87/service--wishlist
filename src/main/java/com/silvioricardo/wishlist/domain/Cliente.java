package com.silvioricardo.wishlist.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Document(collection = "clientes")
public class Cliente {
  @Id
  private String id;
  private String nome;
  private String email;

  @DateTimeFormat(pattern = "dd-MM-yyyy")
  private LocalDate dataNascimento;

  @Indexed(unique = true)
  private String cpf;
  private List<Produto> wishlist = new ArrayList<>();
}
