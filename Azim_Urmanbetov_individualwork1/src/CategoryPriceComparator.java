import java.util.Comparator;


public class CategoryPriceComparator implements Comparator<Furniture>{

    @Override
    public int compare(Furniture a, Furniture b){
        int result = a.getCategory().compareTo(b.getCategory());

        if(result != 0){
            return result;
        }
        return Double.compare(a.getPrice(), b.getPrice());
    }
}
