package dev.zac.lockbox.entity;

import lombok.Data;

import com.google.cloud.firestore.annotation.DocumentId;

@Data
public class Credential {
    @DocumentId
    private String id;
    private String companyId;
    private String username;
    private String password;
}
