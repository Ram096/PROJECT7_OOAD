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

    static List<String> names = Arrays.asList("John", "Laura", "Joe", "Sam", "Sarah");
    Manager() {
        super();
        type = Enums.StaffType.Manager;
        salary = 120;
    }
    //implement manager functions
}
class Cook extends Staff{
    Cook(){
        super();
        type = Enums.StaffType.Cook;
        salary = 80;
    }
    //implement cook function
}
class Cashier extends Staff{
    static List<String> names = Arrays.asList("Emma, Louis, Ana, Jorge");
    static Namer namer = new Namer(names);
    Cashier(){
        super();
        type= Enums.StaffType.Cashier;
        name = namer.getNext();
        salary = 75;
    }
    //implement cashier function
}
class Driver extends Staff {
    Driver() {
        super();
        type = Enums.StaffType.Driver;
        // every new salesperson gets a new name
    }
    //add function for delivery orders
}