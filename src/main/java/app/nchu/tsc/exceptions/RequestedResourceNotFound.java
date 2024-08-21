package app.nchu.tsc.exceptions;

public class RequestedResourceNotFound extends RuntimeException {

    private static final String msg = "The requested resource of the {0} type with ID {1} is not found.";

    public RequestedResourceNotFound(String type, String id) {
        super(msg.replace("{0}", type).replace("{1}", id));
    }

    public RequestedResourceNotFound(String message) {
        super(message);
    }

    public RequestedResourceNotFound(String type, String id, Throwable cause) {
        super(msg.replace("{0}", type).replace("{1}", id), cause);
    }

    public RequestedResourceNotFound(String message, Throwable cause) {
        super(message, cause);
    }
    
}
