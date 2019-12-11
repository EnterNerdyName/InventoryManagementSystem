package InventoryManagementSystem.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author JDSlo
 */

public class Inventory {

    // Observable list for parts in inventory
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    // Observable list for products in inventory
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public Inventory() {
        allParts = FXCollections.observableArrayList();
        allProducts = FXCollections.observableArrayList();
    }

    //Add a Part
    public static void addPart(Part _Part) {
        allParts.add(_Part);
    }

    //Add a Product
    public static void addProduct(Product _Product) {
        allProducts.add(_Product);
    }

    //Find a Part
    public static Part lookupPart(int partID) {
        for (Part p : allParts) {
            if (p.getId() == partID) {
                return p;
            }
        }
        return null;
    }

    //Get Observable list of parts
    public static ObservableList<Part> getParts() {
        return allParts;
    }

    //Get Observable list of Products
    public static ObservableList<Product> getProducts() {
        return allProducts;
    }

    //Get Observable list of parts
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    //Get Observable list of Products
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    //Find a Product
    public static Product lookupProduct(int productID) {
        for (Product p : allProducts) {
            if (p.getId() == productID) {
                return p;
            }
        }
        return null;
    }

    //Delete a Part
    public static void deletePart(int _ID) {
        for (Part part : allParts) {
            if (part.getId() == _ID) {
                allParts.remove(part);
            }
        }
    }

    //Delete a Product
    public static void deleteProduct(int _ID) {
        for (Product product : allProducts) {
            if (product.getId() == _ID) {
                allProducts.remove(product);
            }
        }
    }

    //Check to see if Product has an associated part to see if it can be safely deleted.
    public static boolean canDeleteProduct(Product product) {
        return product.getAssociatedPartsCount() == 0;
    }

    //Modify a Part
    public static void updatePart(Part updatedPart) {
        allParts.set(updatedPart.getId(), updatedPart);
    }

    //Modify a Product
    public static void updateProduct(Product updatedProduct) {
        allProducts.set((updatedProduct.getId() - 1), updatedProduct);
    }

    //Count number of Parts
    public static int getPartsCount() {
        return allParts.size();
    }

    //Count number of Products
    public static int getProductsCount() {
        return allProducts.size();
    }
}
/*
F.  Using the attached “UML Class Diagram”, create appropriate classes and instance variables with the following criteria:
•  five classes with the 16 associated instance variables
•  variables are only accessible through getter methods
•  variables are only modifiable through setter methods
 */