# Ecards

Greeting ecards web application: Angular(front-end) + Spring(Rest API) + MySQL 

## Front-end (Angular 6 + Bootstrap 4)

In the application.properties, you have to modify the following:
* properties related to JavaMail
    * spring.mail.host = smtp.gmail.com
    * spring.mail.username = ********@gmail.com
    * spring.mail.password = ******
* properties related to the MySQL database
    * spring.datasource.url = jdbc:mysql://localhost:3306/db_ecards?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&useSSL=false
    * spring.datasource.username = *********
    * spring.datasource.password = *********

_Keywords: Typescript/Javascript, Single page application, html5, Css3, Responsive design(Flexbox), 
Component architecture, functional/reactive programming (rxjs), routing/navigation, testing(Jasmine), security(authentification(Oauth2)/authorization), validation, exception handling, caching, internationalization, Visual studio code_

## Back-end (REST Api Spring 5 + Hibernate + MySQL)

You'll need to install the following dependencies:

* https://www.npmjs.com/package/angular2_photoswipe
* https://www.npmjs.com/package/ngx-quill
* https://www.npmjs.com/package/ngx-pagination
* https://www.npmjs.com/package/ngx-cookie-service

Also in the environment.ts, you have to modify the following properties:
* apiUrl: 'http://localhost:8080',   (this is the url to the Spring REST API)
* giphy_api_key: '************'   (you can get the Giphy API key here: https://developers.giphy.com/docs/)

_Keywords: REST API, Java, SQL, xml/Json, Spring boot, tests unitaires(JUnit+Mockito), Tomcat, 
maven, Eclipse (STS), documentation(Swagger), multithreading/concurrency, Programmation orientée objet, design patterns, Service oriented architecture, security(authentification(Oauth2)/authorization), validation, exception handling, caching, monitoring, internationalization, versioning, pagination/filtering/sorting, content-negotiation, JavaMail_

## Screenshots

![screenshot](/screenshot-1.png)
![screenshot](/screenshot-2.png)
![screenshot](/screenshot-3.png)
![screenshot](/screenshot-4.png)
![screenshot](/screenshot-5.png)
  
