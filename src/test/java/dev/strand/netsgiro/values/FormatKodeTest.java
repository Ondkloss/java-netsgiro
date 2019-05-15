package dev.strand.netsgiro.values;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FormatKodeTest {

    @Test
    public void formatKodeTest() {
        FormatKode fk = FormatKode.fromString("NY");
        Assertions.assertEquals(fk, FormatKode.NY);
    }

    @Test
    public void formatKodeUgyldigTest() {
        FormatKode fk = FormatKode.fromString("RUBBISH");
        Assertions.assertEquals(fk, FormatKode.UGYLDIG);
    }
}