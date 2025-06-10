import com.pdc.library.db.LibDbRepository;

public class TestUi {
    public static void main(String[] args) {
        var View = new View(new Controller(new LibDbRepository(null)));
        View.setVisible(true);
    }
}
