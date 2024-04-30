package cp213;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.print.PrinterJob;
import java.math.BigDecimal;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * The GUI for the Order class.
 *
 * @author Anousheh Shahid
 * @author Abdul-Rahman Mawlood-Yunis
 * @author David Brown
 * @version 2022-05-20
 */
@SuppressWarnings("serial")
public class OrderPanel extends JPanel {
	private HashMap<JTextField, MenuItem> store = new HashMap<JTextField, MenuItem>();
	
	private Order order;
	
	private Menu menu;
	
	private JLabel subTotal;
	
	private JLabel tax;
	
	private JLabel total;
	
	private JButton printButton;

    /**
     * Implements an ActionListener for the 'Print' button. Prints the current
     * contents of the Order to a system printer or PDF.
     */
    private class PrintListener implements ActionListener {

		@Override
		public void actionPerformed(final ActionEvent e) {
	
			final PrinterJob Print = PrinterJob.getPrinterJob();
			Print.setPrintable(OrderPanel.this.order);
			
			if (Print.printDialog()) {
			try {
				Print.print();
			}catch(final Exception printException) {
				System.err.println(printException);
			}
			}
	
		}
    }
    
    private class QuantityListener implements FocusListener{

		@Override
		public void focusGained(FocusEvent e) {
			// Nothing needs to be done when focus is gained
		}

		@Override
		public void focusLost(FocusEvent e) {
			JTextField source = (JTextField) e.getSource();
			
			String quantity = source.getText();
			
			int quant;
			
			try {
			    quant = Integer.parseInt(quantity);
			} catch (NumberFormatException exception) {
				System.out.println("Please enter a number!");
				quant = 0;
			}
			
			if (quant > 0) {
				MenuItem item = OrderPanel.this.store.get(source);
				
				OrderPanel.this.order.add(item, quant);
				
				BigDecimal subtotal = OrderPanel.this.order.getSubTotal();
				
				BigDecimal taxes = OrderPanel.this.order.getTaxes();
				
				BigDecimal total = OrderPanel.this.order.getTotal();
				
				String format= "%6.2f";
				
				OrderPanel.this.subTotal.setText(String.format(format, subtotal));
				
				OrderPanel.this.tax.setText(String.format(format, taxes));
				
				OrderPanel.this.total.setText(String.format(format, total));
			}
		}
    	
    }

    public OrderPanel(Menu menu) {
    	this.order = new Order();
    	
    	this.menu = menu;
    	
    	int size = this.menu.size() + 5;
    	
    	GridLayout grid = new GridLayout(size, 3);
    	
    	this.setBorder(new EmptyBorder(10, 10, 10, 10));
    	
    	this.setLayout(grid);
    	
    	this.add(new JLabel("Item"));
    	
    	this.add(new JLabel("Price"));
    	
    	this.add(new JLabel("Quantity"));
    	
    	
    	JLabel priceLabel;
    	
    	JTextField quantity;
    	
    	for (int i = 0; i < menu.size(); i++) {
    		MenuItem item = menu.getItem(i);
    		
    		this.add(new JLabel(item.getName()));
    		
    		priceLabel = new JLabel(String.format("$%.2f", item.getPrice()));
    		
    		priceLabel.setHorizontalAlignment(JLabel.TRAILING);
    		
    		this.add(priceLabel);
    		
    		quantity = new JTextField("0");
    		
    		quantity.setName(item.getName());
    		
    		quantity.addFocusListener(new QuantityListener());
    		
    		this.store.put(quantity, item);
    		
    		this.add(quantity);
    	}
    	
    	this.add(new JLabel("Subtotal:"));
    	this.add(new JLabel(""));
    	
    	this.subTotal = new JLabel("$0.00");
    	this.subTotal.setHorizontalAlignment(JLabel.TRAILING);
    	this.add(this.subTotal);
    	
    	this.add(new JLabel("Tax:"));
    	this.add(new JLabel(""));
    	
    	this.tax = new JLabel("$0.00");
    	this.tax.setHorizontalAlignment(JLabel.TRAILING);
    	this.add(this.tax);
    	
    	this.add(new JLabel("Total:"));
    	this.add(new JLabel(""));
    	
    	this.total = new JLabel("$0.00");
    	this.total.setHorizontalAlignment(JLabel.TRAILING);
    	this.add(this.total);
    	
    	this.add(new JLabel(""));
    	
    	this.printButton = new JButton("Print");
    	this.printButton.addActionListener(new PrintListener());
    	this.add(this.printButton);
    	
    	this.add(new JLabel(""));
    	
    }

}