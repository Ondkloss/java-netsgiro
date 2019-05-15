package dev.strand.netsgiro;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import dev.strand.netsgiro.exception.ValidationException;

public class RecordTest {

    @Test
    public void recordTest() throws ValidationException {
        Record r = new Record("NY000010000080800170031000102000000000000000000000000000000000000000000000000000"); // Start forsendelse
    }

    @Test
    public void recordInvalidLengthTest() throws ValidationException {
        ValidationException thrown = Assertions.assertThrows(ValidationException.class,
                () -> new Record("nah"));
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
}