package com.pdc.library.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ModelsTest {

    @Test
    public void testBook() {
        Book book = new Book(24, "JRR Tolkein", "Fellowship Of The Ring");
        assertEquals(24, book.getId());
        assertEquals("JRR Tolkein", book.getAuthor());
        assertEquals("Fellowship Of The Ring", book.getName());
    }

    @Test
    public void testUser() {
        User user = new User(42, "Jono Bono");
        assertEquals(42, user.getId());
        assertEquals("Jono Bono", user.getName());
    }

    @Test
    public void testUserBook() {
        UserBook userBook = UserBook.create(42, 24, 10);
        assertEquals(42, userBook.getUserId());
        assertEquals(24, userBook.getBookId());
        assertEquals(10, userBook.getAllowedDays());
    }
}
