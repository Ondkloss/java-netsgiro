package dev.strand.netsgiro;

import java.util.ArrayList;
import java.util.List;

/**
 * A collection of static utility functions.
 */
public class Util {

    private Util() {
    }

    /**
     * Get a list of all integers between two values, inclusive in both ends.
     *
     * @param from minimum value (inclusive)
     * @param to maximum value (inclusive)
     * @return The requested list
     */
    public static List<Integer> list(int from, int to) {
        if (from > to) {
            throw new IllegalArgumentException("From cannot be larger than to.");
        }

        List<Integer> result = new ArrayList<>();

        for (int i = from; i <= to; i++) {
            result.add(i);
        }

        return result;
    }

    /**
     * Get the number of Beløpsposts associated with a given transaction type.
     *
     * @param transactionType The transaction type
     * @return The number of Beløpsposts
     */
    public static int getNumberOfBelopspost(int transactionType) {
        if (!list(10, 21).contains(transactionType)) {
            throw new IllegalArgumentException("Invalid transaction type.");
        }

        if (list(20, 21).contains(transactionType)) {
            return 3;
        }

        return 2;
    }
}
