package com.training;

import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import com.training.db.ConnectionUtil;
import com.training.db.ProductDAOJdbcImpl;
import com.training.resources.ExampleSecureResource;
import com.training.resources.ProductResource;
import com.training.security.AppAuthenticator;
import com.training.security.AppAuthorizer;
import com.training.security.User;

import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.setup.Environment;
// java com.training.App server configuration.yml
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
		
		// -------------------- Seccurity Configuration Follows ------------------
		environment.jersey().register(new ExampleSecureResource());
		//Setup Basic Security
				environment.jersey().register(new AuthDynamicFeature(new BasicCredentialAuthFilter.Builder<User>()
		                .setAuthenticator(new AppAuthenticator())
		                .setAuthorizer(new AppAuthorizer())
		                .setRealm("App Security")
		                .buildAuthFilter()));
		        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));
		        environment.jersey().register(RolesAllowedDynamicFeature.class);
		        
		//------------------------------------------------------------------------
		
	}

}
