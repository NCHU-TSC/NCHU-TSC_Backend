package app.nchu.tsc.exceptions;

import java.util.concurrent.CompletableFuture;

import org.springframework.stereotype.Component;

import com.netflix.graphql.dgs.exceptions.DefaultDataFetcherExceptionHandler;

import graphql.execution.DataFetcherExceptionHandlerParameters;
import graphql.execution.DataFetcherExceptionHandlerResult;

@Component
public class DefaultHandler {

    private final DefaultDataFetcherExceptionHandler defaultHandler = new DefaultDataFetcherExceptionHandler();

    public CompletableFuture<DataFetcherExceptionHandlerResult> handleException(DataFetcherExceptionHandlerParameters parameters) {
        return defaultHandler.handleException(parameters);
    }
    
}
