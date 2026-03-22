package view.menu;

/**
 * Defines the basic actions that every menu screen must implement.
 * Any class that implements this interface can build its UI and open itself.
 */
public interface MenuInterface {

    /**
     * Builds and arranges all the UI components of the menu.
     */
    void buildUI();
    
    
    /**
     * Makes the menu visible to the user.
     */
    void open();
}
