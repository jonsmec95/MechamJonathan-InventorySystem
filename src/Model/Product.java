package Model;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * This is the Product class.
 *
 * @author Jonathan Mecham
 */
public class Product {

    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private Double price;
    private int stock;
    private int min;
    private int max;
    public static int idCount = 1;



    public Product() {};

    /**
     * Product Constructor
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     */
    public Product(int id, String name, Double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Product Constructor
     * @param id
     * @param name
     * @param price
     * @param stock
     * @param min
     * @param max
     * @param associatedParts
     */
    public Product(int id, String name, Double price, int stock, int min, int max, ObservableList associatedParts) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.associatedParts.addAll(associatedParts);
    }

    //getters

    /**
     * @return id
     */
    public int getId(){
        return id;
    }
    /**
     * @return name
     */
    public String getName() {
        return name;
    }
    /**
     * @return price
     */
    public Double getPrice(){
        return price;
    }
    /**
     * @return stock
     */
    public int getStock() {
        return stock;
    }
    /**
     * @return min
     */
    public int getMin() {
        return min;
    }
    /**
     * @return max
     */
    public int getMax() {
        return max;
    }
    /**
     * @return associatedParts
     */
    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }
    /**
     * @return id count
     */
    public static int getIdCount(){
        return idCount;
    }

    //setters

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @param price the price to set
     */
    public void setPrice(Double price) {
        this.price = price;
    }
    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @return next idCount
     */
    public static int generateID() {
        return idCount++;
    }


    /**
     * adds part to associated parts
     * @param part part to add
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * deletes part from associated parts
     * @param partId part to delete
     * @return boolean if part deleted or not
     */
    public boolean deleteAssociatedPart(int partId) {
        for (Part i : associatedParts) {
            if (i.getId() == partId) {
                associatedParts.remove(i);
                return true;
            }
        }
        return false;
    }

}