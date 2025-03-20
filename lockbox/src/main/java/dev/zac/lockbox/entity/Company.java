package dev.zac.lockbox.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import com.google.cloud.firestore.annotation.DocumentId;

@Data
public class Company {
    @DocumentId
    private String id;
    private String userId;
    private String name;
    private String url;
    private boolean isFavourite = false;
    private List<String> credentialIds = new ArrayList<>();
    private List<String> twoFactorAuthIds = new ArrayList<>();
}
