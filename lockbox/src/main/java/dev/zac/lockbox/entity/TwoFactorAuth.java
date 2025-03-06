package dev.zac.lockbox.entity;

import lombok.Data;

@Data
public class TwoFactorAuth {
    private String id;
    private String serviceId;
    private String secret;
}
