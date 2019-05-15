package dev.strand.netsgiro.values;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RecordTypeTest {

    @Test
    public void recordTypeTest() {
        RecordType rt = RecordType.fromInt(10);
        Assertions.assertEquals(rt, RecordType.START_FORSENDELSE);
    }

    @Test
    public void recordTypeUgyldigTest() {
        RecordType rt = RecordType.fromInt(-1337);
        Assertions.assertEquals(rt, RecordType.UGYLDIG);
    }
}