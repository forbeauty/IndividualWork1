public class Material{
    private String name;
    private double additionalPrice;

    public Material(String name, double additionalPrice){
        setName(name);
        setAdditionalPrice(additionalPrice);
    }

    public String getName(){
        return name;
    }

    public double getAdditionalPrice(){
        return additionalPrice;
    }

    public void setName(String name){
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("Material name cannot be empty");
        }
        this.name = name.trim();
    }

    public void setAdditionalPrice(double additionalPrice){
        if(additionalPrice < 0){
            throw new IllegalArgumentException("Material price cannot be negative");
        }
        this.additionalPrice = additionalPrice;
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(!(o instanceof Material)){
            return false;
        }
        Material other = (Material) o;
        return name.equalsIgnoreCase(other.name);
    }

    @Override
    public int hashCode(){
        return name.toLowerCase().hashCode();
    }

    public void displayInfo(){
        System.out.printf("%s - additional price: %.2f (added to the furniture price)%n",
                name, additionalPrice);
    }
}
