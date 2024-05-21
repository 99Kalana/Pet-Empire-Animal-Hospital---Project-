package lk.ijse.repository;

import lk.ijse.db.DbConnectionPET;
import lk.ijse.model.Medicine;
import lk.ijse.model.MedicineSupplier;
import lk.ijse.model.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicineSupplierRepo {
    public static MedicineSupplier searchById(String id) throws SQLException {

        String sql = "SELECT * FROM medicine_supplier WHERE s_id = ?";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()){
            String ms_id = resultSet.getString(1);
            String ms_mId = resultSet.getString(2);
            String ms_date = resultSet.getString(3);

            MedicineSupplier medicineSupplier = new MedicineSupplier(ms_id, ms_mId, ms_date);

            return medicineSupplier;

        }

        return null;

    }

    public static boolean save(MedicineSupplier medicineSupplier) throws SQLException {

        String sql = "INSERT INTO medicine_supplier VALUES(?, ?, ?)";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, medicineSupplier.getId());
        pstm.setObject(2,medicineSupplier.getmId());
        pstm.setObject(3,medicineSupplier.getDate());


        return pstm.executeUpdate() > 0;

    }

    public static boolean update(MedicineSupplier medicineSupplier) throws SQLException {

        String sql = "UPDATE medicine_supplier SET m_id = ?, supply_date = ?  WHERE s_id = ? ";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1,medicineSupplier.getmId());
        pstm.setObject(2,medicineSupplier.getDate());
        pstm.setObject(3, medicineSupplier.getId());

        return pstm.executeUpdate() > 0;

    }

    public static boolean delete(String id) throws SQLException {

        String sql = "DELETE FROM medicine_supplier WHERE s_id = ?";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1,id);

        return pstm.executeUpdate() > 0;

    }

    public static List<MedicineSupplier> getAll() throws SQLException {

        String sql = "SELECT * FROM medicine_supplier";

        PreparedStatement pstm = DbConnectionPET.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<MedicineSupplier> medicineSupplierList = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String mId = resultSet.getString(2);
            String date = String.valueOf(resultSet.getDate(3));

            MedicineSupplier medicineSupplier = new MedicineSupplier(id, mId, date);
            medicineSupplierList.add(medicineSupplier);
        }
        return medicineSupplierList;

    }
}
