package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


/**
 * This is the Inventory class.
 *
 * @author Jonathan Mecham
 */
public class Inventory {

    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    

    //getter

    /**
     *  gets allParts list
     * @return allParts
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /**
     * gets allProducts list
     * @return allProducts
     */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }

    /**
     * adds part to allParts list
     * @param newPart part to add
     */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    /**
     * adds product to allProducts list
     * @param newProduct product to add
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * deletes part from allParts list
     * @param selectedPart part to delete
     * @return boolean
     */
    public static boolean deletePart(int selectedPart) {
        for (Part i: allParts) {
            if (i.getId() == selectedPart) {
                allParts.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * deltes product from allProducts list
     * @param selectedProduct product to delete
     * @return boolean
     */
    public static boolean deleteProduct(int selectedProduct) {
        for (Product i : allProducts) {
            if (i.getId() == selectedProduct) {
                allProducts.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * looks up part in the allParts list
     * @param partId part to look up
     * @return part
     */
    public static Part lookupPart(int partId) {
        for (Part i : allParts) {
            if (i.getId() == partId) {
                return i;
            }
        }
        return null;
    }

    /**
     * looks up product in the allProducts list
     * @param productId product to look up
     * @return product
     */
    public static Product lookupProduct(int productId) {
        for (Product i : allProducts) {
            if ( i.getId() == productId) {
                return i;
            }
        }
        return null;
    }

    /**
     * updates part in the allParts List
     * @param part to update
     */
    public static void updatePart(Part part) {
        for (int i = 0; i < getAllParts().size(); i++){
            if (getAllParts().get(i).getId() == part.getId()) {
                allParts.set(i, part);
            }
        }
    }
}