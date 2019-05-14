package dev.strand.netsgiro;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class UtilTest {

    @Test
    public void listTest() {
        List<Integer> list = Util.list(-2, 0);
        Assertions.assertTrue(-2 == list.get(0));
        Assertions.assertTrue(-1 == list.get(1));
        Assertions.assertTrue(0 == list.get(2));
    }

    @Test
    public void listTest2() {
        List<Integer> list = Util.list(0, 0);
        Assertions.assertTrue(0 == list.get(0));
        Assertions.assertTrue(list.size() == 1);
    }

    @Test
    public void listTestInvalid() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class,
                () -> Util.list(1, 0));
        Assertions.assertTrue(thrown.getMessage().contains("From cannot be larger than to."));
    }

    @Test
    public void getNumberOfBelopspostTest() {
        Assertions.assertEquals(3, Util.getNumberOfBelopspost(20));
        Assertions.assertEquals(3, Util.getNumberOfBelopspost(21));
        Assertions.assertEquals(2, Util.getNumberOfBelopspost(19));
    }

    @Test
    public void getNumberOfBelopspostTestInvalid() {
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class,
                () -> Assertions.assertEquals(3, Util.getNumberOfBelopspost(-3)));
        Assertions.assertTrue(thrown.getMessage().contains("Invalid transaction type."));
    }
}
