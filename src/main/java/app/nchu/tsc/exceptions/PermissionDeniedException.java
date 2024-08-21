package app.nchu.tsc.exceptions;

import com.netflix.graphql.types.errors.TypedGraphQLError;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import graphql.execution.DataFetcherExceptionHandler;
import graphql.execution.DataFetcherExceptionHandlerParameters;
import graphql.execution.DataFetcherExceptionHandlerResult;
import graphql.GraphQLError;

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

    @Component
    public static class PermissionDeniedExceptionHandler implements DataFetcherExceptionHandler {

        @Autowired
        private DefaultHandler defaultHandler;

        @Override
        public CompletableFuture<DataFetcherExceptionHandlerResult> handleException(DataFetcherExceptionHandlerParameters parameters) {
            if (parameters.getException() instanceof PermissionDeniedException) {
                HashMap<String, Object> debugInfo = new HashMap<>();
                debugInfo.put("hello", "world");
                
                GraphQLError error = TypedGraphQLError.newPermissionDeniedBuilder()
                        .message(parameters.getException().getMessage())
                        .path(parameters.getPath()).location(parameters.getSourceLocation())
                        .debugInfo(debugInfo).debugUri("debugUri").build();
                        
                return CompletableFuture.completedFuture(DataFetcherExceptionHandlerResult.newResult().error(error).build());
            } else {
                return defaultHandler.handleException(parameters);
            }
        }

    }
    
}
