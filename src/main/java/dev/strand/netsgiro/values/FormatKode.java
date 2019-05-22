package dev.strand.netsgiro.values;

import dev.strand.netsgiro.Record;

/**
 * Represents the "Format kode" portion of a {@link Record}.
 * It is typically found in positions 1-2 of a line from the OCR file.
 */
public enum FormatKode {
    NY("NY"), UGYLDIG("");

    private String value;

    FormatKode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static FormatKode fromString(String value) {
        for (FormatKode fk : FormatKode.values()) {
            if (fk.getValue().equals(value)) {
                return fk;
            }
        }

        return UGYLDIG;
    }
}
