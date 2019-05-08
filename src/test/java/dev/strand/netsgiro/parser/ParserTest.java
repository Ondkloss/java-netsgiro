package dev.strand.netsgiro.parser;

import dev.strand.netsgiro.Forsendelse;
import dev.strand.netsgiro.exception.ParseException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ParserTest {

    @Test
    public void parserTest() throws ParseException, IOException, URISyntaxException {
        List<String> lines = Files.readAllLines(Paths.get(this.getClass().getResource("/ocr1.txt").toURI()),
                StandardCharsets.UTF_8);
        Parser p = new Parser(lines.toArray(new String[0]));
        Forsendelse f = p.parse();
        System.out.println(f);
    }

    @Disabled
    @Test
    public void parserTest2() throws ParseException, IOException, URISyntaxException {
        List<String> lines = Files.readAllLines(Paths.get(this.getClass().getResource("/ocr2.txt").toURI()),
                StandardCharsets.UTF_8);
        Parser p = new Parser(lines.toArray(new String[0]));
        Forsendelse f = p.parse();
        System.out.println(f);
    }
}
