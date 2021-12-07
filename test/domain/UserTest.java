package domain;

import domain.exceptions.EmailCantBeNullOrEmpty;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class UserTest {

    @Before
    public void before() throws EmailCantBeNullOrEmpty {
        user = User.createUser(email, firstName, lastName);
    }

    @Test
    public void canCreateUser() {
        assertNotNull(user);
        assertEquals(email, user.getEmail());
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
    }

    @Test
    public void userGetsAnIDWhenCreated() {
        assertNotNull(user.getId());
    }

    @Test(expected = EmailCantBeNullOrEmpty.class)
    public void emailCantBeSetToNull() throws EmailCantBeNullOrEmpty {
        user.setEmail(null);
    }

    @Test
    public void twoUserWithTheSameEmailAreConsideredEqual() throws EmailCantBeNullOrEmpty {
        User user1 = User.createUser("toto@titi.com", "toto", "titi");
        User user2 = User.createUser("toto@titi.com", "Didier", "Raoult");
        assertEquals(user1, user2);
    }

    @Test
    public void cannotAddTwoUsersWithTheSameEmailAddressToASet() throws EmailCantBeNullOrEmpty {
        User user1 = User.createUser("toto@titi.com", "toto", "titi");
        User user2 = User.createUser("toto@titi.com", "Didier", "Raoult");
        HashSet<User> users = new HashSet<User>();
        users.add(user1);
        users.add(user2);
        assertEquals(1, users.size());
    }

    private User user;
    private String firstName = "FirstName";
    private String lastName = "LastName";
    private String email = "email@fai.com";

}
