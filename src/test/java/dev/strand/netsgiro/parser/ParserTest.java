package dev.strand.netsgiro.parser;

import dev.strand.netsgiro.Forsendelse;
import dev.strand.netsgiro.exception.ParseException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ParserTest {

    @Test
    public void parserTest() throws ParseException, IOException, URISyntaxException {
        List<String> lines = Files.readAllLines(Paths.get(this.getClass().getResource("/ocr1.txt").toURI()),
                StandardCharsets.UTF_8);
        Parser p = new Parser(lines);
        Forsendelse f = p.parse();
        System.out.println(f);
    }

    @Disabled
    @Test
    public void parserTest2() throws ParseException, IOException, URISyntaxException {
        List<String> lines = Files.readAllLines(Paths.get(this.getClass().getResource("/ocr2.txt").toURI()),
                StandardCharsets.UTF_8);
        Parser p = new Parser(lines);
        Forsendelse f = p.parse();
        System.out.println(f);
    }

    @Test
    public void parserTest3() throws ParseException, IOException, URISyntaxException {
        // This test is hand made with the first transaction being replaced with one of type 21
        // The rest of the file is identical to ocr1.txt
        List<String> lines = Files.readAllLines(Paths.get(this.getClass().getResource("/ocr3.txt").toURI()),
                StandardCharsets.UTF_8);
        Parser p = new Parser(lines);
        Forsendelse f = p.parse();
        System.out.println(f);
    }

    @Test
    public void parserExceptionTooFewLinesTest() throws ParseException, IOException, URISyntaxException {
        List<String> lines = new ArrayList<>();
        ParseException thrown = Assertions.assertThrows(ParseException.class, () -> {
            Parser p = new Parser(lines);
            p.parse();
        });
        Assertions.assertEquals("Valid data cannot be less than two records.", thrown.getMessage());
    }

    @Test
    public void parserExceptionFromValidationTest() throws ParseException, IOException, URISyntaxException {
        List<String> lines = new ArrayList<>();
        lines.add("NY000010000090900170031000102000000000000000000000000000000000000000000000000000"); // Start forsendelse with error
        lines.add("NY000089000000230000005000000000001563000240304000000000000000000000000000000000"); // End forsendelse
        ParseException thrown = Assertions.assertThrows(ParseException.class, () -> {
            Parser p = new Parser(lines);
            p.parse();
        });
        Assertions.assertEquals("Error during validation.", thrown.getMessage());
        Assertions.assertEquals("Invalid data sender. Should be 00008080 (NETS).", thrown.getCause().getMessage());
    }
}
