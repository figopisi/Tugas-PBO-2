package DAO.Voucher;

import config.Database;
import model.Voucher;
import util.Exception.ApiException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VoucherDAO {

    public List<Voucher> findAll() {
        try (Connection conn = Database.getConnection()) {
            String query = "SELECT * FROM vouchers";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            List<Voucher> vouchers = new ArrayList<>();
            while (rs.next()) {
                vouchers.add(new Voucher(
                        rs.getInt("id"),
                        rs.getString("code"),
                        rs.getString("description"),
                        rs.getDouble("discount"),
                        rs.getString("start_date"),
                        rs.getString("end_date")
                ));
            }
            return vouchers;
        } catch (Exception e) {
            throw new ApiException.InternalServerErrorApiException("Failed to fetch vouchers.");
        }
    }

    public Voucher findById(int id) {
        try (Connection conn = Database.getConnection()) {
            String query = "SELECT * FROM vouchers WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                throw new ApiException.NotFoundApiException("Voucher with id " + id + " not found.");
            }

            return new Voucher(
                    rs.getInt("id"),
                    rs.getString("code"),
                    rs.getString("description"),
                    rs.getDouble("discount"),
                    rs.getString("start_date"),
                    rs.getString("end_date")
            );
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException.InternalServerErrorApiException("Failed to get voucher.");
        }
    }

    public int create(Voucher voucher) {
        try (Connection conn = Database.getConnection()) {
            String query = "INSERT INTO vouchers (code, description, discount, start_date, end_date) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, voucher.getCode());
            stmt.setString(2, voucher.getDescription());
            stmt.setDouble(3, voucher.getDiscount());
            stmt.setString(4, voucher.getStartDate());
            stmt.setString(5, voucher.getEndDate());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            rs.next();
            return rs.getInt(1);
        } catch (Exception e) {
            throw new ApiException.InternalServerErrorApiException("Failed to create voucher.");
        }
    }

    public void update(int id, Voucher voucher) {
        try (Connection conn = Database.getConnection()) {
            String query = "UPDATE vouchers SET code = ?, description = ?, discount = ?, start_date = ?, end_date = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, voucher.getCode());
            stmt.setString(2, voucher.getDescription());
            stmt.setDouble(3, voucher.getDiscount());
            stmt.setString(4, voucher.getStartDate());
            stmt.setString(5, voucher.getEndDate());
            stmt.setInt(6, id);
            int affected = stmt.executeUpdate();
            if (affected == 0) {
                throw new ApiException.NotFoundApiException("Voucher with id " + id + " not found.");
            }
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException.InternalServerErrorApiException("Failed to update voucher.");
        }
    }

    public void delete(int id) {
        try (Connection conn = Database.getConnection()) {
            String query = "DELETE FROM vouchers WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            int affected = stmt.executeUpdate();
            if (affected == 0) {
                throw new ApiException.NotFoundApiException("Voucher with id " + id + " not found.");
            }
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            throw new ApiException.InternalServerErrorApiException("Failed to delete voucher.");
        }
    }

    public boolean existsById(int id) {
        try (Connection conn = Database.getConnection()) {
            String query = "SELECT id FROM vouchers WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception e) {
            throw new ApiException.InternalServerErrorApiException("Failed to check voucher existence.");
        }
    }
}
