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
    List<Enums.Topping> prefTopping;
    static List<String> names = Arrays.asList("Gunna", "Josie", "Alex", "Diana");
    static Namer namer = new Namer(names);

    Customer(){
        type = Utility.randomEnum(Enums.BuyerType.class);
        name = namer.getNext();
        out("Their name is "+name);
        prefSize = Utility.randomEnum(Enums.Size.class);
        out("They prefer a size of "+prefSize);
        prefCrust = Utility.randomEnum(Enums.Crust.class);
        out("They prefer a crust of "+prefCrust);
        prefSauce = Utility.randomEnum(Enums.Sauce.class);
        out("They prefer a sauce of "+prefSauce);
        prefTopping = getToppings();
        out("They prefer toppings of "+prefTopping);
    }

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
}
