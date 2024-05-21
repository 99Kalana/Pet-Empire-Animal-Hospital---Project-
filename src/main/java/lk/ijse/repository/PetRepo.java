package lk.ijse.repository;

import lk.ijse.db.DbConnectionPET;
import lk.ijse.model.Medicine;
import lk.ijse.model.Pet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PetRepo {

    public static boolean save(Pet pet) throws SQLException {

        String sql = "INSERT INTO pet VALUES(?, ?, ?, ?, ?)";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, pet.getId());
        pstm.setObject(2, pet.getName());
        pstm.setObject(3, pet.getBreed());
        pstm.setObject(4, pet.getWeight());
        pstm.setObject(5, pet.getColour());

        return pstm.executeUpdate() > 0;

    }

    public static boolean update(Pet pet) throws SQLException {

        String sql = "UPDATE pet SET pet_name = ?, pet_breed = ?, pet_weight = ?, pet_colour = ? WHERE pet_id = ?";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setObject(1, pet.getName());
        pstm.setObject(2, pet.getBreed());
        pstm.setObject(3, pet.getWeight());
        pstm.setObject(4, pet.getColour());
        pstm.setObject(5, pet.getId());

        return pstm.executeUpdate() > 0;

    }

    public static boolean delete(String id) throws SQLException {

        String sql = "DELETE FROM pet WHERE pet_id = ?";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1,id);

        return pstm.executeUpdate() > 0;
    }


    public static Pet searchById(String id) throws SQLException {

        String sql = "SELECT * FROM pet WHERE pet_id = ?";

        Connection connection = DbConnectionPET.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, id);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()){
            String p_id = resultSet.getString(1);
            String p_name = resultSet.getString(2);
            String p_breed = resultSet.getString(3);
            double p_weight = resultSet.getDouble(4);
            String p_colour = resultSet.getString(5);

            Pet pet = new Pet(p_id, p_name, p_breed, p_weight, p_colour);

            return pet;

        }

        return null;

    }

    public static List<Pet> getAll() throws SQLException {

        String sql = "SELECT * FROM pet";

        PreparedStatement pstm = DbConnectionPET.getInstance().getConnection()
                .prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<Pet> petList = new ArrayList<>();

        while (resultSet.next()) {
            String id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String breed = resultSet.getString(3);
            double weight = resultSet.getDouble(4);
            String colour = resultSet.getString(5);


            Pet pet = new Pet(id, name, breed, weight, colour);
            petList.add(pet);
        }
        return petList;

    }

    public static List<String> getIds() throws SQLException {

        String sql = "SELECT pet_id FROM pet";
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
