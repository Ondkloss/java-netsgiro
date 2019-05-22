package dev.strand.netsgiro.values;

import dev.strand.netsgiro.Record;

/**
 * Represents the "Record type" portion of a {@link Record}. It is typically
 * found in positions 7-8 of a line from the OCR file.
 */
public enum RecordType {
    START_FORSENDELSE(10), START_OPPDRAG(20), BELOPSPOST_1(30), BELOPSPOST_2(31), BELOPSPOST_3(32), SLUTT_OPPDRAG(88),
    SLUTT_FORSENDELSE(89), UGYLDIG(-1);

    private int value;

    RecordType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static RecordType fromInt(int value) {
        for (RecordType rt : RecordType.values()) {
            if (rt.getValue() == value) {
                return rt;
            }
        }

        return UGYLDIG;
    }
}
