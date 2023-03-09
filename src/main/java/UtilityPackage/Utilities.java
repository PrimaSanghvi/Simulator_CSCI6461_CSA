/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UtilityPackage;

import GettersSetters.GPRIR;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author primasanghvi
 */
public class Utilities {
    
     public static GPRIR setDefaultValuesGPRIR() {
        return new GPRIR();
    }
     
     //Convert hexadecimal to decimal
     public static int convertHexadecimalToDecimal(String value) {
        if (value.equals("")) return 0;
        return Integer.parseInt(value,16);
    }
     //Convert Hexadecimal to Binary
      public static String convertHexadecimalToBinary(String value) {
        String binaryString = Integer.toBinaryString(convertHexadecimalToDecimal(value));
        return ("0000000000000000" + binaryString).substring(binaryString.length());
    }

      //Convert Integer to String
        public static String convertIntegerToString(int value) {
        return String.valueOf(value);
    }
        //Convert String to Integer
         public static int convertStringToInteger(String value) {
        return Integer.parseInt(value);
    }
        //Convert Decimal to Hexadecimal
    public static String convertDecimalToHexadecimal(String value) {
        return Integer.toHexString(convertStringToInteger(value));
    }
    //Convert Binary to Hexadecimal
       public static String convertBinaryToHexadecimal(String value) {
        int decimal = convertBinaryToDecimal(value);
        String decimalString = convertIntegerToString(decimal);
        return convertDecimalToHexadecimal(decimalString);
    }
       //Convert Binary to Decimal
        public static int convertBinaryToDecimal(String value) {
            if (value.equals("")) return 0;
        return Integer.parseInt(value,2);
    }

        //Convert Decimal to Binary
        public static String convertDecimalToBinary(String value) {
            String binaryString = Integer.toBinaryString(Integer.valueOf(value));
            return ("0000000000000000" + binaryString).substring(binaryString.length());
        }
}
