package com.didekin.tutor.crypto.vigenere;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.stream.Collectors;

import static com.didekin.tutor.crypto.charhelper.CharManipulator.cipherXorWithHexArrayKey;
import static com.didekin.tutor.crypto.charhelper.CharManipulator.getAsciiCharFromAsciiDecimal;
import static com.didekin.tutor.crypto.charhelper.CharManipulator.getAsciiHexStrFromStrText;
import static com.didekin.tutor.crypto.charhelper.CharManipulator.getAsciiHexStrFromSymbolChar;
import static com.didekin.tutor.crypto.charhelper.CharManipulator.xorCharArrayWithKeyArray;
import static com.didekin.tutor.crypto.vigenere.VigenereBreaker.composeIthStream;
import static com.didekin.tutor.crypto.vigenere.VigenereBreaker.doProbArrayFromText;
import static com.didekin.tutor.crypto.vigenere.VigenereBreaker.getIthCharInKey;
import static com.didekin.tutor.crypto.vigenere.VigenereBreaker.getKeyCharsFromCipheredAndKeyPeriod;
import static com.didekin.tutor.crypto.vigenere.VigenereBreaker.getKeyPeriodFromText;
import static com.didekin.tutor.crypto.vigenere.VigenereBreaker.getSubtextForTrialPeriod;
import static com.didekin.tutor.crypto.vigenere.VigenereBreaker.key_max_length;
import static java.lang.String.valueOf;
import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * User: pedro@didekin
 * Date: 21/09/17
 * Time: 10:59
 */
public class VigenereBreakerTest {

    private static final String plaintText =
            "hereupon legrand arose, with a grave and stately air, and brought me the beetle" +
                    "from a glass case in which it was enclosed. it was a beautiful scarabaeus, and, at" +
                    "that time, unknown to naturalists of course a great prize in a scientific point" +
                    "of view. there were two round black spots near one extremity of the back, and a" +
                    "long one near the other. the scales were exceedingly hard and glossy, with all the" +
                    "appearance of burnished gold. the weight of the insect was very remarkable, and," +
                    "taking all things into consideration, i could hardly blame jupiter for his opinion" +
                    "respecting it.";

    @Test
    public void test_GetSubtextByShift() throws Exception   // ZY:X>
    {
        assertThat(getSubtextForTrialPeriod("5A593A583E", 2), allOf
                (
                        hasItem('Z'),
                        hasItem(':'),
                        hasItem('>')
                )
        );
        assertThat(getSubtextForTrialPeriod("5A593A583E", 2).size(), is(3));

        assertThat(getSubtextForTrialPeriod("5A593A583E", 1).size(), is(5));
    }

    @Test
    public void test_DoProbArrayFromText() throws Exception
    {
        double[] probabilities = doProbArrayFromText(asList('a', 'a', 'b', 'c', 'b', 'a'));
        assertThat(probabilities['a'], is(3.0 / 6));
        assertThat(probabilities['b'], is(2.0 / 6));
        assertThat(probabilities['c'], is(1.0 / 6));
        assertThat(probabilities['A'], is(0.0));
        assertThat(probabilities['Z'], is(0.0));
    }

    @Test
    public void test_GetKeyPeriodFromText() throws Exception
    {
        // We check that the period obtained is a multiple of the key period used: the algorithm has no way to choose between muliples of the period.
        assertThat(getKeyPeriodFromText(doCipheredText("a", "f", "z"), key_max_length) % 3 == 0, is(true));
        /* Try with a prime without multiples in the range 1-13.*/
        assertThat(getKeyPeriodFromText(doCipheredText("a", "f", "z", "1", "4", "G", "P"), key_max_length), is(7));
    }

    @Test
    public void test_ComposeIthStream() throws Exception
    {
        String sourceText = "añsldkfjqpwoeiru<-x.c,vm1029394857";
        String hexSourceText = getAsciiHexStrFromStrText(sourceText);  // 61F1736C646B666A7170776F656972753C2D782E632C766D31303239333934383537
        assertThat(composeIthStream(hexSourceText, 1, 10), hasItems('a', 'w', 'c', '4'));  //  añsldkfjqp woeiru<-x. c,vm102939 4857
        assertThat(composeIthStream(hexSourceText, 4, 10), hasItems('l', 'i', 'm', '7'));
        assertThat(composeIthStream(hexSourceText, 3, 7), hasItems('s', 'p', '<', 'm', '4'));   //  añsldkf jqpwoei ru<-x.c ,vm1029 394857
    }

