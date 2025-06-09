import com.pdc.library.db.interfaces.LibRepository;

public class Controller {

    private final LibRepository repository;

    public Controller(LibRepository repository) {
        this.repository = repository;
    }
}
