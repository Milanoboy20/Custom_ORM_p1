# Custom_ORM

## Project Description

To create a custom object relational mapping (ORM) framework, written in Java, which allows for a simplified and SQL-free interaction with a relational data source. Low-level JDBC is completely abstracted away from the developer, allowing them to easily query and persist data to a data source. Makes heavy use of the Java Reflection API in order to support any entity objects as defined by the developer. Additionally, the framework offers connection pooling to support multi-threaded applications.

## Technologies Used

* Java 8
* JUnit 5
* Apache Maven
* PostgreSQL
* AWS RDS
* Git SCM (on GitHub)

## Features

* Works with PostgreSQL url format
* Able to perform CRUD Operations on your database
* Works with any entity objects defined in your database
* Add a connection.properties file to work with a specific endpoint
* Can be added as a Maven local dependency to other projects

To-do list:
* Add Log4j to log transactions/operation performed with the Custom_ORM
* Add support for basic transaction management (begin, commit, savepoint, rollback)
* Add connection pooling to support multi-threaded applications
* Add functionality to easily parse in RDBMS endpoint, url, username and password.

## Getting Started

This ORM works with PostgreSQL statements.

To use this Custom_ORM, first clone the repository. Open it in your prefered IDE and add a 
connection.properties (with your endpoint, username and password) file to the resources folder.
Check and change the url format in the utilities/ConnectionToDB to the appropriate url format.

To Test the ORM with the available JUnit 5 Test folder. Create three tables in your RDBMS with 
the provided SQL Script, add some data(optional) and then run the test class. 



## Usage

> This Custom_ORM was used to create a basic Restaurant Reservation web application with Servlets
> link: 

## Contributors

> Micheal Ord


