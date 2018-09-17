# t

See `doc/spec.pdf` for description. Run program with `lein run`, tests with
`lein test`. For development, I recommend running `lein autoexpect` in a
separate terminal for easy, automatic rerun of all tests on each change.

For distribution, compile with `lein uberjar` and then run with

```
java -jar <path-to-jar>
```

Default place for jar after compilation::

```
java -jar target/uberjar/t-app-standalone.jar
```

## License

Copyright © 2018 Gary Verhaegen.
