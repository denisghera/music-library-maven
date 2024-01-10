package org.classes;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void testDefaultConstructor() {
        User user = new User();

        assertNotNull(user.getUserMD());
        assertNotNull(user.getCredentials());
    }

    @Test
    void testParameterizedConstructor() {
        Metadata userMetadata = new Metadata();
        Credentials credentials = new Credentials();

        User user = new User(userMetadata, credentials);

        assertNotNull(user.getUserMD());
        assertNotNull(user.getCredentials());
        assertEquals(userMetadata, user.getUserMD());
        assertEquals(credentials, user.getCredentials());
    }
}
