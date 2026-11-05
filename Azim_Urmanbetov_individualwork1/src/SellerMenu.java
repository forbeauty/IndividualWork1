public class SellerMenu{
    private FurnitureShop shop;
    private Seller seller;

    public SellerMenu(FurnitureShop shop, Seller seller){
        this.shop = shop;
        this.seller = seller;
    }

    public void run(){
        int choice = -1;

        while(choice != 0){
            System.out.println();
            System.out.println("===== SELLER MENU (" + seller.getName() + ") =====");
            System.out.println("1. Add a new chair");
            System.out.println("2. Add a new sofa");
            System.out.println("3. Add a new table");
            System.out.println("4. Show all furniture in stock");
            System.out.println("5. Search for furniture by ID");
            System.out.println("6. Update base price");
            System.out.println("7. Update stock quantity");
            System.out.println("8. Show products with low stock");
            System.out.println("9. Show materials and additional prices");
            System.out.println("10. Add a new material");
            System.out.println("11. Update a material price");
            System.out.println("12. Assemble, clean and move furniture");
            System.out.println("13. Remove furniture from the shop");
            System.out.println("14. Show furniture by category");
            System.out.println("15. Show furniture sorted by...");
            System.out.println("16. Show the order queue");
            System.out.println("17. Process the next order");
            System.out.println("18. Show statistics");
            System.out.println("19. Show furniture up to a price");
            System.out.println("0. Return to main menu");

            choice = InputHelper.readInt("Choose option: ");
            System.out.println();

            try{
                handleChoice(choice);
            }
            catch(ShopException e){
                System.out.println("Error: " + e.getMessage());
            }
            catch(IllegalArgumentException e){
                System.out.println("Wrong data: " + e.getMessage());
            }
        }
    }

    private void handleChoice(int choice) throws ShopException{
        switch(choice){
            case 1:
                addChair();
                break;
            case 2:
                addSofa();
                break;
            case 3:
                addTable();
                break;
            case 4:
                shop.showFurnitures();
                break;
            case 5:
                searchFurniture();
                break;
            case 6:
                updatePrice();
                break;
            case 7:
                updateStock();
                break;
            case 8:
                shop.showLowStock();
                break;
            case 9:
                shop.showMaterials();
                break;
            case 10:
                addMaterial();
                break;
            case 11:
                updateMaterialPrice();
                break;
            case 12:
                furnitureActions();
                break;
            case 13:
                removeFurniture();
                break;
            case 14:
                shop.showByCategory();
                break;
            case 15:
                showSorted();
                break;
            case 16:
                shop.showPendingOrders();
                break;
            case 17:
                processNextOrder();
                break;
            case 18:
                shop.showStatistics();
                break;
            case 19:
                showCheaper();
                break;
            case 0:
                System.out.println("Returning to main menu...");
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private int readNewId(){
        int id = InputHelper.readPositiveInt("Furniture ID: ");

        if(shop.findStockItem(id) != null){
            System.out.println("A product with ID " + id + " already exists. Nothing was added.");
            return -1;
        }
        return id;
    }

    private void addChair() throws ShopException{
        System.out.println("--- Add a new chair ---");

        int id = readNewId();
        if(id == -1){
            return;
        }

        String name = InputHelper.readText("Name: ");
        double price = InputHelper.readPositiveDouble("Base price: ");
        double weight = InputHelper.readPositiveDouble("Weight (kg): ");
        String type = InputHelper.readText("Chair type (example: Office): ");
        int height = InputHelper.readPositiveInt("Height (cm): ");
        int stock = InputHelper.readNonNegativeInt("Stock quantity: ");

        Chair chair = new Chair(id, name, price, weight, type, height);
        shop.addFurniture(chair, stock);
    }

    private void addSofa() throws ShopException{
        System.out.println("--- Add a new sofa ---");

        int id = readNewId();
        if(id == -1){
            return;
        }

        String name = InputHelper.readText("Name: ");
        double price = InputHelper.readPositiveDouble("Base price: ");
        double weight = InputHelper.readPositiveDouble("Weight (kg): ");
        int seats = InputHelper.readPositiveInt("Number of seats: ");
        boolean hasBed = InputHelper.readYesNo("Does it have a bed?");
        int stock = InputHelper.readNonNegativeInt("Stock quantity: ");

        Sofa sofa = new Sofa(id, name, price, weight, seats, hasBed);
        shop.addFurniture(sofa, stock);
    }

    private void addTable() throws ShopException{
        System.out.println("--- Add a new table ---");

        int id = readNewId();
        if(id == -1){
            return;
        }

        String name = InputHelper.readText("Name: ");
        double price = InputHelper.readPositiveDouble("Base price: ");
        double weight = InputHelper.readPositiveDouble("Weight (kg): ");
        double length = InputHelper.readPositiveDouble("Length (m): ");
        double width = InputHelper.readPositiveDouble("Width (m): ");
        int stock = InputHelper.readNonNegativeInt("Stock quantity: ");

        Table table = new Table(id, name, price, weight, length, width);
        shop.addFurniture(table, stock);
    }

    private void searchFurniture(){
        int id = InputHelper.readInt("Enter furniture ID: ");
        StockItem item = shop.findStockItem(id);

        if(item == null){
            System.out.println("No furniture with ID " + id + " was found.");
        }
        else{
            item.getFurniture().displayInfo();
            System.out.println("Stock quantity: " + item.getQuantity());
        }
    }

    private void updatePrice(){
        int id = InputHelper.readInt("Enter furniture ID: ");
        Furniture furniture = shop.findFurniture(id);

        if(furniture == null){
            System.out.println("No furniture with ID " + id + " was found.");
            return;
        }

        System.out.printf("%s - current base price: %.2f%n",
                furniture.getFurName(), furniture.getPrice());

        double newPrice = InputHelper.readPositiveDouble("New base price: ");
        furniture.setPrice(newPrice);

        System.out.printf("Price updated. New base price: %.2f%n", furniture.getPrice());
    }

    private void updateStock() throws ShopException{
        int id = InputHelper.readInt("Enter furniture ID: ");
        StockItem item = shop.findStockItem(id);

        if(item == null){
            System.out.println("No furniture with ID " + id + " was found.");
            return;
        }

        System.out.println(item.getFurniture().getFurName()
                + " - current stock: " + item.getQuantity());

        int newStock = InputHelper.readNonNegativeInt("New stock quantity: ");
        shop.setStockQuantity(id, newStock);

        System.out.println("Stock updated. New stock: " + shop.getStockQuantity(id));
    }

    private void addMaterial() throws ShopException{
        String name = InputHelper.readText("Material name: ");
        double price = InputHelper.readNonNegativeDouble("Additional price: ");
        shop.addMaterial(name, price);
    }

    private void updateMaterialPrice() throws ShopException{
        shop.showMaterials();

        if(shop.getMaterialCount() == 0){
            return;
        }

        int number = InputHelper.readInt("Material number: ");
        double price = InputHelper.readNonNegativeDouble("New additional price: ");

        shop.setMaterialPrice(number - 1, price);
        Material material = shop.getMaterial(number - 1);

        System.out.printf("%s additional price is now %.2f%n",
                material.getName(), material.getAdditionalPrice());
    }

    private void furnitureActions(){
        int id = InputHelper.readInt("Enter furniture ID: ");
        Furniture furniture = shop.findFurniture(id);

        if(furniture == null){
            System.out.println("No furniture with ID " + id + " was found.");
            return;
        }

        furniture.assemble();
        furniture.clean();
        furniture.move();
    }

    private void removeFurniture() throws ShopException{
        int id = InputHelper.readInt("Enter furniture ID to remove: ");

        shop.removeFurniture(id);
        System.out.println("Furniture " + id + " was removed from the shop.");
    }

    private void showCheaper(){
        double maxPrice = InputHelper.readPositiveDouble("Maximum price: ");
        shop.showCheaperThan(maxPrice);
    }

    private void showSorted(){
        System.out.println("1. By ID (natural order)");
        System.out.println("2. By price");
        System.out.println("3. By name");
        System.out.println("4. By category, then by price");

        int choice = InputHelper.readInt("Sort by: ");

        switch(choice){
            case 1:
                shop.showSortedNatural();
                break;
            case 2:
                shop.showSorted("Sorted by price", new PriceComparator());
                break;
            case 3:
                shop.showSorted("Sorted by name", new NameComparator());
                break;
            case 4:
                shop.showSorted("Sorted by category and price", new CategoryPriceComparator());
                break;
            default:
                System.out.println("Invalid option.");
        }
    }

    private void processNextOrder(){
        FurnitureOrder order = shop.processNextOrder(seller);

        if(order == null){
            System.out.println("The queue is empty. Nothing to process.");
            return;
        }

        order.displayShort();

        if(order.getStatus() == OrderStatus.COMPLETED){
            order.printReceipt(shop.getShopName());
        }
    }
}
