package com.pdc.library.db;

import com.pdc.library.models.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;

// This test class demonstrates the following is working:
// - Successful database creation
// - successful adding of content to the tables (dummy data methods in LibDbRepository class)
// - accessing the table FindAllUserBooks
// - removal of content from the table/s.

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DBTest {
    private Connection connection;
    private LibDbRepository repository;

    @BeforeAll
    public void setupDatabase() throws SQLException {
        connection = DriverManager.getConnection("jdbc:derby:memory:libraryTestDB;create=true");
        repository = new LibDbRepository(connection);
        repository.createUserTable();
        repository.createBookTable();
        repository.createUserBookTable();
    }

    @AfterAll
    public void tearDownDatabase() throws SQLException {
        connection.close();
    }

    @Test
    public void testFindAllUserBooks() throws SQLException {
        Collection<UserBook> userBooks = repository.findAllUserBooks();
        assertNotNull(userBooks);
    };

    // If this passes, it not only suggests the Remove() method works but also that the add method for the
    // book and user table works, as the dummy data was correctly added.
    // Also, it is important to note that when build files aren't deleted, the following method will return false,
    // proving that the removal of values is constant.
    @Test
    public void testRemoveUserBook() throws SQLException {
        assertTrue(repository.findUserBooksByUserId(1).size() > 0);

        repository.removeUserBook(1);
        assertTrue(repository.findUserBooksByUserId(1).isEmpty());
    }
}
