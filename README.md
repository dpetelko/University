# University

Task9(dev)
Analyze and decompose University (create UML class diagram for application).
You should make a research and describe university structure.
The main feature of the application is Class Timetable for students and teachers.
Students or teachers can get their timetable for a day or for a month.

Task10
Create a JAVA project based on the University UML class diagram from the previous task

Task11
Create Spring JDBC based DAO for your application

Task12
Create a service layer and implement business logic (add/remove entities to other entities and save them to DB, etc). A mentor can provide additional business rules. You should use Spring IoC

Task13
Add custom exceptions and logging. Use slf4j + logback

Task14
Create status pages (read data from DB - show it in HTML). Use Spring MVC and Thymeleaf, Bootstrap

Task15
Create full CRUD pages for models that were used in the previous task.

Task16
Create DataSource in web-project configuration files. Switch DAO layer to lookup DataSource by JNDI name.

Task17 
Rewrite the DAO layer. Use Hibernate instead of Spring JDBC

Task18
Convert application to Spring Boot

Task19
Rewrite the DAO layer. Use Spring Data JPA instead of Hibernate

Task20
Add validation to your models. It could be name validation, time validation, number of students in groups, etc. Ask your mentor for validations that should be implemented in your code.

Task21 
Add REST Endpoints to your project. All UI functionality should be available in the REST endpoints

Task22
Add Swagger documentation to your project 

Task23
Write Integration(or/and System) tests using Spring Boot Test, H2 DB (it is possible to use Database Rider)


<hr>
<h1 align="center"> Installation of the working environment:</h1>
1. Install JDK8 from here https://www.oracle.com/java/technologies/javase-jdk8-downloads.html<br>
2. Install latest version Eclipse IDE for EE from here https://www.eclipse.org/downloads/<br>
3. Install Apache Tomcat 8.5 from here https://tomcat.apache.org/download-80.cgi<br>
	Don't use Installer, download Tomcat as ZIP and unzip to directory<br>
	In Eclipse choose Window - Show view - Servers and setup your Tomcat 8.5 as localhost<br>
4. Install PostgreSQL 12 from here https://www.postgresql.org/download/<br>
5. Install latest version Git from here https://git-scm.com/downloads<br>
	Generate SSH-keys (info here https://git.foxminded.com.ua/help/ssh/README) and add this keys to Eclipse and to your Gitlab profile<br>

<h1 align="center"> Preparing for work: </h1>
1. Clone application from Gitlab to local repository<br>
2. Import cloned project to Eclipse (Import/General/Projects from Folder or Archive)<br>
3. Run Sql-scripts from src/main/resources/sql in the next order:<br>
    3-1. Create database user: <br>
         For Unix-like: $ sudo -u postgres psql -f create_user.sql<br>
	    For Windows psql -U postgres -f "src\main\resources\sql\create_user.sql"<br>
    3-2. Create working database: <br>
	    For Unix-like: $ sudo -u postgres psql -f create_work_db.sql<br>
	    For Windows psql -U postgres -f "src\main\resources\sql\create_work_db.sql"<br>
    3-3. Create testinf database:<br>
        For Unix-like: $ sudo -u postgres psql -f create_test_db.sql<br>
        For Windows psql -U postgres -f "src\main\resources\sql\create_test_db.sql"<br>
    3-4. For create tables use <br>
        mvn flyway:baseline<br>
        mvn flyway:clean<br>
    3-5. For insert example database entries:<br>
        For Unix-like: $ sudo -u postgres psql -f insert_data.sql<br>
        For Windows psql -U postgres -d university_local -f "src\main\resources\sql\insert_data.sql"<br>
       
<h1 align="center"> Deploy&Run </h1>

1. Add record<br>
&lt;user username="user" password="user" roles="manager,manager-script,manager-gui"/&gt;<br>
    to file "TomcatDir"/conf/tomcat-users.xml). Use name&password from POM.xml<br>
2. Add record maven/conf/settings.xml<br>
&lt;server&gt;<br>
&lt;id&gt;TomcatServer&lt;/id&gt;<br>
&lt;username&gtuser&lt;/username&gt<br>
&lt;password&gt;user&lt;/password&gt;<br>
&lt;/server&gt;<br>
3. Run "/bin/startup.bat" in your Tomcat directory for Starting Server<br>
4. Run in Console mvn tomcat:deploy<br>
5. Enter http://127.0.0.1:8080/university/ in browser<br>
<hr>	
    Enjoy!!!
    
Dear friend!
If you want to setup JNDI in Tomcat container, follow the directions below:
1. Delete context.xml & web.xml from webapp folder
1. Add to context.xml in the Context section:<br>
&lt;ResourceLink <br>
    name="jdbc/universityDatabase"<br>
    global="jdbc/universityDatabase" <br>
    type="javax.sql.DataSource" &gt;<br>
2. Add to server.xml in the GlobalNamingResources section: <br>
        &lt;Resource <br>
        name="jdbc/universityDatabase"<br>
            auth="Container" <br>
            type="javax.sql.DataSource"<br>
            driverClassName="org.postgresql.Driver"<br>
            url="jdbc:postgresql://localhost/university_local"<br>
            username="university_admin" <br>
            password="050679" <br>
            maxTotal="20"<br>
            maxIdle="10" <br>
            maxWaitMillis="-1" /&gt;<br>


Dear friend!
REST API documentation is at http://localhost:8080/swagger-ui.html#/

May the Force be with you!!! Good luck!!!

