package dev.strand.netsgiro;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import dev.strand.netsgiro.exception.ValidationException;
import dev.strand.netsgiro.values.FormatKode;
import dev.strand.netsgiro.values.RecordType;
import dev.strand.netsgiro.values.TjenesteKode;
import dev.strand.netsgiro.values.Type;

public class RecordTest {

    @Test
    public void recordTest() throws ValidationException {
        Record r = new Record("NY000010000080800170031000102000000000000000000000000000000000000000000000000000"); // Start forsendelse
        Assertions.assertEquals("NY000010000080800170031000102000000000000000000000000000000000000000000000000000", r.getData());
        Assertions.assertEquals(FormatKode.NY, r.getFormatKode());
        Assertions.assertEquals(RecordType.START_FORSENDELSE, r.getRecordType());
        Assertions.assertEquals(TjenesteKode.NULL, r.getTjenesteKode());
        Assertions.assertEquals(Type.BLANK, r.getType());
    }

    @Test
    public void recordInvalidLengthTest() throws ValidationException {
        ValidationException thrown = Assertions.assertThrows(ValidationException.class, () -> new Record("nah"));
        Assertions.assertEquals("Invalid line length. Must be 80 characters.", thrown.getMessage());
    }

    @Test
    public void recordInvalidNumericValueTest() throws ValidationException {
        ValidationException thrown = Assertions.assertThrows(ValidationException.class,
                () -> new Record("NYAB0010000080800170031000102000000000000000000000000000000000000000000000000000"));
        Assertions.assertEquals("Invalid numeric field. Could not be parsed.", thrown.getMessage());
    }

    @Test
    public void recordInvalidFormatKodeTest() throws ValidationException {
        ValidationException thrown = Assertions.assertThrows(ValidationException.class,
                () -> new Record("AB000010000080800170031000102000000000000000000000000000000000000000000000000000"));
        Assertions.assertEquals("Invalid record type. Cannot be identified.", thrown.getMessage());
    }

    @Test
    public void recordInvalidRegionTest() throws ValidationException {
        ValidationException thrown = Assertions.assertThrows(ValidationException.class, () -> {
            Record r = new Record("NY000010000080800170031000102000000000000000000000000000000000000000000000000000");
            r.getString(-5, -1);
        });
        Assertions.assertEquals("Invalid region. Requested segment is out of bounds.", thrown.getMessage());
    }

    @Test
    public void recordInvalidNullTest() throws ValidationException {
        ValidationException thrown = Assertions.assertThrows(ValidationException.class, () -> {
            new Record(null);
        });
        Assertions.assertEquals("Invalid data. Cannot be null.", thrown.getMessage());
    }
}