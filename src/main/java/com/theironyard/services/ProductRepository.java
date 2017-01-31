package com.theironyard.services;

import com.theironyard.entities.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by dlocke on 1/31/17.
 */
public interface ProductRepository extends CrudRepository<Product, Integer> {
    List<Product> findByUser(String userName);
}
