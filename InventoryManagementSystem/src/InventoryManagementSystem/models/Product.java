package InventoryManagementSystem.models;

import InventoryManagementSystem.exceptions.ValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import InventoryManagementSystem.models.Part;

/**
 *
 * @author JDSlo
 */

public class Product {

    /**
     * A new product with params
     *
     * @param id - Product ID - Integer
     * @param name - Product Name - String
     * @param price - Product Price - Double
     * @param stock - Current stock level of Product - Integer
     * @param min - Minimum allowable stock level of Product - Integer
     * @param max - Maximum allowable stock level of Product - Integer
     */
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product() {
    }

    ; //Default constructor
public Product(int _id, String _name, double _price, int _stock, int _min, int _max) {
        id = _id;
        name = _name;
        price = _price;
        stock = _stock;
        min = _min;
        max = _max;
    }

    ; //constructor

//Get Product ID
public int getId() {
        return id;
    }

    ;

//Get Product Name
public String getName() {
        return name;
    }

    ;

//Get Product Price
public double getPrice() {
        return price;
    }

    ;

//Get Product Stock Level
public int getStock() {
        return stock;
    }

    ;

//Get Allowable Product Minimum Inventory
public int getMin() {
        return min;
    }

    ;

//Get Allowable Product Maximum Inventory
public int getMax() {
        return max;
    }

    ;

//Set Product ID
public void setId(int _id) {
        id = _id;
    }

    ;

//Set Product Name
public void setName(String _name) {
        name = _name;
    }

    ;

//Set Product Price
public void setPrice(double _price) {
        price = _price;
    }

    ;

//Set Product Stock Level
public void setStock(int _stock) {
        stock = _stock;
    }

    ;

//Set Allowable Product Minimum Inventory
public void setMin(int _min) {
        min = _min;
    }

    ;

//Set Allowable Product Maximum Inventory
public void setMax(int _max) {
        max = _max;
    }

    ;
public void setProductParts(ObservableList<Part> parts) {
        this.associatedParts = parts;
    }

    public ObservableList getProductParts() {
        return associatedParts;
    }
//Create observable list of associated parts
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

//Add to observable list of associated parts
    public void addAssociatedPart(Part _associatedPart) {
        associatedParts.add(_associatedPart);
    }

    ; 

//Remove from observable list of associated parts
public void deleteAssociatedPart(int partID) {
        for (Part p : associatedParts) {
            if (p.getId() == partID) {
                associatedParts.remove(p);
            }
        }
    }

    ; 

//Get count of observable list of associated parts
public int getAssociatedPartsCount() {
        return associatedParts.size();
    }

//Get observable list of associated parts
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

    public void purgeAssociatedParts() {
        associatedParts = FXCollections.observableArrayList();
    }

    public Part lookupAssociatedPart(int _partId) {
        for (Part p : associatedParts) {
            if (p.getId() == _partId) {
                return p;
            }
        }

        return null;
    }

//Product Data Validation
    public boolean isValid() throws ValidationException {
        double totalPartsPrice = 0.00;

        //Price Summary of a Product's Associated Parts
        for (Part p : getAllAssociatedParts()) { //Used to verify for Exception Controls Set 2
            totalPartsPrice += p.getPrice();
        }

        //Prohibit Unnamed Products - "Rule of Acquistion #239: Never be afraid to mislabel a product."
        if (getName().equals("")) { //Exception Controls Set 2
            throw new ValidationException("The name field cannot be null.");
        }

        //Ensure price is greater than zero - "Rule of Acquistion #37: If it's free, take it and worry about hidden costs later."
        if (getPrice() < 0) { //Exception Controls Set 2
            throw new ValidationException("The price must be greater than zero dollars.");
        }

        //Ensure Profitable Margin - "Rule of Acquistion #89: Ask not what your profits can do for you, ask what you can do for your profits."
        if (totalPartsPrice > getPrice()) { //Exception Controls Set 2
            throw new ValidationException("The product price must be greater than the total cost of it's associated parts.");
        }

        //Prohibit Out of Stock Inventory level - "Rule of Acquistion #109: Dignity and an empty sack is worth the sack."
        if (getStock() < 0) { //Exception Controls Set 1
            throw new ValidationException("The current inventory must be greater than zero.");
        }

        //Ensure positive inventory level - "Rule of Acquistion #242: More is good... all is better."
        if (getMin() < 0) { //Exception Controls Set 1
            throw new ValidationException("The minimum inventory must be greater than zero units.");
        }

        //Ensure maximum inventory is greater than minimum inventory - "Rule of Acquistion #125: You can't make a deal if you're dead."
        if (getMin() > getMax()) { //Exception Controls Set 1
            throw new ValidationException("The minimum inventory must be less than the maximum number of units.");
        }
        // a product must have at least one part
        if (getAssociatedPartsCount() < 1) {
            throw new ValidationException("The product must contain at least 1 part. Currently at " + getAssociatedPartsCount() + "units");
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