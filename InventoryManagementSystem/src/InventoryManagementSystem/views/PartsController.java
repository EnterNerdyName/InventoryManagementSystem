package InventoryManagementSystem.views;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import InventoryManagementSystem.models.InHouse;
import InventoryManagementSystem.models.Inventory;
import InventoryManagementSystem.models.OutsourcePart;
import InventoryManagementSystem.models.Part;
import static InventoryManagementSystem.views.MainController.getModifiedPart;
import InventoryManagementSystem.exceptions.ValidationException;

/**
 *
 * @author JDSlo
 */

public class PartsController implements Initializable {

    //Part ID field
    @FXML
    private TextField PartsIDField;

    //Part Name field
    @FXML
    private TextField PartsNameField;

    //Part Inventory field
    @FXML
    private TextField PartsInStockField;

    //Part Price field
    @FXML
    private TextField PartsPriceField;

    //Part Maximum Inventory field
    @FXML
    private TextField PartsMaxField;

    //Part Minimum Inventory field
    @FXML
    private TextField PartsMinField;

    //Part Manufacturer/MachineID label
    @FXML
    private Label PartsMfgLabel;

    //Part Manufacturer/MachineID field
    @FXML
    private TextField PartsMfgField;

    //Part page label
    @FXML
    private Label PartsPageLabel;

    //InHouse Part radio button
    @FXML
    private RadioButton PartsInHouseRadioButton;

    //Outsourced Part radio button
    @FXML
    private RadioButton PartsOutsourcedRadioButton;

    //Flag Part as InHouse vs. Outsourced
    private boolean isInHouse;

    //Part currently under modification 
    private final Part modifyPart;

    //Constructor
    public PartsController() {
        this.modifyPart = getModifiedPart();
    }

    //Sets Part as one produced InHouse
    @FXML
    void handleInHouse(ActionEvent event) {
        isInHouse = true;
        PartsMfgLabel.setText("Mach ID");
    }

    //Sets Part as one produced by an Outsourced entity
    @FXML
    void handleOutsource(ActionEvent event) {
        isInHouse = false;
        PartsMfgLabel.setText("Company Nm");
    }

    //Cancel Part Modification
    @FXML
    void handleCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Cancel Modification");
        alert.setHeaderText("Confirm cancellation");
        alert.setContentText("Are you sure you want to cancel update of part " + PartsNameField.getText() + "?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            Parent loader = FXMLLoader.load(getClass().getResource("Main.fxml"));
            Scene scene = new Scene(loader);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
    }

    //Save Part Modification
    @FXML
    void handleSave(ActionEvent event) throws IOException {
        String partName = PartsNameField.getText();
        String partInv = PartsInStockField.getText();
        String partPrice = PartsPriceField.getText();
        String partMin = PartsMinField.getText();
        String partMax = PartsMaxField.getText();
        String partDyn = PartsMfgField.getText();

        if ("".equals(partInv)) {
            partInv = "0";
        }

        if (isInHouse) {
            InHouse modifiedPart = new InHouse();
            modifiedPart.setName(partName);
            modifiedPart.setPrice(Double.parseDouble(partPrice));
            modifiedPart.setStock(Integer.parseInt(partInv));
            modifiedPart.setMin(Integer.parseInt(partMin));
            modifiedPart.setMax(Integer.parseInt(partMax));
            modifiedPart.setMachineId(Integer.parseInt(partDyn));

            try {
                modifiedPart.isValid();
                if (modifyPart == null) {
                    modifiedPart.setId(Inventory.getPartsCount());
                    Inventory.addPart(modifiedPart);
                } else {
                    int partID = modifyPart.getId();
                    modifiedPart.setId(partID);
                    Inventory.updatePart(modifiedPart);
                }

                Parent loader = FXMLLoader.load(getClass().getResource("Main.fxml"));
                Scene scene = new Scene(loader);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            } catch (ValidationException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ValidationError");
                alert.setHeaderText("Part not valid");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        } else {

            OutsourcePart modifiedPart = new OutsourcePart();
            modifiedPart.setName(partName);
            modifiedPart.setPrice(Double.parseDouble(partPrice));
            modifiedPart.setStock(Integer.parseInt(partInv));
            modifiedPart.setMin(Integer.parseInt(partMin));
            modifiedPart.setMax(Integer.parseInt(partMax));
            modifiedPart.setCompanyName(partDyn);

            try {
                modifiedPart.isValid();

                if (modifyPart == null) {
                    modifiedPart.setId(Inventory.getPartsCount());
                    Inventory.addPart(modifiedPart);
                } else {
                    int partID = modifyPart.getId();
                    modifiedPart.setId(partID);
                    Inventory.updatePart(modifiedPart);
                }

                Parent loader = FXMLLoader.load(getClass().getResource("Main.fxml"));
                Scene scene = new Scene(loader);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
            } catch (ValidationException e) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("ValidationError");
                alert.setHeaderText("Part not valid");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }

    //Initialize Part Data
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (modifyPart == null) {
            PartsPageLabel.setText("Add Part");
            int partAutoID = Inventory.getPartsCount();
            PartsIDField.setText("AUTO GEN: " + partAutoID);

            isInHouse = true;
            PartsMfgLabel.setText("Mach ID");
        } else {
            PartsPageLabel.setText("Modify Part");
            PartsIDField.setText(Integer.toString(modifyPart.getId()));
            PartsNameField.setText(modifyPart.getName());
            PartsInStockField.setText(Integer.toString(modifyPart.getStock()));
            PartsPriceField.setText(Double.toString(modifyPart.getPrice()));
            PartsMinField.setText(Integer.toString(modifyPart.getMin()));
            PartsMaxField.setText(Integer.toString(modifyPart.getMax()));

            if (modifyPart instanceof InHouse) {
                PartsMfgField.setText(Integer.toString(((InHouse) modifyPart).getMachineId()));

                PartsMfgLabel.setText("Mach ID");
                PartsInHouseRadioButton.setSelected(true);

            } else {
                PartsMfgField.setText(((OutsourcePart) modifyPart).getCompanyName());
                PartsMfgLabel.setText("Comp Nm");
                PartsOutsourcedRadioButton.setSelected(true);
            }
        }
    }
}
/*
B.  An add part screen, showing the following controls:
•  radio buttons for “In-House” and “Outsourced” parts
•  buttons for “Save” and “Cancel”
•  text fields for ID, name, inventory level, price, max and min values, and company name or machine ID
•  labels for ID, name, inventory level, price/cost, max and min values, the application title, and company name or machine ID

C.  A modify part screen, with fields that populate with presaved data, showing the following controls:
•  radio buttons for “In-House” and “Outsourced” parts
•  buttons for “Save” and “Cancel”
•  text fields for ID, name, inventory level, price, max and min values, and company name or machine ID
•  labels for ID, name, inventory level, price, max and min values, the application title, and company name or machine ID

H.  Add the following functionalities to the part screens, using the methods provided in the attached “UML Class Diagram”:
1.  “Add Part” screen
•  select “In-House” or “Outsourced”
•  enter name, inventory level, price, max and min values, and company name or machine ID
•  save the data and then redirect to the main screen
•  cancel or exit out of this screen and go back to the main screen
2.  “Modify Part” screen
•  select “In-House” or “Outsourced”
•  modify or change data values
•  save modifications to the data and then redirect to the main screen
•  cancel or exit out of this screen and go back to the main screen
 */