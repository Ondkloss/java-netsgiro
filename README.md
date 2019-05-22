# NetsGiro

[![Build Status](https://travis-ci.com/Ondkloss/java-netsgiro.svg?branch=master)](https://travis-ci.com/Ondkloss/java-netsgiro) [![Coverage Status](https://coveralls.io/repos/github/Ondkloss/java-netsgiro/badge.svg?branch=master)](https://coveralls.io/github/Ondkloss/java-netsgiro?branch=master)

Java implementation of "OCR Giro" specification by Nets. To see the javadoc and package structure go to the [java-netsgiro](https://ondkloss.github.io/java-netsgiro) GitHub pages.

It currently does not support "Autogiro", "AvtaleGiro", "Direkte remittering" or "Egiro". While tests are limited it is not "production ready" and comes with no guarantees.

## Example usage

If you have a OCR file:

    List<String> lines = Files.readAllLines(Paths.get("path", "to", "ocr.txt"), StandardCharsets.ISO_8859_1);
    Parser p = new Parser(lines);
    Forsendelse f = p.parse();

## Source commands

To build:

    ./gradlew build

To generate javadoc:

    ./gradlew javadoc

To test with coverage report:

    ./gradlew test jacocoTestReport

## Resources

* The test resource `src/main/test/resources/ocr1.txt` is from the [OCR Giro specification](https://www.nets.eu/no-nb/PublishingImages/Lists/Accordion%20%20OCR%20giro/AllItems/OCR%20giro%20Systemspesifikasjon.pdf).
* The test resource `src/main/test/resources/ocr2.txt` is `ocr_giro_transactions.txt` from [python-netsgiro](https://github.com/otovo/python-netsgiro). Their project is licensed under Apache 2.0, which can be read in `external/apache2.0.txt`.
* The rest of the test resources are derivates of the above, or crafted for the tests in this project. They are under the license of this project.

## License

This project is released under the Apache License 2.0, as found in the `LICENSE` file.

## Other projects

* [python-netsgiro](https://github.com/otovo/python-netsgiro): A Python implementation.
