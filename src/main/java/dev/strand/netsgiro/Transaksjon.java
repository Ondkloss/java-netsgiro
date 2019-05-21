package dev.strand.netsgiro;

import java.time.LocalDate;

import dev.strand.netsgiro.exception.ValidationException;

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

    // Belop2
    private long blankettNummer;
    private int arkivReferanse;
    private LocalDate oppdragsDato;
    private long debetKonto;

    // Belop3
    private String fritekstMelding;

    public Transaksjon(Record belop1, Record belop2, Record belop3) throws ValidationException {
        belop1(belop1);
        belop2(belop2);
        belop3(belop3);
    }

    public Transaksjon(Record belop1, Record belop2) throws ValidationException {
        belop1(belop1);
        belop2(belop2);
    }

    private void belop1(Record data) throws ValidationException {
        int t, tN, sI, dK, dN, lN, kU, b1F;
        long b;
        String f, k;
        LocalDate oD;

        t = data.getInt(5, 6);
        tN = data.getInt(9, 15);
        oD = data.getLocalDate(16, 21);
        sI = data.getInt(22, 23);
        dK = data.getInt(24, 25);
        dN = data.getInt(26, 26);
        lN = data.getInt(27, 31);
        f = data.getString(32, 32);
        b = data.getLong(33, 49);
        k = data.getString(50, 74).trim();
        kU = data.getInt(75, 76);
        b1F = data.getInt(77, 80);

        if (!Util.list(10, 21).contains(t)) {
            throw new ValidationException("Invalid transaction type. Should be between 10 and 21, inclusive.");
        } else if (dK < 1 || dK > 31) {
            throw new ValidationException("Invalid day code. Should be between 1 and 31, inclusive.");
        } else if (Util.list(18, 21).contains(transaksjonsType) && dN != 0) {
            throw new ValidationException("Invalid part payment number. Should be 0 for transactions of type 18 to 21, inclusive.");
        } else if (!f.equals("-") && !f.equals("0")) {
            throw new ValidationException("Invalid prefix. Should be either - or 0.");
        } else if (Util.list(20, 21).contains(transaksjonsType) && !k.isEmpty()) {
            throw new ValidationException("Invalid KID. Should be empty for transactions of type 20 and 21.");
        } else if (!Util.list(18, 21).contains(transaksjonsType) && kU != 0) {
            throw new ValidationException(
                    "Invalid card supplier. Should be empty for all other transactions than type 18 to 21, inclusive.");
        } else if (0 != b1F) {
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
    }

    private void belop2(Record data) throws ValidationException {
        int t, tN, aR, b2F1, b2F2;
        long bN, dK;
        LocalDate oD;

        t = data.getInt(5, 6);
        tN = data.getInt(9, 15);
        bN = data.getLong(16, 25);
        aR = data.getInt(26, 34);
        b2F1 = data.getInt(35, 41);
        oD = data.getLocalDate(42, 47);
        dK = data.getLong(48, 58);
        b2F2 = data.getInt(59, 80);

        if (t != transaksjonsType) {
            throw new ValidationException("Invalid transaction type. Should match type from previous post.");
        } else if (tN != transaksjonsNummer) {
            throw new ValidationException("Invalid transaction number. Should match number from previous post.");
        } else if (Util.list(18, 21).contains(transaksjonsType) && dK != 0) {
            throw new ValidationException("Invalid debit account. Should be empty for transactions of type 18 to 21, inclusive.");
        } else if (0 != b2F1 || 0 != b2F2) {
            throw new ValidationException("Invalid filler. Should be all zeros.");
        }

        blankettNummer = bN;
        arkivReferanse = aR;
        oppdragsDato = oD;
        debetKonto = dK;
    }

    private void belop3(Record data) throws ValidationException {
        int t, tN, b3F;
        String fM;

        t = data.getInt(5, 6);
        tN = data.getInt(9, 15);
        fM = data.getString(16, 55).trim();
        b3F = data.getInt(56, 80);

        if (t != transaksjonsType) {
            throw new ValidationException("Invalid transaction type. Should match type from previous post.");
        } else if (!Util.list(20, 21).contains(transaksjonsType)) {
            throw new ValidationException("Invalid transaction type. Should be of type 20 or 21.");
        } else if (tN != transaksjonsNummer) {
            throw new ValidationException("Invalid transaction number. Should match number from previous post.");
        } else if (0 != b3F) {
            throw new ValidationException("Invalid filler. Should be all zeros.");
        }

        fritekstMelding = fM;
    }

    public int getTransaksjonsType() {
        return transaksjonsType;
    }

    public int getArkivReferanse() {
        return arkivReferanse;
    }

    public long getBelop() {
        return belop;
    }

    public long getBlankettNummer() {
        return blankettNummer;
    }

    public int getDagKode() {
        return dagKode;
    }

    public long getDebetKonto() {
        return debetKonto;
    }

    public int getDelavregningsNummer() {
        return delavregningsNummer;
    }

    public String getFortegn() {
        return fortegn;
    }

    public String getFritekstMelding() {
        return fritekstMelding;
    }

    public String getKid() {
        return kid;
    }

    public int getKortUtsteder() {
        return kortUtsteder;
    }

    public int getLopeNummer() {
        return lopeNummer;
    }

    public LocalDate getOppdragsDato() {
        return oppdragsDato;
    }

    public LocalDate getOppgjorsDato() {
        return oppgjorsDato;
    }

    public int getSentralId() {
        return sentralId;
    }

    public int getTransaksjonsNummer() {
        return transaksjonsNummer;
    }
}
