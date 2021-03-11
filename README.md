# LOG4W
### A logging framework meant to assist in the monitoring of java running java applications.

#### What is logging?
* Keeping a record of everything that has happened.
* Specifies who performed an action inside an application.
* Keeps track of the logic flow inside the program
#### Why should we use logging?
* Track unauthorized interactions
* Debugging
* Keep records of events that persist through application crashes
### Specifications
* Log events and actions when they occur to a file of some kind
* Configurable 
> * What events to be logged
> * The information to be logged
> * The location of the log file
> * The format of the log file (timestamps for example)

* Log custom information about events
* Breadcrumb feature to enable devs to track a category of logs (Story feature)
* Time travel from a log file to reproduce the log events in the order they occurred
* Dying wish feature - Log one more event to the file in the event of a complete system crash (blackbox, a snapshot of the application)

### Use this in a project
* clone the project 
    > git clone project.git
* Package the application as a jar and add it to your local repository
    > mvn install
* Add the jar as a dependency into your project's pom.xml
    ```
    <dependency>
        <groupId>dev.enterprise</groupId>
        <artifactId>Log4W</artifactId>
        <version>0.1</version>
    </dependency>
  ```