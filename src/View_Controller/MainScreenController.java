package View_Controller;



import Model.Inventory;
import Model.Part;
import Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static Model.Inventory.*;

/**
 * This class controls the main screen. A solved logic error and a future implementation idea are located here.
 *
 * @author Jonathan Mecham
 *
 * */
public class MainScreenController implements Initializable {

    //table views & columns
    @FXML private TableView<Part> partsTableView;
    @FXML private TableColumn<Part, Integer> partsIdCol;
    @FXML private TableColumn<Part, String> partsNameCol;
    @FXML private TableColumn<Part, Integer> partsInvCol;
    @FXML private TableColumn<Part, Double> partsPriceCol;

    @FXML private TableView<Product> productsTableView;
    @FXML private TableColumn<Product, Integer> productsIdCol;
    @FXML private TableColumn<Product, String> productsNameCol;
    @FXML private TableColumn<Product, Integer> productsInvCol;
    @FXML private TableColumn<Product, Double> productsPriceCol;

    //text fields
    @FXML private TextField partsSearch;
    @FXML private TextField productsSearch;

    /**
     * This method searches the parts for entered part name or ID. I ran into a Logic Error finding a way to search for ID and Name in the same method.
     * Upon searching for by name, an error message would display multiple times.
     * @param event event
     */
    @FXML void searchParts(ActionEvent event) {
        String searchFor = partsSearch.getText();
        ObservableList<Part> searchedParts = FXCollections.observableArrayList();

        if (searchFor.isEmpty()) {
            partsTableView.setItems(Inventory.getAllParts());
        } else {
            try{
                //search for ID
                Part searchID = Inventory.lookupPart(Integer.parseInt(searchFor));
                if (searchID == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Part not found");
                    alert.setContentText("please try again");
                    alert.showAndWait();
                }else {
                    searchedParts.add(searchID);
                    partsTableView.setItems(searchedParts);
                }
            } catch (NumberFormatException e){
                //search for Name
                boolean foundName = false;
                for (Part i : getAllParts()) {
                    if (i.getName().equals(searchFor)) {
                        searchedParts.add(i);
                        partsTableView.setItems(searchedParts);
                        foundName = true;

                    }
                }
                /**
                  The logic error was solved by including a boolean value describing if the object was found by Name.
                  This made it so an error message only displayed once if the object wasn't found, rather than once for each object in the list.
                    @param foundName boolean
                 */
                if (!foundName){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Part not found");
                    alert.setContentText("please try again");
                    alert.showAndWait();
                }
            }
        }
    }

    /**
     * This method searches the products for entered part name or ID.
     * @param event event
     */
    @FXML void searchProducts(ActionEvent event) {
        String searchFor = productsSearch.getText();
        ObservableList<Product> searchedProducts = FXCollections.observableArrayList();

        if (searchFor.isEmpty()) {
            productsTableView.setItems(Inventory.getAllProducts());
        } else {
            try{
                //search for ID
                Product searchID = Inventory.lookupProduct(Integer.parseInt(searchFor));
                if (searchID == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Product not found");
                    alert.setContentText("please try again");
                    alert.showAndWait();
                }else {
                    searchedProducts.add(searchID);
                    productsTableView.setItems(searchedProducts);
                }
            } catch (NumberFormatException e){
                //search for Name
                boolean foundName = false;
                for (Product i : getAllProducts()) {
                    if (i.getName().equals(searchFor)) {
                        searchedProducts.add(i);
                        productsTableView.setItems(searchedProducts);
                        foundName = true;

                    }
                }
                if (!foundName){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Product not found");
                    alert.setContentText("please try again");
                    alert.showAndWait();
                }
            }
        }
    }

    /**
     * This method changes screen to Add Part Screen. This method is called when the add parts button is pushed.
     * @param event event
     * @throws IOException IOException
     */
    @FXML void addPartButtonPushed(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPart.fxml"));
        Parent addPartScreen = loader.load();
        Scene addPartScene = new Scene(addPartScreen);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(addPartScene);
        stage.show();
    }

