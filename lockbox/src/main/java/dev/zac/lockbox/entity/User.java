package dev.zac.lockbox.entity;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.cloud.firestore.annotation.DocumentId;

@Data
public class User {
    @DocumentId
    private String id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Map<String, LocalDateTime> knownIpAddresses = new HashMap<>();
    private List<String> serviceIds = new ArrayList<>();
    private List<String> backupCodes = new ArrayList<>();
}
