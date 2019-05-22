package dev.strand.netsgiro.parser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import dev.strand.netsgiro.Forsendelse;
import dev.strand.netsgiro.exception.ParseException;

public class ParserTest {

    @Test
    public void parserTest() throws ParseException, IOException, URISyntaxException {
        List<String> lines = Files.readAllLines(Paths.get(this.getClass().getResource("/ocr1.txt").toURI()), StandardCharsets.UTF_8);
        Parser p1 = new Parser(lines);
        Forsendelse f1 = p1.parse();
        System.out.println(f1);
        Assertions.assertEquals(1, f1.getOppdrag().size());
        Assertions.assertEquals(50, f1.getAntallRecords());
        Assertions.assertEquals(23, f1.getOppdrag().get(0).getTransaksjoner().size());
        Assertions.assertEquals(48, f1.getOppdrag().get(0).getAntallRecords());

        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            f1.getOppdrag().add(null);
        });
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            f1.getOppdrag().get(0).getTransaksjoner().add(null);
        });

        Parser p2 = new Parser(lines.toArray(new String[0]));
        Forsendelse f2 = p2.parse();
        System.out.println(f2);
        Assertions.assertEquals(1, f2.getOppdrag().size());
        Assertions.assertEquals(50, f2.getAntallRecords());
        Assertions.assertEquals(23, f2.getOppdrag().get(0).getTransaksjoner().size());
        Assertions.assertEquals(48, f2.getOppdrag().get(0).getAntallRecords());

        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            f2.getOppdrag().add(null);
        });
        Assertions.assertThrows(UnsupportedOperationException.class, () -> {
            f2.getOppdrag().get(0).getTransaksjoner().add(null);
        });
    }

    @Disabled
    @Test
    public void parserTest2() throws ParseException, IOException, URISyntaxException {
        List<String> lines = Files.readAllLines(Paths.get(this.getClass().getResource("/ocr2.txt").toURI()), StandardCharsets.UTF_8);
        Parser p = new Parser(lines);
        Forsendelse f = p.parse();
        System.out.println(f);
    }

    @Test
    public void parserTest3() throws ParseException, IOException, URISyntaxException {
        // This test is hand made with only one transaction of type 21
        List<String> lines = Files.readAllLines(Paths.get(this.getClass().getResource("/ocr3.txt").toURI()), StandardCharsets.UTF_8);
        Parser p = new Parser(lines);
        Forsendelse f = p.parse();

        Assertions.assertEquals(7, f.getAntallRecords());
        Assertions.assertEquals(1, f.getAntallTransaksjoner());
        Assertions.assertEquals(44000, f.getSumBelop());
        Assertions.assertEquals(44000, f.getOppdrag().get(0).getTransaksjoner().get(0).getBelop());
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
