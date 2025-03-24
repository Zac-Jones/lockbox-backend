package dev.zac.lockbox.entity;

import lombok.Data;

import com.google.cloud.firestore.annotation.DocumentId;

@Data
public class Company {
    @DocumentId
    private String id;
    private String userId;
    private String name;
    private String url;
    private boolean isFavourite = false;
}
