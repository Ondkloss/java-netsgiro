package dev.strand.netsgiro;

import dev.strand.netsgiro.exception.ParseException;
import dev.strand.netsgiro.exception.ValidationException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class Forsendelse {

    List<Oppdrag> oppdrag = new ArrayList<>();

    // Start
    private int dataAvsender;
    private int forsendelsesNummer;
    private int dataMottaker;
    private int startFiller;

    // Slutt
    private int antallTransaksjoner;
    private int antallRecords;
    private long sumBelop;
    private LocalDate oppgjorsDato;
    private int sluttFiller;

    public void start(String data) throws ValidationException {
        int dA, fN, dM, sF;

        try {
            dA = Integer.parseInt(data.substring(8, 16));
            fN = Integer.parseInt(data.substring(16, 23));
            dM = Integer.parseInt(data.substring(23, 31));
            sF = Integer.parseInt(data.substring(31, 80));
        }
        catch (NumberFormatException ex) {
            throw new ValidationException("Invalid numeric field. Could not be parsed.", ex);
        }

        if (8080 != dA) {
            throw new ValidationException("Invalid data sender. Should be 00008080 (NETS).");
        }
        else if (0 != sF) {
            throw new ValidationException("Invalid filler. Should be all zeros.");
        }

        dataAvsender = dA;
        forsendelsesNummer = fN;
        dataMottaker = dM;
        startFiller = sF;
    }

    public void slutt(String data) throws ValidationException {
        int aT, aR, sF;
        long sB;
        LocalDate oD;

        try {
            aT = Integer.parseInt(data.substring(8, 16));
            aR = Integer.parseInt(data.substring(16, 24));
            sB = Long.parseLong(data.substring(24, 41));
            oD = LocalDate.parse(data.substring(41, 47), DateTimeFormatter.ofPattern("ddMMyy"));
            sF = Integer.parseInt(data.substring(47, 80));
        }
        catch (NumberFormatException ex) {
            throw new ValidationException("Invalid numeric field. Could not be parsed.", ex);
        }
        catch (DateTimeParseException ex) {
            throw new ValidationException("Invalid date. Could not be parsed.");
        }

        if (0 != sF) {
            throw new ValidationException("Invalid filler. Should be all zeros.");
        }

        antallTransaksjoner = aT;
        antallRecords = aR;
        sumBelop = sB;
        sluttFiller = sF;
        oppgjorsDato = oD;
    }

    public boolean addOppdrag(Oppdrag o) {
        if(o == null) {
            throw new IllegalArgumentException("Cannot add null elements.");
        }
        return oppdrag.add(o);
    }

}
