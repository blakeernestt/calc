# Expression Calculator
This project provides a simple calculator, written in Java, that evaluates it's own expression language.  See below for examples of usage.

## Usage:
The calculator is a command line tool built using Gradle.  Resolved expressions are output to the console and logging levels can be manipulated at runtime.  See below for how to invoke:

### Build and Extract Distribution:
* gradlew build
* This project uses the 'application' plugin in order to produce zip/tar distributable files, containing a shell script or batch file for simple execution.  It resolves the need to manually set the classpath for associated packaged dependencies.
* Navigate to /build/distributions
* Extract the zip or tar
* Navigate to /cal<version>/bin
* There will be a shell script and batch file to select from

### Calculator Execution:
* calc <expression\>

#### Sample Calculator Expressions
|Input|Output|
|-----|------|
|add(1, 2)|3|
|add(1, mult(2, 3))|7|
|mult(add(2, 2), div(9, 3))|12|
|let(a, 5, add(a, a))|10|
|let(a, 5, let(b, mult(a, 10), add(b, a)))|55|
|let(a, let(b, 10, add(b, b)), let(b, 20, add(a, b)))|40|

#### Rules/Limitations

#### Assumptions

### Logging
Logging has been implemented using the following levels:
* ERROR
* INFO
* DEBUG
By default a console logger has been supplied.  Feel free to add/modify your own in the properties file.

#### Dynamically
* 'calc `<expression>` -l `<logging level>`

#### Change Log4J Properties File
* A Log4J properties file has been supplied with defaults in /src/main/resource
