package service;

import java.sql.Connection;

import dao.BookDAO;
import dao.BookDAOImpl;
import dao.RentalDAO;
import dao.RentalDAOImpl;
import dto.BookDTO;
import util.DBUtil;

public class LibraryServiceImpl implements LibraryService {

    private final BookDAO bookDAO = new BookDAOImpl();
    private final RentalDAO rentalDAO = new RentalDAOImpl();

    @Override
    public int rentBook(int bookId, int memberId) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false); 

            // 2) 도서 상태 확인 (Y인지)
            BookDTO book = bookDAO.selectById(conn, bookId)
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 도서 번호입니다."));

            if (!"Y".equals(book.getStatus())) {
                throw new IllegalStateException("이미 대여 중인 도서입니다.");
            }
            
            rentalDAO.insert(conn, bookId, memberId);

        
            bookDAO.updateStatus(conn, bookId, "N");

            conn.commit(); 
            return 1;

        } catch (Exception e) {
            if (conn != null) try { conn.rollback(); } catch (Exception ignore) {}
            System.out.println("❌ 대여 실패: " + e.getMessage());
            return 0;

        } finally {
            if (conn != null) try { conn.setAutoCommit(true); conn.close(); } catch (Exception ignore) {}
        }
    }

    @Override
    public int returnBook(int bookId) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            conn.setAutoCommit(false);

            // 1) 반납 기록 업데이트
            int updated = rentalDAO.markReturned(conn, bookId);
            if (updated == 0) {
                throw new IllegalStateException("반납할 대여 기록이 없습니다.");
            }

            bookDAO.updateStatus(conn, bookId, "Y"); 

            conn.commit();
            return 1;

        } catch (Exception e) {
            if (conn != null) try { conn.rollback(); } catch (Exception ignore) {}
            System.out.println("❌ 반납 실패: " + e.getMessage());
            return 0;

        } finally {
            if (conn != null) try { conn.setAutoCommit(true); conn.close(); } catch (Exception ignore) {}
        }
    }
}