package lk.ijse.repository;

import lk.ijse.db.DbConnectionPET;
import lk.ijse.model.Medicine;
import lk.ijse.model.Prescription;
import lk.ijse.model.Veterinarian;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionRepo {
    public static Prescription searchById(String id) throws SQLException {

        String sql = "SELECT * FROM prescription WHERE p_id = ?";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()){
            String p_id = resultSet.getString(1);
            String p_type = resultSet.getString(2);
            String p_veterinarianId = resultSet.getString(3);

            Prescription prescription = new Prescription(p_id, p_type, p_veterinarianId);

            return prescription;

        }

        return null;

    }

    public static boolean save(Prescription prescription) throws SQLException {

        String sql = "INSERT INTO prescription VALUES(?, ?, ?)";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, prescription.getId());
        pstm.setObject(2, prescription.getType());
        pstm.setObject(3, prescription.getVeterinarianId());


        return pstm.executeUpdate() > 0;

    }

    public static boolean update(Prescription prescription) throws SQLException {

        String sql = "UPDATE prescription SET p_type = ?, veterinarian_id = ? WHERE p_id = ? ";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, prescription.getType());
        pstm.setObject(2, prescription.getVeterinarianId());
        pstm.setObject(3, prescription.getId());

        return pstm.executeUpdate() > 0;

    }

    public static boolean delete(String id) throws SQLException {

        String sql = "DELETE FROM prescription WHERE p_id = ?";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1,id);

        return pstm.executeUpdate() > 0;

    }

    public static List<Prescription> getAll() throws SQLException {

        String sql = "SELECT * FROM prescription";

        PreparedStatement pstm = DbConnectionPET.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Prescription> prescriptionList = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String type = resultSet.getString(2);
            String veterinarianId = resultSet.getString(3);

            Prescription prescription = new Prescription(id, type, veterinarianId);
            prescriptionList.add(prescription);
        }
        return prescriptionList;

    }
}
