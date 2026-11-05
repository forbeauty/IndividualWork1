import java.util.Comparator;

public class NameComparator implements Comparator<Furniture>{

    @Override
    public int compare(Furniture a, Furniture b){
        return a.getFurName().compareToIgnoreCase(b.getFurName());
    }
}
