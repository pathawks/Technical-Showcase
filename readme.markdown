# Photo Album CLI

This application retrieves photo information from an API.

I attempted to write it in a style similar to the Spring Boot services that I
have built in the past. This _was_ my first time building a command line
application with Spring, and it was fun to learn about `ApplicationRunner`.

When the applications starts, it will check to see if any albums were specified
as command line arguments. If so, it will retrieve a list of all photos
belonging to those albums, and display a list of those photos. If there are
_no_ albums specified, it will retrieve a list of _all_ photos, and display
that list.

All of the API interaction is encapsulated inside `PhotoService`. How photo
information is displayed is controlled by `PhotoDataFormatter`. The photo
information itself is represented by `PhotoData`.

`PhotoService` is tested via integration tests that spin up a mock web server.
This allows us to make sure that our client is making requests to the proper URL
and knows how to deserialize the JSON response. `WebClient` is super powerful,
but all of the chaining makes it a bit difficult to mock for purposes of unit
testing. Here, our approach of using an _actual_ `WebClient` and a mock server
allows us a high degree of confidence that our code is behaving as intended, at
the expense of slightly longer tests than a mocked `WebClient` might give us.

`PhotoDataFormatter` is tested via a simple unit test: we build a `PhotoData`
object, and ensure that we get a properly formatted string. We are not testing
any edge cases here, because we have (perhaps ill-placed) confidence that we are
only formatting data that been correctly mapped from JSON returned by the API. If
in the future that assumption is violated, or if we were to begin using
`PhotoDataFormatter` for more mission-critical applications, it would be wise to
consult with our stake-holders to determine how to handle those edge cases.

---

I had originally started writing this application in Rust, because I have been
reading about Rust and it seems interesting. However, when I started looking
into mocking frameworks available in Rust it was not obvious to me which one was
superior for my needs, so I fell back to Java with which I am more comfortable.
I did not want to begin writing code without a plan to adequately test that
code. Were I working in a collaborative environment with other developers, I
would love to seek out a Rust expert and pick their brain about how best to mock
out some calls, but this showcase is not the proper venue for that exercise.


## Building

Assuming one has Maven and a Java build environment available, one can build
this project and run the test suite with the command:

```sh
mvn clean verify
```

This will generate a `.jar` file inside the `target/` directory, which can be
executed via:

```sh
java -jar ./target/demo-0.0.1-SNAPSHOT.jar
# or
java -jar ./target/demo-0.0.1-SNAPSHOT.jar <album-id>
```
