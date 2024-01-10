package org.classes;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class UserDatabaseTest {

    @Test
    void testDefaultConstructor() {
        UserDatabase userDatabase = new UserDatabase();

        assertNotNull(userDatabase.getUserList());
        assertEquals(0, userDatabase.getUserList().size());
    }

    @Test
    void testParameterizedConstructor() {
        List<User> users = new ArrayList<>();
        users.add(new User());
        UserDatabase userDatabase = new UserDatabase(users);
        assertEquals(users, userDatabase.getUserList());
    }
}
