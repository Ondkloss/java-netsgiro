package dev.strand.netsgiro;

import java.util.ArrayList;
import java.util.List;

public class Util {

    /**
     * Get a list of all integers between two values, inclusive in both ends.
     *
     * @param from
     * @param to
     * @return
     */
    public static List<Integer> list(int from, int to) throws IllegalArgumentException {
        if (from > to) {
            throw new IllegalArgumentException("From cannot be larger than to.");
        }

        List<Integer> result = new ArrayList<>();

        for (int i = from; i <= to; i++) {
            result.add(i);
        }

        return result;
    }

    public static int getNumberOfBelopspost(int transactionType) throws IllegalArgumentException {
        if (!list(10, 21).contains(transactionType)) {
            throw new IllegalArgumentException("Invalid transaction type.");
        }

        if (list(20, 21).contains(transactionType)) {
            return 3;
        }

        return 2;
    }
}
