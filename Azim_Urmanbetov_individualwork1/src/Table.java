public class Table extends Furniture{
    private double length;
    private double width;

    public Table(int furID, String furName, double price, double weight,
                 double length, double width){
        super(furID, furName, price, weight);
        setLength(length);
        setWidth(width);
    }

    public double getLength(){
        return length;
    }

    public double getWidth(){
        return width;
    }

    public void setLength(double length){
        if(length <= 0){
            throw new IllegalArgumentException("Length must be greater than 0");
        }
        this.length = length;
    }

    public void setWidth(double width){
        if(width <= 0){
            throw new IllegalArgumentException("Width must be greater than 0");
        }
        this.width = width;
    }

    @Override
    public void displayInfo(){
        super.displayInfo();
        System.out.println("Length: " + length + " m");
        System.out.println("Width: " + width + " m");
        System.out.println();
    }

    public double calculateArea(){
        return length * width;
    }

    @Override
    public double calculateSpecialPrice(){
        return getPrice() + calculateArea() * 10;
    }

    @Override
    public Category getCategory(){
        return Category.TABLE;
    }

    @Override
    public void showCategory(){
        System.out.println("Category: Table");
    }

    @Override
    public double calculateDiscount(){
        return getPrice() * 0.05;
    }

    @Override
    public void assemble(){
        System.out.println("Table " + getFurID() + " - " + getFurName()
                + " is being assembled");
    }

    @Override
    public void clean(){
        System.out.println("Table " + getFurID() + " - " + getFurName()
                + " is being cleaned");
    }

    @Override
    public void move(){
        System.out.println("Table " + getFurID() + " - " + getFurName()
                + " is being moved");
    }

    public void pricePerSquareMeter(){
        if(calculateArea() > 0){
            System.out.println("Price per square meter: "
                    + getPrice() / calculateArea());
        }
    }
}