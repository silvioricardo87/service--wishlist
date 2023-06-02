package com.silvioricardo.wishlist.adapter.in.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.AccessMode;
import java.time.LocalDate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class ClienteDto {
  @Schema(accessMode = AccessMode.READ_ONLY)
  @JsonInclude(JsonInclude.Include.NON_NULL)
  private String id;

  @NotBlank
  @Size(min = 2, max = 100)
  @Schema(example = "João da Silva")
  private String nome;

  @Email
  @Schema(example = "joao@silva.com")
  private String email;

  @DateTimeFormat(pattern = "dd-MM-yyyy")
  @Past
  @Schema(example = "01-01-2000")
  private LocalDate dataNascimento;

  @CPF
  @Schema(example = "19263953015")
  private String cpf;
}
