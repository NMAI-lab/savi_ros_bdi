package savi_ros_java.savi_ros_bdi.agent_state;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public abstract class ItemManager<T> {
    /**
     * List of literals being managed.
     */
    protected Queue<T> items;

    /**
     * Default constructor.
     */
    public ItemManager() {
        this.items = new LinkedList<>();
    }

    /**
     * Check if there are any items in the list.
     * @return
     */
    public boolean isItemAvailable() {
        return !this.items.isEmpty();
    }

    /**
     * Add items contained in itemString to the manager
     * @param itemString
     */
    public abstract void addItem(String itemString);


    /**
     * Get a copy of the item list and clear contents
     * @return
     */
    public List<T> getItemList() {
        List<T> returnList = new LinkedList<>(this.items);
        this.items = new LinkedList<>();
        return returnList;
    }

    /**
     * Get the next item in the list
     * @return
     */
    public T getNextItem() {
        if (this.isItemAvailable()) {
            return this.items.remove();
        } else {
            return null;
        }
    }

    /**
     * This tests the location of the brackets to see if they make sense for a real literal
     * @return True if this contains something that looks like a literal
     */
    public abstract boolean validateItemString(String itemString);
}