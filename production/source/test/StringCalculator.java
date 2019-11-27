package test;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StringCalculator {

    private static Integer lim=1000;

    public void set_lim(Integer i){
        this.lim=i;
    }

    private static List<Integer> negatives(String[] data) {
        return NumberStream(data).filter(x -> x < 0).boxed().collect(Collectors.toList());
    }

    private static IntStream NumberStream(String[] data) {
        return Arrays.stream(data).mapToInt(i -> Integer.valueOf(i)).filter(x->x<lim);
    }

    private static String set_delimiters(String numbers) {
        String delimiter = numbers.substring(2, numbers.indexOf("\n"));

        if(delimiter.contains("[")==false) {return delimiter;}

        String safety1="";      //In case someone has the smart idea of using [
        String safety2="";      // or ] as separator

        if(delimiter.contains("[[")){
            safety1="|\\[";
            delimiter=delimiter.replaceAll("\\[\\[\\]","");
        }
        if(delimiter.contains("]]")){
            safety1="|\\"+"]";
            delimiter=delimiter.replaceAll("\\["+"\\]"+"\\]","");
        }

        delimiter=delimiter.replaceAll("\\[","").replaceAll("\\]","|");
        delimiter=delimiter.substring(0,delimiter.length()-1);
        return delimiter+safety1+safety2;

    }

    private static String exclude_delimiters_declaration(String numbers){
        return numbers.substring(numbers.indexOf("\n")+1,numbers.length());
    }




    public static int add(String numbers) {
        if(numbers.isEmpty()) return 0;

        String delimiters= ",|\n" ;
        if (numbers.startsWith("//")) {
            delimiters = set_delimiters(numbers);
            numbers= exclude_delimiters_declaration(numbers);
        }

        if(numbers.length()==1) return Integer.valueOf(numbers);

        String[] data=numbers.split(delimiters);

        List<Integer> neg = negatives(data);
        if (neg.isEmpty()==false) {
            throw new IllegalArgumentException("Negatives not allowed: "+ neg.toString());
        }

        return NumberStream(data).sum();

    }


}
