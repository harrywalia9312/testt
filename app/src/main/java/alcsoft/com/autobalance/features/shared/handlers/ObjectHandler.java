package alcsoft.com.autobalance.features.shared.handlers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import alcsoft.com.autobalance.features.shared.containers.ObjectContainer;

/**
 * ObjectHandler Class
 *  This class handles the arrayList of objects of Purchases or Budgets.
 *
 *  @author ALCRamirez94
 *  @version 1.0
 *  @since 3.0
 */
public abstract class ObjectHandler {
    /**
     * ArrayList of specific objects
     */
    private ArrayList<ObjectContainer> arrayList;

    /**
     * Top value of ArrayList
     */
    private int top;

    /**
     * @param arrayData the array list as a string
     * @param topData the top value of the array list
     */
    protected ObjectHandler(String arrayData, int topData){
        // Checks if list Exists
        if(arrayData.equals("none")){
            // Loads new Array List
            arrayList = new ArrayList<>();
            // Loads a default top value
            top = 0;
        }else{
            // Loads a new GSON reader
            Gson gson = new Gson();
            // Loads the type
            Type arrayType = new TypeToken<ArrayList<?>>() {}.getType();
            // Loads array list from JSON string
            arrayList = gson.fromJson(arrayData,arrayType);
            // Loads the top value
            top = topData;
        }
    }

    /**
     * Adds a new object to the arrayList
     * @param object the new object to add to the list
     */
    public void addObjectToList(ObjectContainer object){
        arrayList.add(object);
        top = top + 1;
    }

    /**
     * Adds one to the top value
     */
    public void addOneToTop(){
        top = top + 1;
    }

    /**
     * Adds a new object to the top of the arrayList
     * @param object the new object to add to the top of the list
     */
    public void addObjectToTopOfList(ObjectContainer object){
        arrayList.add(object);
    }

    /**
     * Replaces the object with a specified object at the specified position.
     * @param position the position of the object
     * @param object the new object to replace with
     */
    public void replaceObjectAtPosition(int position, ObjectContainer object){
        arrayList.set(position,object);
    }

    /**
     * Removes the object from the arrayList at the given position.
     * @param position the position of the object
     */
    public void deleteObjectAtPosition(int position){
        top = top - 1;
        arrayList.remove(position);
    }

    /**
     * Gets the Object from the list
     * @param position the object's position in the array list
     */
    public Object getObjectAt(int position){
        return arrayList.get(position);
    }

    /**
     * Deletes everything in the arrayList and sets the top value to 0
     */
    public void resetArrayList(){
        arrayList.clear();
        top = 0;
    }

    /**
     * Resets the handler and clears the arrayList
     */
    public abstract void resetHandlerDefaults();

    public ArrayList<ObjectContainer> getArrayList() {
        return arrayList;
    }

    /**
     *
     * @return String of Array
     */
    public String getArrayToSave(){
        Gson gson = new Gson();
        return gson.toJson(arrayList);
    }

    /**
     *
     * @return int Top Value in Array
     */
    public int getArrayTopToSave(){
        return top;
    }
}
