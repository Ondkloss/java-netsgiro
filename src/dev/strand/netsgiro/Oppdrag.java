package dev.strand.netsgiro;

import dev.strand.netsgiro.exception.ParseException;
import dev.strand.netsgiro.exception.ValidationException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class Oppdrag {

    List<Transaksjon> transaksjoner = new ArrayList<>();

    // Start
    private int avtaleId;
    private int oppdragsNummer;
    private long oppdragsKonto;
    private int startFiller;

    // Slutt
    private int antallTransaksjoner;
    private int antallRecords;
    private long sumBelop;
    private LocalDate oppgjorsDato;
    private LocalDate forsteOppgjorsDato;
    private LocalDate sisteOppgjorsDato;
    private int sluttFiller;

    public void start(String data) throws ValidationException {
        int aI, oN, sF;
        long oK;

        try {
            aI = Integer.parseInt(data.substring(8, 17));
            oN = Integer.parseInt(data.substring(17, 24));
            oK = Long.parseLong(data.substring(24, 35));
            sF = Integer.parseInt(data.substring(35, 80));
        }
        catch (NumberFormatException ex) {
            throw new ValidationException("Invalid numeric field. Could not be parsed.", ex);
        }

        if (0 != sF) {
            throw new ValidationException("Invalid filler. Should be all zeros.");
        }

        avtaleId = aI;
        oppdragsNummer = oN;
        oppdragsKonto = oK;
        startFiller = sF;
    }

    public void slutt(String data) throws ParseException {
        int aT, aR, sF;
        long sB;
        LocalDate oD, fOD, sOD;

        try {
            aT = Integer.parseInt(data.substring(8, 16));
            aR = Integer.parseInt(data.substring(16, 24));
            sB = Integer.parseInt(data.substring(24, 41));
            oD = LocalDate.parse(data.substring(41, 47), DateTimeFormatter.ofPattern("ddMMyy"));
            fOD = LocalDate.parse(data.substring(47, 53), DateTimeFormatter.ofPattern("ddMMyy"));
            sOD = LocalDate.parse(data.substring(53, 59), DateTimeFormatter.ofPattern("ddMMyy"));
            sF = Integer.parseInt(data.substring(59, 80));
        }
        catch (NumberFormatException ex) {
            throw new ParseException("Invalid numeric field. Could not be parsed.", ex);
        }
        catch (DateTimeParseException ex) {
            throw new ParseException("Invalid date. Could not be parsed.");
        }

        if (0 != sF) {
            throw new ParseException("Invalid filler. Should be all zeros.");
        }

        antallTransaksjoner = aT;
        antallRecords = aR;
        sumBelop = sB;
        sluttFiller = sF;
        oppgjorsDato = oD;
        forsteOppgjorsDato = fOD;
        sisteOppgjorsDato = sOD;
    }

    public boolean addTransaksjon(Transaksjon t) {
        if(t == null) {
            throw new IllegalArgumentException("Cannot add null elements.");
        }
        return transaksjoner.add(t);
    }

}
