package app;

import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Scanner;

import dao.BookDAO;
import dao.BookDAOImpl;
import dto.BookDTO;
import service.LibraryService;
import service.LibraryServiceImpl;
import util.InputUtil;

public class MainApp {

    public static void main(String[] args) {
        Charset cs = Charset.forName("cp949");
        Scanner sc = new Scanner(new InputStreamReader(System.in, cs));
        
        BookDAO bookDAO = new BookDAOImpl();
        LibraryService service = new LibraryServiceImpl();

        while (true) {
            System.out.println("\n==== 도서 관리 시스템 ====");
            System.out.println("1) 도서 등록");
            System.out.println("2) 도서 목록 조회");
            System.out.println("3) 도서 대여");
            System.out.println("4) 도서 반납");
            System.out.println("5) 도서 검색");
            System.out.println("0) 종료");
            int menu = InputUtil.readInt(sc, "선택> ");

            try {
                if (menu == 1) {
                    String title = InputUtil.readNonBlank(sc, "제목> ");
                    String author = InputUtil.readNonBlank(sc, "저자> ");

                    BookDTO book = new BookDTO(0, title, author, "Y");
                    int r = bookDAO.insert(book);
                    System.out.println(r == 1 ? "✅ 등록 완료" : "❌ 등록 실패");

                } else if (menu == 2) {
                    List<BookDTO> list = bookDAO.selectAll();
                    if (list.isEmpty()) {
                        System.out.println("(목록 없음)");
                    } else {
                        System.out.println("ID | 제목 | 저자 | 상태");
                        for (BookDTO b : list) {
                            System.out.printf("%d | %s | %s | %s%n",
                                    b.getBookId(), b.getTitle(), b.getAuthor(), b.getStatus());
                        }
                    }

                } else if (menu == 3) {
                    int bookId = InputUtil.readInt(sc, "bookId> ");
                    int memberId = InputUtil.readInt(sc, "memberId> ");
                    int ok = service.rentBook(bookId, memberId);
                    System.out.println(ok == 1 ? "✅ 대여 완료" : "❌ 대여 실패");

                } else if (menu == 4) {
                    int bookId = InputUtil.readInt(sc, "bookId> ");
                    int ok = service.returnBook(bookId);
                    System.out.println(ok == 1 ? "✅ 반납 완료" : "❌ 반납 실패");

                } else if (menu == 5) {
                String keyword = InputUtil.readNonBlank(sc, "검색어(제목/저자)> ");
                List<BookDTO> list = bookDAO.search(keyword);

                if (list.isEmpty()) {
                    System.out.println("(검색 결과 없음)");
                } else {
                    System.out.println("ID | 제목 | 저자 | 상태");
                    for (BookDTO b : list) {
                        System.out.printf("%d | %s | %s | %s%n",
                                b.getBookId(), b.getTitle(), b.getAuthor(), b.getStatus());
                    }
                 }
                } else if (menu == 0) {
                    System.out.println("Bye 👋");
                    break;
                    
                } else {
                    System.out.println("❌ 메뉴 다시 선택");
                }
            } catch (Exception e) {
                
                System.out.println("❌ 오류: " + e.getMessage());
            }
        }

        sc.close();
    }
}