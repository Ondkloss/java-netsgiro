package dev.strand.netsgiro.parser;

import dev.strand.netsgiro.exception.ParseException;
import java.util.ArrayList;
import java.util.List;

import dev.strand.netsgiro.*;
import dev.strand.netsgiro.exception.ValidationException;
import dev.strand.netsgiro.values.RecordType;

/**
 * Specification:
 * https://www.nets.eu/no-nb/PublishingImages/Lists/Accordion%20%20OCR%20giro/AllItems/OCR%20giro%20Systemspesifikasjon.pdf
 */
public class Parser {

    private final String[] data;

    public Parser(String[] data) {
        this.data = data;
    }

    public Forsendelse parse() throws ParseException {
        try {
            List<Record> records = new ArrayList<>();

            for (String d : data) {
                records.add(new Record(d));
            }

            Forsendelse forsendelse = null;
            Oppdrag oppdrag = null;
            Transaksjon transaksjon = null;

            for (int i = 0; i < records.size(); i++) {
                Record r = records.get(i);

                if (r.getRecordType() == RecordType.START_FORSENDELSE) {
                    forsendelse = new Forsendelse();
                    forsendelse.start(r.getData());
                } else if (r.getRecordType() == RecordType.START_OPPDRAG) {
                    oppdrag = new Oppdrag();
                    oppdrag.start(r.getData());
                } else if (r.getRecordType() == RecordType.BELOPSPOST_1) {
                    transaksjon = new Transaksjon();
                    transaksjon.belop1(r.getData());
                    int posts = Util.getNumberOfBelopspost(transaksjon.getTransaksjonsType());

                    if (posts >= 2) {
                        transaksjon.belop2(records.get(i + 1).getData());
                        i++;
                    }
                    if (posts >= 3) {
                        transaksjon.belop2(records.get(i + 2).getData());
                        i++;
                    }
                    oppdrag.addTransaksjon(transaksjon);
                    transaksjon = null;
                } else if (r.getRecordType() == RecordType.SLUTT_OPPDRAG) {
                    oppdrag.slutt(r.getData());
                    forsendelse.addOppdrag(oppdrag);
                    oppdrag = null;
                } else if (r.getRecordType() == RecordType.SLUTT_FORSENDELSE) {
                    forsendelse.slutt(r.getData());
                }
            }

            return forsendelse;
        }
        catch(ValidationException ex) {
            throw new ParseException("Error during validation.", ex);
        }
    }
}
