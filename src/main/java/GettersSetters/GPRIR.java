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
    
    //Getters and Setter for all registers
    
   public OpcodeIns opcode;
   private String Mar;
   private String Pc;
   private String Mbr;
   private String registerOne; 
   private String registerTwo;
   private String registerThree;
   private String registerZero;
   private String Ir; 
   private String IndexRegisterOne;
   private String IndexRegisterTwo;
   private String IndexRegisterThree;
   private int haltIns;
   private int[] conditionCode;
   
   public GPRIR()
   {
    opcode = new OpcodeIns();  
    Mar = "";
    Pc = "";
    Mbr = "";
    registerOne = "";
    registerTwo = "";
    registerThree = "";
    registerZero = "";
    Ir = "";
    IndexRegisterOne = "";
    IndexRegisterTwo = "";
    IndexRegisterThree = "";
    haltIns = 0;
   }
   
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
    public String getRegisterOne() {
        
        return registerOne;
    }

    public void setRegisterOne(String registerOne) {

        this.registerOne = registerOne;
    }

    public String getRegisterTwo() {
        return registerTwo;
    }

    public void setRegisterTwo(String registerTwo) {

        this.registerTwo = registerTwo;
    }

    public String getRegisterThree() {
        return registerThree;
    }

    public void setRegisterThree(String registerThree) {

        this.registerThree = registerThree;
    }
    
    public void setRegisterZero(String registerZero)
    {
        this.registerZero = registerZero;
    }
    
    public String getRegisterZero()
    {
        return registerZero;
    }
    public void setIr(String Ir)
    {
        this.Ir = Ir;
        
    }
    public String getIr()
    {
        return Ir;
    }
    
       public void setIndexRegisterOne(String registerOne)
    {
        this.IndexRegisterOne = registerOne;
    }
    
    public String getIndexRegisterOne()
    {
        return IndexRegisterOne;
    }
       public void setIndexRegisterTwo(String registerTwo)
    {
        this.IndexRegisterTwo = registerTwo;
    }
    
    public String getIndexRegisterTwo()
    {
        return IndexRegisterTwo;
    }
        public void setIndexRegisterThree(String registerThree)
    {
        this.IndexRegisterThree = registerThree;
    }
    
    public String getIndexRegisterThree()
    {
        return IndexRegisterThree;
    }
    public void setHaltIns(Integer value)
    {
        this.haltIns = value;
    }
    
    public Integer getHaltIns()
    {
        return haltIns;
    }
    public int[] getConditionCode() {
        return conditionCode;
    }

    public void setConditionCode(int[] ConditionCode) {
    	conditionCode = ConditionCode;
    }
}

