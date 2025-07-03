package DAO.Customer;

import config.Database;
import model.Customer;
import util.Exception.ApiException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    public List<Customer> findAll() {
        String query = "SELECT * FROM customers";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            List<Customer> customers = new ArrayList<>();
            while (rs.next()) {
                customers.add(new Customer(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone")
                ));
            }
            return customers;
        } catch (SQLException e) {
            throw new ApiException.InternalServerErrorApiException("Gagal mengambil data customer.");
        }
    }

    public Customer findById(int id) {
        String query = "SELECT * FROM customers WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                throw new ApiException.NotFoundApiException("Customer dengan id " + id + " tidak ditemukan.");
            }
            return new Customer(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone")
            );
        } catch (SQLException e) {
            throw new ApiException.InternalServerErrorApiException("Gagal mengambil customer.");
        }
    }

    public int create(Customer customer) {
        String query = "INSERT INTO customers(name, email, phone) VALUES (?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getEmail());
            stmt.setString(3, customer.getPhone());
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) return keys.getInt(1);
            else throw new ApiException.InternalServerErrorApiException("Gagal mendapatkan ID customer baru.");
        } catch (SQLException e) {
            throw new ApiException.InternalServerErrorApiException("Gagal menambahkan customer.");
        }
    }

    public void update(int id, Customer customer) {
        if (!existsById(id)) {
            throw new ApiException.NotFoundApiException("Customer dengan id " + id + " tidak ditemukan.");
        }

        String query = "UPDATE customers SET name = ?, email = ?, phone = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, customer.getName());
            stmt.setString(2, customer.getEmail());
            stmt.setString(3, customer.getPhone());
            stmt.setInt(4, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new ApiException.InternalServerErrorApiException("Gagal memperbarui customer.");
        }
    }

    public boolean existsById(int id) {
        String query = "SELECT id FROM customers WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new ApiException.InternalServerErrorApiException("Gagal memeriksa ID customer.");
        }
    }
}