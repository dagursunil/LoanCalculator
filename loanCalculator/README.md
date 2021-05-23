# loanCalculator 
Loan calculator is a spring boot project. This project is a service to show plan for payment of loan throughout the term.

This project has one POST service  `` /generate-plan `` which will accept  user input in below format.

```
{
  "duration": 50,
  "loanAmount": 5000,
  "nominalRate": 12,
  "startDate":"2021-05-23T14:05:42Z"
}
```

data should be sent as payload in desired format else user will see error message complaining for desired data format.

If input data is in correct format , service will return the plan for payment of installments along with all required details.


# production ready dependencies
1. Basic health-check and monitoring functions using spring HealthIndicator ,This module has some of the great features like metrics, health check, log, info, etc. exposed as the endpoints. 

```
xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```
2. Logger configuration.
3. To create a ‘fully executable’ jar with Maven use the following plugin configuration:

```
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
    <configuration>
        <executable>true</executable>
    </configuration>
</plugin>
```
4. Adjusting the number of concurrent threads in application.properties example: server.tomcat.max-threads=400 .

5. Port of the application to be run can be changed in application.properties ,example:
server.port=8080

# How to build

Prerequisite :  maven should be installed and enabled.
For executing the project directly from directory please follow steps mentioned below :

```
Windows :
1.) Go to project directory .
2.) Open command prompt.
3.) Type mvnw clean install
4.) type mvnw spring-boot:run
```

```
Unix : 
1. cd to project directory.
2.) ./mvnw clean install
3.) ./mvnw spring-boot:run
```
	
