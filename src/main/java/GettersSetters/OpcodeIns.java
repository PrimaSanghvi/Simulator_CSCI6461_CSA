/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package GettersSetters;

/**
 *
 * @author primasanghvi
 */
import UtilityPackage.Utilities;

public class OpcodeIns {
    
    private String operations;
    private String generalPurposeRegister;
    private String indexRegister;
    private String indirectMode;
    private String address;
    private String effectiveAddress;
    private Boolean shouldIncrementPC;
    
Utilities util = new Utilities();

    OpcodeIns() {
        operations = "";
        generalPurposeRegister = "";
        indexRegister = "";
        indirectMode = "";
        address = "";
        shouldIncrementPC = true;
    }
    public String getOperations() {
        return operations;
    }

    public void setOperations(String operations) {
        this.operations = operations;
    }

    public String getGeneralPurposeRegister() {
        return generalPurposeRegister;
    }

    public void setGeneralPurposeRegister(String generalPurposeRegister) {
        this.generalPurposeRegister = generalPurposeRegister;
    }

    public String getIndexRegister() {
        return indexRegister;
    }

    public void setIndexRegister(String indexRegister) {
        this.indexRegister = indexRegister;
    }

    public String getIndirectMode() {
        return indirectMode;
    }

    public void setIndirectMode(String indirectMode) {
        this.indirectMode = indirectMode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEffectiveAddress() {
        return effectiveAddress;
    }

    public void setEffectiveAddress(String effectiveAddress) {
        this.effectiveAddress = util.convertHexadecimalNumberToFourDigits(effectiveAddress);
    }

    public Boolean getShouldIncrementPC() {
        return shouldIncrementPC;
    }

    public void setShouldIncrementPC(Boolean shouldIncrementPC) {
        this.shouldIncrementPC = shouldIncrementPC;
    }

    @Override
    public String toString() {
        return "Opcode{" +
                "operations='" + operations + '\'' +
                ", generalPurposeRegister='" + generalPurposeRegister + '\'' +
                ", indexRegister='" + indexRegister + '\'' +
                ", indirectMode='" + indirectMode + '\'' +
                ", address='" + address + '\'' +
                ", effectiveAddress='" + effectiveAddress + '\'' +
                ", shouldIncrementPC=" + shouldIncrementPC +
                '}';
    }
     

}
