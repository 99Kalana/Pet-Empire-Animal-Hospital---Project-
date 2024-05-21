package lk.ijse.repository;

import lk.ijse.db.DbConnectionPET;
import lk.ijse.model.Medicine;
import lk.ijse.model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierRepo {
    public static Supplier searchById(String id) throws SQLException {

        String sql = "SELECT * FROM supplier WHERE s_id = ?";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()){
            String s_id = resultSet.getString(1);
            String s_name = resultSet.getString(2);
            int s_contactNo = resultSet.getInt(3);
            String s_location = resultSet.getString(4);
            String s_email = resultSet.getString(5);
            String s_productType = resultSet.getString(6);
            int s_qtyOnHand = resultSet.getInt(7);

            Supplier supplier = new Supplier(s_id, s_name, s_contactNo, s_location, s_email, s_productType, s_qtyOnHand);

            return supplier;

        }

        return null;

    }

    public static boolean save(Supplier supplier) throws SQLException {

        String sql = "INSERT INTO supplier VALUES(?, ?, ?, ?, ?, ?, ?)";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, supplier.getId());
        pstm.setObject(2, supplier.getName());
        pstm.setObject(3, supplier.getContactNo());
        pstm.setObject(4, supplier.getLocation());
        pstm.setObject(5, supplier.getEmail());
        pstm.setObject(6, supplier.getProductType());
        pstm.setObject(7, supplier.getQtyOnHand());


        return pstm.executeUpdate() > 0;

    }

    public static boolean update(Supplier supplier) throws SQLException {

        String sql = "UPDATE supplier SET s_name = ?, s_contact_no = ?, s_location = ?, s_email = ?, product_type = ?, qty_on_hand = ?  WHERE s_id = ? ";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, supplier.getName());
        pstm.setObject(2, supplier.getContactNo());
        pstm.setObject(3, supplier.getLocation());
        pstm.setObject(4, supplier.getEmail());
        pstm.setObject(5, supplier.getProductType());
        pstm.setObject(6, supplier.getQtyOnHand());
        pstm.setObject(7, supplier.getId());

        return pstm.executeUpdate() > 0;

    }

    public static boolean delete(String id) throws SQLException {

        String sql = "DELETE FROM supplier WHERE s_id = ?";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1,id);

        return pstm.executeUpdate() > 0;

    }

    public static List<Supplier> getAll() throws SQLException {

        String sql = "SELECT * FROM supplier";

        PreparedStatement pstm = DbConnectionPET.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Supplier> supplierList = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            int contactNo = resultSet.getInt(3);
            String location = resultSet.getString(4);
            String email = resultSet.getString(5);
            String productType = resultSet.getString(6);
            int qtyOnHand = resultSet.getInt(7);


            Supplier supplier = new Supplier(id, name, contactNo, location, email, productType, qtyOnHand);
            supplierList.add(supplier);
        }
        return supplierList;

    }
}
