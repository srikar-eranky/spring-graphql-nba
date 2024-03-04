# spring-graphql-nba
Basic spring graphql application using an in memory H2 database and spring jdbc

# GraphQL setup
Under src/main/resources, create nba.graphqls file and add the schema for types and queries <br> 
Enable the graphiql web interface in application.properties<br>
Create a basic @Controller class and use @QueryMapping to map the graph ql query<br>

# H2 db setup and enabling jdbc
Define schema.sql to create tables and data.sql to insert some values<br>
Create a data source @Configuration class and define @Bean data source<br>
Create entities, row mappers to map from db to java class and DAOs (@Repository) to fetch data from DB using @JdbcTemplate<br>
Define h2 db properties in application.properties and disable jpa hibernate as we will use pure spring jdbc only for this project<br>
Inject the DAO into the controller to get the data from DB when the graphql query is issued from the graphql web console<br>
H2 Console http://localhost:8080/h2-console <br>
Use jdbc:h2:mem:testdb as JDBC URL


# Queries to execute
GraphQL browser : http://localhost:8080/graphiql?path=/graphql <br>
query {<br>
teams {<br>
    id<br>
    name<br>
    city<br>
  }<br>
}<br>

# Constraints
A Player can belong to only one Team<br>
A Player can have only one Position<br>
A Team can have only 5 players<br>

# Stats
Team stats - Played, Won, Lost, PPG, Rebounds, Assists, Blocks, FG %, FT % <br>
Player stats - Min, PTS, FG %, FT%, 3P %, ASS, STL, BLK <br>

# Actions
List all teams <br>
List all players in a team <br>
Create or Remove Team <br>
Create or Remove Player <br>
Add or remove Player from Team <br>
List all stats for a Team , for a Player <br>
List all players in Team with their stats <br>



  
