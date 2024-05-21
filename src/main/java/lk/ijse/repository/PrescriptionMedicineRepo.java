package lk.ijse.repository;

import lk.ijse.db.DbConnectionPET;
import lk.ijse.model.Medicine;
import lk.ijse.model.PrescriptionMedicine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionMedicineRepo {
    public static PrescriptionMedicine searchById(String id) throws SQLException {

        String sql = "SELECT * FROM prescription_medicine WHERE m_id = ?";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()){
            String m_id = resultSet.getString(1);
            String p_id = resultSet.getString(2);

            PrescriptionMedicine prescriptionMedicine = new PrescriptionMedicine(m_id, p_id);

            return prescriptionMedicine;

        }

        return null;

    }

    public static boolean save(PrescriptionMedicine prescriptionMedicine) throws SQLException {

        String sql = "INSERT INTO prescription_medicine VALUES(?, ?)";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, prescriptionMedicine.getId());
        pstm.setObject(2, prescriptionMedicine.getpId());


        return pstm.executeUpdate() > 0;

    }

    public static boolean update(PrescriptionMedicine prescriptionMedicine) throws SQLException {

        String sql = "UPDATE prescription_medicine SET p_id = ? WHERE m_id = ? ";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1,prescriptionMedicine.getpId());
        pstm.setObject(2,prescriptionMedicine.getId());

        return pstm.executeUpdate() > 0;

    }

    public static boolean delete(String id) throws SQLException {

        String sql = "DELETE FROM prescription_medicine WHERE m_id = ?";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1,id);

        return pstm.executeUpdate() > 0;

    }


    public static List<PrescriptionMedicine> getAll() throws SQLException {

        String sql = "SELECT * FROM prescription_medicine";

        PreparedStatement pstm = DbConnectionPET.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<PrescriptionMedicine> prescriptionMedicineList = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String pId = resultSet.getString(2);

            PrescriptionMedicine prescriptionMedicine = new PrescriptionMedicine(id, pId);
            prescriptionMedicineList.add(prescriptionMedicine);
        }
        return prescriptionMedicineList;

    }
}
