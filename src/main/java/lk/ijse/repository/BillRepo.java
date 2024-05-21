package lk.ijse.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import lk.ijse.db.DbConnectionPET;
import lk.ijse.model.Bill;

public class BillRepo {
    public static String getCurrentBillId() throws SQLException {

        String sql = "SELECT bill_id FROM bills ORDER BY bill_id DESC LIMIT 1";
        PreparedStatement pstm = DbConnectionPET.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        if(resultSet.next()) {
            String  billId = resultSet.getString(1);
            return billId;
        }
        return null;

    }

    public static boolean save(Bill bill) throws SQLException {

        String sql = "INSERT INTO bills VALUES(?, ?, ?)";

        PreparedStatement pstm = DbConnectionPET.getInstance().getConnection().prepareStatement(sql);

        pstm.setString(1, bill.getBillId());
        pstm.setString(2, bill.getClientId());
        pstm.setDate(3, bill.getDate());

        return pstm.executeUpdate() > 0;

    }
}
