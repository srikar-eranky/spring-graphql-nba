package com.stti.nba.config;

import com.stti.nba.exceptions.CustomGraphQLException;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;

@Component
public class CustomGraphQLExceptionHandler extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, 
    DataFetchingEnvironment env){
        if (ex instanceof CustomGraphQLException) {
            ErrorType errorType;
            if(((CustomGraphQLException) ex).getStatusCode() == 400){
                errorType = ErrorType.BAD_REQUEST;
                return graphQLError(errorType, (CustomGraphQLException) ex, env);
            }
            if(((CustomGraphQLException) ex).getStatusCode()==404){
                errorType = ErrorType.NOT_FOUND;
                return graphQLError(errorType, (CustomGraphQLException) ex, env);
            } else {
                return GraphqlErrorBuilder.newError().build();
            }
        } else {
            return GraphqlErrorBuilder.newError().build();
        }
    }

    private GraphQLError graphQLError(ErrorType errorType, CustomGraphQLException ex, DataFetchingEnvironment env){
        return GraphqlErrorBuilder.newError()
                .errorType(errorType)
                .message(ex.getMessage())
                .path(env.getExecutionStepInfo().getPath())
                .location(env.getField().getSourceLocation())
                .build();
    }

}
