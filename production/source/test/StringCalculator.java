package test;

import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StringCalculator {

    private static List<Integer> negatives(String[] data) {
        return NumberStream(data).filter(x -> x < 0).boxed().collect(Collectors.toList());
    }

    private static IntStream NumberStream(String[] data) {
        return Arrays.stream(data).mapToInt(i -> Integer.valueOf(i));
    }

    private static String set_delimiters(String numbers) {
        String delimiter = numbers.substring(2, numbers.indexOf("\n")); //between // and \n both excluded

        if(delimiter.contains("[")==false) {return delimiter;} //1 char single delimiter

        String safety1="";      //In case someone has the smart idea of using [
        String safety2="";      // or ] as separator

        if(delimiter.contains("[[")){safety1="|\\"+"[";}
        if(delimiter.contains("]]")){safety1="|\\"+"]";}

        delimiter=delimiter.replaceAll("\\]",""); //delete all "]" characters

        delimiter= String.join("|", delimiter.split("\\[")).substring(1);
        return delimiter+safety1+safety2;

        /*This does not work for now multiple patterns.It'd be far better and cleaner, but it's also difficult to adadpt in the maniac case separator==[ or ]

        Pattern p = Pattern.compile(".*\\[ *(.*) *\\].*");
        Matcher m = p.matcher(delimiter);
        m.find();
        return m.group(1);
         */

    }

    private static String exclude_delimiters_declaration(String numbers){
        return numbers.substring(numbers.indexOf("\n")+1,numbers.length());
    }




    public static int add(String numbers) {
        if(numbers.isEmpty()) return 0;

        String delimiters= ",|\n" ;
        if (numbers.startsWith("//")) {  //if a new delimiter is given, redefine delimiters and numbers
            delimiters = set_delimiters(numbers);
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
