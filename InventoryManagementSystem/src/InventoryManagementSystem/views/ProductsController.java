package InventoryManagementSystem.views;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import InventoryManagementSystem.models.Inventory;
import InventoryManagementSystem.models.Part;
import static InventoryManagementSystem.views.MainController.getModifiedProduct;
import InventoryManagementSystem.exceptions.ValidationException;
import InventoryManagementSystem.models.Product;

/**
 *
 * @author JDSlo
 */

public class ProductsController implements Initializable {

    //Product Page label
    @FXML
    private Label ProductsPageLabel;

    //Product ID field
    @FXML
    private TextField ProductsIDField;

    //Product Name field
    @FXML
    private TextField ProductsNameField;

    //Product Maximum Inventory field
    @FXML
    private TextField ProductsMaxField;

    //Product Minimum Inventory field
    @FXML
    private TextField ProductsMinField;

    //Product Current Inventory field
    @FXML
    private TextField ProductsInStockField;

    //Product Price field
    @FXML
    private TextField ProductsPriceField;

    //Product's Parts Search field
    @FXML
    private TextField ProductPartsSearchField;

    //Product's Parts table
    @FXML
    private TableView<Part> ProductAllPartsTable;

    //Product's Parts table ID
    @FXML
    private TableColumn<Part, Integer> ProductAllPartsIDCol;

    //Product's Parts table Name
    @FXML
    private TableColumn<Part, String> ProductAllPartsNameCol;

    //Product's Parts table Inventory
    @FXML
    private TableColumn<Part, Integer> ProductAllPartsInStockCol;

    //Product's Parts table Price
    @FXML
    private TableColumn<Part, Double> ProductAllPartsPriceCol;

    //Product's Current Parts table
    @FXML
    private TableView<Part> ProductCurrentPartsTable;

    //Product's Current Parts ID
    @FXML
    private TableColumn<Part, Integer> ProductCurrentPartsIDCol;

    //Product's Current Parts Name
    @FXML
    private TableColumn<Part, String> ProductCurrentPartsNameCol;

    //Product's Current Parts Inventory
    @FXML
    private TableColumn<Part, Integer> ProductCurrentPartsInStockCol;

    //Product's Current Parts Price
    @FXML
    private TableColumn<Part, Double> ProductCurrentPartsPriceCol;

    // Product's list of Parts associated with it
    private ObservableList<Part> productParts = FXCollections.observableArrayList();

    //Product currently under modification
    private final Product modifiedProduct;

    //Constructor
    public ProductsController() {
        this.modifiedProduct = getModifiedProduct();
    }

    //Add a Part to a Product
    @FXML
    void handleAddProductPart(ActionEvent event) {
        Part part = ProductAllPartsTable.getSelectionModel().getSelectedItem();
        productParts.add(part);
        populateCurrentPartsTable();
        populateAvailablePartsTable();
        //Inventory.updateProduct(modifiedProduct);
    }

    //Cancel Product Modification
    @FXML
    void handleCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Cancel Modification");
        alert.setHeaderText("Confirm cancellation");
        alert.setContentText("Are you sure you want to cancel update of product " + ProductsNameField.getText() + "?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            Parent loader = FXMLLoader.load(getClass().getResource("Main.fxml"));
            Scene scene = new Scene(loader);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
    }

