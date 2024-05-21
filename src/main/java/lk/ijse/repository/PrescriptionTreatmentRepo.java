package lk.ijse.repository;

import lk.ijse.db.DbConnectionPET;
import lk.ijse.model.Medicine;
import lk.ijse.model.PrescriptionTreatment;
import lk.ijse.model.Veterinarian;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionTreatmentRepo {

    public static PrescriptionTreatment searchById(String id) throws SQLException {

        String sql = "SELECT * FROM prescription_treatment WHERE p_id = ?";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()){
            String pt_pId = resultSet.getString(1);
            String pt_tId = resultSet.getString(2);
            double pt_price = resultSet.getInt(3);
            String pt_date = resultSet.getString(4);
            String pt_time = resultSet.getString(5);

            PrescriptionTreatment prescriptionTreatment = new PrescriptionTreatment(pt_pId, pt_tId, pt_price, pt_date, pt_time);

            return prescriptionTreatment;

        }

        return null;

    }

    public static boolean save(PrescriptionTreatment prescriptionTreatment) throws SQLException {

        String sql = "INSERT INTO prescription_treatment VALUES(?, ?, ?, ?, ?)";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, prescriptionTreatment.getpId());
        pstm.setObject(2, prescriptionTreatment.gettId());
        pstm.setObject(3, prescriptionTreatment.getPrice());
        pstm.setObject(4, prescriptionTreatment.getDate());
        pstm.setObject(5, prescriptionTreatment.getTime());


        return pstm.executeUpdate() > 0;

    }

    public static boolean update(PrescriptionTreatment prescriptionTreatment) throws SQLException {

        String sql = "UPDATE prescription_treatment SET t_id = ?, treatment_price = ?, pt_date = ?, pt_time = ? WHERE p_id = ? ";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, prescriptionTreatment.gettId());
        pstm.setObject(2, prescriptionTreatment.getPrice());
        pstm.setObject(3, prescriptionTreatment.getDate());
        pstm.setObject(4, prescriptionTreatment.getTime());
        pstm.setObject(5, prescriptionTreatment.getpId());

        return pstm.executeUpdate() > 0;

    }

    public static boolean delete(String id) throws SQLException {

        String sql = "DELETE FROM prescription_treatment WHERE p_id = ?";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1,id);

        return pstm.executeUpdate() > 0;

    }

    public static List<PrescriptionTreatment> getAll() throws SQLException {

        String sql = "SELECT * FROM prescription_treatment";

        PreparedStatement pstm = DbConnectionPET.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<PrescriptionTreatment> prescriptionTreatmentList = new ArrayList<>();

        while (resultSet.next()) {
            String pId = resultSet.getString(1);
            String tId = resultSet.getString(2);
            double price = resultSet.getDouble(3);
            String date = String.valueOf(resultSet.getDate(4));
            String time = String.valueOf(resultSet.getTime(5));


            PrescriptionTreatment prescriptionTreatment = new PrescriptionTreatment(pId, tId, price, date, time);
            prescriptionTreatmentList.add(prescriptionTreatment);
        }
        return prescriptionTreatmentList;

    }

}
