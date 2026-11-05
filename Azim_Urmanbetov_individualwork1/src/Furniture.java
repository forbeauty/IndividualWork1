import java.util.List;
abstract class Furniture implements Furnishable, Discountable, Comparable<Furniture>{
    private int furID;
    private String furName;
    private double price;
    private double weight;

    public Furniture(){
        this.furID = 100;
        this.furName = "Furniture";
        this.price = 0;
        this.weight = 0.1;
    }

    public Furniture(int furID, String furName, double price, double weight){
        setFurID(furID);
        setFurName(furName);
        setPrice(price);
        setWeight(weight);
    }

    public int getFurID(){
        return furID;
    }

    public String getFurName(){
        return furName;
    }

    public double getPrice(){
        return price;
    }

    public double getWeight(){
        return weight;
    }

    private void setFurID(int furID){
        if(furID <= 0){
            throw new IllegalArgumentException("Furniture ID can't be less or equal 0");
        }
        this.furID = furID;
    }

    public void setFurName(String furName){
        this.furName = furName;
    }

    public void setPrice(double price){
        if(price < 0){
            throw new IllegalArgumentException("Price cannot be negative");
        }
        this.price = price;
    }

    public void setWeight(double weight){
        if(weight <= 0){
            throw new IllegalArgumentException("Weight cannot be less or equal 0");
        }
        this.weight = weight;
    }

    public void displayInfo(){
        System.out.println("Furniture ID: " + furID);
        System.out.println("Furniture name: " + furName);
        System.out.println("Price: " + price);
        System.out.println("Weight: " + weight + " kg");
    }



    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(!(o instanceof Furniture)){
            return false;
        }
        Furniture other = (Furniture) o;
        return furID == other.furID;
    }

    @Override
    public int hashCode(){
        return Integer.hashCode(furID);
    }

    @Override
    public int compareTo(Furniture other){
        return Integer.compare(furID, other.furID);
    }

    public abstract Category getCategory();

    public abstract double calculateSpecialPrice();

    public abstract void showCategory();

    public abstract double calculateDiscount();

    public static double calculateTotal(List<Furniture> furnitures){
        double sum = 0;

        for(Furniture furniture : furnitures){
            sum += furniture.getPrice();
        }

        return sum;
    }

    public static double calculateWeight(List<Furniture> furnitures){
        double weight = 0;

        for(Furniture furniture : furnitures){
            weight += furniture.getWeight();
        }

        return weight;
    }
}