package alcsoft.com.autobalance.features.shared.containers;

/**
 * ObjectContainer
 * A ObjectContainer is an abstract class that contains base data and extended by child containers.
 *
 * @author ALCRAmirez94
 * @version 1.0
 * @since 3.0
 */
public abstract class ObjectContainer {
    /**
     * Contains the name of the object. (PurchaseName or BudgetName)
     */
    private String name;

    /**
     * Constructs the object using the name.
     * @param nameIn name of object
     */
    public ObjectContainer(String nameIn){
        this.name = nameIn;
    }

    /**
     * Sets the new name of the object
     * @param name new name to set the object to
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Gets the current name of the object
     * @return the name of the object
     */
    public String getName(){
        return name;
    }
}
