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
import java.util.ResourceBundle;

import static Model.Inventory.getAllParts;
import static Model.Inventory.getAllProducts;

/**
 * This class controls Modify Product Screen.
 * @author Jonathan Mecham
 */
public class ModifyProductController implements Initializable {
    @FXML private TextField productIdTextField;
    @FXML private TextField productNameTextField;
    @FXML private TextField productInvTextField;
    @FXML private TextField productPriceTextField;
    @FXML private TextField productMaxTextField;
    @FXML private TextField productMinTextField;
    @FXML private TextField searchTextField;

    @FXML private TableView<Part> allPartsTableView;
    @FXML private TableColumn<Part, Integer> allPartsIdCol;
    @FXML private TableColumn<Part, String> allPartsNameCol;
    @FXML private TableColumn<Part, Integer> allPartsInvCol;
    @FXML private TableColumn<Part, Double> allPartsPriceCol;

    @FXML private TableView<Part> associatedPartsTableView;
    @FXML private TableColumn<Part, Integer> associatedPartsIdCol;
    @FXML private TableColumn<Part, String> associatedPartsNameCol;
    @FXML private TableColumn<Part, Integer> associatedPartsInvCol;
    @FXML private TableColumn<Part, Double> associatedPartsPriceCol;

    Product product;

    /**
     * This method searches the parts for entered part name or ID.
     * I ran into a Logic Error finding a way to search for ID and Name in the same method.
     * Upon searching for by name, an error message would display multiple times.
     * @param event event
     */
    @FXML void searchParts(ActionEvent event) {
        String searchFor = searchTextField.getText();
        ObservableList<Part> searchedParts = FXCollections.observableArrayList();

        if (searchFor.isEmpty()) {
            allPartsTableView.setItems(Inventory.getAllParts());
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
                    allPartsTableView.setItems(searchedParts);
                }
            } catch (NumberFormatException e){
                //search for Name
                boolean foundName = false;
                for (Part i : getAllParts()) {
                    if (i.getName().equals(searchFor)) {
                        searchedParts.add(i);
                        allPartsTableView.setItems(searchedParts);
                        foundName = true;

                    }
                }
                /*
                  The logic error was solved by including a boolean value describing if the object was found by Name.
                  This made it so an error message only displayed once if the object wasn't found, rather than once for each object in the list.
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
     * This method hands the selected product from the main screen to the modify product screen. It is called when a product is selected in the main screen and the modify product button is pushed"
     * @param modProduct product to modify
     */
    public void getProductToModify(Product modProduct){
        productIdTextField.setText(String.valueOf(modProduct.getId()));
        productNameTextField.setText(String.valueOf(modProduct.getName()));
        productInvTextField.setText(String.valueOf(modProduct.getStock()));
        productPriceTextField.setText(String.valueOf(modProduct.getPrice()));
        productMaxTextField.setText(String.valueOf(modProduct.getMax()));
        productMinTextField.setText(String.valueOf(modProduct.getMin()));

        product.getAssociatedParts().addAll(modProduct.getAssociatedParts());
    }

    /**
     * This method adds part to the associated parts list. It is called when a part is selected and the add button is pushed.
     * @param event event
     */
    @FXML void addButtonPushed(ActionEvent event) {
        Part selectedPart = allPartsTableView.getSelectionModel().getSelectedItem();
        product.getAssociatedParts().add(selectedPart);
    }

    /**
     * This method removes a selected part from the associated parts list. It is called when the remove associated part button is pushed
     * @param event event
     */
    @FXML void removeAssociatedPart(ActionEvent event){
        Part part = associatedPartsTableView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Remove associated part?");
        alert.setContentText("Press OK to confirm.");
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            product.deleteAssociatedPart(part.getId());
        }
        else {
            alert.close();
        }
    }

    /**
     * This method saves and updates the modified product. It is called when the save button is pushed.
     * @param event event
     * @throws IOException IOException
     */
    @FXML public void saveButtonPushed(ActionEvent event) throws IOException {
        try {
            int id = Part.generateNextPartID();
            String name = productNameTextField.getText();
            int inv = Integer.parseInt(productInvTextField.getText());
            Double price = Double.parseDouble(productPriceTextField.getText());
            int max = Integer.parseInt(productMaxTextField.getText());
            int min = Integer.parseInt(productMinTextField.getText());

            String errorMessage = "";

            if (name.equals("")) {
                errorMessage += "Product must have a name\n";
            }
            if (min > max) {
                errorMessage += "Min must be less than Max\n";
            }
            if (inv > max || inv < min) {
                errorMessage += "Inv must be between between Min and Max\n";
            }

            if (errorMessage.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR, errorMessage);
                alert.showAndWait();
            }
            else {
                if (updateProduct(Integer.parseInt(productIdTextField.getText()), new Product(Integer.parseInt(productIdTextField.getText()), productNameTextField.getText(), Double.parseDouble(productPriceTextField.getText()), Integer.parseInt(productInvTextField.getText()), Integer.parseInt(productMinTextField.getText()), Integer.parseInt(productMaxTextField.getText()), product.getAssociatedParts()))) {

                    //back to main
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
                    Parent backToMain = loader.load();
                    Scene addPartScene = new Scene(backToMain);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(addPartScene);
                    stage.show();
                }
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Fill out all fields!");
            alert.showAndWait();
        }
    }

    /**
     * This method updates the modified product. It is called in the saveButtonPushed method.
     * @param id id of product to update
     * @param product product to update
     * @return boolean
     */
    public boolean updateProduct(int id, Product product){

        for (int index = 0; index < getAllProducts().size(); index ++) {
            if (getAllProducts().get(index).getId() == id) {
                getAllProducts().set(index, product);
                return true;
            }
        }
        return false;
    }

    /**
     * This method cancels adding the product and redirects user back to main screen.
     * @param event event
     * @throws IOException IOException
     */
    @FXML void cancelButtonPushed(ActionEvent event) throws IOException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Cancel modifying product?");
        alert.setContentText("Press OK to confirm.");
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            //back to main
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
            Parent backToMain = loader.load();
            Scene addPartScene = new Scene(backToMain);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(addPartScene);
            stage.show();
        }

    }

    /**
     * This method initializes the ModifyProduct Screen.
     * @param location location
     * @param resources resource
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        allPartsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartsInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        allPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartsIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartsNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartsInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productIdTextField.setEditable(false);

        product = new Product();

        allPartsTableView.setItems(Inventory.getAllParts());
        associatedPartsTableView.setItems(product.getAssociatedParts());

    }
}
