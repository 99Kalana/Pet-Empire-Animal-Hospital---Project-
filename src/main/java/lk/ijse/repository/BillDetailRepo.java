package lk.ijse.repository;

import lk.ijse.db.DbConnectionPET;
import lk.ijse.model.BillDetail;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class BillDetailRepo {
    public static boolean save(List<BillDetail> billList) throws SQLException {
        
        for (BillDetail bd : billList){
            boolean isSaved = saved(bd);
            if (!isSaved){
                return false;
            }
        }
        return true;
    }

    private static boolean saved(BillDetail bd) throws SQLException {

        String sql = "INSERT INTO bills_details VALUES(?, ?, ?, ?)";

        PreparedStatement pstm = DbConnectionPET.getInstance().getConnection().prepareStatement(sql);

        pstm.setString(1, bd.getBillId());
        pstm.setString(2, bd.getMedicineId());
        pstm.setInt(3,bd.getMedicineQty());
        pstm.setDouble(4,bd.getMedicinePrice());

        return pstm.executeUpdate() > 0;

    }




}
