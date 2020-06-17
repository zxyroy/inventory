# Inventory Application for a department store
This is a interview answer
## Assumption
Category and Sub category is store in system. User cannot add/modify it

Since it is a backend test, frontend do not implement with complex structure
## Local Run
The project is running and test on JDK 11

The frontend project is wrotten by ReactJs

For local run from source code, start backend first. Then start frontend
```sh
mvn spring-boot:run
cd frontend
npm start
```
Then you can visit through `http://localhost:3000`

Or You can run both of them with the packaged jar
```sh
mvn clean install package
cd target
jar -jar inventory-0.0.1-SNAPSHOT.jar
```  
Then you can visit through `http://localhost:8080`

You can also run with Docker, docker file is also ready
#### Reset the database
Each time restart the database will be reset
## Local Develop
**Lombok** is being used. Plugin need to be installed if you want to develop with IDE.
#### Intellij IDEA
https://plugins.jetbrains.com/plugin/6317-lombok
#### Eclipse
https://projectlombok.org/setup/eclipse