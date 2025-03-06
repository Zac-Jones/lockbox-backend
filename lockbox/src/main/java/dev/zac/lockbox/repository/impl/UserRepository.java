package dev.zac.lockbox.repository.impl;

import dev.zac.lockbox.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository extends FirestoreRepository<User> {

    @Override
    protected String getEntityId(User entity) {
        return entity.getId();
    }
    
}
