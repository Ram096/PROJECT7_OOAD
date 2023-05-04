import java.util.Arrays;
import java.util.List;

public class ManagerCreator implements StaffCreator {//setting up factory pattern creation for Managers
    static List<String> names = Arrays.asList("Joel", "Harden", "Tyrese", "Ben");
    private static Namer namer = new Namer(names);

    @Override
    public Staff createStaff() {
        Manager manager = new Manager();
        manager.name = namer.getNext(); // set the managers name
        return manager;
    }
}
