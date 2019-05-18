package dev.strand.netsgiro;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import dev.strand.netsgiro.exception.ValidationException;

public class TransaksjonTest {

    @Test
    public void transaksjonTest() throws ValidationException {
        Record post1 = new Record("NY09103000000012403040124112345000000000000044000           33000083672049000000");
        Record post2 = new Record("NY091031000000160004322610945611540000000230304888810111280000000000000000000000");
        Transaksjon t = new Transaksjon(post1, post2);
        System.out.println(t);
        Assertions.assertEquals(Integer.parseInt("10"), t.getTransaksjonsType());
        Assertions.assertEquals(Integer.parseInt("0000001"), t.getTransaksjonsNummer());
        Assertions.assertEquals(LocalDate.of(2004, 03, 24), t.getOppgjorsDato());
        Assertions.assertEquals(Integer.parseInt("01"), t.getSentralId());
        Assertions.assertEquals(Integer.parseInt("24"), t.getDagKode());
        Assertions.assertEquals(Integer.parseInt("1"), t.getDelavregningsNummer());
        Assertions.assertEquals(Integer.parseInt("12345"), t.getLopeNummer());
        Assertions.assertEquals("0", t.getFortegn());
        Assertions.assertEquals(Long.parseLong("00000000000044000"), t.getBelop());
        Assertions.assertEquals("33000083672049", t.getKid());
        Assertions.assertEquals(Integer.parseInt("00"), t.getKortUtsteder());

        Assertions.assertEquals(Long.parseLong("6000432261"), t.getBlankettNummer());
        Assertions.assertEquals(Integer.parseInt("094561154"), t.getArkivReferanse());
        Assertions.assertEquals(LocalDate.of(2004, 03, 23), t.getOppdragsDato());
        Assertions.assertEquals(Long.parseLong("88881011128"), t.getDebetKonto());
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
}