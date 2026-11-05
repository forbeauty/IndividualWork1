import java.util.Comparator;

public class PriceComparator implements Comparator<Furniture>{

    @Override
    public int compare(Furniture a, Furniture b){
        return Double.compare(a.getPrice(), b.getPrice());
    }
}
