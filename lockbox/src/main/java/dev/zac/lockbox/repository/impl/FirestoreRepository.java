package dev.zac.lockbox.repository.impl;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;

import dev.zac.lockbox.entity.User;
import dev.zac.lockbox.repository.IFirestoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
public abstract class FirestoreRepository<T> implements IFirestoreRepository<T> {
    
    @Autowired
    protected Firestore firestore;
    
    protected final String collectionName;
    protected final Class<T> entityClass;
    
    @SuppressWarnings("unchecked")
    protected FirestoreRepository() {
        this.entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                .getActualTypeArguments()[0];
        this.collectionName = entityClass.getSimpleName().toLowerCase() + "s";
    }
    
    @Override
    public void save(T entity) {
        try {
            DocumentReference docRef = firestore.collection(collectionName).document();

            if (entity instanceof User) 
                docRef = firestore.collection(collectionName).document(((User) entity).getId());

            ApiFuture<WriteResult> result = docRef.set(entity);
            result.get(); // Wait for the operation to complete
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Error saving document", e);
        }
    }
    
    @Override
    public void update(T entity) {
        try {
            String id = getEntityId(entity);
            DocumentReference docRef = firestore.collection(collectionName).document(id);
            ApiFuture<WriteResult> result = docRef.set(entity);
            result.get(); // Wait for the operation to complete
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Error updating document", e);
        }
    }
    
    @Override
    public void delete(String id) {
        try {
            DocumentReference docRef = firestore.collection(collectionName).document(id);
            ApiFuture<WriteResult> result = docRef.delete();
            result.get(); // Wait for the operation to complete
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Error deleting document", e);
        }
    }
    
    @Override
    public T findById(String id) throws ExecutionException, InterruptedException {
        DocumentReference docRef = firestore.collection(collectionName).document(id);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        
        if (document.exists()) {
            return document.toObject(entityClass);
        }
        return null;
    }
    
    @Override
    public List<T> findAll() {
        try {
            ApiFuture<QuerySnapshot> future = firestore.collection(collectionName).get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            
            List<T> entities = new ArrayList<>();
            for (QueryDocumentSnapshot document : documents) {
                entities.add(document.toObject(entityClass));
            }
            return entities;
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Error retrieving all documents", e);
        }
    }
    
    // Helper method to get the ID field value from an entity
    protected abstract String getEntityId(T entity);
}
