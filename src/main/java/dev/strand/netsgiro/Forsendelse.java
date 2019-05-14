package dev.strand.netsgiro;

import dev.strand.netsgiro.exception.ValidationException;
import dev.strand.netsgiro.values.RecordType;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class Forsendelse {

    List<Oppdrag> oppdrag = new ArrayList<>();

    // Start
    private int dataAvsender;
    private int forsendelsesNummer;
    private int dataMottaker;

    // Slutt
    private int antallTransaksjoner;
    private int antallRecords;
    private long sumBelop;
    private LocalDate oppgjorsDato;

    public Forsendelse(Record start, Record slutt, List<Record> contents) throws ValidationException {
        start(start);
        slutt(slutt);
        parseContents(contents);
    }

    private void start(Record data) throws ValidationException {
        int dA, fN, dM, sF;

        try {
            dA = data.getInt(9, 16);
            fN = data.getInt(17, 23);
            dM = data.getInt(24, 31);
            sF = data.getInt(32, 80);
        } catch (NumberFormatException ex) {
            throw new ValidationException("Invalid numeric field. Could not be parsed.", ex);
        }

        if (8080 != dA) {
            throw new ValidationException("Invalid data sender. Should be 00008080 (NETS).");
        } else if (0 != sF) {
            throw new ValidationException("Invalid filler. Should be all zeros.");
        }

        dataAvsender = dA;
        forsendelsesNummer = fN;
        dataMottaker = dM;
    }

    private void slutt(Record data) throws ValidationException {
        int aT, aR, sF;
        long sB;
        LocalDate oD;

        try {
            aT = data.getInt(9, 16);
            aR = data.getInt(17, 24);
            sB = data.getLong(25, 41);
            oD = data.getLocalDate(42, 47);
            sF = data.getInt(48, 80);
        } catch (NumberFormatException ex) {
            throw new ValidationException("Invalid numeric field. Could not be parsed.", ex);
        } catch (DateTimeParseException ex) {
            throw new ValidationException("Invalid date. Could not be parsed.");
        }

        if (0 != sF) {
            throw new ValidationException("Invalid filler. Should be all zeros.");
        }

        antallTransaksjoner = aT;
        antallRecords = aR;
        sumBelop = sB;
        oppgjorsDato = oD;
    }

    private void parseContents(List<Record> contents) throws ValidationException {
        Record start = null;
        List<Record> oppdragContents = null;

        for (Record r : contents) {
            if (r.getRecordType() == RecordType.START_OPPDRAG) {
                start = r;
                oppdragContents = new ArrayList<>();
            } else if (r.getRecordType() == RecordType.SLUTT_OPPDRAG) {
                if (start == null || oppdragContents == null || oppdragContents.isEmpty()) {
                    throw new ValidationException("Invalid oppdrag. Missing start or contents.");
                }

                addOppdrag(new Oppdrag(start, r, oppdragContents));
                start = null;
                oppdragContents = null;
            } else {
                if (oppdragContents == null) {
                    throw new ValidationException(("Invalid oppdrag. Missing start."));
                }

                oppdragContents.add(r);
            }
        }

        if (start != null || oppdragContents != null) {
            throw new ValidationException(("Invalid oppdrag. Missing end."));
        }
    }

    public boolean addOppdrag(Oppdrag o) {
        if (o == null) {
            throw new IllegalArgumentException("Cannot add null elements.");
        }
        return oppdrag.add(o);
    }

}
