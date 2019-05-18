package dev.strand.netsgiro;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import dev.strand.netsgiro.exception.ValidationException;

public class ForsendelseTest {

    @Test
    public void forsendelseTest() throws ValidationException {
        Record start = new Record("NY000010000080800170031000102000000000000000000000000000000000000000000000000000");
        Record slutt = new Record("NY000089000000230000005000000000001563000240304000000000000000000000000000000000");
        Forsendelse f = new Forsendelse(start, slutt, new ArrayList<Record>());
        System.out.println(f);
        Assertions.assertEquals(Integer.parseInt("00008080"), f.getDataAvsender());
        Assertions.assertEquals(Integer.parseInt("0170031"), f.getForsendelsesNummer());
        Assertions.assertEquals(Integer.parseInt("00010200"), f.getDataMottaker());

        Assertions.assertEquals(Integer.parseInt("00000023"), f.getAntallTransaksjoner());
        Assertions.assertEquals(Integer.parseInt("00000050"), f.getAntallRecords());
        Assertions.assertEquals(Long.parseLong("00000000001563000"), f.getSumBelop());
        Assertions.assertEquals(LocalDate.of(2004, 03, 24), f.getOppgjorsDato());
    }

    @Test
    public void forsendelseInvalidNumericStartTest() throws ValidationException {
        ValidationException thrown = Assertions.assertThrows(ValidationException.class, () -> {
            Record start = new Record(
                    "NY0000100000ABCD0170031000102000000000000000000000000000000000000000000000000000");
            Record slutt = new Record(
                    "NY000089000000230000005000000000001563000240304000000000000000000000000000000000");
            new Forsendelse(start, slutt, new ArrayList<Record>());
        });
        Assertions.assertEquals("Invalid numeric field. Could not be parsed.", thrown.getMessage());
    }

    @Test
    public void forsendelseInvalidFillerStartTest() throws ValidationException {
        ValidationException thrown = Assertions.assertThrows(ValidationException.class, () -> {
            Record start = new Record(
                    "NY000010000080800170031000102000000000000000000000000000000000000000000000000001");
            Record slutt = new Record(
                    "NY000089000000230000005000000000001563000240304000000000000000000000000000000000");
            new Forsendelse(start, slutt, new ArrayList<Record>());
        });
        Assertions.assertEquals("Invalid filler. Should be all zeros.", thrown.getMessage());
    }

    @Test
    public void forsendelseInvalidNumericEndTest() throws ValidationException {
        ValidationException thrown = Assertions.assertThrows(ValidationException.class, () -> {
            Record start = new Record(
                    "NY000010000080800170031000102000000000000000000000000000000000000000000000000000");
            Record slutt = new Record(
                    "NY000089000000AB0000005000000000001563000240304000000000000000000000000000000000");
            new Forsendelse(start, slutt, new ArrayList<Record>());
        });
        Assertions.assertEquals("Invalid numeric field. Could not be parsed.", thrown.getMessage());
    }

    @Test
    public void forsendelseInvalidFillerEndTest() throws ValidationException {
        ValidationException thrown = Assertions.assertThrows(ValidationException.class, () -> {
            Record start = new Record(
                    "NY000010000080800170031000102000000000000000000000000000000000000000000000000000");
            Record slutt = new Record(
                    "NY000089000000230000005000000000001563000240304000000000000000000000000000000001");
            new Forsendelse(start, slutt, new ArrayList<Record>());
        });
        Assertions.assertEquals("Invalid filler. Should be all zeros.", thrown.getMessage());
    }
}