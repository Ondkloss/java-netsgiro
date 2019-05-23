package dev.strand.netsgiro;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import dev.strand.netsgiro.exception.ValidationException;

public class TransaksjonTest {

        @Test
        public void transaksjonTest() throws ValidationException {
                Record post1 = new Record("NY09103000000012403040124112345000000000000044000           33000083672049000000");
                Record post2 = new Record("NY091031000000160004322610945611540000000230304888810111280000000000000000000000");
                Transaksjon t = new Transaksjon(post1, post2);
                System.out.println(t);
                Assertions.assertEquals(Integer.parseInt("10"), t.getTransaksjonsType());
                Assertions.assertEquals(Integer.parseInt("0000001"), t.getTransaksjonsNummer());
                Assertions.assertEquals(LocalDate.of(2004, 03, 24), t.getOppgjorsDato());
                Assertions.assertEquals(Integer.parseInt("01"), t.getSentralId());
                Assertions.assertEquals(Integer.parseInt("24"), t.getDagKode());
                Assertions.assertEquals(Integer.parseInt("1"), t.getDelavregningsNummer());
                Assertions.assertEquals(Integer.parseInt("12345"), t.getLopeNummer());
                Assertions.assertEquals("0", t.getFortegn());
                Assertions.assertEquals(Long.parseLong("00000000000044000"), t.getBelop());
                Assertions.assertEquals("33000083672049", t.getKid());
                Assertions.assertEquals(Integer.parseInt("00"), t.getKortUtsteder());

                Assertions.assertEquals(Long.parseLong("6000432261"), t.getBlankettNummer());
                Assertions.assertEquals(Integer.parseInt("094561154"), t.getArkivReferanse());
                Assertions.assertEquals(LocalDate.of(2004, 03, 23), t.getOppdragsDato());
                Assertions.assertEquals(Long.parseLong("88881011128"), t.getDebetKonto());
        }

        @Test
        public void transaksjonPost3Test() throws ValidationException {
                Record post1 = new Record("NY09213000000012403040124012345000000000000044000                         000000");
                Record post2 = new Record("NY092131000000160004322610945611540000000230304000000000000000000000000000000000");
                Record post3 = new Record("NY0921320000001ABCDEFGHIJKLMNOPQRSTUVWXZYabcdefghijklmn0000000000000000000000000");
                Transaksjon t = new Transaksjon(post1, post2, post3);
                System.out.println(t);
                Assertions.assertEquals(Integer.parseInt("21"), t.getTransaksjonsType());
                Assertions.assertEquals(Integer.parseInt("0000001"), t.getTransaksjonsNummer());
                Assertions.assertEquals(LocalDate.of(2004, 03, 24), t.getOppgjorsDato());
                Assertions.assertEquals(Integer.parseInt("01"), t.getSentralId());
                Assertions.assertEquals(Integer.parseInt("24"), t.getDagKode());
                Assertions.assertEquals(Integer.parseInt("0"), t.getDelavregningsNummer());
                Assertions.assertEquals(Integer.parseInt("12345"), t.getLopeNummer());
                Assertions.assertEquals("0", t.getFortegn());
                Assertions.assertEquals(Long.parseLong("00000000000044000"), t.getBelop());
                Assertions.assertEquals("", t.getKid());
                Assertions.assertEquals(Integer.parseInt("00"), t.getKortUtsteder()); // TODO: Real value?

                Assertions.assertEquals(Long.parseLong("6000432261"), t.getBlankettNummer());
                Assertions.assertEquals(Integer.parseInt("094561154"), t.getArkivReferanse());
                Assertions.assertEquals(LocalDate.of(2004, 03, 23), t.getOppdragsDato());
                Assertions.assertEquals(Long.parseLong("00000000000"), t.getDebetKonto());

                Assertions.assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXZYabcdefghijklmn", t.getFritekstMelding());
        }

        @Test
        public void transaksjonPost3Test2() throws ValidationException {
                Record post1 = new Record("NY09213000000012403040124012345000000000000044000                         000000");
                Record post2 = new Record("NY092131000000160004322610945611540000000230304000000000000000000000000000000000");
                Record post3 = new Record("NY0921320000001ABCDEFGHIJKLMNOPQRSTUVWXZY              0000000000000000000000000");
                Transaksjon t = new Transaksjon(post1, post2, post3);
                System.out.println(t);
                Assertions.assertEquals("ABCDEFGHIJKLMNOPQRSTUVWXZY", t.getFritekstMelding());
        }

