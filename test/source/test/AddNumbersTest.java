package test;
import StringCalc.StringCalculator;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

public class AddNumbersTest {

    @Test
    void emptyString(){
        //assertEquals(0,StringCalculator.add(""));  //equivalent to
        assertThat(StringCalculator.add(""), is(0));
    }

    @Test
    void oneNumber() {
        assertThat(StringCalculator.add("1"), is(1));
    }

    @Test
    void twoNumbers(){
        assertThat(StringCalculator.add("1,2"), is(3));
    }

    @Test
    void moreNumbers(){
        assertThat(StringCalculator.add("1,2,3,5,0"), is(11));
    }

    @Test
    void moreSeparators(){
        assertThat(StringCalculator.add("1,2,3\n5,0"), is(11));
    }

    @Test
    void customSep(){
        assertThat(StringCalculator.add("//[\n1[2[4"), is(7));
    }

    @Test
    void uselessCustomsep(){
        assertThat(StringCalculator.add("//;\n1"), is(1));
    }

    @Test
    void negativeTest(){
        try { StringCalculator.add("//;\n1;-1;-12;-3");
        fail();}
        catch (IllegalArgumentException err){
            assertThat(err.getMessage(),startsWith("Negatives not allowed: "));
        }
    }

    @Test
    void crazySeparator(){
        assertThat(StringCalculator.add("//[l][j][]]\n1l2j4]3"), is(10));
    }

    @Test
    void longSeparator(){
        assertThat(StringCalculator.add("//[::]\n1::2"), is(3));
    }


    @Test
    void multipleLongSeparatorLimit(){
        assertThat(StringCalculator.add("//[xyz][yyz][zzz]\n1zzz2xyz4yyz3yyz2yyz1005"), is(12));
    }

}

