package lk.ijse.repository;

import lk.ijse.db.DbConnectionPET;
import lk.ijse.model.BillRecord;
import lk.ijse.model.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillRecordRepo {
    public static BillRecord searchById(String id) throws SQLException {

        String sql = "SELECT * FROM bills WHERE bill_id = ?";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()){
            String bill_id = resultSet.getString(1);
            String client_id = resultSet.getString(2);
            String date = resultSet.getString(3);

            BillRecord billRecord = new BillRecord(bill_id, client_id, date);

            return billRecord;

        }

        return null;

    }

    public static boolean delete(String id) throws SQLException {

        String sql = "DELETE FROM bills WHERE bill_id = ?";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1,id);

        return pstm.executeUpdate() > 0;

    }

    public static List<BillRecord> getAll() throws SQLException {

        String sql = "SELECT * FROM bills";

        PreparedStatement pstm = DbConnectionPET.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<BillRecord> billRecordList = new ArrayList<>();

        while (resultSet.next()) {
            String billId = resultSet.getString(1);
            String clientId = resultSet.getString(2);
            String date = resultSet.getString(3);

            BillRecord billRecord = new BillRecord(billId, clientId, date);
            billRecordList.add(billRecord);
        }
        return billRecordList;

    }
}
