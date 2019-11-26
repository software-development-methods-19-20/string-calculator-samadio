package test;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddNumbersTest {


    @Test
    void emptyString(){
        //assertEquals(0,StringCalculator.add(""));  //equivalent to
        assertThat(StringCalculator.add(""), is(0));
    }

    @Test
    void OneNumber() {
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
    void moreseparators(){
        assertThat(StringCalculator.add("1,2,3\n5,0"), is(11));
    }

    @Test
    void customsep(){
        assertThat(StringCalculator.add("//;\n1;2;4"), is(7));
    }

    @Test
    void useless_customsep(){
        assertThat(StringCalculator.add("//;\n1"), is(1));
    }

    @Test
    void negative_test(){
        try { StringCalculator.add("//;\n1;-1;-12;-3");
        fail();}
        catch (IllegalArgumentException err){
            assertThat(err.getMessage(),startsWith("Negatives not allowed: "));
        }
    }

}

