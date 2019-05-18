package dev.strand.netsgiro;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import dev.strand.netsgiro.exception.ValidationException;

public class OppdragTest {

    @Test
    public void oppdragTest() throws ValidationException {
        Record start = new Record("NY090020001767676000000199991111111000000000000000000000000000000000000000000000");
        Record slutt = new Record("NY090088000000230000004800000000001563000240304230304250304000000000000000000000");
        Oppdrag o = new Oppdrag(start, slutt, new ArrayList<Record>());
        System.out.println(o);
        Assertions.assertEquals(Integer.parseInt("001767676"), o.getAvtaleId());
        Assertions.assertEquals(Integer.parseInt("0000001"), o.getOppdragsNummer());
        Assertions.assertEquals(Long.parseLong("99991111111"), o.getOppdragsKonto());

        Assertions.assertEquals(Integer.parseInt("00000023"), o.getAntallTransaksjoner());
        Assertions.assertEquals(Integer.parseInt("00000048"), o.getAntallRecords());
        Assertions.assertEquals(Long.parseLong("00000000001563000"), o.getSumBelop());
        Assertions.assertEquals(LocalDate.of(2004, 03, 24), o.getOppgjorsDato());
        Assertions.assertEquals(LocalDate.of(2004, 03, 23), o.getForsteOppgjorsDato());
        Assertions.assertEquals(LocalDate.of(2004, 03, 25), o.getSisteOppgjorsDato());
    }

    @Test
    public void oppdragAddNullTest() throws ValidationException {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Record start = new Record(
                    "NY090020001767676000000199991111111000000000000000000000000000000000000000000000");
            Record slutt = new Record(
                    "NY090088000000230000004800000000001563000240304230304250304000000000000000000000");
            Oppdrag o = new Oppdrag(start, slutt, new ArrayList<Record>());
            o.addTransaksjon(null);
        });
        Assertions.assertEquals("Cannot add null elements.", thrown.getMessage());
    }

    @Test
    public void oppdragAddInvalidStartTest() throws ValidationException {
        ValidationException thrown = Assertions.assertThrows(ValidationException.class, () -> {
            Record start = new Record(
                    "NY090020001767676000000199991111111000000000000000000000000000000000000000000000");
            Record slutt = new Record(
                    "NY090088000000230000004800000000001563000240304230304250304000000000000000000000");
            ArrayList<Record> list = new ArrayList<>();
            list.add(new Record("NY091031000000160004322610945611540000000230304888810111280000000000000000000000"));
            new Oppdrag(start, slutt, list);
        });
        Assertions.assertEquals("Invalid transaksjon. Missing start.", thrown.getMessage());
    }

    @Test
    public void recordInvalidNumericValueStartTest() throws ValidationException {
        ValidationException thrown = Assertions.assertThrows(ValidationException.class, () -> {
            Record start = new Record(
                    "NY0900200017676760000001999911111AB000000000000000000000000000000000000000000000");
            Record slutt = new Record(
                    "NY090088000000230000004800000000001563000240304230304250304000000000000000000000");
            new Oppdrag(start, slutt, new ArrayList<Record>());
        });
        Assertions.assertEquals("Invalid numeric field. Could not be parsed.", thrown.getMessage());
    }

    @Test
    public void recordInvalidFillerStartTest() throws ValidationException {
        ValidationException thrown = Assertions.assertThrows(ValidationException.class, () -> {
            Record start = new Record(
                    "NY090020001767676000000199991111111000000000000000000000000000000000000000000001");
            Record slutt = new Record(
                    "NY090088000000230000004800000000001563000240304230304250304000000000000000000000");
            new Oppdrag(start, slutt, new ArrayList<Record>());
        });
        Assertions.assertEquals("Invalid filler. Should be all zeros.", thrown.getMessage());
    }

    @Test
    public void recordInvalidNumericValueEndTest() throws ValidationException {
        ValidationException thrown = Assertions.assertThrows(ValidationException.class, () -> {
            Record start = new Record(
                    "NY090020001767676000000199991111111000000000000000000000000000000000000000000000");
            Record slutt = new Record(
                    "NY0900880000002300000048000000000015AB000240304230304250304000000000000000000000");
            new Oppdrag(start, slutt, new ArrayList<Record>());
        });
        Assertions.assertEquals("Invalid numeric field. Could not be parsed.", thrown.getMessage());
    }

    @Test
    public void recordInvalidDateEndTest() throws ValidationException {
        ValidationException thrown = Assertions.assertThrows(ValidationException.class, () -> {
            Record start = new Record(
                    "NY090020001767676000000199991111111000000000000000000000000000000000000000000000");
            Record slutt = new Record(
                    "NY0900880000002300000048000000000015630002403AB230304250304000000000000000000000");
            new Oppdrag(start, slutt, new ArrayList<Record>());
        });
        Assertions.assertEquals("Invalid date. Could not be parsed.", thrown.getMessage());
    }

    @Test
    public void recordInvalidFillerEndTest() throws ValidationException {
        ValidationException thrown = Assertions.assertThrows(ValidationException.class, () -> {
            Record start = new Record(
                    "NY090020001767676000000199991111111000000000000000000000000000000000000000000000");
            Record slutt = new Record(
                    "NY090088000000230000004800000000001563000240304230304250304000000000000000000001");
            new Oppdrag(start, slutt, new ArrayList<Record>());
        });
        Assertions.assertEquals("Invalid filler. Should be all zeros.", thrown.getMessage());
    }
}