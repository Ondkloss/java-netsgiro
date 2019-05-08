package dev.strand.netsgiro.values;

public enum TjenesteKode {
    NULL(0),
    NI(9),
    UGYLDIG(-1);

    private int value;

    TjenesteKode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static TjenesteKode fromInt(int value) {
        for(TjenesteKode tk : TjenesteKode.values()) {
            if(tk.getValue() == value) {
                return tk;
            }
        }

        return UGYLDIG;
    }
}
