package InventoryManagementSystem.models;

/**
 *
 * @author JDSlo
 */

public class InHouse extends Part {

    /* An InHoused Part with params
* @param machineId - Machine ID - Integer
     */
    private int machineId;

    public InHouse() {
    }

    ;
public InHouse(int _id, String _name, double _price, int _stock, int _min, int _max, int _machineId) {
        super(_id, _name, _price, _stock, _min, _max);
        machineId = _machineId;
    }

    ;

public int getMachineId() {
        return machineId;
    }

    public void setMachineId(int _machineId) {
        machineId = _machineId;
    }

}
/*
F.  Using the attached “UML Class Diagram”, create appropriate classes and instance variables with the following criteria:
•  five classes with the 16 associated instance variables
•  variables are only accessible through getter methods
•  variables are only modifiable through setter methods
 */