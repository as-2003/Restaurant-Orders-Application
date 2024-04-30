package cp213;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Wraps around an Order object to ask for MenuItems and quantities.
 *
 * @author Anousheh Shahid
 * @author Abdul-Rahman Mawlood-Yunis
 * @author David Brown
 * @version 2022-05-20
 */
public class Cashier {

    // Attributes
    private Menu menu = null;

    /**
     * Constructor.
     *
     * @param menu A Menu.
     */
    public Cashier(Menu menu) {
	this.menu = menu;
    }

    /**
     * Prints the menu.
     */
    private void printCommands() {
	System.out.println("\nMenu:");
	System.out.println(menu.toString());
	System.out.println("Press 0 when done.");
	System.out.println("Press any other key to see the menu again.\n");
    }
    
    private int getCommand(Scanner keyboard) {
    	int command;
    	
    	try {
			command = keyboard.nextInt();
		}catch (InputMismatchException e) {
			command = -1;
			keyboard.nextLine();
			System.out.println("Not a valid number");
		}
    	
    	return command;
    }

    /**
     * Asks for commands and quantities. Prints a receipt when all orders have been
     * placed.
     *
     * @return the completed Order.
     */
    public Order takeOrder() {
	System.out.println("Welcome to WLU Foodorama!");

	this.printCommands();
	
	Scanner keyboard = new Scanner(System.in);
	
	int size = this.menu != null ? this.menu.size() : 0;
	
	int command;
	
	Order newOrder = new Order();
	
	do {
		
		System.out.print("Command: ");
		
		command = this.getCommand(keyboard);
		
		if ((1 <= command) && (command <= size)) {
			
			MenuItem item = menu.getItem(command - 1);
			
			System.out.print("How many do you want? ");
			
			int quantity = this.getCommand(keyboard);
			
			if (quantity > 0) {
				newOrder.add(item, quantity);
			}
		}
		else if(command != 0) {
			this.printCommands();
		}
		
	}while(command != 0);
	
	System.out.println("----------------------------------------");
	
	System.out.println("Receipt");
	
	System.out.println(newOrder.toString());
	
	return newOrder;
    }
}