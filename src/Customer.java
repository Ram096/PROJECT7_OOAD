import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Customer implements SysOut {
    String name;
    Enums.BuyerType type;
    Enums.Size prefSize;
    Enums.Sauce prefSauce;
    Enums.Crust prefCrust;
    Enums.Student studentStat;
    List<Enums.Topping> prefTopping;
    static List<String> names = Arrays.asList("Gunna", "Josie", "Alex", "Diana");
    static Namer namer = new Namer(names);

    Customer(){
        type = Utility.randomEnum(Enums.BuyerType.class);
        name = namer.getNext();
        prefSize = Utility.randomEnum(Enums.Size.class);
        prefCrust = Utility.randomEnum(Enums.Crust.class);
        prefSauce = Utility.randomEnum(Enums.Sauce.class);
        prefTopping = getToppings();
        studentStat = getStudent();
    }

    // Grabs a random number of toppings and then chooses that number of unique toppings and sets that as the customers preference
    public List<Enums.Topping> getToppings() {
        int rndToppingNum = Utility.rndFromRange(1, 7);
        List<Enums.Topping> availableToppings = new ArrayList<>(Arrays.asList(Enums.Topping.values()));
        List<Enums.Topping> selectedToppings = new ArrayList<>();
        while (selectedToppings.size() < rndToppingNum && availableToppings.size() > 0) {
            int rndIndex = Utility.rndFromRange(0, availableToppings.size() - 1);
            Enums.Topping topping = availableToppings.get(rndIndex);
            selectedToppings.add(topping);
            availableToppings.remove(rndIndex);
        }
        return selectedToppings;
    }

    // Randomly determines whether the customer is a student or not
    public Enums.Student getStudent() {
        double rand = Utility.rnd();
        Enums.Student studentStat;

        if (rand < 0.3) {
            studentStat = Enums.Student.student;
        } else {
            studentStat = Enums.Student.not_student;
        }
        return studentStat;
    }
}
