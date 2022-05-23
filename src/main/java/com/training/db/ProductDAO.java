package com.training.db;

import java.util.List;
import java.util.Optional;

import com.training.api.Product;

public interface ProductDAO {

	public int insert(Product toBeInserted);
	public Optional<Product> findById(int id);
	public List<Product> findAll();
	public boolean update(Product toBeUpdated);
	public boolean deleteById(int id);
}