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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import InventoryManagementSystem.models.Inventory;
import static InventoryManagementSystem.models.Inventory.getParts;
import static InventoryManagementSystem.models.Inventory.getProducts;
import static InventoryManagementSystem.models.Inventory.deletePart;
import static InventoryManagementSystem.models.Inventory.deleteProduct;
import InventoryManagementSystem.models.Part;
import InventoryManagementSystem.models.Product;

/**
 *
 * @author JDSlo
 */

public class MainController implements Initializable {

    //Entire Parts table
    @FXML
    private TableView<Part> MainPartsTable;

    //Parts table ID column
    @FXML
    private TableColumn<Part, Integer> MainPartIDCol;

    //Parts table Name column
    @FXML
    private TableColumn<Part, String> MainPartNameCol;

    //Parts table Inventory column
    @FXML
    private TableColumn<Part, Integer> MainPartInStockCol;

    //Parts table Price column
    @FXML
    private TableColumn<Part, Double> MainPartPriceCol;

    //Part Search field
    @FXML
    private TextField MainPartsSearchField;

    //Entire Products table
    @FXML
    private TableView<Product> MainProductsTable;

    //Products table ID column
    @FXML
    private TableColumn<Product, Integer> MainProductIDCol;

    //Products table Name column
    @FXML
    private TableColumn<Product, String> MainProductNameCol;

    //Products table Inventory column
    @FXML
    private TableColumn<Product, Integer> MainProductInStockCol;

    //Product table Price column
    @FXML
    private TableColumn<Product, Double> MainProductPriceCol;

    //Product Search field
    @FXML
    private TextField MainProductsSearchField;

    //The current modified Part
    private static Part modifiedPart;

    //The current modified Product
    private static Product modifiedProduct;

    //Constructor
    public MainController() {
    }

    //Get Modified Part
    public static Part getModifiedPart() {
        return modifiedPart;
    }

    //Set Part as Modified
    public void setModifiedPart(Part modifyPart) {
        MainController.modifiedPart = modifyPart;
    }

    //Get Modified Product
    public static Product getModifiedProduct() {
        return modifiedProduct;
    }

    //Set Product as Modified
    public void setModifiedProduct(Product modifiedProduct) {
        MainController.modifiedProduct = modifiedProduct;
    }

    //Exit the Application
    @FXML
    void handleExit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirm exit!");
        alert.setContentText("Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    //Add a Part
    @FXML
    void handleAddPart(ActionEvent event) throws IOException {
        showPartsScreen(event);
    }

    //Add a Product
    @FXML
    void handleAddProduct(ActionEvent event) throws IOException {
        showProductScreen(event);
    }

    //Delete a Part
    @FXML
    void handleDeletePart(ActionEvent event) throws IOException {
        Part part = MainPartsTable.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Part Delete");
        alert.setHeaderText("Confirm deletion?");
        alert.setContentText("Are you sure you want to delete " + part.getName() + "?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            deletePart(part.getId());
            populatePartsTable();
        }
    }

    //Delete a Product
    @FXML
    void handleDeleteProduct(ActionEvent event) throws IOException {
        Product product = MainProductsTable.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Product Delete");
        alert.setHeaderText("Confirm deletion?");
        alert.setContentText("Are you sure you want to delete " + product.getName() + "?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            deleteProduct(product.getId());
            populatePartsTable();

        }
    }

    //Modify a Part
    @FXML
    void handleModifyPart(ActionEvent event) throws IOException {
        modifiedPart = MainPartsTable.getSelectionModel().getSelectedItem();
        setModifiedProduct(modifiedProduct);

        showPartsScreen(event);
    }

    //Modify a Product
    @FXML
    void handleModifyProduct(ActionEvent event) throws IOException {
        modifiedProduct = MainProductsTable.getSelectionModel().getSelectedItem();
        setModifiedProduct(modifiedProduct);

        showProductScreen(event);
    }

    //Search for a Part
    @FXML
    void handleSearchPart(ActionEvent event) throws IOException {
        String partsSearchIdString = MainPartsSearchField.getText();
        Part searchedPart = Inventory.lookupPart(Integer.parseInt(partsSearchIdString));

        if (searchedPart != null) {
            ObservableList<Part> filteredPartsList = FXCollections.observableArrayList();
            filteredPartsList.add(searchedPart);
            MainPartsTable.setItems(filteredPartsList);
        } else {
            populatePartsTable();
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Search Error");
            alert.setHeaderText("Part not found");
            alert.setContentText("The search term entered does not match any part!");
            alert.showAndWait();
        }
    }

    //Search for a Product
    @FXML
    void handleSearchProduct(ActionEvent event) throws IOException {
        String productSearchIdString = MainProductsSearchField.getText();
        Product searchedProduct = Inventory.lookupProduct(Integer.parseInt(productSearchIdString));

        if (searchedProduct != null) {
            ObservableList<Product> filteredProductList = FXCollections.observableArrayList();
            filteredProductList.add(searchedProduct);
            MainProductsTable.setItems(filteredProductList);
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Search Error");
            alert.setHeaderText("Product not found");
            alert.setContentText("The search term entered does not match any current product in the inventory!");
            alert.showAndWait();
        }
    }

    //Initialize Part and Product with null values
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setModifiedPart(null);
        setModifiedProduct(null);

        MainPartIDCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        MainPartNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        MainPartInStockCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getStock()).asObject());
        MainPartPriceCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());

        MainProductIDCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        MainProductNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        MainProductInStockCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getStock()).asObject());
        MainProductPriceCol.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());

        populatePartsTable();
        populateProductsTable();
    }

    //Populates the Parts table.
    public void populatePartsTable() {
        MainPartsTable.setItems(getParts());
    }

    //Populates the Product table.
    public void populateProductsTable() {
        MainProductsTable.setItems(getProducts());
    }

    //Sets the main app, also Populates the Parts and Products tables.
    public void setMainApp(ActionEvent event) {
        populatePartsTable();
        populateProductsTable();
    }

    //Display the Parts screen, used for both add and modify Parts functionality.
    public void showPartsScreen(ActionEvent event) throws IOException {
        Parent loader = FXMLLoader.load(getClass().getResource("Parts.fxml"));
        Scene scene = new Scene(loader);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    //Display the Products screen, used for both add and modify Products functionality.
    public void showProductScreen(ActionEvent event) throws IOException {
        Parent loader = FXMLLoader.load(getClass().getResource("Products.fxml"));
        Scene scene = new Scene(loader);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    //Display the Main screen, used for both add and modify Products functionality.
    public void showMainScreen(ActionEvent event) throws IOException {
        Parent loader = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene scene = new Scene(loader);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}

/*
A.  A main screen, showing the following controls:
•  buttons for “Add”, “Modify”, “Delete”, “Search” for parts and products, and “Exit”
•  lists for parts and products
•  text boxes for searching for parts and products
•  title labels for parts, products, and the application title 

G.  Add the following functionalities to the main screen, using the methods provided in the attached “UML Class Diagram”:
•  redirect the user to the “Add Part”, “Modify Part”, “Add Product”, or “Modify Product” screens
•  delete a selected part or product from the list
•  search for a part or product and display matching results
•  exit the main screen
 */