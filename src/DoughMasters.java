import javax.security.sasl.SaslException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
// Copyright = Bruce Montgomery - OOAD Spring 2023 Project 2 - Parts of FNCD CLASS
public class DoughMasters implements SysOut{
    ArrayList<Staff> staff;  // folks working
    ArrayList<Staff> departedStaff;   // folks that left
    private double budget;   // big money pile
    //add ingredient inventory perhaps & money made

    DoughMasters() {
        staff = new ArrayList<>();
        departedStaff = new ArrayList<>();
        /*
        ADD INGREDIENT INVENTORY
         */
        budget = 150000;  // I changed this just to see additions to the budget happen
    }

    double getBudget() {
        return budget;    // I'm keeping this private to be on the safe side
    }
    void moneyIn(double cash) {  // Nothing special about taking money in yet
        budget += cash;
    }
    void moneyOut(double cash) {   // I check for budget overruns on every payout
        budget -= cash;
        if (budget<0) {
            budget += 75000;
            out("***Budget overrun*** Added $75K, budget now: " + Utility.asDollar(budget));
        }
    }

    void updateStaffandInventory() {
        hireNewStaff();    // hire up to 3 of each staff type
        //updateInventory()
    }

    void hireNewStaff() {
        final int numberInStaff = 3;
        for (Enums.StaffType t : Enums.StaffType.values()) {
            int typeInList = Staff.howManyStaffByType(staff, t);
            int need = numberInStaff - typeInList;
            for (int i = 1; i<=need; ++i) addStaff(t);
        }
    }

    void addStaff(Enums.StaffType t) {
        //make this more readable
        Staff newStaff = null;
        CashierCreator cashierCreator = new CashierCreator();//creating staff using factory pattern
        Staff cashier = cashierCreator.createStaff();

        CookCreator cookCreator = new CookCreator();//creating staff using factory pattern
        Staff cook = cookCreator.createStaff();

        ManagerCreator managerCreator = new ManagerCreator();//creating staff using factory pattern
        Staff manager = managerCreator.createStaff();

        DriverCreator driverCreator = new DriverCreator();//creating staff using factory pattern
        Staff driver = driverCreator.createStaff();

        if (t == Enums.StaffType.Cashier) newStaff = cashier;
        if (t == Enums.StaffType.Cook) newStaff = cook;
        if (t == Enums.StaffType.Manager) newStaff = manager;
        if (t == Enums.StaffType.Driver) newStaff = driver;

        out("Hired a new "+newStaff.type+" named "+ newStaff.name);
        staff.add(newStaff);
    }

//    void updateInventory() {
//        final int numberInInventory = 6;
//        for (Enums.VehicleType t : Enums.VehicleType.values()) {
//            int typeInList = Vehicle.howManyVehiclesByType(inventory, t);
//            int need = numberInInventory - typeInList;
//            for (int i = 1; i<=need; ++i) addVehicle(t);
//        }
//
//    }

}
