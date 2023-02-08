/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.simulator_csci6461;
        
import GettersSetters.GPRIR;
import GettersSetters.OpcodeIns;
import UtilityPackage.Utilities;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author primasanghvi
 */
public class BackendSimulator extends Simulator_CSCI6461{
    
    HashMap<String,String> fileContentMap = new HashMap<>();;
     Utilities util = new Utilities();
     GPRIR gpIr = new GPRIR();
     public static void main(String[] args) {
           FrontPanel panel = new FrontPanel();
   
     //HashMap<String,String> fileContentMap = panel.map;
     
    // System.out.println("map: " + fileContentMap.values());
     }
     public void readFile(File file)
{
	try
	{
		@SuppressWarnings("resource")
		Scanner fileReader = new Scanner(file);
		
		 
		
			while(fileReader.hasNext())
			{
				String address = fileReader.next();
				String value = fileReader.next();
				writeData(address, value);
				
			}
                        
	} catch (FileNotFoundException e) {
		
		e.printStackTrace();
                
	}	
	
}
     public void writeData(String address,String value)
     {
         String hexToBinary =  util.convertHexadecimalToBinary(value);
         fileContentMap.put(address,hexToBinary);
         assignOpcodeValue(hexToBinary);
     }
      
     private void assignOpcodeValue(String value) {
        OpcodeIns opcode = gpIr.getOpcode();
        opcode.setOperations(value.substring(0,6));
        opcode.setGeneralPurposeRegister(value.substring(6,8));
        opcode.setIndexRegister(value.substring(8,10));
        opcode.setIndirectMode(value.substring(10,11));
        opcode.setAddress(value.substring(11,16));
        opcode.setShouldIncrementPC(true);
        gpIr.setOpcode(opcode);
    }

      public void runBtnListner ()
    {
         int size = fileContentMap.size();
         int i =0;
        while (i < size) {
            singleStepBtnListener();
            i++;
        }
    }
      public void singleStepBtnListener()
      {
          gpIr.setMar(gpIr.getPc());
          gpIr.setMbr(gpIr.getMar());
      }
      
      public void binaryOpcodeInput (String value)
      {
          
      }
    }

     

