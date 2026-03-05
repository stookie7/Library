package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import dto.BookDTO;

public interface BookDAO {
    int insert(BookDTO book) throws SQLException; // 가이드 A 패턴: 내부에서 Connection 생성 가능 :contentReference[oaicite:1]{index=1}

    Optional<BookDTO> selectById(Connection conn, int bookId) throws SQLException; // 트랜잭션용: conn 전달
    int updateStatus(Connection conn, int bookId, String status) throws SQLException;

    List<BookDTO> selectAll() throws SQLException;

    List<BookDTO> search(String keyword) throws SQLException;
}
