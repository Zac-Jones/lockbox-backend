package dev.zac.lockbox.entity;

import lombok.Data;

import com.google.cloud.firestore.annotation.DocumentId;

@Data
public class TwoFactorAuth {
    @DocumentId
    private String id;
    private String serviceId;
    private String secret;
}
