/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.simulator_csci6461;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author primasanghvi
 */
public class BackendSimulator extends Simulator_CSCI6461{
    
    HashMap<String,String> fileContentMap;
   
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
		
		 fileContentMap = new HashMap<>();
		
			while(fileReader.hasNext())
			{
				String address = fileReader.next();
				String value = fileReader.next();
				
				fileContentMap.put(address, value);
			}
	} catch (FileNotFoundException e) {
		
		e.printStackTrace();
                
	}	
	
}
     public void convertToBinary()
     {
         System.out.println("Hii");
     }
     
}
