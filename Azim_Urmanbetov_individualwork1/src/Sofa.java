public class Sofa extends Furniture{
    private int seats;
    private boolean hasBed;

    public Sofa(int furID, String furName, double price, double weight,
                int seats, boolean hasBed){
        super(furID, furName, price, weight);
        setSeats(seats);
        setHasBed(hasBed);
    }

    public int getSeats(){
        return seats;
    }

    public boolean getHasBed(){
        return hasBed;
    }

    public void setSeats(int seats){
        if(seats <= 0){
            throw new IllegalArgumentException("Seats must be greater than 0");
        }
        this.seats = seats;
    }

    public void setHasBed(boolean hasBed){
        this.hasBed = hasBed;
    }

    @Override
    public void displayInfo(){
        super.displayInfo();
        System.out.println("Number of seats: " + seats);
        System.out.println("Has bed: " + hasBed);
        System.out.println();
    }

    @Override
    public double calculateSpecialPrice(){
        if(hasBed == true){
            return getPrice() + 100;
        }
        else{
            return getPrice();
        }
    }

    @Override
    public Category getCategory(){
        return Category.SOFA;
    }

    @Override
    public void showCategory(){
        System.out.println("Category: Sofa");
    }

    @Override
    public double calculateDiscount(){
        return getPrice() * 0.15;
    }

    @Override
    public void assemble(){
        System.out.println("Sofa " + getFurID() + " - " + getFurName()
                + " is being assembled");
    }

    @Override
    public void clean(){
        System.out.println("Sofa " + getFurID() + " - " + getFurName()
                + " is being cleaned");
    }

    @Override
    public void move(){
        System.out.println("Sofa " + getFurID() + " - " + getFurName()
                + " is being moved");
    }

    public void pricePerSeat(){
        if(seats > 0){
            System.out.println("Price per seat: " + getPrice() / seats);
        }
    }
}