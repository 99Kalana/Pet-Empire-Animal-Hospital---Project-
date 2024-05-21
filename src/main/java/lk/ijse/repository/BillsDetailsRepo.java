package lk.ijse.repository;

import lk.ijse.db.DbConnectionPET;
import lk.ijse.model.BillRecord;
import lk.ijse.model.BillsDetails;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BillsDetailsRepo {

    public static BillsDetails searchById(String id) throws SQLException {

        String sql = "SELECT * FROM bills_details WHERE bill_id = ?";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()){

            String bill_id = resultSet.getString(1);
            String medicine_id = resultSet.getString(2);
            int medicine_qty = resultSet.getInt(3);
            double medicine_price = resultSet.getDouble(4);

            BillsDetails billsDetails = new BillsDetails(bill_id,medicine_id,medicine_qty,medicine_price);

            return billsDetails;

        }

        return null;

    }

    public static boolean delete(String id) throws SQLException {

        String sql = "DELETE FROM bills_details WHERE bill_id = ?";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1,id);

        return pstm.executeUpdate() > 0;

    }


    public static List<BillsDetails> getAll() throws SQLException{

        String sql = "SELECT * FROM bills_details";

        PreparedStatement pstm = DbConnectionPET.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<BillsDetails> billsDetailsList = new ArrayList<>();

        while (resultSet.next()) {
            String billId = resultSet.getString(1);
            String medicineId = resultSet.getString(2);
            int medicineQty = resultSet.getInt(3);
            double medicinePrice = resultSet.getDouble(4);

            BillsDetails billsDetails = new BillsDetails(billId, medicineId, medicineQty, medicinePrice);
            billsDetailsList.add(billsDetails);
        }
        return billsDetailsList;

    }
}
