package controller;

import model.Category;
import model.Product;
import service.category.CategoryService;
import service.category.ICategoryService;
import service.product.IProductService;
import service.product.ProductService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ProductServlet", urlPatterns = "/products")
public class ProductServlet extends HttpServlet {

    private IProductService productService = new ProductService();
    private ICategoryService categoryService = new CategoryService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null){
            action = "";

        }
        switch (action){
            case "":
                showAllProduct(request, response);
                break;
            case "edit":
                editProduct(request, response);
                break;
            case "create":
                createNewProduct(request, response);
                break;

        }
    }

    private void createNewProduct(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String[] categoriesString = request.getParameterValues("categories");
        int[] category = new int[categoriesString.length];
        for (int i = 0; i < categoriesString.length; i++) {
            category[i] = Integer.parseInt(categoriesString[i]);
        }
        Product p = new Product(name, description);
        productService.save(p, category);
        try {
            response.sendRedirect("/products");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void editProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        int id = Integer.parseInt(request.getParameter("id"));
        Product p = new Product(id, name, description);
        this.productService.update(p);
        response.sendRedirect("/products");
//        RequestDispatcher requestDispatcher = request.getRequestDispatcher("edit.jsp");
//        request.setAttribute("p", p);
//        requestDispatcher.forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null){
            action = "";

        }
        switch (action){
            case "":
                showAllProduct(request, response);
                break;
            case "edit":
                showFormEidt(request, response);
                break;
            case "create":
                createProduct(request, response);
            case "permision":
                addProductPermision(request, response);
                break;

        }

    }

    private void createProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product = new Product();
        List<Category> categories = categoryService.findAll();
        RequestDispatcher rd = request.getRequestDispatcher("create.jsp");
        request.setAttribute("p", product);
        request.setAttribute("categories", categories);
        rd.forward(request, response);
    }

    private void addProductPermision(HttpServletRequest request, HttpServletResponse response) {
//        Product p = new Product("SP cua anh Hieu", "Mo ta sp demo");
//        int[] permision = {1,3, 4};
//        productService.save(p, permision);
    }

    private void showFormEidt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Product product = this.productService.findById(id);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("edit.jsp");
        request.setAttribute("p", product);
        requestDispatcher.forward(request, response);
    }

    private void showAllProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("list.jsp");
        List<Product> productList = productService.findAll();
        request.setAttribute("list", productList);
        requestDispatcher.forward(request, response);
    }
}
