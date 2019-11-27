package test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StringCalculator {

    private static Integer lim=1001;

    private static IntStream NumberStream(String[] data) {
        return Arrays.stream(data).mapToInt(i -> Integer.valueOf(i)).filter(x->x<lim);
    }


    private static List<Integer> negatives(String[] data) {
        return NumberStream(data).filter(x -> x < 0).boxed().collect(Collectors.toList());
    }

    private static boolean single_custom_separator(String s){
        return s.contains("[")==false;
    }

    private static String delete_last_square_bracket(String s){
        return s.substring(0,s.length()-1);
    }

    private static String extract_delimiters_declaration(String s){
        return s.substring(2, s.indexOf("\n"));
    }

    private static String exclude_delimiters_declaration(String numbers){
        return numbers.substring(numbers.indexOf("\n")+1,numbers.length());
    }



    private static String set_delimiters(String numbers) {
        String delimiter = extract_delimiters_declaration(numbers);

        if(single_custom_separator(delimiter)) {return delimiter;}

        String bracket1="";      //In case someone has the brilliant idea of using [
        String bracket2="";      // or ] as separator

        if(delimiter.contains("[[")){
            bracket1="|\\[";
            delimiter=delimiter.replaceAll("\\[\\[\\]","");
        }
        if(delimiter.contains("]]")){
            bracket2="|\\]";
            delimiter=delimiter.replaceAll("\\["+"\\]"+"\\]","");
        }

        delimiter=delete_last_square_bracket(delimiter).replaceAll("\\[","").replaceAll("\\]","|");
        return delimiter+bracket1+bracket2;

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
