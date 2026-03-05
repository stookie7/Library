package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RentalDAOImpl implements RentalDAO {

    @Override
    public int insert(Connection conn, int bookId, int memberId) throws SQLException {
        String sql = "INSERT INTO rentals(book_id, member_id) VALUES(?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bookId);
            pstmt.setInt(2, memberId);
            return pstmt.executeUpdate();
        }
    }

    @Override
    public int markReturned(Connection conn, int bookId) throws SQLException {
        String sql = "UPDATE rentals SET return_date = CURRENT_DATE WHERE book_id = ? AND return_date IS NULL";
        // 가이드 C의 핵심 SQL 흐름 :contentReference[oaicite:4]{index=4}
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bookId);
            return pstmt.executeUpdate();
        }
    }
}
