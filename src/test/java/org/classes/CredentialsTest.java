package org.classes;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CredentialsTest {

    @Test
    void testDefaultConstructor() {
        Credentials credentials = new Credentials();

        assertNull(credentials.getUsername());
        assertNull(credentials.getPassword());
    }

    @Test
    void testParameterizedConstructor() {
        String username = "testUser";
        String password = "testPassword";

        Credentials credentials = new Credentials(username, password);

        assertEquals(username, credentials.getUsername());
        assertEquals(password, credentials.getPassword());
    }
}
