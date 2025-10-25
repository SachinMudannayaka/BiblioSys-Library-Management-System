package DAO;

import dbConnect.DBContext;
import entity.Borrower;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BorrowerDAO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public ArrayList<Borrower> getListBorrowerByPage(ArrayList<Borrower> list, int start, int end) {
        ArrayList<Borrower> arr = new ArrayList<>();
        for (int i = start; i < end; ++i) {
            arr.add(list.get(i));
        }
        return arr;
    }

    public void deleteBorrower(String id) {
        String sql = "DELETE FROM `borrower` WHERE id = ?";
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
    }

    public ArrayList<Borrower> getBorrowerByStatusAndUsername(String status, String username) {
        ArrayList<Borrower> list = new ArrayList<>();
        String sql = "SELECT * FROM `borrower` WHERE `status` = ? AND `username` = ?";
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setString(2, username);
            rs = ps.executeQuery();
            while (rs.next()) {
                Borrower b = new Borrower(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getInt("book_id"),
                        rs.getString("form"),
                        rs.getString("to"),
                        rs.getString("status")
                );
                if (!"processing".equals(status)) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = dateFormat.parse(rs.getString("to"));
                    if (!date.after(new Date())) {
                        b.setLate(true);
                    }
                }
                list.add(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return list;
    }

    public ArrayList<Borrower> getBorrowerByStatus(String status) {
        ArrayList<Borrower> list = new ArrayList<>();
        String sql = "SELECT * FROM `borrower` WHERE `status` = ?";
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            rs = ps.executeQuery();
            while (rs.next()) {
                Borrower b = new Borrower(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getInt("book_id"),
                        rs.getString("form"),
                        rs.getString("to"),
                        rs.getString("status")
                );
                if (!"processing".equals(status)) {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date date = dateFormat.parse(rs.getString("to"));
                    if (!date.after(new Date())) {
                        b.setLate(true);
                    }
                }
                list.add(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return list;
    }

    public ArrayList<Borrower> getBorrowerByStatusAndUserId(String status, String username) {
        ArrayList<Borrower> list = new ArrayList<>();
        String sql = "SELECT * FROM `borrower` WHERE `status` = ? AND `username` = ?";
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ps.setString(2, username);
            rs = ps.executeQuery();
            while (rs.next()) {
                Borrower b = new Borrower(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getInt("book_id"),
                        rs.getString("form"),
                        rs.getString("to"),
                        rs.getString("status")
                );
                list.add(b);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return list;
    }

    public Borrower getBorrowerById(String id) {
        Borrower b = null;
        String sql = "SELECT * FROM `borrower` WHERE id = ?";
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                b = new Borrower(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getInt("book_id"),
                        rs.getString("form"),
                        rs.getString("to"),
                        rs.getString("status")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return b;
    }

    public void updateBorrower(Borrower borrower) {
        String sql = "UPDATE `borrower` SET `username`=?, book_id=?, form=?, `to`=?, `status`=? WHERE id=?";
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, borrower.getUsername());
            ps.setInt(2, borrower.getBookid());
            ps.setString(3, borrower.getBorrow_from());
            ps.setString(4, borrower.getBorrow_to());
            ps.setString(5, borrower.getStatus());
            ps.setInt(6, borrower.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
    }

    public void insertBorrower(String username, String bookid) {
        String sql = "INSERT INTO `borrower` (username, book_id, `status`) VALUES (?, ?, 'processing')";
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, bookid);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
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
