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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 *
 * @author primasanghvi
 */
public class BackendSimulator extends Simulator_CSCI6461{
    
     public List<String> programMem;  //Local memory of size 4096
     Utilities util = new Utilities();
     public List<String> fileContentList; //Dupicate memory to store only file contents
     GPRIR gpIr; 
     public Map<Integer, Map<String, String>> cache ;
 
     public BackendSimulator()
     {
          programMem = new ArrayList<>(4096);
         fileContentList = new ArrayList<>();
           this.gpIr = util.setDefaultValuesGPRIR();           
           for(int i=0 ; i< 4096 ; i++)
           {
                programMem.add("0");         
           }
           this.cache = new TreeMap<>(Collections.reverseOrder());
     }
     //To get the data from memory and LOAD the contents in MBR when address in entered in MAR
     public String marDisplayValue(String data)
     {
         String res = getDataFromMemoryMap(data);
         if(res.equals("0"))
         return util.convertHexadecimalToBinary(res);
         else
             return getDataFromMemoryMap(data);
     }
     //Set PC values from frontend
      public void pcListener(String value) {
        gpIr.setPc(value);
      }
      //Set MAR values from frontend
      public void marListener(String value) {
        gpIr.setMar(value);
      }
      //Set MBR values from frontend
       public void MbrListener(String value) {
        gpIr.setMbr(value); 
    }
     public static void main(String[] args) {
           FrontPanel panel = new FrontPanel();
   
     }

     //To read the contents of the file 
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
     //Write file contents to main memory as well as fileContentList in binary form
     public void writeData(String address,String value)
     {
         String hexToBinary =  util.convertHexadecimalToBinary(value);
          int addressInDecimal = util.convertHexadecimalToDecimal(address);
          programMem.set(addressInDecimal,hexToBinary);
          fileContentList.add(addressInDecimal,hexToBinary);
     }
      //To decode the opcode and set values using substring
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

     //Perform operations based on the opcode: here the opcode comparision is made in binary
    public void performOperations()
     {
         
        String opcodeVal = gpIr.getOpcode().getOperations();
        calculateEffectiveAddress();   //calculate the effective address 
        setcache();
        switch (opcodeVal) {
            case "000000":
                haltOperation();
                break;
            case "000001":
                loadRgstFromMem();
                break;
            case "000010":
               storeRgstToMemory();
                break;
            case "000011":
                loadRgstWithAddress();
                break;
            case "100001":
              loadIndexRegisterFromMemory();
                break;
            case "100010":
                storeIndexRegisterToMemory();
                break;
            case "001000":
            	jumpIfZero();
            	break;
            case "001001" :
            	jumpIfNotEqual();
            	break;
            case "001010":
            	jumpIfConditionCode();
            	break;
            case "001011":
            	unconditionalJumpToAddress();
            	break;
            case "001100":
            	jumpAndSaveReturnAddress();
            	break;
            case "001101":
            	returnFromSubRoutine();
            	break;
            case "001110":
            	subtractOneAndBranch();
            	break;
            case "001111":
            	jumpGreaterThanOrEqualTo();
            	break;
            case "000100":
            	addMemoryToRegister();
            	break;
            case "000101":
            	subtractMemoryToRegister();
            	break;
            case "000110":
            	addImmediateToRegister();
            	break;
            case "000111" :
            	subtractImmediateToRegister();
            	break;
            case "010000":
            	multiplyRegisterToRegister();
            	break;
            case "010001" :
            	divisionRegisterToRegister();
            	break;
            case "010010" :
            	testEqualityRegisterToRegister();
            	break;
            case "010011" :
            	logicalAndRegisterToRegister();
            	break;
            case "010100":
            	logicalOrRegisterAndRegister();
            	break;
            case "010101":
            	logicalNotOfRegister();
            	break;
            case "011001":
            	shiftRegisterByCount();
            	break;
            case "011010":
            	rotateRegisterByCount();
            	break;
             default:
                System.out.println("Invalid operations");
        }
    
     }
    private void haltOperation() {
        gpIr.setHaltIns(1);

    }
    //To perform LDX operation
    public void loadRgstFromMem()
    {
        String getValueFromMainMemoryInBinary = getDataFromMemoryMap(
                gpIr.getOpcode().getEffectiveAddress());
        loadGPRFromOpcode(getValueFromMainMemoryInBinary);
     
    }
    //To perform STX operation
    public void storeRgstToMemory()
    {
        String dataFromGPRByOpcodeInBinary = getDataFromGPRByOpcode();
        setDataInMainMemoryByLocation(dataFromGPRByOpcodeInBinary,
                gpIr.getOpcode().getEffectiveAddress());
       
    }
    //To perform LDA operation
    private void loadRgstWithAddress() {
        loadGPRFromOpcode(gpIr.getOpcode().getEffectiveAddress());
    }
    
