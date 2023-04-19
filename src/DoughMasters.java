// Dough Masters pizza class
/*
Dough Masters class represents the logistics of staff, inventory and pizza orders

 */



import java.util.ArrayList;
import java.util.Collections;

public class DoughMasters implements SysOut{
    ArrayList<Staff> staff;
    ArrayList<Staff> departedStaff;
    static ArrayList<Staff> inventory;
    private double budget;

    DoughMasters() {
        staff = new ArrayList<>();
        departedStaff = new ArrayList<>();
        inventory = new ArrayList<>();
        budget = 100000;
    }

    void hireNewStaff() {
        final int numberInStaff = 2;
        for (Enums.StaffType t : Enums.StaffType.values()) {
            int typeInList = Staff.howManyStaffByType(staff, t);
            int need = numberInStaff - typeInList;
            for (int i = 1; i<=need; ++i) addStaff(t);
        }
    }

    void addStaff(Enums.StaffType employed) {
        Staff newStaff = null;
        if (employed == Enums.StaffType.Cook) newStaff = new Cook();
        if (employed == Enums.StaffType.Cashier) newStaff = new Cashier();
        if (employed == Enums.StaffType.Driver) newStaff = new Driver();
        if (employed == Enums.StaffType.Manager) newStaff = new Manager();
        out("Hired a new "+newStaff.type+" named "+ newStaff.name);
        staff.add(newStaff);
    }


    /*
    double getBudget() {
        return budget;
    }
    void moneyIn(double cash) {
        budget += cash;
    }
    void moneyOut(double cash) {
        budget -= cash;
        if (budget < 0) {
            budget += 100000;
            out("** Budget overrun ** Added $100K, budget now: " + Utility.asDollar(budget));
        }
    }
    */


}
