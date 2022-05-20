package com.training.db;

import java.util.List;

import com.training.api.Product;

public interface ProductDAO {

	int save(Product toBeSaved);

	List<Product> findAll();

}