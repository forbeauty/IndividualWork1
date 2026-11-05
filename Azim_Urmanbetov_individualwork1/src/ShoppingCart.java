import java.util.ArrayList;
import java.util.List;

public class ShoppingCart{
    private List<CartItem> items;

    public ShoppingCart(){
        items = new ArrayList<>();
    }

    public List<CartItem> getItems(){
        return items;
    }

    public int getItemCount(){
        return items.size();
    }

    public boolean isEmpty(){
        return items.size() == 0;
    }

    public CartItem getItem(int index){
        if(index < 0 || index >= items.size()){
            return null;
        }
        return items.get(index);
    }

    public void addItem(Furniture furniture, Material material, int quantity){
        CartItem newItem = new CartItem(furniture, material, quantity);
        int index = items.indexOf(newItem);

        if(index >= 0){
            CartItem oldItem = items.get(index);
            oldItem.setQuantity(oldItem.getQuantity() + quantity);
        }
        else{
            items.add(newItem);
        }
    }

    public boolean removeItem(int index){
        if(index < 0 || index >= items.size()){
            return false;
        }
        items.remove(index);
        return true;
    }


    public int getQuantityForFurniture(int furID){
        int total = 0;

        for(int i = 0; i < items.size(); i++){
            if(items.get(i).getFurniture().getFurID() == furID){
                total += items.get(i).getQuantity();
            }
        }

        return total;
    }

    public double getSubtotal(){
        double sum = 0;

        for(int i = 0; i < items.size(); i++){
            sum += items.get(i).getLineSubtotal();
        }

        return sum;
    }

    public double getTotalDiscount(){
        double sum = 0;

        for(int i = 0; i < items.size(); i++){
            sum += items.get(i).getLineDiscount();
        }

        return sum;
    }

    public double getFinalTotal(){
        return getSubtotal() - getTotalDiscount();
    }

    public void clear(){
        items.clear();
    }

    public void displayCart(){
        System.out.println("===== Shopping Cart =====");

        if(items.size() == 0){
            System.out.println("Your cart is empty.");
            return;
        }

        for(int i = 0; i < items.size(); i++){
            CartItem item = items.get(i);

            System.out.println((i + 1) + ". " + item.getFurniture().getFurName()
                    + " (ID " + item.getFurniture().getFurID() + ")");
            System.out.printf("   Material: %s (+%.2f per unit)%n",
                    item.getMaterial().getName(), item.getMaterialPrice());
            System.out.println("   Quantity: " + item.getQuantity());
            System.out.printf("   Price per unit (base + material): %.2f%n", item.getUnitPrice());
            System.out.printf("   Line subtotal: %.2f%n", item.getLineSubtotal());
            System.out.printf("   Line discount: %.2f%n", item.getLineDiscount());
            System.out.printf("   Line total: %.2f%n", item.getLineTotal());
        }

        System.out.println("-------------------------");
        System.out.printf("Subtotal: %.2f%n", getSubtotal());
        System.out.printf("Total discount: %.2f%n", getTotalDiscount());
        System.out.printf("Final total: %.2f%n", getFinalTotal());
    }
}
