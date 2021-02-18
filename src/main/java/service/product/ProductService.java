package service.product;

import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductService implements IProductService {

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3308/productmanager",
                    "root",
                    "123456"
            );
        } catch (ClassNotFoundException e) {
            System.out.println("không có driver");
        } catch (SQLException throwables) {
            System.out.println("Không kết nối được");
        }
        System.out.println("ket noi thanh cong");

        return connection;
    }


    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
            Connection connection = getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from product");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                products.add(new Product(id, name, description));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return products;
    }

    @Override
    public Product findById(int id) {
       Product product= null;
       Connection connection = getConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("{CALL get_product_by_id(?)}");
            callableStatement.setInt(1, id);
            ResultSet resultSet = callableStatement.executeQuery();
            while (resultSet.next()){
//                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String desription = resultSet.getString("description");
                product = new Product(id, name, desription);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
//        try {
//            PreparedStatement p = connection.prepareStatement("select * from product where id=?");
//            p.setInt(1, id);
//            ResultSet resultSet = p.executeQuery();
//            while (resultSet.next()){
////                int id = resultSet.getInt("id");
//                String name = resultSet.getString("name");
//                String desription = resultSet.getString("description");
//                product = new Product(id, name, desription);
//            }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
        return product;
    }

    @Override
    public boolean update(Product product) {
        Connection connection = getConnection();
        boolean check = false;
        try {
            PreparedStatement p = connection.prepareStatement("update product set name= ?, description= ? where id =?");
            p.setInt(3, product.getId());
            p.setString(1, product.getName());
            p.setString(2, product.getDescription());
            check = p.executeUpdate() >0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return check;
    }
}