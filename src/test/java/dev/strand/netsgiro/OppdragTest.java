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
}