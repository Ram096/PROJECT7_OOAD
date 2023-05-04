import java.util.List;
import java.util.Arrays;
public class DriverCreator implements StaffCreator {//setting up factory pattern creation for Drivers
    private static List<String> names = Arrays.asList("Anthony","Austin","Rui","James");
    private static Namer namer = new Namer(names);

    @Override
    public Staff createStaff() {
        Driver driver = new Driver();
        driver.name = namer.getNext(); // set the drivers name
        return driver;
    }
}