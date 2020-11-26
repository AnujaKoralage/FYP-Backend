# ELECTRONIC WALLET BASED FOREX TRADING SYSTEM [Back end]

Overall target is to develop a platform to invest money for poor know ledged people on rich know ledged people, then rich knowledge people can invest more money and earn more profits. Also, poor knowledge people can earn money without taking more risks. This application will able to develop a strong relationship between low level investors (trader) and high-level investors (investor). By using this application low level investor able to gather more money and earn more profits for themselves and for the money supplier (high level investor). Also, low level investor can earn more reputation based on him/her past investing activities.

### Built with

* Spring Framework
* Spring Security
* Spring Hystrix
* Spring Cloud
* Netflix Eureka Service Registry
* Spring Cloud Gateway
* Maven
* Many More...

### Prerequisites

You need git to clone the `FYP-Back-End` repository. You can get git from [here].

This project contains 8 microservices and here i gather it together. In future i will upload one by one microservice with specs.

### Clone `FYP-Back-End`

Clone the `FYP-Back-End` repository using git:

```
https://github.com/AnujaKoralage/FYP-Backend.git
cd FYP-Back-End
```
![Architecture Design](https://user-images.githubusercontent.com/40436845/100263456-8ab82300-2f73-11eb-8ff8-ec6cbf16134c.PNG)

## Installing

*	Default active profile is **`main`**. When the application is running,
*   Direct into Each and every service and install maven dependencies and run services

#### Running the application with IDE

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.fyp.serviceName` class from your IDE.

* 	Download the zip or clone the Git repository.
* 	Unzip the zip file (if you downloaded one)
* 	Open Command Prompt and Change directory (cd) to folder containing pom.xml
* 	Open Eclipse
	* File -> Import -> Existing Maven Project -> Navigate to the folder where you unzipped the zip
	* Select the project
* 	Choose the Spring Boot Application file (search for @SpringBootApplication)
* 	Right Click on the file and Run as Java Application

#### Running the application with Maven

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
$ git clone https://github.com/Spring-Boot-Framework/Spring-Boot-Application-Template.git
$ cd Spring-Boot-Application-Template
$ mvn spring-boot:run
```
