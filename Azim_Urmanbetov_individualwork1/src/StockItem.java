public class StockItem{
    private Furniture furniture;
    private int quantity;

    public StockItem(Furniture furniture, int quantity){
        this.furniture = furniture;
        setQuantity(quantity);
    }

    public Furniture getFurniture(){
        return furniture;
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(!(o instanceof StockItem)){
            return false;
        }
        StockItem other = (StockItem) o;
        return furniture.equals(other.furniture);
    }

    @Override
    public int hashCode(){
        return furniture.hashCode();
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int quantity){
        if(quantity < 0){
            throw new IllegalArgumentException("Stock quantity cannot be negative");
        }
        this.quantity = quantity;
    }
}
