package DAO;

import dbConnect.DBContext;
import entity.TopBook;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DAOO {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    DBContext db = new DBContext();

    public ArrayList<TopBook> getTopBook() {
        ArrayList<TopBook> list = new ArrayList<>();
        String sql = "SELECT book.name, A.Total " +
                "FROM (SELECT book_id, COUNT(book_id) AS Total " +
                "      FROM borrower " +
                "      WHERE status != 'processing' " +
                "      GROUP BY book_id " +
                "      ORDER BY Total DESC " +
                "      LIMIT 5) A " +
                "JOIN book ON A.book_id = book.id";
        try {
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                TopBook tb = new TopBook(rs.getString("name"), rs.getInt("Total"));
                list.add(tb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return list;
    }

    public ArrayList<TopBook> getTopUser() {
        ArrayList<TopBook> list = new ArrayList<>();
        String sql = "SELECT username, COUNT(username) AS Total " +
                "FROM borrower " +
                "WHERE status != 'processing' " +
                "GROUP BY username " +
                "ORDER BY Total DESC " +
                "LIMIT 5";
        try {
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                TopBook tb = new TopBook(rs.getString("username"), rs.getInt("Total"));
                list.add(tb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeConnections();
        }
        return list;
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
