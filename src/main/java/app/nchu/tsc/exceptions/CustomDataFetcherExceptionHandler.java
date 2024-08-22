package app.nchu.tsc.exceptions;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Component;

import com.netflix.graphql.dgs.exceptions.DefaultDataFetcherExceptionHandler;
import com.netflix.graphql.types.errors.ErrorDetail;
import com.netflix.graphql.types.errors.ErrorType;
import com.netflix.graphql.types.errors.TypedGraphQLError;

import graphql.GraphQLError;
import graphql.execution.DataFetcherExceptionHandler;
import graphql.execution.DataFetcherExceptionHandlerParameters;
import graphql.execution.DataFetcherExceptionHandlerResult;

@Component
public class CustomDataFetcherExceptionHandler  implements DataFetcherExceptionHandler {

    private final DefaultDataFetcherExceptionHandler defaultHandler = new DefaultDataFetcherExceptionHandler();

    @Override
    public CompletableFuture<DataFetcherExceptionHandlerResult> handleException(DataFetcherExceptionHandlerParameters parameters) {
        if(parameters.getException() instanceof UnauthenticatedException) {
            HashMap<String, Object> debugInfo = new HashMap<>();
            debugInfo.put("hello", "world");
            
            GraphQLError error = TypedGraphQLError.newBuilder().errorType(ErrorType.UNAUTHENTICATED)
                    .message(parameters.getException().getMessage())
                    .path(parameters.getPath()).location(parameters.getSourceLocation())
                    .debugInfo(debugInfo).debugUri("debugUri").build();
                    
            return CompletableFuture.completedFuture(DataFetcherExceptionHandlerResult.newResult().error(error).build());
        } else if (parameters.getException() instanceof PermissionDeniedException) {
            HashMap<String, Object> debugInfo = new HashMap<>();
            debugInfo.put("hello", "world");
            
            GraphQLError error = TypedGraphQLError.newPermissionDeniedBuilder()
                    .message(parameters.getException().getMessage())
                    .path(parameters.getPath()).location(parameters.getSourceLocation())
                    .debugInfo(debugInfo).debugUri("debugUri").build();
                    
            return CompletableFuture.completedFuture(DataFetcherExceptionHandlerResult.newResult().error(error).build());
        } else if(parameters.getException() instanceof RequestedResourceNotFound) {
            HashMap<String, Object> debugInfo = new HashMap<>();
            debugInfo.put("hello", "world");
            
            GraphQLError error = TypedGraphQLError.newNotFoundBuilder().errorDetail(ErrorDetail.Common.FIELD_NOT_FOUND)
                    .message(parameters.getException().getMessage())
                    .path(parameters.getPath()).location(parameters.getSourceLocation())
                    .debugInfo(debugInfo).debugUri("debugUri").build();
                    
            return CompletableFuture.completedFuture(DataFetcherExceptionHandlerResult.newResult().error(error).build());
        } else {
            return defaultHandler.handleException(parameters);
        }
    }
    
}
