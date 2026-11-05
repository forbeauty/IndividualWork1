import java.util.Comparator;

public class OrderPriorityComparator implements Comparator<FurnitureOrder>{

    @Override
    public int compare(FurnitureOrder a, FurnitureOrder b){
        if(a.isExpress() && !b.isExpress()){
            return -1;
        }
        if(!a.isExpress() && b.isExpress()){
            return 1;
        }
        return Integer.compare(a.getOrderID(), b.getOrderID());
    }
}
