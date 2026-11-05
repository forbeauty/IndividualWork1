import java.util.ArrayList;
import java.util.List;

public class FurnitureOrder{
    private int orderID;
    private Customer customer;
    private List<CartItem> items;
    private OrderDetails details;
    private boolean express;
    private OrderStatus status;
    private String note;

    public FurnitureOrder(int orderID, Customer customer, List<CartItem> cartItems,
                          String paymentMethod, boolean express){
        this.orderID = orderID;
        this.customer = customer;
        this.express = express;
        this.status = OrderStatus.PENDING;
        this.note = "";
        items = new ArrayList<>();

        int totalQuantity = 0;

        for(CartItem item : cartItems){
            items.add(item);
            totalQuantity += item.getQuantity();
        }

        details = new OrderDetails(totalQuantity, paymentMethod);
    }

    public int getOrderID(){
        return orderID;
    }

    public Customer getCustomer(){
        return customer;
    }

    public List<CartItem> getItems(){
        return items;
    }

    public boolean isExpress(){
        return express;
    }

    public OrderStatus getStatus(){
        return status;
    }

    public String getNote(){
        return note;
    }

    public void setStatus(OrderStatus status, String note){
        this.status = status;
        this.note = note;
    }

    public int getQuantityForFurniture(int furID){
        int total = 0;

        for(CartItem item : items){
            if(item.getFurniture().getFurID() == furID){
                total += item.getQuantity();
            }
        }

        return total;
    }

    public double getSubtotal(){
        double sum = 0;

        for(CartItem item : items){
            sum += item.getLineSubtotal();
        }

        return sum;
    }

    public double getTotalDiscount(){
        double sum = 0;

        for(CartItem item : items){
            sum += item.getLineDiscount();
        }

        return sum;
    }

    public double getFinalTotal(){
        return getSubtotal() - getTotalDiscount();
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(!(o instanceof FurnitureOrder)){
            return false;
        }
        FurnitureOrder other = (FurnitureOrder) o;
        return orderID == other.orderID;
    }

    @Override
    public int hashCode(){
        return Integer.hashCode(orderID);
    }

    // One line for lists
    public void displayShort(){
        String type = "STANDARD";
        if(express){
            type = "EXPRESS";
        }

        System.out.printf("Order %d | %s | %s | customer: %s | total: %.2f%n",
                orderID, type, status, customer.getName(), getFinalTotal());
        if(!note.equals("")){
            System.out.println("   Note: " + note);
        }
    }

    public void printReceipt(String shopName){
        System.out.println();
        System.out.println("============== RECEIPT ==============");
        System.out.println("Shop: " + shopName);
        System.out.println("Order ID: " + orderID);
        System.out.println("Customer: " + customer.getName());
        System.out.println("=====================================");

        for(int i = 0; i < items.size(); i++){
            CartItem item = items.get(i);

            System.out.println((i + 1) + ". " + item.getFurniture().getFurName()
                    + " (ID " + item.getFurniture().getFurID() + ")");
            System.out.println("   Material: " + item.getMaterial().getName());
            System.out.println("   Quantity: " + item.getQuantity());
            System.out.printf("   Price per unit before discount: %.2f%n", item.getUnitPrice());
            System.out.printf("   Discount: %.2f%n", item.getLineDiscount());
            System.out.printf("   Final line total: %.2f%n", item.getLineTotal());
            System.out.println("-------------------------------------");
        }

        System.out.printf("Subtotal: %.2f%n", getSubtotal());
        System.out.printf("Total discount: %.2f%n", getTotalDiscount());
        System.out.printf("FINAL AMOUNT TO PAY: %.2f%n", getFinalTotal());
        System.out.println("Payment method: " + details.getPaymentMethod());
        System.out.println("=====================================");
    }
}
