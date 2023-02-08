/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GettersSetters;

/**
 *
 * @author primasanghvi
 */
public class GPRIR {
    
    OpcodeIns opcode = new OpcodeIns();
    String Mar;
    String Pc;
    String Mbr;
     public void setOpcode(OpcodeIns opcode) {
        this.opcode = opcode;
    }

     public OpcodeIns getOpcode() {
        return opcode;
    }
    public void setMar(String Mar)
    {
        this.Mar = Mar;
    }
    
    public String getMar()
    {
        return this.Mar;
    }
    
    public void setPc(String Pc)
    {
        this.Pc = Pc;
    }
    public String getPc()
    {      
        return this.Pc;
    }
    public void setMbr(String Mbr)
    {
        this.Mbr = Mbr;
    }
    
    public String getMbr()
    {
        return this.Mbr;
    }
}
