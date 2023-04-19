import java.util.Arrays;
import java.util.List;

public class CashierCreator implements StaffCreator {//setting up factory pattern creation for Cashiers
    static List<String> names = Arrays.asList("Jason", "Sue", "Marcus", "Jimmy");
    private static Namer namer = new Namer(names);

    @Override
    public Staff createStaff() {
        Cashier cashier = new Cashier();
        cashier.name = namer.getNext(); // set the mechanic's name
        return cashier;
    }
}
