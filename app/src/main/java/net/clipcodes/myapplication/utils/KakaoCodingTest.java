package net.clipcodes.myapplication.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class KakaoCodingTest {

    public static void main(String[] args) {
//        String inputString = "aabbaccc";
//        String inputString = "ababcdcdababcdcd";
//        String inputString = "abcabcdede";
//        String inputString = "abcabcabcabcdedededede";
        String inputString = "xababcdcdababcdcd";

        int temp = inputString.length();
        for(int i = 1 ; i <= inputString.length() ; i ++){
            int length = getOptimizedStringShortestLength(inputString, i);
            if(length < temp){
                temp = length;
            }
        }

        System.out.println("가장짧은문자열은 " + temp + "입니다.");

    }

    public static int getOptimizedStringShortestLength(String inputString, int unit){

        String[] parsedStringArray = getParsedString(inputString, unit);

        String result = getOptimizedString(parsedStringArray);
        return result.length();
    }

    public static String getOptimizedString(String[] parsedStringArray){
        StringBuffer optimizedString = new StringBuffer();

        List<String> optimizedStringList = new ArrayList<String>();

        int count = 1 ;
        for(int i = 0 ; i < parsedStringArray.length ; i++){
            if(i+1 < parsedStringArray.length){
                if(parsedStringArray[i].equals(parsedStringArray[i+1])){
                    count++;
                }else{
                    if(count == 1){
                        optimizedStringList.add(parsedStringArray[i]);
                    }else{
                        optimizedStringList.add(count + parsedStringArray[i]);
                    }
                    count = 1;
                }
            }else{
                if(count == 1){
                    optimizedStringList.add(parsedStringArray[i]);
                }else{
                    optimizedStringList.add(count + parsedStringArray[i]);
                }
            }
        }

        for(String item : optimizedStringList){
            optimizedString.append(item);
        }

        return optimizedString.toString();
    }

    public static String[] getParsedString(String inputString, int unit){
        int parsedStringArraySize = 0;
        if(inputString.length() % unit == 0 ){
            parsedStringArraySize = inputString.length() / unit;
        }else{
            parsedStringArraySize = (inputString.length() / unit) + 1;
        }
        String[] parsedStringArray = new String[parsedStringArraySize];

        int startIdx = 0;
        int endIdx = 0;
        for(int i = 0 ; i < parsedStringArray.length ; i++){
            endIdx += unit;

            if(endIdx <= inputString.length()){
                parsedStringArray[i] = inputString.substring(startIdx, endIdx);
                startIdx += unit;
            }else{
                parsedStringArray[i] = inputString.substring(startIdx, inputString.length());
                startIdx += unit;
            }
        }
        return parsedStringArray;
    }
}
