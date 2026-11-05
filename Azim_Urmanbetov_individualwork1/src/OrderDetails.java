public class OrderDetails{
    private int quantity;
    private String paymentMethod;

    public OrderDetails(){
        quantity = 1;
        paymentMethod = "Cash";
    }

    public OrderDetails(int quantity, String paymentMethod){
        setQuantity(quantity);
        setPaymentMethod(paymentMethod);
    }

    public int getQuantity(){
        return quantity;
    }

    public String getPaymentMethod(){
        return paymentMethod;
    }

    public void setQuantity(int quantity){
        if(quantity < 1){
            throw new IllegalArgumentException("Quantity must be at least 1");
        }
        this.quantity = quantity;
    }

    public void setPaymentMethod(String paymentMethod){
        if(paymentMethod == null || paymentMethod.isEmpty()){
            throw new IllegalArgumentException("Payment method cannot be empty");
        }
        this.paymentMethod = paymentMethod;
    }

    public void displayDetails(){
        System.out.println("Total quantity: " + quantity);
        System.out.println("Payment method: " + paymentMethod);
    }
}
