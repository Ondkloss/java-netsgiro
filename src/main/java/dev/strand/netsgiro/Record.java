package dev.strand.netsgiro;

import dev.strand.netsgiro.exception.ValidationException;
import dev.strand.netsgiro.values.FormatKode;
import dev.strand.netsgiro.values.RecordType;
import dev.strand.netsgiro.values.TjenesteKode;
import dev.strand.netsgiro.values.Type;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Record {

    private final String data;
    private String formatKode;
    private int tjenesteKode;
    private int type;
    private int recordType;

    public Record(String data) throws ValidationException {
        this.data = data;
        parseData();
    }

    private void parseData() throws ValidationException {
        if (data == null) {
            throw new ValidationException("Invalid data. Cannot be null.");
        }
        else if (data.length() != 80) {
            throw new ValidationException("Invalid line length. Must be 80 characters.");
        }

        formatKode = getString(1, 2);
        tjenesteKode = getInt(3, 4);
        type = getInt(5, 6);
        recordType = getInt(7, 8);

        if (!validate()) {
            throw new ValidationException("Invalid record type. Cannot be identified.");
        }
    }

    public FormatKode getFormatKode() {
        return FormatKode.fromString(formatKode);
    }

    public TjenesteKode getTjenesteKode() {
        return TjenesteKode.fromInt(tjenesteKode);
    }

    public Type getType() {
        return Type.fromInt(type);
    }

    public RecordType getRecordType() {
        return RecordType.fromInt(recordType);
    }

    private boolean validate() {
        if (getFormatKode() == FormatKode.NY && getTjenesteKode() == TjenesteKode.NULL && getType() == Type.BLANK
                && getRecordType() == RecordType.START_FORSENDELSE) {
            return true;
        } else if (getFormatKode() == FormatKode.NY && getTjenesteKode() == TjenesteKode.NI && getType() == Type.BLANK
                && getRecordType() == RecordType.START_OPPDRAG) {
            return true;
        } else if (getFormatKode() == FormatKode.NY && getTjenesteKode() == TjenesteKode.NI
                && Util.list(10, 21).contains(type) && getRecordType() == RecordType.BELOPSPOST_1) {
            return true;
        } else if (getFormatKode() == FormatKode.NY && getTjenesteKode() == TjenesteKode.NI
                && Util.list(10, 21).contains(type) && getRecordType() == RecordType.BELOPSPOST_2) {
            return true;
        } else if (getFormatKode() == FormatKode.NY && getTjenesteKode() == TjenesteKode.NI
                && Util.list(20, 21).contains(type) && getRecordType() == RecordType.BELOPSPOST_3) {
            return true;
        } else if (getFormatKode() == FormatKode.NY && getTjenesteKode() == TjenesteKode.NI && getType() == Type.BLANK
                && getRecordType() == RecordType.SLUTT_OPPDRAG) {
            return true;
        } else if (getFormatKode() == FormatKode.NY && getTjenesteKode() == TjenesteKode.NULL && getType() == Type.BLANK
                && getRecordType() == RecordType.SLUTT_FORSENDELSE) {
            return true;
        }

        return false;
    }

    public String getData() {
        return data;
    }

    public long getLong(int from, int to) throws ValidationException {
        try {
            return Long.parseLong(getString(from, to));
        } catch (NumberFormatException ex) {
            throw new ValidationException("Invalid numeric field. Could not be parsed.", ex);
        }
    }

    public int getInt(int from, int to) throws ValidationException {
        try {
            return Integer.parseInt(getString(from, to));
        } catch (NumberFormatException ex) {
            throw new ValidationException("Invalid numeric field. Could not be parsed.", ex);
        }
    }

    public LocalDate getLocalDate(int from, int to) throws ValidationException {
        try {
            return LocalDate.parse(getString(from, to), DateTimeFormatter.ofPattern("ddMMyy"));
        } catch (DateTimeParseException ex) {
            throw new ValidationException("Invalid date. Could not be parsed.", ex);
        }
    }

    public String getString(int from, int to) throws ValidationException {
        try {
        return getData().substring(from - 1, to);
        } catch (IndexOutOfBoundsException ex) {
            throw new ValidationException("Invalid region. Requested segment is out of bounds.", ex);
        }
    }

}
