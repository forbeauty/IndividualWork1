public class CustomerMenu{
    private FurnitureShop shop;
    private Customer customer;
    private ShoppingCart cart;
    private FurnitureService service;

    public CustomerMenu(FurnitureShop shop, Customer customer){
        this.shop = shop;
        this.customer = customer;
        cart = customer.getCart();
        service = new FurnitureService();
    }

    public void run(){
        int choice = -1;

        while(choice != 0){
            System.out.println();
            System.out.println("===== CUSTOMER MENU (" + customer.getName() + ") =====");
            System.out.println("1. Show all available furniture");
            System.out.println("2. Search for furniture by ID");
            System.out.println("3. View full product details");
            System.out.println("4. Show materials and additional prices");
            System.out.println("5. Add furniture to the cart");
            System.out.println("6. View the cart");
            System.out.println("7. Change quantity of a cart item");
            System.out.println("8. Remove an item from the cart");
            System.out.println("9. Place an order (goes to the queue)");
            System.out.println("10. Show furniture sorted by price");
            System.out.println("11. Show furniture sorted by name");
            System.out.println("12. Show furniture by category");
            System.out.println("13. My orders");
            System.out.println("14. Show receipt of a completed order");
            System.out.println("15. Show furniture up to a price");
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
                shop.showAvailableFurnitures();
                break;
            case 2:
                searchFurniture();
                break;
            case 3:
                showDetails();
                break;
            case 4:
                shop.showMaterials();
                break;
            case 5:
                addToCart();
                break;
            case 6:
                cart.displayCart();
                break;
            case 7:
                changeQuantity();
                break;
            case 8:
                removeItem();
                break;
            case 9:
                placeOrder();
                break;
            case 10:
                shop.showSorted("Sorted by price", new PriceComparator());
                break;
            case 11:
                shop.showSorted("Sorted by name", new NameComparator());
                break;
            case 12:
                shop.showByCategory();
                break;
            case 13:
                System.out.println("===== My orders =====");
                shop.showOrders(customer.getOrders());
                break;
            case 14:
                showReceipt();
                break;
            case 15:
                showCheaper();
                break;
            case 0:
                System.out.println("Returning to main menu...");
                break;
            default:
                System.out.println("Invalid option. Please try again.");
        }
    }

    private void searchFurniture(){
        int id = InputHelper.readInt("Enter furniture ID: ");
        StockItem item = shop.findStockItem(id);

        if(item == null){
            System.out.println("No furniture with ID " + id + " was found.");
            return;
        }

        item.getFurniture().displayInfo();

        if(item.getQuantity() > 0){
            System.out.println("In stock: " + item.getQuantity());
        }
        else{
            System.out.println("This product is out of stock.");
        }
    }

    private void showDetails(){
        int id = InputHelper.readInt("Enter furniture ID: ");
        StockItem item = shop.findStockItem(id);

        if(item == null){
            System.out.println("No furniture with ID " + id + " was found.");
            return;
        }

        service.showProductDetails(item.getFurniture());
        System.out.println("In stock: " + item.getQuantity());
    }

    private void addToCart(){
        int id = InputHelper.readInt("Enter furniture ID: ");
        StockItem item = shop.findStockItem(id);

        if(item == null){
            System.out.println("No furniture with ID " + id + " was found.");
            return;
        }
        if(item.getQuantity() == 0){
            System.out.println("Sorry, this product is out of stock.");
            return;
        }
        if(shop.getMaterialCount() == 0){
            System.out.println("There are no materials. Ask the seller to add one.");
            return;
        }

        Furniture furniture = item.getFurniture();

        shop.showMaterials();
        int materialNumber = InputHelper.readInt("Choose material number: ");
        Material material = shop.getMaterial(materialNumber - 1);

        if(material == null){
            System.out.println("Invalid material number. Nothing was added.");
            return;
        }

        int quantity = InputHelper.readInt("Quantity: ");

        if(quantity < 1){
            System.out.println("Quantity must be at least 1. Nothing was added.");
            return;
        }

        int inCart = cart.getQuantityForFurniture(id);

        if(inCart + quantity > item.getQuantity()){
            System.out.println("Not enough stock. In stock: " + item.getQuantity()
                    + ", already in your cart: " + inCart);
            return;
        }

        cart.addItem(furniture, material, quantity);
        System.out.println(quantity + " x " + furniture.getFurName()
                + " (" + material.getName() + ") added to the cart.");
    }

    private int readCartIndex(){
        if(cart.isEmpty()){
            System.out.println("Your cart is empty.");
            return -1;
        }

        cart.displayCart();

        int number = InputHelper.readInt("Enter cart item number: ");

        if(cart.getItem(number - 1) == null){
            System.out.println("Invalid cart item number.");
            return -1;
        }
        return number - 1;
    }

    private void changeQuantity(){
        int index = readCartIndex();
        if(index == -1){
            return;
        }

        CartItem cartItem = cart.getItem(index);
        int newQuantity = InputHelper.readInt("New quantity: ");

        if(newQuantity < 1){
            System.out.println("Quantity must be at least 1. Use the remove option to delete an item.");
            return;
        }

        int id = cartItem.getFurniture().getFurID();
        int otherLines = cart.getQuantityForFurniture(id) - cartItem.getQuantity();
        int stock = shop.getStockQuantity(id);

        if(otherLines + newQuantity > stock){
            System.out.println("Not enough stock. In stock: " + stock
                    + ", in other cart lines: " + otherLines);
            return;
        }

        cartItem.setQuantity(newQuantity);
        System.out.println("Quantity updated.");
    }

    private void removeItem(){
        int index = readCartIndex();
        if(index == -1){
            return;
        }

        cart.removeItem(index);
        System.out.println("Item removed from the cart.");
    }

    private void placeOrder() throws ShopException{
        System.out.println("===== PLACE ORDER =====");

        if(cart.isEmpty()){
            System.out.println("Your cart is empty. Add furniture first.");
            return;
        }

        cart.displayCart();

        String paymentMethod = askPaymentMethod();

        if(paymentMethod == null){
            System.out.println("Cancelled. Your cart was not changed.");
            return;
        }

        boolean express = InputHelper.readYesNo("Express order (processed first)?");

        FurnitureOrder order = shop.placeOrder(customer, paymentMethod, express);

        System.out.println("Order " + order.getOrderID() + " is in the queue. Orders waiting: "
                + shop.getPendingCount());
    }

    private void showCheaper(){
        double maxPrice = InputHelper.readPositiveDouble("Maximum price: ");
        shop.showCheaperThan(maxPrice);
    }

    private void showReceipt(){
        int id = InputHelper.readInt("Order ID: ");
        FurnitureOrder order = shop.findOrder(id);

        if(order == null || !order.getCustomer().equals(customer)){
            System.out.println("You have no order with ID " + id);
        }
        else if(order.getStatus() != OrderStatus.COMPLETED){
            System.out.println("The receipt is available only for completed orders. Status: "
                    + order.getStatus());
        }
        else{
            order.printReceipt(shop.getShopName());
        }
    }


    private String askPaymentMethod(){
        while(true){
            System.out.println("Payment method:");
            System.out.println("1. Cash");
            System.out.println("2. Card");
            System.out.println("0. Cancel");

            int choice = InputHelper.readInt("Choose option: ");

            if(choice == 1){
                return "Cash";
            }
            else if(choice == 2){
                return "Card";
            }
            else if(choice == 0){
                return null;
            }
            else{
                System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
