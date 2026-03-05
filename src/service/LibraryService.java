package service;

public interface LibraryService {
    int rentBook(int bookId, int memberId);
    int returnBook(int bookId);
}