package com.medicinedonation.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.medicinedonation.dao.RequestDAO;
import com.medicinedonation.db.DBUtil;
import com.medicinedonation.model.Request;

public class RequestDAOImpl implements RequestDAO {

    @Override
    public void insertRequest(Request request) {
        String sql = "INSERT INTO requests (user_id, medicine_id, quantity, request_date, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, request.getUserId()); // use userId
            ps.setInt(2, request.getMedicineId());
            ps.setInt(3, request.getQuantity());
            ps.setDate(4, request.getRequestDate());
            ps.setString(5, request.getStatus());

            ps.executeUpdate(); // no need to fetch requestId

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateRequest(Request request) {
        String sql = "UPDATE requests SET medicine_id=?, quantity=?, request_date=?, status=? WHERE user_id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, request.getMedicineId());
            ps.setInt(2, request.getQuantity());
            ps.setDate(3, request.getRequestDate());
            ps.setString(4, request.getStatus());
            ps.setInt(5, request.getUserId());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteRequest(int userId) {
        String sql = "DELETE FROM requests WHERE user_id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Request getRequestByUserId(int userId) {
        String sql = "SELECT * FROM requests WHERE user_id=?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Request(
                    rs.getInt("request_id"),
                    rs.getInt("medicine_id"),
                    rs.getInt("quantity"),
                    rs.getDate("request_date"),
                    rs.getString("status"),
                    rs.getInt("user_id")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public List<Request> getAllRequests() {
        List<Request> requests = new ArrayList<>();
        String sql = "SELECT * FROM requests";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

        	while (rs.next()) {
        	    requests.add(new Request(
        	        rs.getInt("request_id"),
        	        rs.getInt("medicine_id"),
        	        rs.getInt("quantity"),
        	        rs.getDate("request_date"),
        	        rs.getString("status"),
        	        rs.getInt("user_id")
        	    ));
        	}


        } catch (Exception e) {
            e.printStackTrace();
        }
        return requests;
    }

    @Override
    public Request getRequestById(int requestId) {
        String sql = "SELECT * FROM requests WHERE request_id=?";
        Request request = null;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, requestId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                request = new Request(
                	    rs.getInt("request_id"),
                	    rs.getInt("medicine_id"),
                	    rs.getInt("quantity"),
                	    rs.getDate("request_date"),
                	    rs.getString("status"),
                	    rs.getInt("user_id")
                	);

            }

            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return request;
    }


   

	
}
