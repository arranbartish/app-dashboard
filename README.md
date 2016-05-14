# app-dashboard
A dashboard for app connected to app direct. I've stayed pretty close to the ideals of a 12 factor app.

## dev url
https://arran-app-dashboard.herokuapp.com

## Improvements / limitations
- Dockerize the artifact so that testing and deployment use the same artifact
- There is a bug in the war distribution. While the API components work as expected, the static dashboard resources do not resolve
- not implemented the openID integration

# Install notes
## As a deployable jar ( Prefered )

1. user@host:~/source-root$ mvn clean package

2. user@host:~/source-root$ java -Dserver.port=(DESIRED_PORT) -jar  jar-dashboard/target/app-dashboard-(VERSION).jar

The application should now be deployed locally on the port you defined in the above command.

## Deployable war ( "Make jar not war!" - Josh Long @ Pivotal )

1. user@host:~/source-root$ mvn clean package -PJAR-AND-WAR

2. user@host:~/source-root$ cd war-dashboard;mvn cargo:run

       -- OR --

2. user@host:~/source-root$ cp war-dashboard/target/app-dashboard-war-(VERSION).war (your_tomcat)/app-dashboard-war.war

Assuming tomcat auto deploy is enabled from the webapp location, then the war will deploy on your configured port on the /app-dashboard-war context

# About the Project

This is a Spring boot app that is continuously deployed to heroku on the URL described in the [POM](parent/pom.xml)

## Continuous Deployment 

The project is build practicing continuous delivery from the beginning and is described in the [DSL](pipeline-dsl.groovy) 

Git flow is being used to allow feature branches to be automatically integrated to the develop branch the moment they pass. Obviously the means tests must be provided before pushing to origin. 

All changes wait on the develop branch until it is decided to cut a candidate branch. The Candidate will result in a versioned release which will be tested, followed by a migration to master and heroku

The command to start the app on heroku is in the Procfile and can be updated any time by changing the [Heroku Procfile template](HerokuProcfile.template). Currently there is only built in support for the version placeholder

### Testing

Tests are all of a unit flavour, however I have made a clear separation between what are true unit tests and those that are integration tests. This separation has been expressed in the maven lifecycle by running all *Test.java files in the test phase and all *IntegrationTest.java files in the integration-test phase.

#### The Jar
The jar is always built and tested by default with no additional effort. 

#### The war
The war only builds when the JAR-AND-WAR maven profile is used. The cargo plugin is bound to the pre-integration-test phase and deploys the packaged war for confirming the deployable

## 12 factor app

### I. Codebase
Single code base using gitflow and multiple version. 

### II. Dependencies
All dependencies are isolated especially SNAPSHOT intermodule dependencies. This can be see in the way the pipeline is built

### III. Config
The app comes with some configurable defaults, however the keys and environment specific items are configured on heroku

### IV. Backing services
Besides the database the only service being used is appdirect which is being used as a service.

### V. Build, release, run
The pipeline has this at its heart

### VI. Processes
App is stateless except for the spring security session, though this can be swapped out got reddit

### VII. Port binding
Self contained... except of course for the optional war component.

### VIII. Concurrency
Stateless. If the app supported user authentication I would have used redis and not app session 

### IX. Disposability
Totally disposable.

### X. Dev/prod parity
The app code deployed locally, used on CI, and on heroku is the same. This could be improved by using docker.

### XI. Logs
Logs have not been used too much in this case.

###  XII. Admin processes
No real admin processes.