        @Test
        public void transaksjonInvalidNumericTest() throws ValidationException {
                ValidationException thrown = Assertions.assertThrows(ValidationException.class, () -> {
                        Record post1 = new Record("NY091030000000A2403040124112345000000000000044000           33000083672049000000");
                        Record post2 = new Record("NY091031000000160004322610945611540000000230304888810111280000000000000000000000");
                        new Transaksjon(post1, post2);
                });
                Assertions.assertEquals("Invalid numeric field. Could not be parsed.", thrown.getMessage());
        }

        @Test
        public void transaksjonInvalidDateTest() throws ValidationException {
                ValidationException thrown = Assertions.assertThrows(ValidationException.class, () -> {
                        Record post1 = new Record("NY09103000000012403AB0124112345000000000000044000           33000083672049000000");
                        Record post2 = new Record("NY091031000000160004322610945611540000000230304888810111280000000000000000000000");
                        new Transaksjon(post1, post2);
                });
                Assertions.assertEquals("Invalid date. Could not be parsed.", thrown.getMessage());
        }

        @Test
        public void transaksjonMismatchTransactionTypeTest() throws ValidationException {
                ValidationException thrown1 = Assertions.assertThrows(ValidationException.class, () -> {
                        Record post1 = new Record("NY09103000000012403040124112345000000000000044000           33000083672049000000");
                        Record post2 = new Record("NY091131000000160004322610945611540000000230304888810111280000000000000000000000");
                        new Transaksjon(post1, post2);
                });

                ValidationException thrown2 = Assertions.assertThrows(ValidationException.class, () -> {
                        Record post1 = new Record("NY09103000000012403040124112345000000000000044000           33000083672049000000");
                        Record post2 = new Record("NY091031000000160004322610945611540000000230304888810111280000000000000000000000");
                        Record post3 = new Record("NY0921320000001ABCDEFGHIJKLMNOPQRSTUVWXZYabcdefghijklmn0000000000000000000000000");
                        new Transaksjon(post1, post2, post3);
                });

                Assertions.assertEquals("Invalid transaction type. Should match type from previous post.", thrown1.getMessage());
                Assertions.assertEquals("Invalid transaction type. Should match type from previous post.", thrown2.getMessage());
        }

        @Test
        public void transaksjonInvalidFillerTest() throws ValidationException {
                ValidationException thrown1 = Assertions.assertThrows(ValidationException.class, () -> {
                        Record post1 = new Record("NY09213000000012403040124012345000000000000044000                         000001");
                        Record post2 = new Record("NY092131000000160004322610945611540000000230304000000000000000000000000000000000");
                        Record post3 = new Record("NY0921320000001ABCDEFGHIJKLMNOPQRSTUVWXZYabcdefghijklmn0000000000000000000000000");
                        new Transaksjon(post1, post2, post3);
                });
                ValidationException thrown2 = Assertions.assertThrows(ValidationException.class, () -> {
                        Record post1 = new Record("NY09213000000012403040124012345000000000000044000                         000000");
                        Record post2 = new Record("NY092131000000160004322610945611540000000230304000000000000000000000000000000001");
                        Record post3 = new Record("NY0921320000001ABCDEFGHIJKLMNOPQRSTUVWXZYabcdefghijklmn0000000000000000000000000");
                        new Transaksjon(post1, post2, post3);
                });
                ValidationException thrown3 = Assertions.assertThrows(ValidationException.class, () -> {
                        Record post1 = new Record("NY09213000000012403040124012345000000000000044000                         000000");
                        Record post2 = new Record("NY092131000000160004322610945611540000000230304000000000000000000000000000000000");
                        Record post3 = new Record("NY0921320000001ABCDEFGHIJKLMNOPQRSTUVWXZYabcdefghijklmn0000000000000000000000001");
                        new Transaksjon(post1, post2, post3);
                });

                Assertions.assertEquals("Invalid filler. Should be all zeros.", thrown1.getMessage());
                Assertions.assertEquals("Invalid filler. Should be all zeros.", thrown2.getMessage());
                Assertions.assertEquals("Invalid filler. Should be all zeros.", thrown3.getMessage());
        }

