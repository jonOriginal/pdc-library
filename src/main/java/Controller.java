import com.pdc.library.db.interfaces.LibRepository;

public class Controller {

    public final LibRepository repository;


    public Controller(LibRepository repository) {
        this.repository = repository;
    }

    public void getBooks() {
    }


}