    //Set data in main memory during store operations: the memory location is decimal so convert hex to decimal
    public void setDataInMainMemoryByLocation(String Memory, String Data)
    {
        String memoryInHex = util.convertBinaryToHexadecimal(Memory);
        int memoryInDecimal = util.convertHexadecimalToDecimal(memoryInHex);
        programMem.set(memoryInDecimal, Data);
       
    }
    
    //To read data from General Purpose Register 
    public String getDataFromGPRByOpcode() {
        String gprRegisterSelect = gpIr.getOpcode().getGeneralPurposeRegister(); 
        if (gprRegisterSelect.equals("00")) {
            return gpIr.getRegisterZero();
        }
        if (gprRegisterSelect.equals("01")) {
            return gpIr.getRegisterOne();
        }
        if (gprRegisterSelect.equals("10")) {
            return gpIr.getRegisterTwo();
        }
        return gpIr.getRegisterThree();
    }
    
    //To write data to General purpose resgister
    public void loadGPRFromOpcode(String data)
    {
        String gprRegisterSelect = gpIr.getOpcode().getGeneralPurposeRegister();
        if (gprRegisterSelect.equals("00")) {
            gpIr.setRegisterZero(data);
            return;
        }
        if (gprRegisterSelect.equals("01")) {
            gpIr.setRegisterOne(data);
            return;
        }
        if (gprRegisterSelect.equals("10")) {
            gpIr.setRegisterTwo(data);
            return;
        }
        gpIr.setRegisterThree(data);
    }
    
    //Calculating the EA
    public void calculateEffectiveAddress() {
         String addressInHex = util.convertBinaryToHexadecimal(
                gpIr.getOpcode().getAddress());
         //Check if IX = 0 then check if Indirect Mode is 0; if true store address field in EA
        if (gpIr.getOpcode().getIndexRegister().equals("00")) {
            if (gpIr.getOpcode().getIndirectMode().equals("0")) {
                gpIr.getOpcode().setEffectiveAddress(gpIr.getOpcode().getAddress());
                return;
            }
            //else if IX = 0 and Indirect Mode is 1 : set concents of adrress field from the main memory to EA
            String dataFromMemoryMap = getDataFromMemoryMap(gpIr.getOpcode().getAddress());
            gpIr.getOpcode().setEffectiveAddress(dataFromMemoryMap);
            return;
        }

        //If IX = 1..3 and Indirect Mode is 0: Add the contents of index register and the contents of memory address to EA
        if (gpIr.getOpcode().getIndirectMode().equals("0")) {
            calculateEffectiveAddressForFalseIndirectMode();
            return;
        } 
        // If IX = 1..3 and Indirect Mode is 1: Add the contents of (contents of IX to contents of memory address)
        else
        calculateEffectiveAddressForTrueIndirectMode();
    }

    public String getDataFromMemoryMap(String value)
    {
         int memInDecimal = util.convertBinaryToDecimal(value);
         String tempBinaryData = programMem.get(memInDecimal);
         int tempDecimalData = util.convertBinaryToDecimal(tempBinaryData);
         String finalValInBinary = util.convertDecimalToHexadecimal(String.valueOf(tempDecimalData));
         return util.convertHexadecimalToBinary(finalValInBinary);
   
    }
            
