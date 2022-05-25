package com.training.resources;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/")
public class ExampleSecureResource {
	
	
	@Produces("text/plain")
	@GET
	@Path("public/data")
	public String aMethodForAll() {
		return "data for public";
		
	}
	
	@RolesAllowed("USER")
	@Produces("text/plain")
	@GET
	@Path("users/data")
	public String aMethodForLoggedInUser() {
		return "data for logged in user";
		
	}
	
	
	@RolesAllowed("ADMIN")
	@Produces("text/plain")
	@GET
	@Path("admin/data")
	public String aMethodForAdmin() {
		return "data for admin";
		
	}

}
