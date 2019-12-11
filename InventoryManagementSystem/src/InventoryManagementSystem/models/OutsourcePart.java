package InventoryManagementSystem.models;

/**
 *
 * @author JDSlo
 */

public class OutsourcePart extends Part {

    /**
     * An Outsourced Part with params
     *
     * @param companyName - Company Name - String
     */
    private String companyName;

    public OutsourcePart() {
    }

    ;
public OutsourcePart(int _id, String _name, double _price, int _stock, int _min, int _max, String _companyName) {
        super(_id, _name, _price, _stock, _min, _max);
        companyName = _companyName;
    }

    ;

public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String _companyName) {
        companyName = _companyName;
    }

}
/*
F.  Using the attached “UML Class Diagram”, create appropriate classes and instance variables with the following criteria:
•  five classes with the 16 associated instance variables
•  variables are only accessible through getter methods
•  variables are only modifiable through setter methods
 */