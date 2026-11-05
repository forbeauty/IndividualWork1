import java.util.Scanner;

public class InputHelper{
    private static Scanner scanner = new Scanner(System.in);

    public static String readLine(String message){
        System.out.print(message);
        return scanner.nextLine().trim();
    }

    public static String readText(String message){
        while(true){
            String text = readLine(message);

            if(text.equals("")){
                System.out.println("Text cannot be empty. Try again.");
            }
            else{
                return text;
            }
        }
    }

    public static int readInt(String message){
        while(true){
            String text = readLine(message);

            try{
                return Integer.parseInt(text);
            }
            catch(NumberFormatException e){
                System.out.println("Please enter a whole number.");
            }
        }
    }

    public static int readPositiveInt(String message){
        while(true){
            int number = readInt(message);

            if(number > 0){
                return number;
            }
            System.out.println("The number must be greater than 0.");
        }
    }

    public static int readNonNegativeInt(String message){
        while(true){
            int number = readInt(message);

            if(number >= 0){
                return number;
            }
            System.out.println("The number cannot be negative.");
        }
    }

    public static double readDouble(String message){
        while(true){
            String text = readLine(message);

            try{
                double number = Double.parseDouble(text);

                if(Double.isNaN(number) || Double.isInfinite(number)){
                    System.out.println("Please enter a valid number.");
                }
                else{
                    return number;
                }
            }
            catch(NumberFormatException e){
                System.out.println("Please enter a number (example: 12.5).");
            }
        }
    }

    public static double readPositiveDouble(String message){
        while(true){
            double number = readDouble(message);

            if(number > 0){
                return number;
            }
            System.out.println("The number must be greater than 0.");
        }
    }

    public static double readNonNegativeDouble(String message){
        while(true){
            double number = readDouble(message);

            if(number >= 0){
                return number;
            }
            System.out.println("The number cannot be negative.");
        }
    }

    public static boolean readYesNo(String message){
        while(true){
            String text = readLine(message + " (y/n): ");

            if(text.equalsIgnoreCase("y") || text.equalsIgnoreCase("yes")){
                return true;
            }
            if(text.equalsIgnoreCase("n") || text.equalsIgnoreCase("no")){
                return false;
            }
            System.out.println("Please type y or n.");
        }
    }
}
