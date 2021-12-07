package domain;

import domain.exceptions.EmailCantBeNullOrEmpty;

import java.util.Objects;
import java.util.UUID;

public class User {

    public static User createUser(String email, String firstName, String lastName) throws EmailCantBeNullOrEmpty {
        instance = new User(email, firstName, lastName);
        return instance;
    }

    private User(String email, String firstName, String lastName) {
        setId(UUID.randomUUID());
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public UUID getId() {
        return id;
    }

    private void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws EmailCantBeNullOrEmpty {
        if (email == null) {
            throw new EmailCantBeNullOrEmpty();
        }
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    private static User instance;
    private UUID id;
    public String firstName;
    public String lastName;
    public String email;
}
