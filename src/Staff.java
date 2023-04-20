// Staff class
/*
Staff class would be in control of all staff types which includes manager, cook, driver and cashier
Anything relevant to staff would also be included, such as salary, tips and days working
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Random;

public abstract class Staff implements SysOut {
    String name;
    double salary;
    double salaryEarned;
    double tipEarned;
    Enums.StaffType type;

    int daysWorked;
    boolean quit;

    Staff() {
        salaryEarned = 0;
        tipEarned = 0;
        daysWorked = 0;
        quit = false;
    }

    void setQuit(boolean quit) {
        this.quit = quit;
    }

    boolean shouldQuit() {
        return quit;
    }

    // Utility for getting Staff by type
    static ArrayList<Staff> getStaffByType(ArrayList<Staff> staffList, Enums.StaffType t) {
        ArrayList<Staff> subclassInstances = new ArrayList<>();
        for (Staff s : staffList) {
            if (s.type == t) subclassInstances.add(s);
        }

        return subclassInstances;
    }

    static int howManyStaffByType(ArrayList<Staff> staffList, Enums.StaffType t) {
        int n = 0;
        for (Staff s: staffList) {
            if (s.type == t) n++;
        }
        return n;
    }
}

class Manager extends Staff {
    static List<String> names = Arrays.asList("Joel", "Harden", "Tyrese", "Ben");
    static Namer namer = new Namer(names);
    Manager() {
        super();
        type = Enums.StaffType.Manager;
        name = namer.getNext(); // Every new manager gets a new name
        salary = 320;
    }
    //implement manager functions
}
class Cook extends Staff{
    static List<String> names = Arrays.asList("Steph", "Klay", "Jordan", "Steve");
    static Namer namer = new Namer(names);
    Cook(){
        super();
        type = Enums.StaffType.Cook;
        name = namer.getNext(); // Every new cook gets a new name
        salary = 300;
    }
    //implement cook function
}
class Cashier extends Staff{
    static List<String> names = Arrays.asList("Jason", "Sue", "Marcus", "Jimmy");
    static Namer namer = new Namer(names);
    Cashier(){
        super();
        type = Enums.StaffType.Cashier;
        name = namer.getNext();  // every new Cashier gets a new name
        salary = 200; // daily salary
    }
    //implement cashier function
}
class Driver extends Staff {
    static List<String> names = Arrays.asList("Anthony","Austin","Rui","James");
    static Namer namer = new Namer(names);
    Driver() {
        super();
        type = Enums.StaffType.Driver;
        name = namer.getNext();  // Every Driver gets a new name
        salary = 150; // daily salary
    }
    //add function for delivery orders
}