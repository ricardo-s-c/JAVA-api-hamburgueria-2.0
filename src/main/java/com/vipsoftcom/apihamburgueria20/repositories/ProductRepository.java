package com.vipsoftcom.apihamburgueria20.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vipsoftcom.apihamburgueria20.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

}
