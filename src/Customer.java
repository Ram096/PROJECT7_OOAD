import java.util.Arrays;
import java.util.List;

public class Customer {
    String name;
    Enums.BuyerType type;
    static List<String> names = Arrays.asList("Gunna", "Josie", "Alex", "Diana");
    static Namer namer = new Namer(names);

    Customer(){
        type = Utility.randomEnum(Enums.BuyerType.class);
        name = namer.getNext();
    }
}