    // To calculate EA for IX = 1..3 and Indirect Mode is 0: Add the contents of index register and the contents of memory address to EA
    public void calculateEffectiveAddressForFalseIndirectMode()
    {
        
        int effectiveAddressInDecimal = util.convertBinaryToDecimal(
                gpIr.getOpcode().getAddress());
        int indexRegisterDataInDecimalInteger = util.convertBinaryToDecimal(
                getDataFromIndexRegisterByAddress());

        int calculatedEffectiveAddressInDecimal = effectiveAddressInDecimal +
                indexRegisterDataInDecimalInteger;

        String calculatedEffectiveAddressInDecimalString = util.convertIntegerToString(
                calculatedEffectiveAddressInDecimal);
        String calculatedEffectiveAddressInHexadecimal = util.convertDecimalToHexadecimal(
                calculatedEffectiveAddressInDecimalString);
        String calculatedEAInBinary = util.convertHexadecimalToBinary(calculatedEffectiveAddressInHexadecimal);
        
        gpIr.getOpcode().setEffectiveAddress(calculatedEffectiveAddressInHexadecimal);
    }
    
    // To calculate EA for IX = 1..3 and Indirect Mode is 1: Add the contents of (contents of IX to contents of memory address)
    public void calculateEffectiveAddressForTrueIndirectMode()
    {
        
        int irInDataInDecimal = util.convertBinaryToDecimal(
                getDataFromIndexRegisterByAddress());

        String dataFromMainMemory = getDataFromMemoryMap(gpIr.getOpcode().getAddress());
        
        int dataFromMemInDecimal = util.convertBinaryToDecimal(dataFromMainMemory);

        int effectiveAddressInDecimal = irInDataInDecimal +
                dataFromMemInDecimal;

        String calculatedEAInDecimalString = util.convertIntegerToString(
                effectiveAddressInDecimal);
        String calculatedEAInHexadecimal = util.convertDecimalToHexadecimal(
                calculatedEAInDecimalString);
        
        String calculatedEAInBinary = util.convertHexadecimalToBinary(calculatedEAInHexadecimal);
        
        gpIr.getOpcode().setEffectiveAddress(calculatedEAInBinary);
    
    }
    
    //To read data from Index Register by main memory address 
    private String getDataFromIndexRegisterByAddress() {
        int indexRegisterInDecimal = Integer.parseInt(gpIr.getOpcode().getIndexRegister());
        String indexRegisterDataInBinaryString;
        
        if (indexRegisterInDecimal == 1)
        {
            indexRegisterDataInBinaryString = gpIr.getIndexRegisterOne();
        } else if (indexRegisterInDecimal == 2)
        {
            indexRegisterDataInBinaryString = gpIr.getIndexRegisterTwo();
        } else 
        {
            indexRegisterDataInBinaryString = gpIr.getIndexRegisterThree();
        }
        if (indexRegisterDataInBinaryString.equals("")) 
        {
            return "0000";
        }
        return indexRegisterDataInBinaryString;
    }
    
    //To perform LDX operation: load index register from memory
     private void loadIndexRegisterFromMemory() {
        String getValueFromMainMemoryInHexadecimal = getDataFromMemoryMap(
                gpIr.getOpcode().getEffectiveAddress());
        loadIRFromOpcode(getValueFromMainMemoryInHexadecimal);
    }
     
     //To set values of Index register
     public void loadIRFromOpcode (String data){
    
        String ixrRegisterSelect = gpIr.getOpcode().getIndexRegister();
        if (ixrRegisterSelect.equals("01")) {
            gpIr.setIndexRegisterOne(data);
            return;
        }
        if (ixrRegisterSelect.equals("10")) {
            gpIr.setIndexRegisterTwo(data);
            return;
        }
        gpIr.setIndexRegisterThree(data);
    
}
     
     // To perform index register to memory operation 
      private void storeIndexRegisterToMemory() {
        String dataFromIXRByOpcodeInBinary = getDataFromIXRByOpcode();
        setDataInMainMemoryByLocation(dataFromIXRByOpcodeInBinary,
                gpIr.getOpcode().getEffectiveAddress());
    }
      
      private String getDataFromIXRByOpcode() {
        String ixrRegisterSelect = gpIr.getOpcode().getIndexRegister();
        if (ixrRegisterSelect.equals("01")) {
            return gpIr.getIndexRegisterOne();
        }
        if (ixrRegisterSelect.equals("10")) {
            return gpIr.getIndexRegisterTwo();
        }
        return gpIr.getIndexRegisterThree();
    }
      
