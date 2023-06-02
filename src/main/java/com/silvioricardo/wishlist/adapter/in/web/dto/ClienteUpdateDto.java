package com.silvioricardo.wishlist.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import javax.validation.constraints.Email;
import javax.validation.constraints.Past;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class ClienteUpdateDto {

  @Email
  @Schema(example = "cliente@exemplo.com")
  private String email;

  @DateTimeFormat(pattern = "dd-MM-yyyy")
  @Past
  @Schema(example = "2000-01-01")
  private LocalDate dataNascimento;
}
