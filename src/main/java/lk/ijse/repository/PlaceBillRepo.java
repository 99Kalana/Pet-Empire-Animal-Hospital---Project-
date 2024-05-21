package lk.ijse.repository;

import lk.ijse.db.DbConnectionPET;
import lk.ijse.model.PlaceBill;

import java.sql.Connection;
import java.sql.SQLException;

public class PlaceBillRepo {

    public static boolean placeBill (PlaceBill pb) throws SQLException {

        Connection connection = DbConnectionPET.getInstance().getConnection();
        connection.setAutoCommit(false);

        try {
            boolean isBillSaved = BillRepo.save(pb.getBill());
            if(isBillSaved){
                boolean isQtyUpdated = MedicineRepo.updates(pb.getBillList());
                if (isQtyUpdated){
                    boolean isBillDetailSaved = BillDetailRepo.save(pb.getBillList());
                    if (isBillDetailSaved){
                        connection.commit();
                        return true;
                    }
                }
            }

            connection.rollback();
            return false;

        }catch (Exception e){
            connection.rollback();
            return false;
        }finally {
            connection.setAutoCommit(true);
        }

    }

}
