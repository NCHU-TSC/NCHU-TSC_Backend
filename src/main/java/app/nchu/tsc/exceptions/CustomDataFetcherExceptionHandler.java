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
        HashMap<String, Object> debugInfo = new HashMap<>();
        debugInfo.put("hello", "world");

        if(parameters.getException() instanceof UnauthenticatedException) {
            GraphQLError error = TypedGraphQLError.newBuilder().errorType(ErrorType.UNAUTHENTICATED)
                    .message(parameters.getException().getMessage())
                    .path(parameters.getPath()).location(parameters.getSourceLocation())
                    .debugInfo(debugInfo).debugUri("debugUri").build();
                    
            return CompletableFuture.completedFuture(DataFetcherExceptionHandlerResult.newResult().error(error).build());
        } else if (parameters.getException() instanceof PermissionDeniedException) {
            GraphQLError error = TypedGraphQLError.newPermissionDeniedBuilder()
                    .message(parameters.getException().getMessage())
                    .path(parameters.getPath()).location(parameters.getSourceLocation())
                    .debugInfo(debugInfo).debugUri("debugUri").build();
                    
            return CompletableFuture.completedFuture(DataFetcherExceptionHandlerResult.newResult().error(error).build());
        } else if(parameters.getException() instanceof RequestedResourceNotFound) {
            GraphQLError error = TypedGraphQLError.newNotFoundBuilder().errorDetail(ErrorDetail.Common.FIELD_NOT_FOUND)
                    .message(parameters.getException().getMessage())
                    .path(parameters.getPath()).location(parameters.getSourceLocation())
                    .debugInfo(debugInfo).debugUri("debugUri").build();
                    
            return CompletableFuture.completedFuture(DataFetcherExceptionHandlerResult.newResult().error(error).build());
        } else if(parameters.getException() instanceof IllegalArgumentException) {
            GraphQLError error = TypedGraphQLError.newBadRequestBuilder().errorDetail(ErrorDetail.Common.INVALID_ARGUMENT)
                    .message(parameters.getException().getMessage())
                    .path(parameters.getPath()).location(parameters.getSourceLocation())
                    .debugInfo(debugInfo).debugUri("debugUri").build();
                    
            return CompletableFuture.completedFuture(DataFetcherExceptionHandlerResult.newResult().error(error).build());
        } else {
            return defaultHandler.handleException(parameters);
        }
    }
    
}
