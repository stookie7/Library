package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import dto.BookDTO;
import util.DBUtil;

public class BookDAOImpl implements BookDAO {

    @Override
    public int insert(BookDTO book) throws SQLException {
        String sql = "INSERT INTO books(title, author) VALUES(?, ?)";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getAuthor());
            return pstmt.executeUpdate();
        }
    }

    @Override
    public Optional<BookDTO> selectById(Connection conn, int bookId) throws SQLException {
        String sql = "SELECT book_id, title, author, status FROM books WHERE book_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bookId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (!rs.next()) return Optional.empty();
                return Optional.of(new BookDTO(
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("status")
                ));
            }
        }
    }

    @Override
    public int updateStatus(Connection conn, int bookId, String status) throws SQLException {
        String sql = "UPDATE books SET status = ? WHERE book_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            pstmt.setInt(2, bookId);
            return pstmt.executeUpdate();
        }
    }

    @Override
    public List<BookDTO> selectAll() throws SQLException {
        String sql = "SELECT book_id, title, author, status FROM books ORDER BY book_id";
        List<BookDTO> list = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                list.add(new BookDTO(
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("status")
                ));
            }
        }
        return list; 
    }

    @Override
    public List<BookDTO> search(String keyword) throws SQLException {
    String sql = """
        SELECT book_id, title, author, status
        FROM books
        WHERE title LIKE ? OR author LIKE ?
        ORDER BY book_id
        """;

    List<BookDTO> list = new ArrayList<>();
    String like = "%" + keyword + "%";

    try (Connection conn = DBUtil.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, like);
        pstmt.setString(2, like);

        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                list.add(new BookDTO(
                        rs.getInt("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("status")
                ));
            }
        }
    }
    return list;
    }
}
