package app.nchu.tsc.exceptions;

import com.netflix.graphql.types.errors.ErrorType;
import com.netflix.graphql.types.errors.TypedGraphQLError;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import graphql.execution.DataFetcherExceptionHandler;
import graphql.execution.DataFetcherExceptionHandlerParameters;
import graphql.execution.DataFetcherExceptionHandlerResult;
import graphql.GraphQLError;

public class UnauthenticatedException extends RuntimeException {

    public UnauthenticatedException() {
        super("Please login first!");
    }

    public UnauthenticatedException(String message) {
        super(message);
    }

    public UnauthenticatedException(Throwable cause) {
        super("Please login first!", cause);
    }

    public UnauthenticatedException(String message, Throwable cause) {
        super(message, cause);
    }

    @Component
    public static class UnauthenticatedExceptionHandler implements DataFetcherExceptionHandler {

        @Autowired
        private DefaultHandler defaultHandler;

        @Override
        public CompletableFuture<DataFetcherExceptionHandlerResult> handleException(DataFetcherExceptionHandlerParameters parameters) {
            if (parameters.getException() instanceof UnauthenticatedException) {
                HashMap<String, Object> debugInfo = new HashMap<>();
                debugInfo.put("hello", "world");
                
                GraphQLError error = TypedGraphQLError.newBuilder().errorType(ErrorType.UNAUTHENTICATED)
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
