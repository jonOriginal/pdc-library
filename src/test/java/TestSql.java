import com.pdc.library.models.*;

public class TestSql {
    public static void main(String[] args) {
        User user = new User(21, "Seth Seth");
        Book book = new Book(13, "Fellowship of the Ring", "JRR Tolkein");

        UserBook userBook = UserBook.create(21, 13, 7);

        System.out.println(book.getId() + ", " +  book.getAuthor() + ", " + book.getName());
        System.out.println(user.getId() + ", " + user.getName());
        System.out.println(userBook.getDateHired());
    }
}
