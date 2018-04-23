# IMDb 
The application will use the API from The Movie DB ( https://developers.themoviedb.org/3/getting-started ). The goal is to implement an application or script that will retrieve and store movie information.

## Prerequisites

* JDK 1.8
* MySQL

## external lib
* json-simple-1.1.jar 
* okhttp -2.7.5.jar and okio-1.14.0.jar
* mysql-connector-java -5.1.46.jar

## Artitecture
* Bean package
** Movie class with id, title, description, original title and a list of its directors
** Director with id, name and profile linke
* DAO
** Interface: Dao
** MovieDataDAO used to connect to MySQL database and update database
* IMDbController:
** A controller with fetch data from IMDb, parse json and map to object and also map to tables in databases


## Built With

* [sts](https://spring.io/tools/sts) - IDE
