package dto;

import java.sql.Date;

public class RentalDTO {
    private int rentalId;
    private int bookId;
    private int memberId;
    private Date rentDate;
    private Date returnDate;

    public int getRentalId() { return rentalId; }
    public void setRentalId(int rentalId) { this.rentalId = rentalId; }

    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }

    public int getMemberId() { return memberId; }
    public void setMemberId(int memberId) { this.memberId = memberId; }

    public Date getRentDate() { return rentDate; }
    public void setRentDate(Date rentDate) { this.rentDate = rentDate; }

    public Date getReturnDate() { return returnDate; }
    public void setReturnDate(Date returnDate) { this.returnDate = returnDate; }    
}
