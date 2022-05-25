package com.training.resources;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

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
		int id = dao.insert(toBeAdded);
		toBeAdded.setId(id);
		return Response.created(URI.create("/products/"+id)).entity(toBeAdded).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getAll(){
		return dao.findAll();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response getById(@PathParam("id") int id) {
		
		Optional<Product> opt = dao.findById(id);
		if(opt.isPresent()) {
			return Response.ok(opt.get()).build();
		}else {
			throw new WebApplicationException(404);
		}
	}
	
	//DELETE /products/{id}
	@DELETE
	@Path("{id}")
	public Response deleteProduct(@PathParam("id") int pid) {
		boolean deleted = dao.deleteById(pid);
		if(deleted) {
			return Response.noContent().build();
		}else {
			throw new WebApplicationException(404);
		}
	}
	
	//PUT+json /products/{id}
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public Response updateExisting(@PathParam("id") int pid,Product p) {
		if(p.getId() != pid) {
			throw new WebApplicationException("path param id and object's id must be same", 400);
		}
		
		boolean updated = dao.update(p);
		if(updated) {
			return Response.noContent().build();
		}else {
			throw new WebApplicationException(Status.NOT_FOUND);
		}
	}
}
