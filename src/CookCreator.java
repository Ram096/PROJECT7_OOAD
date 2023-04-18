import java.util.Arrays;
import java.util.List;

public class CookCreator implements StaffCreator {//setting up factory pattern creation for Cooks
    static List<String> names = Arrays.asList("Steph", "Klay", "Jordan", "Steve");
    private static Namer namer = new Namer(names);

    @Override
    public Staff createStaff() {
        Cook cook = new Cook();
        cook.name = namer.getNext(); // set the mechanic's name
        return cook;
    }
}
