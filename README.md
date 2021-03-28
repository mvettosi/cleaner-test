# Your submission

This is the README for your submission, add any relevant information to it.

You can find details instruction in the INSTRUCTIONS.md file.

## Submitting your solution

Please push your changes to the `master branch` of this repository. You can push one or more commits. <br>

Once you are finished with the task, please click the `Complete task` link on <a href="https://app.codescreen.dev/#/codescreentest8ccdaaaf-f3df-492b-a58d-e6bcf261cb2f" target="_blank">this screen</a>.

# Candidate-added documentation

# Build and run

The application only require Java 11 to be installed and available to the environment, and can be built and run with
```shell
./mvnw spring-boot:run
```

# Test

The application showcases a 100% test coverage on all the relevant business logic (some generated code from Lombok might be missed by coverage analyzers), and they can be all launched using
```shell
./mvnw test
```

# Formatting

The code style used for this project is the official Google Java formatting style with no modifications.
In normal conditions on a project with multiple developers, it would be enforced with a pre-commit hook by a plugin like spotless, but considered the nature of this project, for the sake of simplicity the coding style was imported in the Intellij IDE and applied manually on every source file before committing.

Definition of the Intellij configuration can be found at https://github.com/google/styleguide/blob/gh-pages/intellij-java-google-style.xml

# Considerations

This application was generally treated and developed as a professional microservice, as you would expect from a code test.

There were, however, a few exceptions to the most common conventions regarding real life microservices, either because of the small scale of the project, or because of unclear requirements:

- The controller and the service classes are not abstracted behind an Interface, as they would be in a normal project, in an attempt to keep the source count small.
- There are no end-to-end tests. This is because it was judged that at this stage the project lacks a real deployment specification like http security, load balancing, etc. Moreover, effective end-to-end tests are often written in a separate language/framework, which is outside the scope of this test.
- For similar reasons, performance tests were also skipped.
- Regex pattern on the request body's navigation instructions is not being applied to allow generating an error message that is more relevant to the user.
- A version of this implementation that uses custom deserializers was initially implemented and is still available in the `alternative/custom-deserializers` branch. While the final implementation with "manual" parsers is simpler to read and more streamlined with the rest of the application, the custom deserializers provided a single point of control for the format of the request body, as well as an easier way to support multiple alternative formats if desired. For these reasons, it was judged worth to provide the alternative implementation, too, for reference. 