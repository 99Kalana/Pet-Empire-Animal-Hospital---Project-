package lk.ijse.repository;

import lk.ijse.db.DbConnectionPET;
import lk.ijse.model.Medicine;
import lk.ijse.model.Treatments;
import lk.ijse.model.Veterinarian;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TreatmentsRepo {
    public static Treatments searchById(String id) throws SQLException {

        String sql = "SELECT * FROM treatments WHERE t_id = ?";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()){
            String t_id = resultSet.getString(1);
            String t_type = resultSet.getString(2);
            String t_desc = resultSet.getString(3);

            Treatments treatments = new Treatments(t_id, t_type, t_desc);

            return treatments;

        }

        return null;

    }

    public static boolean save(Treatments treatments) throws SQLException {

        String sql = "INSERT INTO treatments VALUES(?, ?, ?)";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, treatments.getId());
        pstm.setObject(2, treatments.getType());
        pstm.setObject(3, treatments.getDescription());

        return pstm.executeUpdate() > 0;

    }

    public static boolean update(Treatments treatments) throws SQLException {

        String sql = "UPDATE treatments SET t_type = ?, t_description = ? WHERE t_id = ? ";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, treatments.getType());
        pstm.setObject(2, treatments.getDescription());
        pstm.setObject(3, treatments.getId());

        return pstm.executeUpdate() > 0;

    }

    public static boolean delete(String id) throws SQLException {

        String sql = "DELETE FROM treatments WHERE t_id = ?";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1,id);

        return pstm.executeUpdate() > 0;

    }

    public static List<Treatments> getAll() throws SQLException {

        String sql = "SELECT * FROM treatments";

        PreparedStatement pstm = DbConnectionPET.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Treatments> treatmentsList = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String type = resultSet.getString(2);
            String description = resultSet.getString(3);

            Treatments treatments = new Treatments(id, type, description);
            treatmentsList.add(treatments);
        }
        return treatmentsList;

    }

}
