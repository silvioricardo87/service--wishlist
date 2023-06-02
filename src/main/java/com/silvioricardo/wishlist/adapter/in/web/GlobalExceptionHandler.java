package com.silvioricardo.wishlist.adapter.in.web;

import com.mongodb.DuplicateKeyException;
import com.mongodb.MongoWriteException;
import com.silvioricardo.wishlist.adapter.in.exception.BadRequestException;
import com.silvioricardo.wishlist.adapter.in.exception.InternalErrorException;
import com.silvioricardo.wishlist.adapter.in.exception.ResourceNotFoundException;
import com.silvioricardo.wishlist.adapter.in.exception.UnauthorizedException;
import com.silvioricardo.wishlist.usecase.exception.BusinessException;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(DuplicateKeyException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<String> handleDuplicateKeyException(DuplicateKeyException e) {
    return ResponseEntity.badRequest().body("Já existe um registro com o mesmo valor para o campo " + e.getMessage());
  }

  @ExceptionHandler(MongoWriteException.class)
  public ResponseEntity<String> handleMongoWriteException(MongoWriteException e) {
    if (e.getError().getCode() == 11000) {
      return new ResponseEntity<>("Já existe este registro na base de dados.", HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>("Erro no banco de dados.", HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<List<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    List<String> listErrors = e.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
        .toList();

    return ResponseEntity.badRequest().body(listErrors);
  }

  @ExceptionHandler(BadRequestException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<String> handleBadRequestException(BadRequestException e) {
    return ResponseEntity.badRequest().body(e.getMessage());
  }

  @ExceptionHandler(UnauthorizedException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  public void handleUnauthorizedException() {
    //
  }

  @ExceptionHandler(InternalErrorException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public void handleInternalErrorException() {
    //
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void handleResourceNotFoundException() {
    //
  }

  @ExceptionHandler(BusinessException.class)
  public ResponseEntity<String> handleBusinessException(BusinessException e) {
    return ResponseEntity.unprocessableEntity().body(e.getMessage());
  }
}
