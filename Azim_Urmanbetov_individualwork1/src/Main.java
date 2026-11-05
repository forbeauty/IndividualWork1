import java.util.NoSuchElementException;

public class Main{
    public static void main(String[] args){

        FurnitureShop shop = new FurnitureShop("My Furniture Shop");

        try{
            addStartData(shop);
            runMainMenu(shop);
        }
        catch(ShopException e){
            System.out.println("Start data error: " + e.getMessage());
        }
        catch(NoSuchElementException e){
            System.out.println();
            System.out.println("No more input. Program closed.");
        }
    }

    private static void runMainMenu(FurnitureShop shop){
        int choice = -1;

        while(choice != 0){
            System.out.println();
            System.out.println("===== FURNITURE SHOP =====");
            System.out.println("1. Seller menu");
            System.out.println("2. Customer menu");
            System.out.println("3. Show all furniture");
            System.out.println("0. Exit");

            choice = InputHelper.readInt("Choose option: ");
            System.out.println();

            try{
                switch(choice){
                    case 1:
                        loginSeller(shop);
                        break;
                    case 2:
                        loginCustomer(shop);
                        break;
                    case 3:
                        shop.showFurnitures();
                        break;
                    case 0:
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
            catch(ShopException e){
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private static void loginSeller(FurnitureShop shop){
        System.out.println("Sellers:");
        shop.showSellers();

        int id = InputHelper.readInt("Enter seller ID: ");
        Seller seller = shop.findSeller(id);

        if(seller == null){
            System.out.println("No seller with ID " + id + " was found.");
            return;
        }

        new SellerMenu(shop, seller).run();
    }

    private static void loginCustomer(FurnitureShop shop) throws ShopException{
        System.out.println("Customers:");
        shop.showCustomers();

        int id = InputHelper.readInt("Enter customer ID (or 0 to register a new one): ");
        Customer customer;

        if(id == 0){
            int newId = InputHelper.readPositiveInt("New customer ID: ");
            String name = InputHelper.readText("Your name: ");
            customer = shop.addCustomer(newId, name);
        }
        else{
            customer = shop.findCustomer(id);
        }

        if(customer == null){
            System.out.println("Customer was not found.");
            return;
        }

        new CustomerMenu(shop, customer).run();
    }

    private static void addStartData(FurnitureShop shop) throws ShopException{
        Chair chair1 = new Chair(101, "Office Chair", 60, 6.2, "Office", 100);
        Chair chair2 = new Chair(102, "Kitchen Chair", 35, 4.5, "Kitchen", 90);
        Sofa sofa1 = new Sofa(201, "Family Sofa", 500, 25, 3, true);
        Sofa sofa2 = new Sofa(202, "Small Sofa", 300, 18, 2, false);
        Table table1 = new Table(301, "Dining Table", 150, 12, 1.5, 0.8);

        shop.addFurniture(chair1, 10);
        shop.addFurniture(chair2, 2);
        shop.addFurniture(sofa1, 5);
        shop.addFurniture(sofa2, 1);
        shop.addFurniture(table1, 4);

        shop.addCustomer(1001, "Alice");
        shop.addCustomer(1002, "Bob");
        shop.addSeller(1, "Sam");
    }
}
