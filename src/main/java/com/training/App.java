package com.training;

import com.training.db.ConnectionUtil;
import com.training.db.ProductDAOJdbcImpl;
import com.training.resources.ProductResource;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class App extends Application<AppConfiguration>{

	public static void main(String[] args) throws Exception {
		new App().run(args);
	}
	
	@Override
	public void run(AppConfiguration configuration, Environment environment) throws Exception {
		
		ConnectionUtil connectionUtil = new ConnectionUtil(configuration.getUrl(), configuration.getUsername(), configuration.getPassword());
		
		ProductDAOJdbcImpl dao = new ProductDAOJdbcImpl(connectionUtil);
		
		ProductResource resource = new ProductResource(dao);
		
		environment.jersey().register(resource);
		
	}

}
