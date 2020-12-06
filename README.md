# GraphQL example with Spring Boot

- [GraphQL Example](#Graphql-example-with-spring-boot)
    - [Introduction](#introduction)
    - [Run](#run)
    - [Information](#information)
    - [Example Usages](#example-usages)
        - [Example Query](#example-query)
        - [Example Usage Ordering](#example-usage-ordering)
        - [Example Usage Filtering](#example-usage-filtering)
        - [Example Usage Paging](#example-usage-paging)
        - [Example Usage Mutation](#example-usage-mutation)

## Introduction

This repo contains a simple Spring Boot (2.x) service that implements [GraphQL](https://graphql.org/) API. The implementation is based on graphql-java-tools a GraphQL java library.

## Install
mvn clean install

## Information
Graphql Endpoint: http://localhost:8080/api

Graphiql Endpoint: http://localhost:8080/graphiql

H2 database console: http://localhost:8080/h2-console

## Example Usages
### Example Query
Query to retrieve all people. Also, info about the pagination is returned.
```graphql
query getPersons {
  persons {
    info {
      count
      pages
      size
      total
    }
    results {
      id
      firstName
      lastName
      age
    }
  }
}
```

Response:
```json
{
  "data": {
    "persons": {
      "info": {
        "count": 4,
        "pages": 1,
        "size": 1000,
        "total": 4
      },
      "results": [
        {
          "id": "1",
          "firstName": "Joe",
          "lastName": "Briggs",
          "age": 23
        },
        {
          "id": "2",
          "firstName": "John",
          "lastName": "Smith",
          "age": 44
        },
        {
          "id": "3",
          "firstName": "Steve",
          "lastName": "Jones",
          "age": 16
        },
        {
          "id": "4",
          "firstName": "Tom",
          "lastName": "Richards",
          "age": 67
        }
      ]
    }
  }
}
```

### Example Usage Ordering
Get people ordered by firt name on descendent:
```graphql
query getPersons {
  persons(orderBy:[LAST_NAME_DESC]) {
    info {
      count
      pages
      size
      total
    }
    results {
      id
      firstName
      lastName
      age
    }
  }
}
```

Response:
```json
{
  "data": {
    "persons": {
      "info": {
        "count": 4,
        "pages": 1,
        "size": 1000,
        "total": 4
      },
      "results": [
        {
          "id": "4",
          "firstName": "Tom",
          "lastName": "Richards",
          "age": 67
        },
        {
          "id": "3",
          "firstName": "Steve",
          "lastName": "Jones",
          "age": 16
        },
        {
          "id": "2",
          "firstName": "John",
          "lastName": "Smith",
          "age": 44
        },
        {
          "id": "1",
          "firstName": "Joe",
          "lastName": "Briggs",
          "age": 23
        }
      ]
    }
  }
}

```

### Example Usage Filtering
Get people filtered by age.
```graphql
{
  persons(filter: {age: 16}) {
    results {
      id
      firstName
      lastName
      age
    }
  }
}

```

Response:
```json
{
  "data": {
    "persons": {
      "results": [
        {
          "id": "3",
          "firstName": "Steve",
          "lastName": "Jones",
          "age": 16
        }
      ]
    }
  }
}
```

### Example Usage Paging
Get people paging in size 2, return page 2.
```graphql
{
  persons(page:2, size: 2) {
    results {
      firstName
      age
    }
  }
}


```

Response:
```json
{
  "data": {
    "persons": {
      "results": [
        {
          "firstName": "Steve",
          "age": 16
        },
        {
          "firstName": "Tom",
          "age": 67
        }
      ]
    }
  }
}
```


### Example Usage Mutation
Create a new person
```graphql
mutation {
  newPerson(firstName: "Jose", lastName: "Palma", age:39) {
    id
  }
}
```

Response:
```json
{
  "data": {
    "newPerson": {
      "id": "5"
    }
  }
}
```