package Repositories;

import java.util.UUID;

public interface Repository <T>{
    public T get(UUID id);
    public void add(UUID id);
    public void delete(UUID id);
    public boolean exists(UUID id);
}
