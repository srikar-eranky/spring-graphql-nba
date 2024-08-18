import React, { useEffect, useState } from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { ApolloClient, InMemoryCache, ApolloProvider } from '@apollo/client';
import 'bootstrap/dist/css/bootstrap.min.css';
import { AuthProvider } from './contexts/authContext';

// const domain = process.env.NEXT_PUBLIC_AUTH0_DOMAIN || '';
// const clientId = process.env.NEXT_PUBLIC_AUTH0_CLIENT_ID || ''
// const redirectUri = process.env.NEXT_PUBLIC_AUTH0_CALLBACK_URL || '';
// const audience = process.env.NEXT_PUBLIC_AUTH0_AUDIENCE || '';

const apolloClient = new ApolloClient({
  uri: "http://localhost:8080/graphql",
  cache: new InMemoryCache(),
});

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <ApolloProvider client={apolloClient}>
      <AuthProvider>
        <React.StrictMode>
          <App />
        </React.StrictMode>
      </AuthProvider>
    </ApolloProvider>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
