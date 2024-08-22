package app.nchu.tsc.exceptions;

import java.util.UUID;

public class PermissionDeniedException extends RuntimeException {

    private static final String msg = "The member with ID {} does not have the permission to perform this operation.";

    public PermissionDeniedException(UUID member_id) {
        super(msg.replace("{}", member_id.toString()));
    }

    public PermissionDeniedException(String message) {
        super(message);
    }

    public PermissionDeniedException(UUID member_id, Throwable cause) {
        super(msg.replace("{}", member_id.toString()), cause);
    }

    public PermissionDeniedException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
