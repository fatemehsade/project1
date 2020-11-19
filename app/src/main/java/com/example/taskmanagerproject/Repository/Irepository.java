package com.example.taskmanagerproject.Repository;

import java.util.List;
import java.util.UUID;

public interface Irepository<E> {
    void insert(E element);
    void delete(E element);
    void update(E element);
    E get(List<E> elemens);
    E getId(UUID element);
}
