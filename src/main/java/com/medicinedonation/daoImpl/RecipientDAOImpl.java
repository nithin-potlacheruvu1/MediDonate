package com.medicinedonation.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.medicinedonation.dao.RecipientDAO;
import com.medicinedonation.db.DBUtil;
import com.medicinedonation.model.Recipient;

public class RecipientDAOImpl implements RecipientDAO {

    @Override
    public int insertRecipient(Recipient recipient) throws Exception {
        String sql = "INSERT INTO recipients (user_id) VALUES (?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, recipient.getUserId());
            return ps.executeUpdate();
        }
    }

    @Override
    public int updateRecipient(Recipient recipient) throws Exception {
        String sql = "UPDATE recipients SET user_id=? WHERE user_id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, recipient.getUserId());
            ps.setInt(2, recipient.getUserId()); // update based on existing userId
            return ps.executeUpdate();
        }
    }

    @Override
    public int deleteRecipient(int userId) throws Exception {
        String sql = "DELETE FROM recipients WHERE user_id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            return ps.executeUpdate();
        }
    }

    @Override
    public Recipient getRecipientById(int userId) throws Exception {
        String sql = "SELECT * FROM recipients WHERE user_id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Recipient(rs.getInt("user_id"));
            }
        }
        return null;
    }

    @Override
    public List<Recipient> getAllRecipients() {
        List<Recipient> recipients = new ArrayList<>();
        String sql = "SELECT * FROM recipients";

        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Recipient r = new Recipient(rs.getInt("user_id"));
                recipients.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return recipients;
    }

    @Override
    public Recipient getRecipientByUserId(int userId) throws Exception {
        String sql = "SELECT * FROM recipients WHERE user_id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Recipient(rs.getInt("user_id"));
            }
        }
        return null;
    }


}
