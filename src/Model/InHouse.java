package Model;

/**
 * This is the InHouse part class.
 *
 * @author Jonathan Mecham
 */
public class InHouse extends Part {
    private int macId;

    /**
     * Constructor for an InHouse Part.
     * @param id id to set
     * @param name name to set
     * @param price price to set
     * @param stock stock to set
     * @param min min to set
     * @param max max to set
     * @param macId macId to set
     */
    public InHouse(int id, String name, Double price, int stock, int min, int max, int macId) {
        super(id, name, price, stock, min, max);
        this.macId = macId;
    }
    //getters

    /**
     * @return macId
     */
    public int getMacId() {
        return macId;
    }

    /**
     * @param macId macId to set
     */
    public void setMacId(int macId) {
        this.macId = macId;
    }
}