    /**
     * This method changes screen to Add Product Screen. This method is called when the add product button is pushed.
     * @param event event
     * @throws IOException IOException
     */
    @FXML void addProductButtonPushed(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddProduct.fxml"));
        Parent addProductScreen = loader.load();
        Scene addProductScene = new Scene(addProductScreen);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(addProductScene);
        stage.show();
    }

    /**
     * This method changes screen to Modify Part Screen. This method is called when a part is selected, and the modify part button is pushed.
     * @param event event
     * @throws IOException IOException
     */
     @FXML void modifyPartButtonPushed(ActionEvent event) throws IOException {
         Part part = partsTableView.getSelectionModel().getSelectedItem();
         if (part == null){
             Alert alert = new Alert(Alert.AlertType.ERROR);
             alert.setHeaderText("No part Selected");
             alert.setContentText("please select a part");
             alert.showAndWait();
             return;
         }

         FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyPart.fxml"));
         Parent modifyPartScreen = loader.load();

         ModifyPartController modifyPartController = loader.getController();
         modifyPartController.getPartToModify(part);

         Scene modifyPartScene = new Scene(modifyPartScreen);
         Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
         stage.setScene(modifyPartScene);
         stage.show();
     }

    /**
     * This method changes screen to Modify Product Screen. This method is called when a part is selected, and the modify product button is pushed.
     * @param event event
     * @throws IOException IOException
     */
    @FXML void modifyProductButtonPushed(ActionEvent event) throws IOException {
        Product product = productsTableView.getSelectionModel().getSelectedItem();

        if (product == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No product Selected");
            alert.setContentText("please select a product");
            alert.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyProduct.fxml"));
        Parent modifyProductScreen = loader.load();

        ModifyProductController modifyProductController = loader.getController();
        modifyProductController.getProductToModify(product);

        Scene modifyProductScene = new Scene(modifyProductScreen);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(modifyProductScene);
        stage.show();
    }

    /**
     * This method deletes a selected part in the Parts Table. This method is called when a part is selected and the delete part button is pushed.
     * A feature that would be useful to implement in the future, would be to allow multiple selected parts deleted at the same time, rather than having to do so one by one.
     *
     */
    @FXML void deletePartButtonPushed(){
        Part partToDelete = partsTableView.getSelectionModel().getSelectedItem();

        if (partToDelete == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No part selected");
            alert.setContentText("please select a part to delete");
            alert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Delete Part?");
        alert.setContentText("press OK to confirm");
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK){
            deletePart(partToDelete.getId());
        }
        else {
            alert.close();
        }
    }

    /**
     * This method deletes a selected product in the Products Table. This method is called when a product is selected and the delete product button is pushed.
     */
    @FXML void deleteProductButtonPushed(){
        Product productToDelete = productsTableView.getSelectionModel().getSelectedItem();

        if (productToDelete == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("No product selected");
            alert.setContentText("please select a product to delete");
            alert.showAndWait();
            return;
        }
        if (productToDelete.getAssociatedParts().size() > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Cannot delete Product with associated parts");
            alert.setContentText("please remove the associated parts before deleting");
            alert.showAndWait();
            return;
        }


        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Delete Product?");
        alert.setContentText("press OK to confirm");
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK){
            deleteProduct(productToDelete.getId());
        }
        else {
            alert.close();
        }
    }

    /**
     * This method exits the program. It is called when the exit button is pushed.
     * @param event event
     */
    @FXML void exitButtonPushed(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Exit Program?");
        alert.setContentText("Press OK to exit.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }


    /**
     * This method Initializes the MainScreenController class.
     * @param location location
     * @param resources resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        partsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productsInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        partsTableView.setItems(Inventory.getAllParts());
        productsTableView.setItems(Inventory.getAllProducts());

    }
}