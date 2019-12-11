package InventoryManagementSystem.models;

import InventoryManagementSystem.exceptions.ValidationException;

/**
 *
 * @author JDSlo
 */
public abstract class Part {

    /**
     * A new product with params
     *
     * @param id - Product ID - Integer
     * @param name - Product Name - String
     * @param price - Product Price - Double
     * @param stock - Current stock level of Product - Integer
     * @param min - Minimum allowable stock level of product - Integer
     * @param max - Maximum allowable stock level of product - Integer
     */
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
//Default constructor
Part() {};
//constructor.
Part(int _id, String _name, double _price, int _stock, int _min, int _max) {
        id = _id;
        name = _name;
        price = _price;
        stock = _stock;
        min = _min;
        max = _max;
    }

    ; 

//Get Part ID
public int getId() {
        return id;
    }

    ;

//Get Part Name
public String getName() {
        return name;
    }

    ;

//Get Part Price
public double getPrice() {
        return price;
    }

    ;

//Get Part Stock Level
public int getStock() {
        return stock;
    }

    ;

//Get Allowable Part Minimum Inventory
public int getMin() {
        return min;
    }

    ;

//Get Allowable Part Maximum Inventory
public int getMax() {
        return max;
    }

    ;

//Set Part ID
public void setId(int _id) {
        id = _id;
    }

    ;

//Set Part Name
public void setName(String _name) {
        name = _name;
    }

    ;

//Set Part Price
public void setPrice(double _price) {
        price = _price;
    }

    ;

//Set Part Stock Level
public void setStock(int _stock) {
        stock = _stock;
    }

    ;

//Set Allowable Part Minimum Inventory
public void setMin(int _min) {
        min = _min;
    }

    ;

//Set Allowable Part Maximum Inventory
public void setMax(int _max) {
        max = _max;
    }

    ;

//Part Data Validation
public boolean isValid() throws ValidationException {

        //Prohibit Unnamed Products - "Rule of Acquistion #239: Never be afraid to mislabel a product."
        if (getName().equals("")) { //Exception Controls Set 2
            throw new ValidationException("The name field cannot be null.");
        }

        //Ensure price is greater than zero - "Rule of Acquistion #37: If it's free, take it and worry about hidden costs later."
        if (getPrice() < 0) { //Exception Controls Set 2
            throw new ValidationException("The price must be greater than zero dollars.");
        }

        //Ensure positive inventory level - "Rule of Acquistion #242: More is good... all is better."
        if (getStock() < 0) { //Exception Controls Set 1
            throw new ValidationException("The current inventory must be greater than zero.");
        }

        //Prohibit Out of Stock Inventory level - "Rule of Acquistion #109: Dignity and an empty sack is worth the sack."
        if (getMin() < 0) { //Exception Controls Set 1
            throw new ValidationException("The minimum inventory must be greater than zero units.");
        }

        //Ensure maximum inventory is greater than minimum inventory - "Rule of Acquistion #125: You can't make a deal if you're dead."
        if (getMin() > getMax()) { //Exception Controls Set 1
            throw new ValidationException("The minimum inventory must be less than the maximum number of units.");
        }

        //Ensure current inventory quantity falls between the defined Minimum and Maximum values - "Rule of Acquistion #126: Count it."
        if (getStock() < getMin() || getStock() > getMax()) { //Exception Controls Set 1
            throw new ValidationException("The current inventory must be between the minimum and maximum inventory threshold.");
        }

        return true;
    }
}

/*
F.  Using the attached “UML Class Diagram”, create appropriate classes and instance variables with the following criteria:
•  five classes with the 16 associated instance variables
•  variables are only accessible through getter methods
•  variables are only modifiable through setter methods
 */