      //Run Button Listener
      public void runBtnListner ()
    {
        int size = fileContentList.size();
        int i = util.convertBinaryToDecimal(gpIr.getPc());
        while (i < size)
        {
            singleStepBtnListener();   //Call single step inside run button upto the size of file 
            i++;
            if(gpIr.getHaltIns() == 1)  //Stop execution as we reach a halt instruction
            i = size;
        }
    }
      
      //Single Step Button Listener 
      public void singleStepBtnListener()
      {
          gpIr.setMar(gpIr.getPc());
          gpIr.setMbr(getDataFromMemoryMap(gpIr.getMar()));
          gpIr.setIr(gpIr.getMbr());
          assignOpcodeValue(gpIr.getIr());
          performOperations();
          
          if (gpIr.getOpcode().getShouldIncrementPC()) {
            incrementPC();
        }
          
      }
     
      //Increment the program counter
      public void incrementPC() {
        int programControlValueInDecimal = util.convertHexadecimalToDecimal(
                gpIr.getPc())+1;
        String programControlValueInHexadecimal = util.convertDecimalToHexadecimal(
                util.convertIntegerToString(programControlValueInDecimal));
        String pcValueInDecimal = util.convertHexadecimalToBinary(programControlValueInHexadecimal);
        
        gpIr.setPc(pcValueInDecimal);
    }
      
      // If the register value is equal to zero. Then, set PC as EA.
      // Otherwise, increment PC by 1.
      private void jumpIfZero() {
          String dataFromGPRByOpcodeInBinary = getDataFromGPRByOpcode();
          //TODO: Need to know the default value of register. Is it 0 or empty?
          if (dataFromGPRByOpcodeInBinary.equals("") || dataFromGPRByOpcodeInBinary.equals("0000") || dataFromGPRByOpcodeInBinary.equals("000000"))
          {
        	  gpIr.setPc(("0000000000000000" + gpIr.getOpcode().getEffectiveAddress()).substring(gpIr.getOpcode().getEffectiveAddress().length()));
        	  gpIr.getOpcode().setShouldIncrementPC(false);
          }
      }
      
      
       // If the register value is not equal to zero. Then, set PC as EA.
      //Otherwise, increment PC by 1.
       
      private void jumpIfNotEqual() {
          String dataFromGPRByOpcodeInBinary = getDataFromGPRByOpcode();
          if (!dataFromGPRByOpcodeInBinary.equals("") && !dataFromGPRByOpcodeInBinary.equals("000000")) {
        	  gpIr.setPc(gpIr.getOpcode().getEffectiveAddress());
        	  gpIr.getOpcode().setShouldIncrementPC(false);
          }
      }
      
     
       // If the register is equal to 1. Then, set PC as EA.
      // Otherwise, increment PC by 1.
      private void jumpIfConditionCode() {
          String gprRegisterSelect = gpIr.getOpcode().getGeneralPurposeRegister();
          int[] cc = gpIr.getConditionCode();
          if ((cc[3] == 1)) {
        	  gpIr.setPc(gpIr.getOpcode().getEffectiveAddress());
        	  gpIr.getOpcode().setShouldIncrementPC(false);
          }
      }
      
       // Sets EA to PC as default
      private void unconditionalJumpToAddress() {
    	  gpIr.setPc(gpIr.getOpcode().getEffectiveAddress());
          gpIr.getOpcode().setShouldIncrementPC(false);
      }
      
      // sets incremented by 1 value of PC to gpr3 and EA to PC.
      private void jumpAndSaveReturnAddress() {
          int programControlValueInDecimal = util.convertBinaryToDecimal(
                  gpIr.getPc())+1;
          String programControlValueInBinary = util.convertDecimalToBinary(
                  util.convertIntegerToString(programControlValueInDecimal));
          gpIr.setRegisterThree(programControlValueInBinary);
          gpIr.setPc(gpIr.getOpcode().getEffectiveAddress());
          gpIr.getOpcode().setShouldIncrementPC(false);
      }

      
       // sets gpr3 value to pc and immed to R0
       
