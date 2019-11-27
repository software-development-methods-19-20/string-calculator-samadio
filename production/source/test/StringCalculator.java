package test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StringCalculator {

    private static Integer lim=1001;

    private static IntStream numberStream(String[] data) {
        return Arrays.stream(data).mapToInt(i -> Integer.valueOf(i)).filter(x->x<lim);
    }


    private static List<Integer> negatives(String[] data) {
        return numberStream(data).filter(x -> x < 0).boxed().collect(Collectors.toList());
    }

    private static boolean singleCustomSeparator(String s){
        return s.contains("[")==false;
    }

    private static String deleteLastSquareBracket(String s){
        return s.substring(0,s.length()-1);
    }

    private static String extractDelimitersDeclaration(String s){
        return s.substring(2, s.indexOf("\n"));
    }

    private static String excludeDelimitersDeclaration(String numbers){
        return numbers.substring(numbers.indexOf("\n")+1,numbers.length());
    }



    private static String setDelimiters(String numbers) {
        String delimiter = extractDelimitersDeclaration(numbers);

        if(singleCustomSeparator(delimiter)) {return delimiter;}

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

        delimiter=deleteLastSquareBracket(delimiter).replaceAll("\\[","").replaceAll("\\]","|");
        return delimiter+bracket1+bracket2;

    }




    public static int add(String numbers) {
        if(numbers.isEmpty()) return 0;

        String delimiters= ",|\n" ;
        if (numbers.startsWith("//")) {
            delimiters = setDelimiters(numbers);
            numbers= excludeDelimitersDeclaration(numbers);
        }

        if(numbers.length()==1) return Integer.valueOf(numbers);

        String[] data=numbers.split(delimiters);

        List<Integer> neg = negatives(data);
        if (neg.isEmpty()==false) {
            throw new IllegalArgumentException("Negatives not allowed: "+ neg.toString());
        }

        return numberStream(data).sum();

    }


}
