package Model;

/**
 * This is the Outsourced part class.
 *
 * @author Jonathan Mecham
 */
public class OutSourced extends Part {
    private String compName;

    /**
     * Constructor for an OutSourced part.
     * @param id id to set
     * @param name name to set
     * @param price price to set
     * @param stock stock to set
     * @param min min to set
     * @param max max to set
     * @param compName compName to set
     */
    public OutSourced(int id, String name, Double price, int stock, int min, int max, String compName) {
        super(id, name, price, stock, min, max);
        this.compName = compName;
    }

    //getters

    /**
     * @return compName
     */
    public String getCompName() {
        return compName;
    }

    /**
     * @param compName compName to set
     */
    public void setCompName(String compName) {
        this.compName = compName;
    }
}