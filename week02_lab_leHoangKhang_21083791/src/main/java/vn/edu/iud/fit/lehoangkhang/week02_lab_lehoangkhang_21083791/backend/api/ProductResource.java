package vn.edu.iud.fit.lehoangkhang.week02_lab_lehoangkhang_21083791.backend.api;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import vn.edu.iud.fit.lehoangkhang.week02_lab_lehoangkhang_21083791.backend.business.EmployeeBO;
import vn.edu.iud.fit.lehoangkhang.week02_lab_lehoangkhang_21083791.backend.business.ProductBO;
import vn.edu.iud.fit.lehoangkhang.week02_lab_lehoangkhang_21083791.backend.entites.Product;
import vn.edu.iud.fit.lehoangkhang.week02_lab_lehoangkhang_21083791.backend.entites.ProductPrice;
import vn.edu.iud.fit.lehoangkhang.week02_lab_lehoangkhang_21083791.backend.enums.ProductStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Path("/products")
public class ProductResource {

    @Inject
    private ProductBO productBO;

    @GET
    @Path("/all")
    public String getAllProducts() {
        return "All products";
    }

    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response addProduct(
            @FormParam("name") String name,
            @FormParam("description") String description,
            @FormParam("manufacturerName") String manufacturerName,
            @FormParam("unit") String unit,
            @FormParam("status") String status,
            @FormParam("price") double price // Thêm giá cho sản phẩm
    ) {
        try {
            System.out.println("Hello from addProduct");
            System.out.println("name: " + name);
            System.out.println("description: " + description);
            System.out.println("manufacturerName: " + manufacturerName);
            System.out.println("unit: " + unit);
            System.out.println("status: " + status);
            System.out.println("price: " + price);
//            // Tạo đối tượng sản phẩm
//            Product product = new Product();
//            product.setName(name);
//            product.setDescription(description);
//            product.setManufacturerName(manufacturerName);
//            product.setUnit(unit);
//            product.setStatus(ProductStatus.valueOf(status));
//
//            ProductPrice productPrice = new ProductPrice();
//            productPrice.setPrice(price);
//            productPrice.setPriceDateTime(LocalDateTime.now());
//            productPrice.setProduct(product); // Liên kết giá với sản phẩm
//
//            product.getProductPrices().add(productPrice);
//            productBO.addProduct(product);

            return Response.ok("Product added successfully with price").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).entity("Error adding product with price").build();
        }
    }
}
