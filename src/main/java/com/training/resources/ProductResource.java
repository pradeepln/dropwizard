package com.training.resources;

import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.training.api.Product;
import com.training.db.ProductDAO;

@Path("/products")
public class ProductResource {

	ProductDAO dao;

	public ProductResource(ProductDAO dao) {
		super();
		this.dao = dao;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addProduct(Product toBeAdded) {
		int id = dao.save(toBeAdded);
		toBeAdded.setId(id);
		return Response.created(URI.create("/products/1")).entity(toBeAdded).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getAll(){
		return dao.findAll();
	}
}
