package com.training.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.training.api.Product;

public class ProductDAOJdbcImpl implements ProductDAO {
	
	ConnectionUtil connectionUtil;
	
	
	
	public ProductDAOJdbcImpl(ConnectionUtil connectionUtil) {
		super();
		this.connectionUtil = connectionUtil;
	}


	@Override
	public int save(Product toBeSaved) {
		int id = 0;
		String sqlToBeExecuted = 
		"insert into product(product_name,product_price,product_qoh) values ('"+toBeSaved.getName()+"',"+toBeSaved.getPrice()+","+toBeSaved.getQoh()+")";
		System.out.println("SQL = "+sqlToBeExecuted);
		
		try {
			Connection c = connectionUtil.getConnection();
			Statement s = c.createStatement();
			s.executeUpdate(sqlToBeExecuted);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return id;
	}
	
	
	@Override
	public List<Product> findAll(){
		List<Product> all = new ArrayList<Product>();
		
		try {
			Connection c = connectionUtil.getConnection();
			Statement s = c.createStatement();
			
			ResultSet rs = s.executeQuery("select * from product");
			while(rs.next()) {
				Product aProduct = new Product();
				aProduct.setId(rs.getInt("product_id"));
				aProduct.setName(rs.getString("product_name"));
				aProduct.setPrice(rs.getFloat("product_price"));
				aProduct.setQoh(rs.getInt("product_qoh"));
				
				all.add(aProduct);
			}
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return all;
	}


}
