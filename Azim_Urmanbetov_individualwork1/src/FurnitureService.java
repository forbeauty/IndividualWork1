public class FurnitureService{

    public void printFurniture(Furniture furniture){
        System.out.println("===== Furniture Service =====");
        furniture.displayInfo();
    }

    public void showFinalPrice(Furniture furniture){
        double finalPrice = furniture.getPrice()
                - furniture.calculateDiscount();

        System.out.println("Product: " + furniture.getFurName());
        System.out.println("Discount: " + furniture.calculateDiscount());
        System.out.println("Final price: " + finalPrice);
    }

    public double calculateUnitPrice(Furniture furniture, Material material){
        return furniture.getPrice() + material.getAdditionalPrice();
    }

    public void showProductDetails(Furniture furniture){
        printFurniture(furniture);
        furniture.showCategory();
        System.out.printf("Base price: %.2f (material is extra)%n", furniture.getPrice());
        System.out.printf("Discount per unit: %.2f%n", furniture.calculateDiscount());
        System.out.printf("Base price after discount: %.2f%n",
                furniture.getPrice() - furniture.calculateDiscount());
        System.out.printf("Special price (own calculation, not used in the cart): %.2f%n",
                furniture.calculateSpecialPrice());
    }
}
