package lk.ijse.repository;

import lk.ijse.db.DbConnectionPET;
import lk.ijse.model.Appointment;
import lk.ijse.model.Client;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientRepo {
    public static Client searchById(String id) throws SQLException {

        String sql = "SELECT * FROM client WHERE client_id = ?";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()){
            String c_id = resultSet.getString(1);
            String c_petId = resultSet.getString(2);
            String c_name = resultSet.getString(3);
            String c_address = resultSet.getString(4);
            String c_email = resultSet.getString(5);
            int c_contactNo = resultSet.getInt(6);

            Client client = new Client(c_id, c_petId, c_name, c_address, c_email, c_contactNo);

            return client;

        }

        return null;

    }

    public static boolean save(Client client) throws SQLException {

        String sql = "INSERT INTO client VALUES(?, ?, ?, ?, ?, ?)";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, client.getId());
        pstm.setObject(2, client.getPetId());
        pstm.setObject(3, client.getName());
        pstm.setObject(4, client.getAddress());
        pstm.setObject(5, client.getEmail());
        pstm.setObject(6, client.getContactNo());

        return pstm.executeUpdate() > 0;

    }

    public static boolean update(Client client) throws SQLException {

        String sql = "UPDATE client SET pet_id = ?, client_name = ?, client_address = ?, client_email = ?, client_contact_no = ? WHERE client_id = ? ";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, client.getPetId());
        pstm.setObject(2, client.getName());
        pstm.setObject(3, client.getAddress());
        pstm.setObject(4, client.getEmail());
        pstm.setObject(5, client.getContactNo());
        pstm.setObject(6, client.getId());


        return pstm.executeUpdate() > 0;

    }

    public static boolean delete(String id) throws SQLException {

        String sql = "DELETE FROM client WHERE client_id = ?";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1,id);

        return pstm.executeUpdate() > 0;

    }

    public static List<Client> getAll() throws SQLException {
        String sql = "SELECT * FROM client";

        PreparedStatement pstm = DbConnectionPET.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Client> clientList = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String petId = resultSet.getString(2);
            String name = resultSet.getString(3);
            String address = resultSet.getString(4);
            String email = resultSet.getString(5);
            int contactNo = resultSet.getInt(6);


            Client client = new Client(id, petId, name, address, email, contactNo);
            clientList.add(client);
        }
        return clientList;
    }

    public static List<String> getIds() throws SQLException {
        String sql = "SELECT client_id FROM client";
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
