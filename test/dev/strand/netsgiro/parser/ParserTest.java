package dev.strand.netsgiro.parser;

import dev.strand.netsgiro.Forsendelse;
import dev.strand.netsgiro.exception.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ParserTest {

    @Test
    public void parserTest() throws ParseException, IOException {
        List<String> lines = Files.readAllLines(Paths.get("test","res", "ocr1.txt"), StandardCharsets.UTF_8);
        Parser p = new Parser(lines.toArray(new String[0]));
        Forsendelse f = p.parse();
        System.out.println(f);
    }
}
