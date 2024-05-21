package lk.ijse.repository;

import lk.ijse.db.DbConnectionPET;
import lk.ijse.model.BillDetail;
import lk.ijse.model.Client;
import lk.ijse.model.Medicine;
import lk.ijse.model.Veterinarian;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicineRepo {
    public static Medicine searchById(String id) throws SQLException {

        String sql = "SELECT * FROM medicine WHERE m_id = ?";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()){
            String m_id = resultSet.getString(1);
            String m_name = resultSet.getString(2);
            String m_type = resultSet.getString(3);
            double m_price = resultSet.getDouble(4);
            String m_desc = resultSet.getString(5);
            int m_qtyOnHand = resultSet.getInt(6);

            Medicine medicine = new Medicine(m_id, m_name, m_type, m_price, m_desc, m_qtyOnHand);

            return medicine;

        }

        return null;

    }

    public static boolean save(Medicine medicine) throws SQLException {

        String sql = "INSERT INTO medicine VALUES(?, ?, ?, ?, ?, ?)";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, medicine.getId());
        pstm.setObject(2, medicine.getName());
        pstm.setObject(3, medicine.getType());
        pstm.setObject(4, medicine.getPrice());
        pstm.setObject(5, medicine.getDescription());
        pstm.setObject(6, medicine.getQtyOnHand());


        return pstm.executeUpdate() > 0;

    }

    public static boolean update(Medicine medicine) throws SQLException {

        String sql = "UPDATE medicine SET m_name = ?, m_type = ?, m_price = ?, m_description = ?, m_qty_on_hand = ? WHERE m_id = ? ";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, medicine.getName());
        pstm.setObject(2, medicine.getType());
        pstm.setObject(3, medicine.getPrice());
        pstm.setObject(4, medicine.getDescription());
        pstm.setObject(5, medicine.getQtyOnHand());
        pstm.setObject(6, medicine.getId());

        return pstm.executeUpdate() > 0;

    }

    public static boolean delete(String id) throws SQLException {

        String sql = "DELETE FROM medicine WHERE m_id = ?";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1,id);

        return pstm.executeUpdate() > 0;

    }

    public static List<Medicine> getAll() throws SQLException {

        String sql = "SELECT * FROM medicine";

        PreparedStatement pstm = DbConnectionPET.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Medicine> medicineList = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String type = resultSet.getString(3);
            double price = resultSet.getDouble(4);
            String description = resultSet.getString(5);
            int qtyOnHand = resultSet.getInt(6);



            Medicine medicine = new Medicine(id, name, type, price, description, qtyOnHand);
            medicineList.add(medicine);
        }
        return medicineList;

    }

    public static List<String> getIds() throws SQLException {

        String sql = "SELECT m_id FROM medicine";
        PreparedStatement pstm = DbConnectionPET.getInstance().getConnection()
                .prepareStatement(sql);

        List<String> idList = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            String id = resultSet.getString(1);
            idList.add(id);
        }
        return idList;

    }

    public static boolean updates(List<BillDetail> billList) throws SQLException {

        for (BillDetail bd : billList){

            boolean isUpdateQty = updateQty(bd.getMedicineId(), bd.getMedicineQty());

            if (!isUpdateQty){
                return false;
            }

        }
        return true;
    }

    private static boolean updateQty(String medicineId, int medicineQty) throws SQLException {

        String sql = "UPDATE medicine SET m_qty_on_hand = m_qty_on_hand - ? WHERE m_id = ?";

        PreparedStatement pstm = DbConnectionPET.getInstance().getConnection().prepareStatement(sql);

        pstm.setInt(1,medicineQty);
        pstm.setString(2,medicineId);

        return pstm.executeUpdate() > 0;

    }


}
