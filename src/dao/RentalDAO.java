package dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface RentalDAO {
    int insert(Connection conn, int bookId, int memberId) throws SQLException;
    int markReturned(Connection conn, int bookId) throws SQLException; 
}
