package frontend.models;



import backend.dtos.ProductDTO;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.GenericType;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

public class ProductModel {
    private static final String BASE_URI = "http://localhost:8080/week03_lab_leHoangKhang_21083791-1.0-SNAPSHOT/api/products";

    public List<ProductDTO> getAllProducts() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(BASE_URI);
        return target.request(MediaType.APPLICATION_JSON)
                .get(new GenericType<List<ProductDTO>>() {});
    }

    public Response addProduct(ProductDTO product) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(BASE_URI);
        return target.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(product, MediaType.APPLICATION_JSON));
    }
}
