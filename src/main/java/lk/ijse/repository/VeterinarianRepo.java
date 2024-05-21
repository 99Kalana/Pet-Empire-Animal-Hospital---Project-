package lk.ijse.repository;

import lk.ijse.db.DbConnectionPET;
import lk.ijse.model.Appointment;
import lk.ijse.model.Medicine;
import lk.ijse.model.Veterinarian;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VeterinarianRepo {
    public static Veterinarian searchById(String id) throws SQLException {

        String sql = "SELECT * FROM veterinarian WHERE veterinarian_id = ?";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()){
            String v_id = resultSet.getString(1);
            String v_name = resultSet.getString(2);
            int v_contactNo = resultSet.getInt(3);
            String v_email = resultSet.getString(4);

            Veterinarian veterinarian = new Veterinarian(v_id, v_name, v_contactNo, v_email);

            return veterinarian;

        }

        return null;

    }

    public static boolean save(Veterinarian veterinarian) throws SQLException {

        String sql = "INSERT INTO veterinarian VALUES(?, ?, ?, ?)";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, veterinarian.getId());
        pstm.setObject(2, veterinarian.getName());
        pstm.setObject(3, veterinarian.getContactNo());
        pstm.setObject(4, veterinarian.getEmail());

        return pstm.executeUpdate() > 0;

    }

    public static boolean update(Veterinarian veterinarian) throws SQLException {

        String sql = "UPDATE veterinarian SET veterinarian_name = ?, veterinarian_contact_no = ?, veterinarian_email = ? WHERE veterinarian_id = ? ";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, veterinarian.getName());
        pstm.setObject(2, veterinarian.getContactNo());
        pstm.setObject(3, veterinarian.getEmail());
        pstm.setObject(4, veterinarian.getId());

        return pstm.executeUpdate() > 0;

    }

    public static boolean delete(String id) throws SQLException {

        String sql = "DELETE FROM veterinarian WHERE veterinarian_id = ?";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1,id);

        return pstm.executeUpdate() > 0;

    }

    public static List<Veterinarian> getAll() throws SQLException {

        String sql = "SELECT * FROM veterinarian";

        PreparedStatement pstm = DbConnectionPET.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Veterinarian> veterinarianList = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            int contactNo = resultSet.getInt(3);
            String email = resultSet.getString(4);

            Veterinarian veterinarian = new Veterinarian(id, name, contactNo, email);
            veterinarianList.add(veterinarian);
        }
        return veterinarianList;

    }

    public static List<String> getIds() throws SQLException {

        String sql = "SELECT veterinarian_id FROM veterinarian";
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
}
