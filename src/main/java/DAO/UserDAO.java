package DAO;

import dbConnect.DBContext;
import entity.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class UserDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public ArrayList<User> getAllUser() {
        ArrayList<User> users = new ArrayList<>();
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            ps = conn.prepareStatement("SELECT * FROM `USER`");
            rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getBoolean("role"));
                user.setName(rs.getString("name"));
                user.setAvt(rs.getString("avt"));
                user.setSex(rs.getBoolean("sex"));
                user.setDatebirth(rs.getString("datebirth")); // MySQL DATE will map to String or LocalDate
                user.setPhone(rs.getString("phone"));
                user.setGmail(rs.getString("gmail"));
                if(!user.isRole()){
                    users.add(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return users;
    }

    public User findUser(String username, String password) {
        User user = null;
        String sql = "SELECT * FROM `USER` WHERE username = ? AND password = ?";
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                user = extractUser(rs);
            }
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return user;
    }

    public User findUserByUsername(String username) {
        User user = null;
        String sql = "SELECT * FROM `USER` WHERE username = ?";
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                user = extractUser(rs);
            }
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return user;
    }

    public void insertUser(User user) {
        String sql = "INSERT INTO `USER` (username,password,role,name,avt,sex,datebirth,phone,gmail) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setBoolean(3, user.isRole());
            ps.setString(4, user.getName());
            ps.setString(5, user.getAvt() == null ? "img/avt/avt.jpg" : user.getAvt());
            ps.setBoolean(6, user.isSex());
            ps.setString(7, user.getDatebirth());
            ps.setString(8, user.getPhone());
            ps.setString(9, user.getGmail());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
    }

    public void deleteUser(String username) {
        String sql = "DELETE FROM `USER` WHERE username = ?";
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
    }

    public void updateUser(User user) {
        String sql = "UPDATE `USER` SET password = ?, role = ?, name = ?, avt = ?, sex = ?, datebirth = ?, phone = ?, gmail = ? WHERE username = ?";
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, user.getPassword());
            ps.setBoolean(2, user.isRole());
            ps.setString(3, user.getName());
            ps.setString(4, user.getAvt());
            ps.setBoolean(5, user.isSex());
            ps.setString(6, user.getDatebirth());
            ps.setString(7, user.getPhone());
            ps.setString(8, user.getGmail());
            ps.setString(9, user.getUsername());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
    }

    // Helper method to extract User from ResultSet
    private User extractUser(ResultSet rs) throws Exception {
        User user = new User();
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setRole(rs.getBoolean("role"));
        user.setName(rs.getString("name"));
        user.setAvt(rs.getString("avt"));
        user.setSex(rs.getBoolean("sex"));
        user.setDatebirth(rs.getString("datebirth"));
        user.setPhone(rs.getString("phone"));
        user.setGmail(rs.getString("gmail"));
        return user;
    }

    // Close resources
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