      private void returnFromSubRoutine() {
          String dataFromGPRByOpcodeInBinary = gpIr.getRegisterThree();
          gpIr.setPc(dataFromGPRByOpcodeInBinary);
          gpIr.getOpcode().setShouldIncrementPC(false);
          gpIr.setRegisterZero("00000000000" + gpIr.getOpcode().getAddress());
      }
      
      
       // Subtract the gpr by 1. If the value is greater than 0, sets EA to PC
      // Otherwise, increment PC by 1.
      private void subtractOneAndBranch() {
          String dataFromGPRByOpcodeInBinary = getDataFromGPRByOpcode();
          int subValue = util.convertBinaryToDecimal(dataFromGPRByOpcodeInBinary) - 1;
          String subValueInString = util.convertIntegerToString(subValue);
          loadGPRFromOpcode(util.convertDecimalToBinary(subValueInString));

          String newDataFromGPRByOpcodeInBinary = getDataFromGPRByOpcode();
          int newDataFromGPRByOpcodeInDecimal = util.convertBinaryToDecimal(newDataFromGPRByOpcodeInBinary);
          if (!(newDataFromGPRByOpcodeInDecimal > 0)) {
              return;
          }
          gpIr.setPc(gpIr.getOpcode().getEffectiveAddress());
          gpIr.getOpcode().setShouldIncrementPC(false);
      }

         //If the value of GPR is greater than or equal to zero, sets ea to pc.
        // Otherwise, increment PC by 1.
      private void jumpGreaterThanOrEqualTo() {
          String dataFromGPRByOpcodeInBinary = getDataFromGPRByOpcode();
          int dataFromGPRByOpcodeInDecimal = util.convertBinaryToDecimal(
        		  dataFromGPRByOpcodeInBinary);

          if (dataFromGPRByOpcodeInDecimal < 0) {
              return;
          }
          gpIr.setPc(gpIr.getOpcode().getEffectiveAddress());
          gpIr.getOpcode().setShouldIncrementPC(false);
      }

      
        // Sums the register value to EA. Stores the result to the register.
      private void addMemoryToRegister() {
          int dataFromGPRByOpcodeInDecimal = util.convertBinaryToDecimal(
                  getDataFromGPRByOpcode());
          int effectiveAddressValue = util.convertBinaryToDecimal(
        		  gpIr.getOpcode().getEffectiveAddress());
          int sumValue = dataFromGPRByOpcodeInDecimal + effectiveAddressValue;
          loadGPRFromOpcode(util.convertDecimalToBinary(
                  util.convertIntegerToString(sumValue)));
      }
      
      // Subtracts the register value to EA. Stores the result to the register.
      private void subtractMemoryToRegister() {
          //TODO: Handle subtraction 0-1
          int dataFromGPRByOpcodeInDecimal = util.convertBinaryToDecimal(
                  getDataFromGPRByOpcode());
          int effectiveAddressValue = util.convertBinaryToDecimal(
        		  gpIr.getOpcode().getEffectiveAddress());
          int subValue = 0;
          if (dataFromGPRByOpcodeInDecimal > effectiveAddressValue) {
        	  subValue = dataFromGPRByOpcodeInDecimal - effectiveAddressValue;
          } else {
        	  subValue = effectiveAddressValue - dataFromGPRByOpcodeInDecimal;
          }
          loadGPRFromOpcode(util.convertDecimalToBinary(
                  util.convertIntegerToString(subValue)));
      }

      // add the immediate and gpr0.
       private void addImmediateToRegister() {
          int dataFromGPRByOpcodeInDecimal = util.convertBinaryToDecimal(
                  getDataFromGPRByOpcode());
          int immediate = util.convertBinaryToDecimal(
        		  gpIr.getOpcode().getEffectiveAddress());

          int sumValue = dataFromGPRByOpcodeInDecimal + immediate;
          loadGPRFromOpcode(util.convertDecimalToBinary(
                  util.convertIntegerToString(sumValue)));
      }

      // subtract the immediate and gpr0.
      
