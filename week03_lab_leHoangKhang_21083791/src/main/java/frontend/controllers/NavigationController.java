package frontend.controllers;

import frontend.models.ProductModel;
import backend.dtos.ProductDTO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;

import java.util.List;

@Path("/navigate")
public class NavigationController {

    private final ProductModel productModel = new ProductModel();

    @GET
    @Path("/toProduct")
    @Produces("text/html")
    public void navigateToProduct(@Context HttpServletRequest request, @Context HttpServletResponse response) {
        try {
            List<ProductDTO> products = productModel.getAllProducts();
            request.setAttribute("products", products);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/product.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to load products.");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @GET
    @Path("/toPriceManagerment")
    @Produces("text/html")
    public void navigateToPriceManagerment(@Context HttpServletRequest request, @Context HttpServletResponse response) {
        try {
            List<ProductDTO> products = productModel.getAllProducts();
            request.setAttribute("products", products);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/price_management.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to load products.");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    @GET
    @Path("/toAddProduct")
    @Produces("text/html")
    public void navigateToAddProduct(@Context HttpServletRequest request, @Context HttpServletResponse response) {
        try {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/views/add_product.jsp");
            dispatcher.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to load products.");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
