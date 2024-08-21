package app.nchu.tsc.exceptions;

import com.netflix.graphql.types.errors.ErrorDetail;
import com.netflix.graphql.types.errors.TypedGraphQLError;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import graphql.execution.DataFetcherExceptionHandler;
import graphql.execution.DataFetcherExceptionHandlerParameters;
import graphql.execution.DataFetcherExceptionHandlerResult;
import graphql.GraphQLError;

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

    @Component
    public static class RequestedResourceNotFoundHandler implements DataFetcherExceptionHandler {

        @Autowired
        private DefaultHandler defaultHandler;

        @Override
        public CompletableFuture<DataFetcherExceptionHandlerResult> handleException(DataFetcherExceptionHandlerParameters parameters) {
            if (parameters.getException() instanceof RequestedResourceNotFound) {
                HashMap<String, Object> debugInfo = new HashMap<>();
                debugInfo.put("hello", "world");
                
                GraphQLError error = TypedGraphQLError.newNotFoundBuilder().errorDetail(ErrorDetail.Common.MISSING_RESOURCE)
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
