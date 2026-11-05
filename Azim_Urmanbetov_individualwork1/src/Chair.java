public class Chair extends Furniture{
    private String type;
    private int height;

    public Chair(int furID, String furName, double price, double weight,
                 String type, int height){
        super(furID, furName, price, weight);
        setType(type);
        setHeight(height);
    }

    public String getType(){
        return type;
    }

    public int getHeight(){
        return height;
    }

    public void setType(String type){
        if(type == null || type.isEmpty()){
            throw new IllegalArgumentException("Chair type cannot be empty");
        }
        this.type = type;
    }

    public void setHeight(int height){
        if(height <= 0){
            throw new IllegalArgumentException("Height must be greater than 0");
        }
        this.height = height;
    }

    @Override
    public void displayInfo(){
        super.displayInfo();
        System.out.println("Chair type: " + type);
        System.out.println("Chair height: " + height + " cm");
        System.out.println();
    }

    @Override
    public double calculateSpecialPrice(){
        return getPrice() + height * 0.5;
    }

    @Override
    public Category getCategory(){
        return Category.CHAIR;
    }

    @Override
    public void showCategory(){
        System.out.println("Category: Chair");
    }

    @Override
    public double calculateDiscount(){
        return getPrice() * 0.10;
    }

    @Override
    public void assemble(){
        System.out.println("Chair " + getFurID() + " - " + getFurName()
                + " is being assembled");
    }

    @Override
    public void clean(){
        System.out.println("Chair " + getFurID() + " - " + getFurName()
                + " is being cleaned");
    }

    @Override
    public void move(){
        System.out.println("Chair " + getFurID() + " - " + getFurName()
                + " is being moved");
    }

    public void showChairValue(){
        System.out.println("Chair value: " + calculateSpecialPrice());
    }
}