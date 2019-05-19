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
    public void forsendelseInvalidDateStartTest() throws ValidationException {
        ValidationException thrown = Assertions.assertThrows(ValidationException.class, () -> {
            Record start = new Record(
                    "NY000010000080800170031000102000000000000000000000000000000000000000000000000000");
            Record slutt = new Record(
                    "NY0000890000002300000050000000000015630002403AB000000000000000000000000000000000");
            new Forsendelse(start, slutt, new ArrayList<Record>());
        });
        Assertions.assertEquals("Invalid date. Could not be parsed.", thrown.getMessage());
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

    @Test
    public void forsendelseAddNullTest() throws ValidationException {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Record start = new Record(
                    "NY000010000080800170031000102000000000000000000000000000000000000000000000000000");
            Record slutt = new Record(
                    "NY000089000000230000005000000000001563000240304000000000000000000000000000000000");
            Forsendelse f = new Forsendelse(start, slutt, new ArrayList<Record>());
            f.addOppdrag(null);
        });
        Assertions.assertEquals("Cannot add null elements.", thrown.getMessage());
    }

    @Test
    public void forsendelseInvalidContentsTest() throws ValidationException {
        ValidationException thrown = Assertions.assertThrows(ValidationException.class, () -> {
            Record start = new Record(
                    "NY000010000080800170031000102000000000000000000000000000000000000000000000000000");
            Record slutt = new Record(
                    "NY000089000000230000005000000000001563000240304000000000000000000000000000000000");
            Record startOppdrag = new Record(
                    "NY090020001767676000000199991111111000000000000000000000000000000000000000000000");
            ArrayList<Record> list = new ArrayList<>();
            list.add(startOppdrag);
            new Forsendelse(start, slutt, list);
        });
        Assertions.assertEquals("Invalid oppdrag. Missing end.", thrown.getMessage());
    }

    @Test
    public void forsendelseInvalidContentTest() throws ValidationException {
        ValidationException thrown = Assertions.assertThrows(ValidationException.class, () -> {
            Record start = new Record(
                    "NY000010000080800170031000102000000000000000000000000000000000000000000000000000");
            Record slutt = new Record(
                    "NY000089000000230000005000000000001563000240304000000000000000000000000000000000");
            ArrayList<Record> list = new ArrayList<>();
            list.add(new Record("NY090088000000230000004800000000001563000240304240304240304000000000000000000000"));
            new Forsendelse(start, slutt, list);
        });
        Assertions.assertEquals("Invalid oppdrag. Missing start or contents.", thrown.getMessage());
    }

    @Test
    public void forsendelseInvalidContentTest2() throws ValidationException {
        ValidationException thrown = Assertions.assertThrows(ValidationException.class, () -> {
            Record start = new Record(
                    "NY000010000080800170031000102000000000000000000000000000000000000000000000000000");
            Record slutt = new Record(
                    "NY000089000000230000005000000000001563000240304000000000000000000000000000000000");
            ArrayList<Record> list = new ArrayList<>();
            list.add(new Record("NY09103000000012403040124112345000000000000044000           33000083672049000000"));
            new Forsendelse(start, slutt, list);
        });
        Assertions.assertEquals("Invalid oppdrag. Missing start.", thrown.getMessage());
    }
}