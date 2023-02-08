/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UtilityPackage;

/**
 *
 * @author primasanghvi
 */
public class Utilities {
    
     public static int convertHexadecimalToDecimal(String value) {
        if (value.equals("")) return 0;
        return Integer.parseInt(value,16);
    }
      public static String convertHexadecimalToBinary(String value) {
        String binaryString = Integer.toBinaryString(convertHexadecimalToDecimal(value));
        return ("0000000000000000" + binaryString).substring(binaryString.length());
    }

       public static String convertHexadecimalNumberToFourDigits(String value) {
        return ("0000" + value).substring(value.length());
    }
        public static String convertIntegerToString(int value) {
        return String.valueOf(value);
    }
         public static int convertStringToInteger(String value) {
        return Integer.parseInt(value);
    }
        
    public static String convertDecimalToHexadecimal(String value) {
        return Integer.toHexString(convertStringToInteger(value));
    }
       public static String convertBinaryToHexadecimal(String value) {
        int decimal = convertBinaryToDecimal(value);
        String decimalString = convertIntegerToString(decimal);
        return convertDecimalToHexadecimal(decimalString);
    }
        public static int convertBinaryToDecimal(String value) {
        return Integer.parseInt(value,2);
    }

}
