package dev.strand.netsgiro;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import dev.strand.netsgiro.exception.ValidationException;
import dev.strand.netsgiro.values.RecordType;

/**
 * An object representation of a Forsendelse.
 * It is assembled from a start and end {@link Record}, and possibly contents.
 */
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

        dA = data.getInt(9, 16);
        fN = data.getInt(17, 23);
        dM = data.getInt(24, 31);
        sF = data.getInt(32, 80);

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

        aT = data.getInt(9, 16);
        aR = data.getInt(17, 24);
        sB = data.getLong(25, 41);
        oD = data.getLocalDate(42, 47);
        sF = data.getInt(48, 80);

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
                    throw new ValidationException("Invalid oppdrag. Missing start.");
                }

                oppdragContents.add(r);
            }
        }

        if (start != null || oppdragContents != null) {
            throw new ValidationException("Invalid oppdrag. Missing end.");
        }
    }

    protected boolean addOppdrag(Oppdrag o) {
        if (o == null) {
            throw new IllegalArgumentException("Cannot add null elements.");
        }
        return oppdrag.add(o);
    }

    public int getAntallRecords() {
        return antallRecords;
    }

    public int getAntallTransaksjoner() {
        return antallTransaksjoner;
    }

    public int getDataAvsender() {
        return dataAvsender;
    }

    public int getDataMottaker() {
        return dataMottaker;
    }

    public int getForsendelsesNummer() {
        return forsendelsesNummer;
    }

    public List<Oppdrag> getOppdrag() {
        return Collections.unmodifiableList(oppdrag);
    }

    public LocalDate getOppgjorsDato() {
        return oppgjorsDato;
    }

    public long getSumBelop() {
        return sumBelop;
    }
}
