package backend.resources.products;

import backend.business.ProductLocal;
import backend.dtos.ProductDTO;
import backend.data.entities.Product;
import backend.data.entities.ProductPrice;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/products")
public class ProductResource {
    @EJB
    private ProductLocal productService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductDTO> productDTOs = products.stream()
                .map(product -> {
                    List<ProductPrice> prices = productService.getPricesByProductId(product.getId());
                    return ProductDTO.fromEntity(product, prices);
                })
                .collect(Collectors.toList());

        return Response.ok(productDTOs).build();
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addProduct(ProductDTO productDTO) {
        Product product = ProductDTO.toEntity(productDTO);
        ProductPrice price = productDTO.getPrices() != null && !productDTO.getPrices().isEmpty() ? ProductDTO.toPriceEntity(productDTO.getPrices().get(0)) : null;

        // Lưu sản phẩm và giá vào cơ sở dữ liệu
        if (price != null) {
            productService.addProduct(product, price);
        } else {
            productService.addProduct(product, null);
        }

        return Response.status(Response.Status.CREATED).build();
    }
}