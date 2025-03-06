package dev.zac.lockbox.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SystemLog {
    private String id;
    private String userId;
    private LogAction action;
    private String description;
    private LocalDateTime dateTime;

    public enum LogAction {
        // General action logging
        CREATE_ACCOUNT("Account has been created for user ID: %s with email: %s from IP address: %s"),
        UPDATE_ACCOUNT("Account with ID %s has been updated from IP address: %s"),
        DELETE_ACCOUNT("Account with ID %s has been deleted from IP address: %s"),
        LOGIN_EXISTING_IP("User with ID %s logged in from an existing IP address: %s"),
        LOGIN_NEW_IP("User with ID %s logged in from a new IP address: %s"),
        LOGOUT("User with ID %s logged out from IP address: %s"),
        CREATE_CREDENTIAL("Password entry has been created for user ID: %s with provider: %s from IP address: %s"),
        UPDATE_CREDENTIAL("Password entry for user ID %s has been updated from IP address: %s"),
        DELETE_CREDENTIAL("Password entry for user ID %s has been deleted from IP address: %s"),
        CREATE_2FA_SERVICE("User with ID %s added a new 2FA service: %s from IP address: %s"),
        DELETE_2FA_SERVICE("User with ID %s removed the 2FA service: %s from IP address: %s"),
        CREATE_BACKUP_CODES("User with ID %s created backup codes for 2FA from IP address: %s"),
        USE_BACKUP_CODES("User with ID %s used backup codes: %s from IP address: %s"),

        // Error logging
        INVALID_CREDENTIALS("Invalid credentials for user with email: %s from IP address: %s"),
        INVALID_2FA_BACKUP_CODES("Invalid 2FA code for user with ID: %s from IP address: %s"),
        INVALID_2FA_GENERATION("Invalid 2FA generation for user with ID: %s"),
        INVALID_IP_ADDRESS("Invalid IP address for user with ID: %s"),
        INVALID_EMAIL_CODE("Invalid email code for user with ID: %s and email: %s from IP address: %s"),
        ;

        private final String defaultDescription;

        LogAction(String defaultDescription) {
            this.defaultDescription = defaultDescription;
        }

        public String getMessage(String... args) {
            return String.format(defaultDescription, (Object[]) args);
        }
    }
}
