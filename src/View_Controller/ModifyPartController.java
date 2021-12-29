package View_Controller;

import Model.InHouse;
import Model.Inventory;
import Model.OutSourced;
import Model.Part;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import static Model.Inventory.getAllParts;

/**
 * This class controls the Modify Part Screen.
 *
 * @author Jonathan Mecham
 */
public class ModifyPartController implements Initializable {

    @FXML private RadioButton inHouseRB;
    @FXML private RadioButton outSourcedRB;
    @FXML private ToggleGroup dynToggleGroup;
    @FXML private TextField partIdTextField;
    @FXML private TextField partNameTextField;
    @FXML private TextField partInvTextField;
    @FXML private TextField partPriceTextField;
    @FXML private TextField partMaxTextField;
    @FXML private TextField partMinTextField;
    @FXML private TextField dynTextField;
    @FXML private Label dynLabel;

    /**
     * This method sets the part as either InHouse or OutSourced and changes the dynamic label between Machine ID and Company Name.
     * @param event event
     */
    @FXML public void RBPushed(ActionEvent event) {
        if (inHouseRB.isSelected()) {
            dynLabel.setText("Machine ID");
        }
        if (outSourcedRB.isSelected()) {
            dynLabel.setText("Company Name");
        }
    }

    /**
     * This method hands the selected part from the main screen to the modify part screen. It is called when a part is selected in the main screen and the modify part button is pushed"
     * @param part part to modify
     */
    @FXML public void getPartToModify(Part part) {
        partIdTextField.setText(String.valueOf(part.getId()));
        partNameTextField.setText(part.getName());
        partInvTextField.setText(String.valueOf(part.getStock()));
        partPriceTextField.setText(String.valueOf(part.getPrice()));
        partMaxTextField.setText(String.valueOf(part.getMax()));
        partMinTextField.setText(String.valueOf(part.getMin()));

        if(part instanceof InHouse) {
            InHouse modPart = (InHouse) part;
            dynTextField.setText(String.valueOf(((InHouse) part).getMacId()));
            dynLabel.setText("Machine ID");
            inHouseRB.setSelected(true);
        }
        else if (part instanceof OutSourced){
            OutSourced modPart = (OutSourced) part;
            dynTextField.setText(String.valueOf(((OutSourced) part).getCompName()));
            dynLabel.setText("Company Name");
            outSourcedRB.setSelected(true);
        }
    }

    /**
     * This method updates and saves the part and entered information associated with it. It is called when the save button is pushed.
     * @param event event
     * @throws IOException IOException
     */
    @FXML void saveButtonPushed(ActionEvent event) throws IOException {

        try {
            String name = partNameTextField.getText();
            int inv = Integer.parseInt(partInvTextField.getText());
            int max = Integer.parseInt(partMaxTextField.getText());
            int min = Integer.parseInt(partMinTextField.getText());


            String errorMessage = "";

            if (!(inHouseRB.isSelected() || outSourcedRB.isSelected())) {
                errorMessage += "Part must be InHouse or OutSourced\n";
            }
            if (name.equals("")) {
                errorMessage += "Part must have a name\n";
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
                if (inHouseRB.isSelected()) {
                    if (updatePart(Integer.parseInt(partIdTextField.getText()), new InHouse(Integer.parseInt(partIdTextField.getText()), partNameTextField.getText(), Double.parseDouble(partPriceTextField.getText()), Integer.parseInt(partInvTextField.getText()), Integer.parseInt(partMinTextField.getText()), Integer.parseInt(partMaxTextField.getText()), Integer.parseInt(dynTextField.getText())))) {

                        //back to main
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
                        Parent backToMain = loader.load();
                        Scene addPartScene = new Scene(backToMain);
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(addPartScene);
                        stage.show();
                    }
                }

               if (outSourcedRB.isSelected()) {
                    if (updatePart(Integer.parseInt(partIdTextField.getText()), new OutSourced(Integer.parseInt(partIdTextField.getText()), partNameTextField.getText(), Double.parseDouble(partPriceTextField.getText()), Integer.parseInt(partInvTextField.getText()), Integer.parseInt(partMinTextField.getText()), Integer.parseInt(partMaxTextField.getText()), dynTextField.getText()))) {

                        //back to main
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScreen.fxml"));
                        Parent backToMain = loader.load();
                        Scene addPartScene = new Scene(backToMain);
                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.setScene(addPartScene);
                        stage.show();


                    }
                }
            }

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Fill out all fields!");
            alert.showAndWait();
        }
    }

    /**
     * This method cancels modifying the part and redirects user back to main screen. It is called when the cancel button is pushed.
     * @param event event
     * @throws IOException IOException
     */
    @FXML void cancelButtonPushed(ActionEvent event) throws IOException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Confirm Cancel?");
        alert.setContentText("Press OK to cancel modifying part");
        alert.showAndWait();

        if (alert.getResult() == ButtonType.OK) {
            //back to main screen
            Parent addPartParent = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene addPartScene = new Scene(addPartParent);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(addPartScene);
            window.show();
        }
        else {
            alert.close();
        }
    }

    /**
     * This method updates the part after it has been modified. This method is called in the saveButtonPushed method.
     * @param id id of part to update
     * @param part part to update
     * @return boolean if updated
     */
    public boolean updatePart(int id, Part part) {

        for (int index = 0; index < getAllParts().size(); index++) {
            if (getAllParts().get(index).getId() == id) {
                getAllParts().set(index, part);
                return true;
            }
        }
        return false;
    }

    /**
     * This method initializes the ModifyPart Screen.
     * @param location location
     * @param resources resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dynToggleGroup = new ToggleGroup();
        inHouseRB.setToggleGroup(dynToggleGroup);
        outSourcedRB.setToggleGroup(dynToggleGroup);
        partIdTextField.setEditable(false);
    }
}
