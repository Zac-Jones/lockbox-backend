package dev.zac.lockbox.entity;

import lombok.Data;

import java.util.List;

@Data
public class Service {
    private String id;
    private String userId;
    private String name;
    private String url;
    private boolean isFavourite;
    private List<String> credentialIds;
    private List<String> twoFactorAuthIds;
}