    @Test
    public void test_GetIthCharInKey() throws Exception
    {
        final String cipheredString = doCipheredText("a");
        List<Character> cipheredChars = cipheredString.chars().mapToObj(intChar -> ((char) intChar)).collect(Collectors.toList());
        assertThat(getKeyPeriodFromText(cipheredString, 3), is(1));
//        assertThat(((char)getIthCharInKey(cipheredChars)), is('a'));
    }

    @Test
    public void test_GetKeyCharsFromCipheredAndKeyPeriod() throws Exception
    {
        final String coursera_hw_1 =
                "F96DE8C227A259C87EE1DA2AED57C93FE5DA36ED4EC87EF2C63AAE5B9A7EFFD673BE4ACF7BE8923CAB1E" +
                        "CE7AF2DA3DA44FCF7AE29235A24C963FF0DF3CA3599A70E5DA36BF1ECE77F8DC34BE129A6CF4D126BF5B" +
                        "9A7CFEDF3EB850D37CF0C63AA2509A76FF9227A55B9A6FE3D720A850D97AB1DD35ED5FCE6BF0D138A84C" +
                        "C931B1F121B44ECE70F6C032BD56C33FF9D320ED5CDF7AFF9226BE5BDE3FF7DD21ED56CF71F5C036A94D" +
                        "963FF8D473A351CE3FE5DA3CB84DDB71F5C17FED51DC3FE8D732BF4D963FF3C727ED4AC87EF5DB27A451" +
                        "D47EFD9230BF47CA6BFEC12ABE4ADF72E29224A84CDF3FF5D720A459D47AF59232A35A9A7AE7D33FB85F" +
                        "CE7AF5923AA31EDB3FF7D33ABF52C33FF0D673A551D93FFCD33DA35BC831B1F43CBF1EDF67F0DF23A15B" +
                        "963FE5DA36ED68D378F4DC36BF5B9A7AFFD121B44ECE76FEDC73BE5DD27AFCD773BA5FC93FE5DA3CB859" +
                        "D26BB1C63CED5CDF3FE2D730B84CDF3FF7DD21ED5ADF7CF0D636BE1EDB79E5D721ED57CE3FE6D320ED57" +
                        "D469F4DC27A85A963FF3C727ED49DF3FFFDD24ED55D470E69E73AC50DE3FE5DA3ABE1EDF67F4C030A44D" +
                        "DF3FF5D73EA250C96BE3D327A84D963FE5DA32B91ED36BB1D132A31ED87AB1D021A255DF71B1C436BF47" +
                        "9A7AF0C13AA14794";

        int keyPeriod = getKeyPeriodFromText(coursera_hw_1, key_max_length);
        System.out.printf("KeyPeriod = %d%n", keyPeriod);
        String key = stream(getKeyCharsFromCipheredAndKeyPeriod(coursera_hw_1, keyPeriod)).mapToObj(intChar -> valueOf(getAsciiCharFromAsciiDecimal(intChar))).collect(joining());
        System.out.printf("Key: %s%n", key);
    }

    // =========================== HELPERS ===========================

    private String doCipheredText(String... charsInKey) throws UnsupportedEncodingException
    {
        String[] keyArrayInHex = doKeyArrayHexadec(charsInKey);
        return cipherXorWithHexArrayKey(plaintText, keyArrayInHex);
    }

    private String[] doKeyArrayHexadec(String... keyChars) throws UnsupportedEncodingException
    {
        String[] keyArrayInHex = new String[keyChars.length];
        for (int i = 0; i < keyChars.length; ++i) {
            keyArrayInHex[i] = getAsciiHexStrFromSymbolChar(keyChars[i]);
        }
        return keyArrayInHex;
    }
}