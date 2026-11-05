
public class CartItem{
    private Furniture furniture;
    private Material material;
    private int quantity;

    public CartItem(Furniture furniture, Material material, int quantity){
        this.furniture = furniture;
        this.material = material;
        setQuantity(quantity);
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(!(o instanceof CartItem)){
            return false;
        }
        CartItem other = (CartItem) o;
        return furniture.equals(other.furniture) && material.equals(other.material);
    }

    @Override
    public int hashCode(){
        return furniture.hashCode() * 31 + material.hashCode();
    }

    public Furniture getFurniture(){
        return furniture;
    }

    public Material getMaterial(){
        return material;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int quantity){
        if(quantity < 1){
            throw new IllegalArgumentException("Quantity must be at least 1");
        }
        this.quantity = quantity;
    }


    public double getBasePrice(){
        return furniture.getPrice();
    }


    public double getMaterialPrice(){
        return material.getAdditionalPrice();
    }

    public double getUnitPrice(){
        return getBasePrice() + getMaterialPrice();
    }

    public double getLineSubtotal(){
        return getUnitPrice() * quantity;
    }

    public double getLineDiscount(){
        return furniture.calculateDiscount() * quantity;
    }

    public double getLineTotal(){
        return getLineSubtotal() - getLineDiscount();
    }
}
