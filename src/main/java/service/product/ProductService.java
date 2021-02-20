package service.product;

import model.Product;
import service.SingletonConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductService implements IProductService {


    private Connection connection = SingletonConnection.getConnection();


    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();
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

    @Override
    public boolean save(Product product, int[] p) {

        // for insert a new user

        PreparedStatement pstmt = null;

        // for assign permision to user

        PreparedStatement pstmtAssignment = null;

        // for getting product id

        ResultSet rs = null;

        try {

            connection = SingletonConnection.getConnection();

            // set auto commit to false

            connection.setAutoCommit(false);

            //

            // Bước 1: Thêm 1 sản phẩm vào bảng product

            //

            pstmt = connection.prepareStatement("INSERT INTO product  (name, description) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, product.getName());

            pstmt.setString(2, product.getDescription());

            int rowAffected = pstmt.executeUpdate();
//            pstmt.executeUpdate();

            // Bước 2: Lấy id của sản phẩm đó

            rs = pstmt.getGeneratedKeys();

            int pId = 0;

            if (rs.next())

                pId = rs.getInt(1);

            //

            // Thêm mới vào bảng product_categories

            //

            if (rowAffected == 1) {

                // assign permision to product

                String sqlPivot = "INSERT INTO product_category(product_id,category_id) VALUES(?,?)";

                pstmtAssignment = connection.prepareStatement(sqlPivot);
                //Lấy danh sách id của permision và thêm mới vào bảng product_permision
                for (int permisionId : p) {

                    pstmtAssignment.setInt(1, pId);

                    pstmtAssignment.setInt(2, permisionId);

                    pstmtAssignment.executeUpdate();

                }

                connection.commit();

            } else {

                connection.rollback();

            }

        } catch (SQLException ex) {

            // roll back the transaction

            try {

                if (connection != null)

                    connection.rollback();

            } catch (SQLException e) {

                System.out.println(e.getMessage());

            }

            System.out.println(ex.getMessage());

        } finally {

            try {

                if (rs != null) rs.close();

                if (pstmt != null) pstmt.close();

                if (pstmtAssignment != null) pstmtAssignment.close();

                if (connection != null) connection.close();

            } catch (SQLException e) {

                System.out.println(e.getMessage());

            }

        }
        return false;
    }

}