package DAO;

import config.Database;
import model.Villa;
import util.Exception.ApiException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VillaDAO {

    public List<Villa> findAll() {
        List<Villa> villas = new ArrayList<>();
        String query = "SELECT * FROM villas";

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Villa villa = new Villa(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getString("address")
                );
                villas.add(villa);
            }
        } catch (SQLException e) {
            throw new ApiException.InternalServerErrorApiException(
                    "Terjadi kesalahan saat mengambil daftar villa: " + e.getMessage()
            );
        }

        return villas;
    }

    public Villa findById(int id) {
        String query = "SELECT * FROM villas WHERE id = ?";

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return new Villa(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getString("address")
                );
            } else {
                throw new ApiException.NotFoundApiException(
                        "Villa dengan id " + id + " tidak ditemukan"
                );
            }

        } catch (SQLException e) {
            throw new ApiException.InternalServerErrorApiException(
                    "Terjadi kesalahan saat mengambil villa dengan id: " + id + ". " + e.getMessage()
            );
        }
    }

    public int create(Villa villa) {
        String query = "INSERT INTO villas(name, description, address) VALUES (?, ?, ?)";

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, villa.getName());
            statement.setString(2, villa.getDescription());
            statement.setString(3, villa.getAddress());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new ApiException.InternalServerErrorApiException(
                        "Gagal mendapatkan ID baru villa."
                );
            }

        } catch (SQLException e) {
            throw new ApiException.InternalServerErrorApiException(
                    "Terjadi kesalahan saat membuat villa: " + e.getMessage()
            );
        }
    }

    public void update(int id, Villa villa) {
        String checkQuery = "SELECT id FROM villas WHERE id = ?";
        String updateQuery = "UPDATE villas SET name = ?, description = ?, address = ? WHERE id = ?";

        try (Connection connection = Database.getConnection()) {
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setInt(1, id);
            ResultSet rs = checkStatement.executeQuery();
            if (!rs.next()) {
                throw new ApiException.NotFoundApiException(
                        "Villa dengan id " + id + " tidak ditemukan"
                );
            }

            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setString(1, villa.getName());
            updateStatement.setString(2, villa.getDescription());
            updateStatement.setString(3, villa.getAddress());
            updateStatement.setInt(4, id);
            updateStatement.executeUpdate();

        } catch (SQLException e) {
            throw new ApiException.InternalServerErrorApiException(
                    "Terjadi kesalahan saat memperbarui villa: " + e.getMessage()
            );
        }
    }

    public void delete(int id) {
        String checkQuery = "SELECT id FROM villas WHERE id = ?";
        String deleteQuery = "DELETE FROM villas WHERE id = ?";

        try (Connection connection = Database.getConnection()) {
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setInt(1, id);
            ResultSet rs = checkStatement.executeQuery();
            if (!rs.next()) {
                throw new ApiException.NotFoundApiException(
                        "Villa dengan id " + id + " tidak ditemukan"
                );
            }

            PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery);
            deleteStatement.setInt(1, id);
            deleteStatement.executeUpdate();

        } catch (SQLException e) {
            throw new ApiException.InternalServerErrorApiException(
                    "Terjadi kesalahan saat menghapus villa: " + e.getMessage()
            );
        }
    }
}
