package Repositories;

import domain.User;

import java.util.UUID;

public class UserRepository implements Repository <User> {
    @Override
    public User get(UUID id) {
        return null;
    }

    @Override
    public void add(UUID id) {

    }

    @Override
    public void delete(UUID id) {

    }

    @Override
    public boolean exists(UUID id) {
        return false;
    }
}