        @Test
        public void transaksjonMismatchTransactionNumberTest() throws ValidationException {
                ValidationException thrown1 = Assertions.assertThrows(ValidationException.class, () -> {
                        Record post1 = new Record("NY09213000000012403040124012345000000000000044000                         000000");
                        Record post2 = new Record("NY092131000000260004322610945611540000000230304000000000000000000000000000000000");
                        Record post3 = new Record("NY0921320000001ABCDEFGHIJKLMNOPQRSTUVWXZY              0000000000000000000000000");
                        new Transaksjon(post1, post2, post3);
                });

                ValidationException thrown2 = Assertions.assertThrows(ValidationException.class, () -> {
                        Record post1 = new Record("NY09213000000012403040124012345000000000000044000                         000000");
                        Record post2 = new Record("NY092131000000160004322610945611540000000230304000000000000000000000000000000000");
                        Record post3 = new Record("NY0921320000002ABCDEFGHIJKLMNOPQRSTUVWXZY              0000000000000000000000000");
                        new Transaksjon(post1, post2, post3);
                });

                Assertions.assertEquals("Invalid transaction number. Should match number from previous post.", thrown1.getMessage());
                Assertions.assertEquals("Invalid transaction number. Should match number from previous post.", thrown2.getMessage());
        }

        @Test
        public void transaksjonInvalidDayCodeTest() throws ValidationException {
                ValidationException thrown = Assertions.assertThrows(ValidationException.class, () -> {
                        Record post1 = new Record("NY09103000000012403040132112345000000000000044000           33000083672049000000");
                        Record post2 = new Record("NY091031000000160004322610945611540000000230304888810111280000000000000000000000");
                        new Transaksjon(post1, post2);
                });
                Assertions.assertEquals("Invalid day code. Should be between 1 and 31, inclusive.", thrown.getMessage());
        }

        @Test
        public void transaksjonInvalidPrefixTest() throws ValidationException {
                ValidationException thrown = Assertions.assertThrows(ValidationException.class, () -> {
                        Record post1 = new Record("NY09103000000012403040124112345+00000000000044000           33000083672049000000");
                        Record post2 = new Record("NY091031000000160004322610945611540000000230304888810111280000000000000000000000");
                        new Transaksjon(post1, post2);
                });
                Assertions.assertEquals("Invalid prefix. Should be either - or 0.", thrown.getMessage());
        }

        @Test
        public void transaksjonInvalidKidTest() throws ValidationException {
                ValidationException thrown = Assertions.assertThrows(ValidationException.class, () -> {
                        Record post1 = new Record("NY09213000000012403040124012345000000000000044000           33000083672049000000");
                        Record post2 = new Record("NY092131000000160004322610945611540000000230304000000000000000000000000000000000");
                        Record post3 = new Record("NY0921320000001ABCDEFGHIJKLMNOPQRSTUVWXZYabcdefghijklmn0000000000000000000000000");
                        Transaksjon t = new Transaksjon(post1, post2, post3);
                });
                Assertions.assertEquals("Invalid KID. Should be empty for transactions of type 20 and 21.", thrown.getMessage());
        }

        @Test
        public void transaksjonInvalidCardSupplierTest() throws ValidationException {
                ValidationException thrown = Assertions.assertThrows(ValidationException.class, () -> {
                        Record post1 = new Record("NY09103000000012403040124112345000000000000044000           33000083672049100000");
                        Record post2 = new Record("NY091031000000160004322610945611540000000230304888810111280000000000000000000000");
                        new Transaksjon(post1, post2);
                });
                Assertions.assertEquals("Invalid card supplier. Should be empty for all other transactions than type 18 to 21, inclusive.",
                                thrown.getMessage());
        }

        @Test
        public void transaksjonInvalidPaymentNumberTest() throws ValidationException {
                ValidationException thrown = Assertions.assertThrows(ValidationException.class, () -> {
                        Record post1 = new Record("NY09213000000012403040124112345000000000000044000           33000083672049000000");
                        Record post2 = new Record("NY092131000000160004322610945611540000000230304000000000000000000000000000000000");
                        Record post3 = new Record("NY0921320000001ABCDEFGHIJKLMNOPQRSTUVWXZYabcdefghijklmn0000000000000000000000000");
                        new Transaksjon(post1, post2, post3);
                });
                Assertions.assertEquals("Invalid part payment number. Should be 0 for transactions of type 18 to 21, inclusive.",
                                thrown.getMessage());
        }

        @Test
        public void transaksjonInvalidDebitAccountTest() throws ValidationException {
                ValidationException thrown = Assertions.assertThrows(ValidationException.class, () -> {
                        Record post1 = new Record("NY09213000000012403040124012345000000000000044000                         000000");
                        Record post2 = new Record("NY092131000000160004322610945611540000000230304000000000100000000000000000000000");
                        Record post3 = new Record("NY0921320000001ABCDEFGHIJKLMNOPQRSTUVWXZYabcdefghijklmn0000000000000000000000000");
                        new Transaksjon(post1, post2, post3);
                });
                Assertions.assertEquals("Invalid debit account. Should be empty for transactions of type 18 to 21, inclusive.",
                                thrown.getMessage());
        }
}