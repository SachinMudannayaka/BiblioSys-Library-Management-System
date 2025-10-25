package DAO;

import dbConnect.DBContext;
import entity.Feedback;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class FeedbackDAO {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    DBContext db = new DBContext();

    public ArrayList<Feedback> getListFeedbackByPage(ArrayList<Feedback> list, int start, int end) {
        ArrayList<Feedback> arr = new ArrayList<>();
        for (int i = start; i < end && i < list.size(); ++i) {
            arr.add(list.get(i));
        }
        return arr;
    }

    public ArrayList<Feedback> getListFeedback() {
        ArrayList<Feedback> list = new ArrayList<>();
        String sql = "SELECT * FROM feedback ORDER BY id DESC";
        try {
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Feedback fb = new Feedback(rs.getString("user_id"), rs.getString("title"), rs.getString("content"));
                list.add(fb);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            closeConnections();
        }
        return list;
    }

    public void insertFeedback(Feedback fb) {
        String sql = "INSERT INTO feedback(user_id, title, content) VALUES (?, ?, ?)";
        try {
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, fb.getUsername());
            ps.setString(2, fb.getTitle());
            ps.setString(3, fb.getConnent());
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            closeConnections();
        }
    }

    private void closeConnections() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
