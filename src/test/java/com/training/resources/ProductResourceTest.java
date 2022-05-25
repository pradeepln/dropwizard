package com.training.resources;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import io.dropwizard.testing.junit5.ResourceExtension;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import com.training.api.Product;
import com.training.db.ProductDAO;

import javax.ws.rs.core.Response;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(DropwizardExtensionsSupport.class)
class ProductResourceTest {
    private static final ProductDAO DAO = mock(ProductDAO.class);
    private static final ResourceExtension EXT = ResourceExtension.builder()
            .addResource(new ProductResource(DAO))
            .build();
    private Product product;

    @BeforeEach
    void setup() {
        product = new Product();
        product.setId(1);
    }

    @AfterEach
    void tearDown() {
        reset(DAO);
    }

    @Test
    void getPersonSuccess() {
        when(DAO.findById(1)).thenReturn(Optional.of(product));

        Product found = EXT.target("/products/1").request().get(Product.class);

        assertThat(found.getId()).isEqualTo(product.getId());
        verify(DAO).findById(1);
    }

    @Test
    void getPersonNotFound() {
        when(DAO.findById(2)).thenReturn(Optional.empty());
        final Response response = EXT.target("/products/2").request().get();

        assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
        verify(DAO).findById(2);
    }
}
