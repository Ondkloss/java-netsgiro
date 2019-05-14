package dev.strand.netsgiro;

import dev.strand.netsgiro.exception.ValidationException;
import dev.strand.netsgiro.values.RecordType;
import dev.strand.netsgiro.values.Type;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class Oppdrag {

    List<Transaksjon> transaksjoner = new ArrayList<>();

    // Start
    private int avtaleId;
    private int oppdragsNummer;
    private long oppdragsKonto;

    // Slutt
    private int antallTransaksjoner;
    private int antallRecords;
    private long sumBelop;
    private LocalDate oppgjorsDato;
    private LocalDate forsteOppgjorsDato;
    private LocalDate sisteOppgjorsDato;

    public Oppdrag(Record start, Record slutt, List<Record> contents) throws ValidationException {
        start(start);
        slutt(slutt);
        parseContents(contents);
    }

    private void start(Record data) throws ValidationException {
        int aI, oN, sF;
        long oK;

        try {
            aI = data.getInt(9, 17);
            oN = data.getInt(18, 24);
            oK = data.getLong(25, 35);
            sF = data.getInt(36, 80);
        } catch (NumberFormatException ex) {
            throw new ValidationException("Invalid numeric field. Could not be parsed.", ex);
        }

        if (0 != sF) {
            throw new ValidationException("Invalid filler. Should be all zeros.");
        }

        avtaleId = aI;
        oppdragsNummer = oN;
        oppdragsKonto = oK;
    }

    private void slutt(Record data) throws ValidationException {
        int aT, aR, sF;
        long sB;
        LocalDate oD, fOD, sOD;

        try {
            aT = data.getInt(9, 16);
            aR = data.getInt(17, 24);
            sB = data.getInt(25, 41);
            oD = data.getLocalDate(42, 47);
            fOD = data.getLocalDate(48, 53);
            sOD = data.getLocalDate(54, 59);
            sF = data.getInt(60, 80);
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
        forsteOppgjorsDato = fOD;
        sisteOppgjorsDato = sOD;
    }

    private void parseContents(List<Record> contents) throws ValidationException {
        for (int i = 0; i < contents.size(); i++) {
            Record r = contents.get(i);
            if (r.getRecordType() == RecordType.BELOPSPOST_1) {
                if (r.getType() == Type.REVERSERING_MED_FRITEKST || r.getType() == Type.KJOP_MED_FRITEKST) {
                    addTransaksjon(new Transaksjon(contents.get(i), contents.get(i + 1), contents.get(i + 2)));
                    i += 2;
                } else {
                    addTransaksjon(new Transaksjon(contents.get(i), contents.get(i + 1)));
                    i += 1;
                }
            } else {
                throw new ValidationException("Invalid transaksjon. Missing start.");
            }
        }
    }

    public boolean addTransaksjon(Transaksjon t) {
        if (t == null) {
            throw new IllegalArgumentException("Cannot add null elements.");
        }
        return transaksjoner.add(t);
    }

}
