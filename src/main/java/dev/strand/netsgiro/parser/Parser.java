package dev.strand.netsgiro.parser;

import dev.strand.netsgiro.Forsendelse;
import dev.strand.netsgiro.Record;
import dev.strand.netsgiro.exception.ParseException;
import dev.strand.netsgiro.exception.ValidationException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Specification:
 * https://www.nets.eu/no-nb/PublishingImages/Lists/Accordion%20%20OCR%20giro/AllItems/OCR%20giro%20Systemspesifikasjon.pdf
 */
public class Parser {

    private final List<String> data;

    public Parser(List<String> data) {
        this.data = data;
    }

    public Parser(String[] data) {
        this(Arrays.asList(data));
    }

    public Forsendelse parse() throws ParseException {
        try {
            List<Record> records = new ArrayList<>();

            for (String d : data) {
                records.add(new Record(d));
            }

            if (records.size() < 2) {
                throw new ParseException("Valid data cannot be less than two records.");
            }

            Record start = records.remove(0);
            Record slutt = records.remove(records.size() - 1);

            Forsendelse forsendelse = new Forsendelse(start, slutt, records);
            return forsendelse;
        } catch (ValidationException ex) {
            throw new ParseException("Error during validation.", ex);
        }
    }
}
