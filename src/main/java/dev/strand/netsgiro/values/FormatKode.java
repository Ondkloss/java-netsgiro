package dev.strand.netsgiro.values;

public enum FormatKode {
    NY("NY"),
    UGYLDIG("");

    private String value;

    FormatKode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static FormatKode fromString(String value) {
        for (FormatKode fk : FormatKode.values()) {
            if (fk.getValue().equals(value)) {
                return fk;
            }
        }

        return UGYLDIG;
    }
}