      private void subtractImmediateToRegister() {
          int dataFromGPRByOpcodeInDecimal = util.convertBinaryToDecimal(
                  getDataFromGPRByOpcode());
          int immediate = util.convertBinaryToDecimal(
        		  gpIr.getOpcode().getEffectiveAddress());

          int subtractedValue = dataFromGPRByOpcodeInDecimal - immediate;
          loadGPRFromOpcode(util.convertDecimalToBinary(
                  util.convertIntegerToString(subtractedValue)));
      }
      // Perform multiplication operation between two registers
      private void multiplyRegisterToRegister() {
          int dataFromGPRByOpcodeInDecimal = util.convertBinaryToDecimal(
        		  getDataGPRForMultiplyAndDivision());
          int dataFromIXRByOpcode = util.convertBinaryToDecimal(
        		  getDataIXRForMultiplyAndDivision());
          int[] cc = gpIr.getConditionCode();
          int multipliedValue = dataFromGPRByOpcodeInDecimal * dataFromIXRByOpcode;
          if (multipliedValue > 4095) {
              multipliedValue = multipliedValue % 4095;
              cc[0] = 1; // This bit indicates overflow is set to one
              gpIr.setConditionCode(cc);
          } else {
              cc[0] = 0; // This bit indicates overflow
              gpIr.setConditionCode(cc);
          }

          String multiplyValueInBinary = util.convertDecimalToBinary(
                  util.convertIntegerToString(multipliedValue));

          String gprValue = multiplyValueInBinary.substring(0,8);  // save in register 0 or 2
          String ixrValue = multiplyValueInBinary.substring(8,16); // save in register 1 or 3

          loadGPRFromOpcode(gprValue);
          loadIXRFromOpcode(ixrValue);
      }

      // Getting data from GPR for multiplication and division operations
       // @return Data from the GPR specific register
       
      public String getDataGPRForMultiplyAndDivision() {
          String gprRegisterSelect = gpIr.getOpcode().getGeneralPurposeRegister();
          String result = "";
          if (gprRegisterSelect.equals("00")) {
              result = gpIr.getRegisterZero();
          }
          if (gprRegisterSelect.equals("10")) {
              result = gpIr.getRegisterTwo();
          }
          return result;
      }
      
      //Getting data from IXR for multiplication and division operations
       // return Data from the IXR specific register
      
      public String getDataIXRForMultiplyAndDivision() {
          String ixrRegisterSelect = gpIr.getOpcode().getIndexRegister();
          String result = "";
          if (ixrRegisterSelect.equals("10")) {
              result = gpIr.getIndexRegisterTwo();
          }
          return result;
      }
      
      //loading index register from opcode
     //  data value to be load
     private void loadIXRFromOpcode(String data) {
          String ixrRegisterSelect = gpIr.getOpcode().getIndexRegister();
          if (ixrRegisterSelect.equals("01")) {
        	  gpIr.setIndexRegisterOne(data);
              return;
          }
          if (ixrRegisterSelect.equals("10")) {
        	  gpIr.setIndexRegisterTwo(data);
              return;
          }
          gpIr.setIndexRegisterThree(data);
      }

     // Perform division operation between two registers
     private void divisionRegisterToRegister() 
     {
         int dividend = util.convertBinaryToDecimal(getDataGPRForMultiplyAndDivision());
         int divisor = util.convertBinaryToDecimal(getDataIXRForMultiplyAndDivision());
         int[] cc = gpIr.getConditionCode();
         if (divisor == 0) {
             cc[3] = 1;
             gpIr.setConditionCode(cc);
         } else {
             cc[3] = 0;
             gpIr.setConditionCode(cc);
         }
         int quotient = dividend / divisor;
         int remainder = dividend % divisor;

         loadGPRFromOpcode(util.convertDecimalToBinary(
                 util.convertIntegerToString(quotient)));
         
         loadIXRFromOpcode(util.convertDecimalToBinary(
                util.convertIntegerToString(remainder)));
     }
     
     // Perform equality test between two registers
     private void testEqualityRegisterToRegister(){
         int dataFromGPRByOpcodeInDecimal = util.convertBinaryToDecimal(
        		 getDataGPRForMultiplyAndDivision());
         int dataFromIXRByOpcode = util.convertBinaryToDecimal(
        		 getDataIXRForMultiplyAndDivision());
         int[] cc = gpIr.getConditionCode();
         if(dataFromGPRByOpcodeInDecimal==dataFromIXRByOpcode){
             cc[3]=1;
         }
         cc[3]=0;
     }

