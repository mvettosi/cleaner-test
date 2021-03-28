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

The application has 47 automated tests, covering all the relevant business logic lines, and they can be all launched using
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