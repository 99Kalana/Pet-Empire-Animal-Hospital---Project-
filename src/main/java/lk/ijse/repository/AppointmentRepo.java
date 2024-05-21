package lk.ijse.repository;

import lk.ijse.db.DbConnectionPET;
import lk.ijse.model.Appointment;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AppointmentRepo {
    public static Appointment searchById(String id) throws SQLException {

        String sql = "SELECT * FROM appointment WHERE ap_id = ?";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()){
            String a_id = resultSet.getString(1);
            String a_clientId = resultSet.getString(2);
            int a_no = resultSet.getInt(3);
            String a_date = String.valueOf(resultSet.getDate(4));
            String a_time = String.valueOf(resultSet.getTime(5));


            Appointment appointment = new Appointment(a_id, a_clientId, a_no, a_date, a_time);

            return appointment;

        }

        return null;

    }

    public static boolean save(Appointment appointment) throws SQLException {

        String sql = "INSERT INTO appointment VALUES(?, ?, ?, ?, ?)";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, appointment.getId());
        pstm.setObject(2, appointment.getClientId());
        pstm.setObject(3, appointment.getNo());
        pstm.setObject(4, appointment.getDate());
        pstm.setObject(5, appointment.getTime());


        return pstm.executeUpdate() > 0;

    }

    public static boolean update(Appointment appointment) throws SQLException {

        String sql = "UPDATE appointment SET client_id = ?, ap_no = ?, ap_date = ?, ap_time = ? WHERE ap_id = ? ";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, appointment.getClientId());
        pstm.setObject(2, appointment.getNo());
        pstm.setObject(3, appointment.getDate());
        pstm.setObject(4, appointment.getTime());
        pstm.setObject(5, appointment.getId());


        return pstm.executeUpdate() > 0;

    }

    public static boolean delete(String id) throws SQLException {

        String sql = "DELETE FROM appointment WHERE ap_id = ?";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1,id);

        return pstm.executeUpdate() > 0;

    }

    public static List<Appointment> getAll() throws SQLException {
        String sql = "SELECT * FROM appointment";

        PreparedStatement pstm = DbConnectionPET.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Appointment> appointmentList = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String clientId = resultSet.getString(2);
            int no = resultSet.getInt(3);
            String date = String.valueOf(resultSet.getDate(4));
            String time = String.valueOf(resultSet.getTime(5));

            Appointment appointment = new Appointment(id, clientId, no, date, time);
            appointmentList.add(appointment);
        }
        return appointmentList;
    }


}
