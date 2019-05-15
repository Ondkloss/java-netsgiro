package dev.strand.netsgiro.values;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TypeTest {

    @Test
    public void typeTest() {
        Type t = Type.fromInt(10);
        Assertions.assertEquals(t, Type.GIRO_BELASTET_KONTO);
    }

    @Test
    public void typeUgyldigTest() {
        Type t = Type.fromInt(-1337);
        Assertions.assertEquals(t, Type.UGYLDIG);
    }
}