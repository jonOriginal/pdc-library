import com.pdc.library.models.*;

public class TestSql {
    public static void main(String[] args) {
        User user = new User();
        user.setUserId(21);
        user.setName("Seth Seth");

        Book book = new Book();
        book.setId(13);
        book.setBookAuthor("JRR Tolkein");
        book.setBookName("Fellowship of the Ring");

        UserBook userBook = new UserBook(21, 13);

        System.out.println(book.getId() + ", " +  book.getBookAuthor() + ", " + book.getBookName());
        System.out.println(user.getUserId() + ", " + user.getName());
        System.out.println(userBook.getDateHired());
    }
}
