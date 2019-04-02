package dev.strand.netsgiro;

import dev.strand.netsgiro.exception.ParseException;
import dev.strand.netsgiro.exception.ValidationException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Transaksjon {

    private int transaksjonsType;

    // Belop1
    private int transaksjonsNummer;
    private LocalDate oppgjorsDato;
    private int sentralId;
    private int dagKode;
    private int delavregningsNummer;
    private int lopeNummer;
    private String fortegn;
    private long belop;
    private String kid;
    private int kortUtsteder;
    private int belop1Filler;

    // Belop2
    private long blankettNummer;
    private int arkivReferanse;
    private int belop2Filler1;
    private LocalDate oppdragsDato;
    private long debetKonto;
    private int belop2Filler2;

    // Belop3
    private String fritekstMelding;
    private long belop3Filler;

    public void belop1(String data) throws ValidationException {
        int t, tN, sI, dK, dN, lN, kU, b1F;
        long b;
        String f, k;
        LocalDate oD;

        try {
            t = Integer.parseInt(data.substring(4, 6));
            tN = Integer.parseInt(data.substring(8, 15));
            oD = LocalDate.parse(data.substring(15, 21), DateTimeFormatter.ofPattern("ddMMyy"));
            sI = Integer.parseInt(data.substring(21, 23));
            dK = Integer.parseInt(data.substring(23, 25));
            dN = Integer.parseInt(data.substring(25, 26));
            lN = Integer.parseInt(data.substring(26, 31));
            f = data.substring(31, 32);
            b = Long.parseLong(data.substring(32, 49));
            k = data.substring(49, 74);
            kU = Integer.parseInt(data.substring(74, 76));
            b1F = Integer.parseInt(data.substring(76, 80));
        }
        catch (NumberFormatException ex) {
            throw new ValidationException("Invalid numeric field. Could not be parsed.", ex);
        }
        catch (DateTimeParseException ex) {
            throw new ValidationException("Invalid date. Could not be parsed.");
        }

        if(!Util.list(10, 21).contains(t)) {
            throw new ValidationException("Invalid transaction type. Should be between 10 and 21, inclusive.");
        }
        else if (dK < 1 || dK > 31) {
            throw new ValidationException("Invalid day code. Should be between 1 and 31, inclusive.");
        }
        else if (Util.list(18, 21).contains(transaksjonsType) && dN != 0) {
            throw new ValidationException("Invalid part payment number. Should be 0 for transactions of type 18 to 21, inclusive.");
        }
        else if (!f.equals("-") && !f.equals("0")) {
            throw new ValidationException("Invalid prefix. Should be either - or 0.");
        }
        else if (Util.list(20, 21).contains(transaksjonsType) && !k.trim().isEmpty()) {
            throw new ValidationException("Invalid KID. Should be empty for transactions of type 20 and 21.");
        }
        else if (!Util.list(18, 21).contains(transaksjonsType) && kU != 0) {
            throw new ValidationException("Invalid card supplier. Should be empty for all other transactions than type 18 to 21, inclusive.");
        }
        else if (0 != b1F) {
            throw new ValidationException("Invalid filler. Should be all zeros.");
        }

        transaksjonsType = t;
        transaksjonsNummer = tN;
        oppgjorsDato = oD;
        sentralId = sI;
        dagKode = dK;
        delavregningsNummer = dN;
        lopeNummer = lN;
        fortegn = f;
        belop = b;
        kid = k;
        kortUtsteder = kU;
        belop1Filler = b1F;
    }

    public void belop2(String data) throws ValidationException {
        int t, tN, aR, b2F1, b2F2;
        long bN, dK;
        LocalDate oD;

        try {
            t = Integer.parseInt(data.substring(4, 6));
            tN = Integer.parseInt(data.substring(8, 15));
            bN = Long.parseLong(data.substring(15, 25));
            aR = Integer.parseInt(data.substring(25, 34));
            b2F1 = Integer.parseInt(data.substring(34, 41));
            oD = LocalDate.parse(data.substring(41, 47), DateTimeFormatter.ofPattern("ddMMyy"));
            dK = Long.parseLong(data.substring(47, 58));
            b2F2 = Integer.parseInt(data.substring(58, 80));
        }
        catch (NumberFormatException ex) {
            throw new ValidationException("Invalid numeric field. Could not be parsed.", ex);
        }
        catch (DateTimeParseException ex) {
            throw new ValidationException("Invalid date. Could not be parsed.");
        }

        if (t != transaksjonsType) {
            throw new ValidationException("Invalid transaction type. Should match type from previous post.");
        }
        else if (tN != transaksjonsNummer) {
            throw new ValidationException("Invalid transaction number. Should match number from previous post.");
        }
        else if (Util.list(18, 21).contains(transaksjonsType) && dK != 0) {
            throw new ValidationException("Invalid debit account. Should be empty for transactions of type 18 to 21, inclusive.");
        }
        else if (0 != b2F1 || 0 != b2F2) {
            throw new ValidationException("Invalid filler. Should be all zeros.");
        }

        blankettNummer = bN;
        arkivReferanse = aR;
        belop2Filler1 = b2F1;
        oppdragsDato = oD;
        debetKonto = dK;
        belop2Filler2 = b2F2;
    }

    public void belop3(String data) throws ValidationException {
        int t, tN, b3F;
        String fM;

        try {
            t = Integer.parseInt(data.substring(4, 6));
            tN = Integer.parseInt(data.substring(8, 15));
            fM = data.substring(15, 55);
            b3F = Integer.parseInt(data.substring(55, 80));
        }
        catch (NumberFormatException ex) {
            throw new ValidationException("Invalid numeric field. Could not be parsed.", ex);
        }
        catch (DateTimeParseException ex) {
            throw new ValidationException("Invalid date. Could not be parsed.");
        }

        if (t != transaksjonsType) {
            throw new ValidationException("Invalid transaction type. Should match type from previous post.");
        }
        else if(!Util.list(20, 21).contains(transaksjonsType)) {
            throw new ValidationException("Invalid transaction type. Should be of type 20 or 21.");
        }
        else if (tN != transaksjonsNummer) {
            throw new ValidationException("Invalid transaction number. Should match number from previous post.");
        }
        else if (0 != b3F) {
            throw new ValidationException("Invalid filler. Should be all zeros.");
        }

        fritekstMelding = fM;
        belop3Filler = b3F;
    }

    public int getTransaksjonsType() {
        return transaksjonsType;
    }
}
