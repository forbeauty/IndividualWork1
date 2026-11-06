import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class FurnitureShop{
    public static final int LOW_STOCK_LIMIT = 3;

    private String shopName;

    private List<StockItem> stockItems;
    private List<Material> materials;

    private Map<Integer, StockItem> stockById;
    private Map<Category, List<StockItem>> stockByCategory;
    private Map<Integer, Customer> customers;
    private Map<Integer, Seller> sellers;
    private Map<Integer, FurnitureOrder> ordersById;

    private Queue<FurnitureOrder> pendingOrders;

    private int nextOrderId;

    public FurnitureShop(String shopName){
        this.shopName = shopName;
        stockItems = new ArrayList<>();
        materials = new ArrayList<>();
        stockById = new HashMap<>();
        stockByCategory = new HashMap<>();
        customers = new HashMap<>();
        sellers = new HashMap<>();
        ordersById = new HashMap<>();
        pendingOrders = new PriorityQueue<>(new OrderPriorityComparator());
        nextOrderId = 5001;

        for(Category category : Category.values()){
            stockByCategory.put(category, new ArrayList<>());
        }

        materials.add(new Material("Wood", 30.00));
        materials.add(new Material("Leather", 80.00));
        materials.add(new Material("Metal", 20.00));
        materials.add(new Material("Fabric", 15.00));
    }

    public String getShopName(){
        return shopName;
    }

    public void addFurniture(Furniture furniture, int quantity) throws ShopException{
        if(furniture == null){
            throw new ShopException("Furniture does not exist");
        }
        if(quantity < 0){
            throw new ShopException("Stock quantity cannot be negative");
        }
        if(findStockItem(furniture.getFurID()) != null){
            throw new ShopException("A product with ID " + furniture.getFurID()
                    + " already exists");
        }

        StockItem item = new StockItem(furniture, quantity);

        stockItems.add(item);
        stockById.put(furniture.getFurID(), item);
        stockByCategory.get(furniture.getCategory()).add(item);

        System.out.println(furniture.getFurName()
                + " was added to " + shopName + " (stock: " + quantity + ")");
    }

    public StockItem findStockItem(int furID){
        return stockById.get(furID);
    }

    public Furniture findFurniture(int furID){
        StockItem item = findStockItem(furID);

        if(item == null){
            return null;
        }
        return item.getFurniture();
    }

    public int getStockQuantity(int furID){
        StockItem item = findStockItem(furID);

        if(item == null){
            return 0;
        }
        return item.getQuantity();
    }

    public void setStockQuantity(int furID, int quantity) throws ShopException{
        StockItem item = findStockItem(furID);

        if(item == null){
            throw new ShopException("No furniture with ID " + furID + " was found.");
        }
        if(quantity < 0){
            throw new ShopException("Stock quantity cannot be negative");
        }
        item.setQuantity(quantity);
    }

    public void removeFurniture(int furID) throws ShopException{
        StockItem item = findStockItem(furID);

        if(item == null){
            throw new ShopException("No furniture with ID " + furID + " was found.");
        }

        for(FurnitureOrder order : pendingOrders){
            if(order.getQuantityForFurniture(furID) > 0){
                throw new ShopException("This furniture is in a pending order. "
                        + "Process the orders first.");
            }
        }

        stockItems.remove(item);
        stockById.remove(furID);
        stockByCategory.get(item.getFurniture().getCategory()).remove(item);
    }

    public int getFurnitureCount(){
        return stockItems.size();
    }

    private void printStockItem(StockItem item){
        item.getFurniture().displayInfo();
        System.out.println("Stock quantity: " + item.getQuantity());
        System.out.println("-----------------------------");
    }

    public void showFurnitures(){
        System.out.println("===== " + shopName + " =====");

        if(stockItems.isEmpty()){
            System.out.println("There is no furniture in the shop yet.");
            return;
        }

        for(StockItem item : stockItems){
            printStockItem(item);
        }
    }

    public void showAvailableFurnitures(){
        System.out.println("===== Available furniture =====");

        int count = 0;

        for(StockItem item : stockItems){
            if(item.getQuantity() > 0){
                printStockItem(item);
                count++;
            }
        }

        if(count == 0){
            System.out.println("No furniture is available right now.");
        }
    }

    public void showLowStock(){
        System.out.println("===== Low stock (" + LOW_STOCK_LIMIT + " or less) =====");

        int count = 0;

        for(StockItem item : stockItems){
            if(item.getQuantity() <= LOW_STOCK_LIMIT){
                printStockItem(item);
                count++;
            }
        }

        if(count == 0){
            System.out.println("No products have low stock.");
        }
    }

    public void showCheaperThan(double maxPrice){
        System.out.println("===== Furniture with price up to " + maxPrice + " =====");

        int count = 0;

        for(StockItem item : stockItems){
            if(item.getFurniture().getPrice() <= maxPrice){
                printStockItem(item);
                count++;
            }
        }

        if(count == 0){
            System.out.println("No furniture was found for this price.");
        }
    }

    public void showByCategory(){
        for(Category category : Category.values()){
            List<StockItem> group = stockByCategory.get(category);

            System.out.println("===== " + category + " (" + group.size() + ") =====");

            for(StockItem item : group){
                System.out.println(item.getFurniture().getFurID() + " - "
                        + item.getFurniture().getFurName() + ", stock: " + item.getQuantity());
            }
        }
    }

    public void showSortedNatural(){
        List<Furniture> list = getFurnitureList();
        Collections.sort(list);
        printFurnitureList("Natural order (ID)", list);
    }


    public void showSorted(String title, Comparator<Furniture> comparator){
        List<Furniture> list = getFurnitureList();
        Collections.sort(list, comparator);
        printFurnitureList(title, list);
    }

    private List<Furniture> getFurnitureList(){
        List<Furniture> list = new ArrayList<>();

        for(StockItem item : stockItems){
            list.add(item.getFurniture());
        }
        return list;
    }

    private void printFurnitureList(String title, List<Furniture> list){
        System.out.println("===== " + title + " =====");

        if(list.isEmpty()){
            System.out.println("There is no furniture in the shop yet.");
            return;
        }

        for(Furniture furniture : list){
            System.out.printf("%d | %s | %s | price: %.2f | stock: %d%n",
                    furniture.getFurID(), furniture.getFurName(), furniture.getCategory(),
                    furniture.getPrice(), getStockQuantity(furniture.getFurID()));
        }
    }


    public int getMaterialCount(){
        return materials.size();
    }

    public Material getMaterial(int index){
        if(index < 0 || index >= materials.size()){
            return null;
        }
        return materials.get(index);
    }

    public void addMaterial(String name, double price) throws ShopException{
        if(price < 0){
            throw new ShopException("Material price cannot be negative");
        }

        Material material = new Material(name, price);

        if(materials.contains(material)){
            throw new ShopException("This material already exists");
        }

        materials.add(material);
        System.out.println("Material " + material.getName() + " was added");
    }

    public void setMaterialPrice(int index, double price) throws ShopException{
        Material material = getMaterial(index);

        if(material == null){
            throw new ShopException("Invalid material number.");
        }
        if(price < 0){
            throw new ShopException("Material price cannot be negative");
        }
        material.setAdditionalPrice(price);
    }

    public void showMaterials(){
        System.out.println("===== Materials =====");

        if(materials.isEmpty()){
            System.out.println("There are no materials.");
            return;
        }

        for(int i = 0; i < materials.size(); i++){
            System.out.print((i + 1) + ". ");
            materials.get(i).displayInfo();
        }
    }

    public Customer addCustomer(int id, String name) throws ShopException{
        if(customers.containsKey(id)){
            throw new ShopException("A customer with ID " + id + " already exists");
        }
        Customer customer = new Customer(id, name);
        customers.put(id, customer);
        return customer;
    }

    public Seller addSeller(int id, String name) throws ShopException{
        if(sellers.containsKey(id)){
            throw new ShopException("A seller with ID " + id + " already exists");
        }
        Seller seller = new Seller(id, name);
        sellers.put(id, seller);
        return seller;
    }

    public Customer findCustomer(int id){
        return customers.get(id);
    }

    public Seller findSeller(int id){
        return sellers.get(id);
    }

    public void showCustomers(){
        for(Customer customer : customers.values()){
            System.out.println(customer.getId() + " - " + customer.getName());
        }
    }

    public void showSellers(){
        for(Seller seller : sellers.values()){
            System.out.println(seller.getId() + " - " + seller.getName());
        }
    }

    public FurnitureOrder placeOrder(Customer customer, String paymentMethod,
                                     boolean express) throws ShopException{
        ShoppingCart cart = customer.getCart();

        if(cart.isEmpty()){
            throw new ShopException("Your cart is empty. Add furniture first.");
        }

        for(CartItem item : cart.getItems()){
            int id = item.getFurniture().getFurID();
            int wanted = cart.getQuantityForFurniture(id);
            int stock = getStockQuantity(id);

            if(wanted > stock){
                throw new ShopException("Not enough stock for " + item.getFurniture().getFurName()
                        + ". In cart: " + wanted + ", in stock: " + stock);
            }
        }

        FurnitureOrder order = new FurnitureOrder(nextOrderId, customer, cart.getItems(),
                paymentMethod, express);
        nextOrderId++;

        ordersById.put(order.getOrderID(), order);
        pendingOrders.offer(order);
        customer.getOrders().add(order);
        cart.clear();
        return order;
    }


    public FurnitureOrder processNextOrder(Seller seller){
        FurnitureOrder order = pendingOrders.poll();

        if(order == null){
            return null;
        }

        for(CartItem item : order.getItems()){
            int id = item.getFurniture().getFurID();
            int wanted = order.getQuantityForFurniture(id);
            int stock = getStockQuantity(id);

            if(wanted > stock){
                order.setStatus(OrderStatus.REJECTED, "Not enough stock for "
                        + item.getFurniture().getFurName() + " (needed " + wanted
                        + ", in stock " + stock + ")");
                seller.orderProcessed();
                return order;
            }
        }


        for(CartItem item : order.getItems()){
            StockItem stockItem = findStockItem(item.getFurniture().getFurID());
            stockItem.setQuantity(stockItem.getQuantity() - item.getQuantity());
        }

        order.setStatus(OrderStatus.COMPLETED, "Processed by " + seller.getName());
        seller.orderProcessed();
        return order;
    }

    public int getPendingCount(){
        return pendingOrders.size();
    }

    public void showPendingOrders(){
        System.out.println("===== Orders in the queue =====");

        if(pendingOrders.isEmpty()){
            System.out.println("The queue is empty.");
            return;
        }

        List<FurnitureOrder> list = new ArrayList<>(pendingOrders);
        Collections.sort(list, new OrderPriorityComparator());

        for(FurnitureOrder order : list){
            order.displayShort();
        }
    }

    public void showOrders(List<FurnitureOrder> orders){
        if(orders.isEmpty()){
            System.out.println("No orders.");
            return;
        }

        for(FurnitureOrder order : orders){
            order.displayShort();
        }
    }

    public FurnitureOrder findOrder(int orderID){
        return ordersById.get(orderID);
    }


    public void showStatistics(){
        double stockValue = 0;

        for(StockItem item : stockItems){
            stockValue += item.getFurniture().getPrice() * item.getQuantity();
        }

        int completed = 0;
        int rejected = 0;
        double revenue = 0;

        Set<Customer> buyers = new HashSet<>();

        for(FurnitureOrder order : ordersById.values()){
            buyers.add(order.getCustomer());

            if(order.getStatus() == OrderStatus.COMPLETED){
                completed++;
                revenue += order.getFinalTotal();
            }
            else if(order.getStatus() == OrderStatus.REJECTED){
                rejected++;
            }
        }

        System.out.println("===== Statistics =====");
        System.out.println("Different products: " + stockItems.size());
        for(Category category : Category.values()){
            System.out.println("   " + category + ": " + stockByCategory.get(category).size());
        }
        System.out.printf("Value of the stock (base prices): %.2f%n", stockValue);
        System.out.println("Customers: " + customers.size());
        System.out.println("Customers who made orders: " + buyers.size());
        System.out.println("Orders in the queue: " + pendingOrders.size());
        System.out.println("Completed orders: " + completed);
        System.out.println("Rejected orders: " + rejected);
        System.out.printf("Revenue (completed orders): %.2f%n", revenue);

        if(completed > 0){
            System.out.printf("Average completed order: %.2f%n", revenue / completed);
        }
    }
}
