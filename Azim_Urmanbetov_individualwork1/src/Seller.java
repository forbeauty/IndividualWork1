
public class Seller{
    private int id;
    private String name;
    private int processedCount;

    public Seller(int id, String name){
        this.id = id;
        this.name = name;
        processedCount = 0;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public int getProcessedCount(){
        return processedCount;
    }

    public void orderProcessed(){
        processedCount++;
    }

    @Override
    public boolean equals(Object o){
        if(this == o){
            return true;
        }
        if(!(o instanceof Seller)){
            return false;
        }
        Seller other = (Seller) o;
        return id == other.id;
    }

    @Override
    public int hashCode(){
        return Integer.hashCode(id);
    }
}