    //Delete a Part from a Product
    @FXML
    void handleDeleteProductPart(ActionEvent event) throws IOException {
        if (productParts.size() >= 2) {
            Part part = ProductCurrentPartsTable.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Delete Part");
            alert.setHeaderText("Confirm deletion");
            alert.setContentText("Are you sure you want to disassociate " + part.getName() + " ?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK) {
                productParts.remove(part);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Part Deletion Error!");
            alert.setHeaderText("Product requires one part!");
            alert.setContentText("This product must have at least one part.");
            alert.showAndWait();
        }
    }

    //Save Product Modification
    @FXML
    void handleProductSave(ActionEvent event) throws IOException {
        String productId = ProductsIDField.getText();

        String productName = ProductsNameField.getText();
        String productPrice = ProductsPriceField.getText();
        String productStock = ProductsInStockField.getText();
        String productMin = ProductsMinField.getText();
        String productMax = ProductsMaxField.getText();
        Product newProduct = new Product();

        // Verify New vs Modified Product
        if (modifiedProduct != null) {
            modifiedProduct.purgeAssociatedParts();
            modifiedProduct.setId(Integer.parseInt(ProductsIDField.getText()));
            modifiedProduct.setName(productName);
            modifiedProduct.setPrice(Double.parseDouble(productPrice));
            modifiedProduct.setStock(Integer.parseInt(productStock));
            modifiedProduct.setMin(Integer.parseInt(productMin));
            modifiedProduct.setMax(Integer.parseInt(productMax));
            modifiedProduct.setId(modifiedProduct.getId());
            for (Part p : productParts) {
                modifiedProduct.addAssociatedPart(p);
            }
            Inventory.updateProduct(modifiedProduct);
            try {
                modifiedProduct.isValid();
                Parent loader = FXMLLoader.load(getClass().getResource("Main.fxml"));
                Scene scene = new Scene(loader);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            } catch (ValidationException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ValidationError");
                alert.setHeaderText("Product not valid");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
            System.out.println("end of modified product");
        } else {

            productId = productId.substring(6);
            newProduct.setId(Integer.parseInt(productId));
            newProduct.setName(productName);
            newProduct.setPrice(Double.parseDouble(productPrice));
            newProduct.setStock(Integer.parseInt(productStock));
            newProduct.setMin(Integer.parseInt(productMin));
            newProduct.setMax(Integer.parseInt(productMax));
            for (Part p : productParts) {
                newProduct.addAssociatedPart(p);
            }
            Inventory.addProduct(newProduct);

            try {
                newProduct.isValid();
                Parent loader = FXMLLoader.load(getClass().getResource("Main.fxml"));
                Scene scene = new Scene(loader);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            } catch (ValidationException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ValidationError");
                alert.setHeaderText("Product not valid");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }

    //Search for Parts of a Product
    @FXML
    void handleSearchParts(ActionEvent event) throws IOException {
        String partsSearchIdString = ProductPartsSearchField.getText();
        Part searchedPart = Inventory.lookupPart(Integer.parseInt(partsSearchIdString));

        if (searchedPart != null) {
            ObservableList<Part> filteredPartsList = FXCollections.observableArrayList();
            filteredPartsList.add(searchedPart);
            ProductAllPartsTable.setItems(filteredPartsList);
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search Error");
            alert.setHeaderText("Part not found");
            alert.setContentText("The search term entered does not match any part!");
            alert.showAndWait();

        }
    }

    //Initialize
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (modifiedProduct == null) {
            ProductsPageLabel.setText("Add Product");
            int productAutoID = (Inventory.getProductsCount() + 1);
            ProductsIDField.setText("Auto: " + productAutoID);
            System.out.println("Here");
        } else {
            ProductsPageLabel.setText("Modify Product");

            ProductsIDField.setText(Integer.toString(modifiedProduct.getId()));
            ProductsNameField.setText(modifiedProduct.getName());
            ProductsInStockField.setText(Integer.toString(modifiedProduct.getStock()));
            ProductsPriceField.setText(Double.toString(modifiedProduct.getPrice()));
            ProductsMinField.setText(Integer.toString(modifiedProduct.getMin()));
            ProductsMaxField.setText(Integer.toString(modifiedProduct.getMax()));

            productParts = modifiedProduct.getAllAssociatedParts();
        }

        ProductAllPartsIDCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        ProductAllPartsNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        ProductAllPartsInStockCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getStock()).asObject());
        ProductAllPartsPriceCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());

        ProductCurrentPartsIDCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        ProductCurrentPartsNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        ProductCurrentPartsInStockCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getStock()).asObject());
        ProductCurrentPartsPriceCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());

        populateAvailablePartsTable();
        populateCurrentPartsTable();
    }

    //Populate the available parts table.
    public void populateAvailablePartsTable() {
        ProductAllPartsTable.setItems(Inventory.getParts());
    }

    //Populate the current parts table.
    public void populateCurrentPartsTable() {
        ProductCurrentPartsTable.setItems(productParts);
    }

}
 /*
D. An add product screen, showing the following controls:
•  buttons for “Save”, “Cancel”, “Add” part, and “Delete” part
•  text fields for ID, name, inventory level, price, and max and min values
•  labels for ID, name, inventory level, price, max and min values, and the application
•  a list for associated parts for this product
•  a “Search” button and a text field with an associated list for displaying the results of the search

E.  A modify product screen, with fields that populate with presaved data, showing the following controls:
•  buttons for “Save”, “Cancel”, “Add” part, and “Delete” part
•  text fields for ID, name, inventory level, price, and max and min values
•  labels for ID, name, inventory level, price, max and min values, and the application
•  a list for associated parts for this product
•  a “Search” button and a text field with associated list for displaying the results of the search

I.  Add the following functionalities to the product screens, using the methods provided in the attached “UML Class Diagram”:
1.  “Add Product” screen
•  enter name, inventory level, price, and max and min values
•  save the data and then redirect to the main screen
•  associate one or more parts with a product
•  remove or disassociate a part from a product
•  cancel or exit out of this screen and go back to the main screen
2.  “Modify Product” screen
•  modify or change data values
•  save modifications to the data and then redirect to the main screen
•  associate one or more parts with a product
•  remove or disassociate a part from a product
•  cancel or exit out of this screen and go back to the main screen
 */
