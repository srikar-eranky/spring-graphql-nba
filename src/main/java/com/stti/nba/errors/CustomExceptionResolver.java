package com.stti.nba.errors;

import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.stereotype.Component;

import com.stti.nba.errors.dataexceptions.InvalidArgumentException;
import com.stti.nba.errors.dataexceptions.PlayerAlreadyExistsException;
import com.stti.nba.errors.dataexceptions.PlayerNotFoundException;
import com.stti.nba.errors.dataexceptions.TeamAlreadyExistsException;
import com.stti.nba.errors.dataexceptions.TeamNotFoundException;
import com.stti.nba.errors.dataexceptions.UserAlreadyExistsException;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import org.springframework.graphql.execution.ErrorType;
import graphql.schema.DataFetchingEnvironment;

@Component
public class CustomExceptionResolver extends DataFetcherExceptionResolverAdapter {
    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
        if (ex instanceof PlayerNotFoundException || 
        ex instanceof TeamNotFoundException) {
            return GraphqlErrorBuilder.newError()
              .errorType(ErrorType.NOT_FOUND)
              .message(ex.getMessage())
              .path(env.getExecutionStepInfo().getPath())
              .location(env.getField().getSourceLocation())
              .build();
        } else if (ex instanceof PlayerAlreadyExistsException || 
        ex instanceof TeamAlreadyExistsException ||
        ex instanceof UserAlreadyExistsException) {
            return GraphqlErrorBuilder.newError()
              .errorType(ErrorType.BAD_REQUEST)
              .message(ex.getMessage())
              .path(env.getExecutionStepInfo().getPath())
              .location(env.getField().getSourceLocation())
              .build();
        } else if (ex instanceof InvalidArgumentException) {
            return GraphqlErrorBuilder.newError()
            .errorType(ErrorType.BAD_REQUEST)
            .message(ex.getMessage())
            .path(env.getExecutionStepInfo().getPath())
            .location(env.getField().getSourceLocation())
            .build();
        } else {
            return null;
        }
    }
}
