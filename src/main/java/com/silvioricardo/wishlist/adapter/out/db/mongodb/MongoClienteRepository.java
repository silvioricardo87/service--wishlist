package com.silvioricardo.wishlist.adapter.out.db.mongodb;

import com.silvioricardo.wishlist.domain.Cliente;
import com.silvioricardo.wishlist.domain.repository.ClienteRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoClienteRepository extends MongoRepository<Cliente, String>, ClienteRepository {
  //
}