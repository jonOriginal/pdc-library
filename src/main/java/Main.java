import com.pdc.library.db.LibDbRepository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    private static final String DatabaseUrl = "jdbc:derby:PdcLibrary;create=true";

    public static void main(String[] args) {
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            var connection = DriverManager.getConnection(DatabaseUrl);
            var repository = new LibDbRepository(connection);

            repository.createUserBookTable();
            repository.createBookTable();
            repository.createUserTable();

            System.out.println("Database and tables created successfully.");

            var controller = new Controller(repository);
            var view = new View(controller);
            view.show();

        } catch (ClassNotFoundException e) {
            System.err.println("Derby JDBC driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("SQL error: " + e.getMessage());
        }
    }
}
