package dev.strand.netsgiro.values;

public enum Type {
    BLANK(0), GIRO_BELASTET_KONTO(10), FASTE_OPPDRAG(11), DIREKTE_REMITTERING(12), BEDRIFTS_TERMINAL_GIRO(13), SKRANKE_GIRO(14),
    AVTALE_GIRO(15), TELE_GIRO(16), GIRO_BETALT_KONTANT(17), REVERSERING_MED_KID(18), KJOP_MED_KID(19), REVERSERING_MED_FRITEKST(20),
    KJOP_MED_FRITEKST(21), UGYLDIG(-1);

    private int value;

    Type(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Type fromInt(int value) {
        for (Type t : Type.values()) {
            if (t.getValue() == value) {
                return t;
            }
        }

        return UGYLDIG;
    }
}
