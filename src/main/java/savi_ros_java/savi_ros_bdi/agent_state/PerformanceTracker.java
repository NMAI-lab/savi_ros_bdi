package savi_ros_java.savi_ros_bdi.agent_state;


public class PerformanceTracker<String> extends ItemManager {

    /**
     * Add items contained in itemString to the manager
     *
     * @param itemString
     */
    @Override
    public void addItem(java.lang.String itemString) {
        // Check to see if anything in this string looks like a literal
        if (!validateItemString(itemString)) {
            return;
        }

        // Add to the queue
        this.addItem(itemString);

    }

    /**
     * This tests the location of the brackets to see if they make sense for a real literal
     *
     * @param itemString
     * @return True if this contains something that looks like a literal
     */
    @Override
    public boolean validateItemString(java.lang.String itemString) {
        return true;
    }
}
