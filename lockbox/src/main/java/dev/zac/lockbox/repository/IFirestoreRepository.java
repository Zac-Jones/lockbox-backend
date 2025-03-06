package dev.zac.lockbox.repository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface IFirestoreRepository<T> {
    public void save(T entity);

    public void update(T entity);

    public void delete(String id);

    public T findById(String id) throws ExecutionException, InterruptedException;

    public List<T> findAll();
}
