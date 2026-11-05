import java.util.ArrayList;
import java.util.List;


public class Customer{
    private int id;
    private String name;
    private ShoppingCart cart;
    private List<FurnitureOrder> orders;

    public Customer(int id, String name){
        this.id = id;
        this.name = name;
        cart = new ShoppingCart();
        orders = new ArrayList<>();
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public ShoppingCart getCart(){
        return cart;
    }

    public List<FurnitureOrder> getOrders(){
        return orders;
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(!(o instanceof Customer)){
            return false;
        }
        Customer other = (Customer) o;
        return id == other.id;
    }

    @Override
    public int hashCode(){
        return Integer.hashCode(id);
    }
}