    // Performing register to register logical AND operation.
    private void logicalAndRegisterToRegister(){
         int dataFromGPRByOpcodeInDecimal = util.convertBinaryToDecimal(
        		 getDataFromGPRByOpcode());
         int dataFromIXRByOpcode = util.convertBinaryToDecimal(
        		 getDataFromGPRByOpcode());
         int rx = dataFromGPRByOpcodeInDecimal & dataFromIXRByOpcode;
         
         loadGPRFromOpcode(util.convertDecimalToBinary(
                 util.convertIntegerToString(rx)));
     }
    
    // Perform logical OR operation for gpr0 and ixr1 registers and store the result on gpr0.
    private void logicalOrRegisterAndRegister() {
        int dataFromGPRByOpcodeInDecimal = util.convertBinaryToDecimal(
                getDataFromGPRByOpcode());
        int dataFromIXRByOpcodeInDecimal = util.convertBinaryToDecimal(
                getDataFromIXRByOpcode());

        int orValue = dataFromGPRByOpcodeInDecimal | dataFromIXRByOpcodeInDecimal;
        loadGPRFromOpcode(util.convertDecimalToBinary(
                util.convertIntegerToString(orValue)));
    }
    
    // Perform logical NOT operation for gpr0 register and store the result on gpr0.
    private void logicalNotOfRegister() {
        int dataFromGPRByOpcodeInDecimal = util.convertBinaryToDecimal(
                getDataFromGPRByOpcode());

        int notValue = ~dataFromGPRByOpcodeInDecimal;
        loadGPRFromOpcode(util.convertDecimalToBinary(
                util.convertIntegerToString(notValue)));
    }
    

   // Shift register by count
    private void shiftRegisterByCount() {
        int dataFromGPRByOpcodeInDecimal = util.convertBinaryToDecimal(
                getDataFromGPRByOpcode());
        String ixrRegisterSelect = gpIr.getOpcode().getIndexRegister();
        int AL = util.convertStringToInteger(ixrRegisterSelect.substring(0,1));
        int LR = util.convertStringToInteger(ixrRegisterSelect.substring(1,2));
        int count = util.convertBinaryToDecimal(gpIr.getOpcode().getEffectiveAddress());
        int Shift;
        if (AL==1 && LR==1)
        {
            Shift = dataFromGPRByOpcodeInDecimal<<count;
        }
        else if (AL==1 && LR==0)
        {
            Shift = dataFromGPRByOpcodeInDecimal>>>count;
        }
        else if (AL==0 && LR==1)
        {
            Shift = dataFromGPRByOpcodeInDecimal<<count;
        }
        else
        {
            Shift = dataFromGPRByOpcodeInDecimal>>count;
        }
        loadGPRFromOpcode(util.convertDecimalToBinary(
                util.convertIntegerToString(Shift)));
    }
    
    // Rotate register by count
    private void rotateRegisterByCount() {
        int dataFromGPRByOpcodeInDecimal = util.convertBinaryToDecimal(
                getDataFromGPRByOpcode());
        String ixrRegisterSelect = gpIr.getOpcode().getIndexRegister();
        int AL = util.convertStringToInteger(ixrRegisterSelect.substring(0,1));
        int LR = util.convertStringToInteger(ixrRegisterSelect.substring(1,2));
        int count = util.convertBinaryToDecimal(gpIr.getOpcode().getEffectiveAddress());
        int Rotate = 0;
        if (AL==1 && LR==1)
        {
            Rotate = (dataFromGPRByOpcodeInDecimal << count) | (dataFromGPRByOpcodeInDecimal >> (32 - count));
        }
        else if (AL==1 && LR==0)
        {
            Rotate = (dataFromGPRByOpcodeInDecimal >> count) | (dataFromGPRByOpcodeInDecimal << (32 - count));
        }
        loadGPRFromOpcode(util.convertDecimalToBinary(
        		util.convertIntegerToString(Rotate)));
    }
    
   private void setcache()
    {
	   String tagValue = gpIr.getPc();
       String dataValue = gpIr.getMbr();

       int index = cache.size();
       Map<String, String> map = new HashMap<>();
       map.put(tagValue, dataValue);
       cache.put(index, map);
    }

 }

     

