package test;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StringCalculator {

    private static List<Integer> negatives(String[] data) {
        return NumberStream(data).filter(x->x<0).boxed().collect(Collectors.toList());
    }

    private static String StreamtoString(IntStream datastream){
        return datastream.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
    }

    private static IntStream NumberStream(String[] data){
        return Arrays.stream(data).mapToInt(i->Integer.valueOf(i));
    }

    private static String set_delimiter(String numbers){
        return numbers.substring(2,numbers.indexOf("\n")); //return separator, between // and \n both excluded
    }

    private static String exclude_delimiters_declaration(String numbers){
        return numbers.substring(numbers.indexOf("\n")+1,numbers.length());
    }




    public static int add(String numbers) {
        if(numbers.isEmpty()) return 0;

        String delimiters= ",|\n" ;
        if (numbers.startsWith("//")) {  //if a new delimiter is given, redefine delimiters and numbers
            delimiters = set_delimiter(numbers);
            numbers= exclude_delimiters_declaration(numbers);
        }

        //1 element case, eventually someone can define a not used separator
        if(numbers.length()==1) return Integer.valueOf(numbers);

        String[] data=numbers.split(delimiters);

        List<Integer> neg = negatives(data);
        if (neg.isEmpty()==false) {
            throw new IllegalArgumentException("Negatives not allowed: "+ neg.toString());
        }


        return NumberStream(data).filter(x->x<1001).sum();

    }


}
