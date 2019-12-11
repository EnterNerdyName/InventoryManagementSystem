package InventoryManagementSystem.exceptions;

public class ValidationException extends Exception {

    /**
     * Constructor. Simply passes the exception message to the base handler.
     *
     * @param message
     */
    public ValidationException(String message) {
        super(message);
    }
}
/**
 *
 * J. Write code to implement exception controls with custom error messages for
 * one requirement out of each of the following sets (pick one from each): 1.
 * Set 1 • entering an inventory value that exceeds the minimum or maximum value
 * for that part or product • preventing the minimum field from having a value
 * above the maximum field • preventing the maximum field from having a value
 * below the minimum field • ensuring that a product must always have at least
 * one part 2. Set 2 • including a confirm dialogue for all “Delete” and
 * “Cancel” buttons • ensuring that the price of a product cannot be less than
 * the cost of the parts • ensuring that a product must have a name, price,
 * category, and inventory level (default 0)
 *
 */