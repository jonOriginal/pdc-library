package com.pdc.library;

import com.pdc.library.db.LibDbRepository;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    private static final String DatabaseUrl = "jdbc:derby:PdcLibrary;create=true";

    public static void main(String[] args) {
        LibDbRepository repository;
        try {
            var connection = DriverManager.getConnection(DatabaseUrl);
            repository = new LibDbRepository(connection);

            repository.createBookTable();
            repository.createUserTable();
            repository.createUserBookTable();

            System.out.println("Database and tables created successfully.");
        } catch (SQLException e) {
            throw new RuntimeException("Failed to connect to the database or create tables.", e);
        }

        var controller = new Controller(repository);
        var view = new View(controller);
        view.setVisible(true);
    }
}
