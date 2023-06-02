package com.silvioricardo.wishlist.adapter.out.db.mongodb;

import com.silvioricardo.wishlist.domain.Produto;
import com.silvioricardo.wishlist.domain.repository.ProdutoRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MongoProdutoRepository extends MongoRepository<Produto, String>, ProdutoRepository {
  //
}