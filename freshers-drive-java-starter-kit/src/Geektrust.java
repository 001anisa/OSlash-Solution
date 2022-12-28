import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Geektrust 
{
  // A map to store the products and their details
  private static Map<String, Product> products = new HashMap<>();

  // A map to store the items in the purchase
  private static Map<String, Integer> purchases = new HashMap<>();

  // Initialize the products map with the details of the products
  static {
    products.put("TSHIRT", new Product("Clothing", 1000, 10));
    products.put("JACKET", new Product("Clothing", 2000, 5));
    products.put("CAP", new Product("Clothing", 500, 20));
    products.put("NOTEBOOK", new Product("Stationery", 200, 20));
    products.put("PENS", new Product("Stationery", 300, 10));
    products.put("MARKERS", new Product("Stationery", 500, 5));
  }

  public static void main(String[] args) 
  {

    try 
    {
      // the file to be opened for reading
      FileInputStream fis = new FileInputStream(args[0]);
      Scanner sc = new Scanner(fis); // file to be scanned
      // returns true if there is another line to read
      while (sc.hasNextLine()) 
      {
         String inputCommand = sc.nextLine();
          String [] arr=inputCommand.split(" ");
          for (String command : args) 
          {
            String[] parts = command.split(" ");
            if (parts[0].equals("ADD_ITEM")) 
            {
              addItem(parts[1], Integer.parseInt(parts[2]));
            } else if (parts[0].equals("PRINT_BILL")) 
            {
              printBill();
            }
          }
      
      }
      sc.close(); // closes the scanner
  } 
  catch (IOException e) 
  {
    System.out.println(e);
  }
    // Parse the input commands and execute them
    
}
  

  // Adds the specified item and quantity to the purchases
  public static void addItem(String itemName, int quantity) {
    Product product = products.get(itemName);
    if (product == null) {
      System.out.println("ERROR_INVALID_ITEM");
      return;
    }
    int maxQuantity = product.category.equals("Clothing") ? 2 : 3;
    if (quantity > maxQuantity) {
      System.out.println("ERROR_QUANTITY_EXCEEDED");
      return;
    }
    purchases.merge(itemName, quantity, Integer::sum);
    System.out.println("ITEM_ADDED");
  }

  // Prints the total discount applied and the total amount to pay
  private static void printBill() 
  {
    double totalDiscount = 0;
    double totalAmount = 0;
    for (Map.Entry<String, Integer> entry : purchases.entrySet()) 
    {
      String itemName = entry.getKey();
      int quantity = entry.getValue();
      Product product = products.get(itemName);
      double price = product.price * quantity;
      totalAmount += price;
      double discount = price * product.discount / 100;
      totalDiscount += discount;
    }
    if (totalAmount >= 1000) 
    {
      totalDiscount += totalAmount * 5 / 100;
    }
    if (totalAmount >= 3000) 
    {
      totalDiscount += totalAmount * 5 / 100;
    }
    totalAmount -= totalDiscount;
    totalAmount += totalAmount * 10 / 100; // apply sales tax
    System.out.println("TOTAL_DISCOUNT " + String.format("%.2f", totalDiscount));
    System.out.println("TOTAL_AMOUNT_TO_PAY " + String.format("%.2f", totalAmount));
}
}