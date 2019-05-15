package dev.strand.netsgiro.values;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TjenesteKodeTest {

    @Test
    public void tjenesteKodeTest() {
        TjenesteKode tk = TjenesteKode.fromInt(9);
        Assertions.assertEquals(tk, TjenesteKode.NI);
    }

    @Test
    public void tjenesteKodeUgyldigTest() {
        TjenesteKode tk = TjenesteKode.fromInt(-1337);
        Assertions.assertEquals(tk, TjenesteKode.UGYLDIG);
    }
}