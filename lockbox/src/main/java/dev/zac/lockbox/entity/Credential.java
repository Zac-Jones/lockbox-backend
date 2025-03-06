package dev.zac.lockbox.entity;

import lombok.Data;

@Data
public class Credential {
    private String id;
    private String serviceId;
    private String username;
    private String password;
}
