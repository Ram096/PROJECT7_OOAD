// Dough Masters pizza class
/*
Dough Masters class represents the logistics of staff, inventory and pizza orders

 */
import javax.security.sasl.SaslException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class DoughMasters implements SysOut {
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

    double getBudget() {
        return budget;
    }

    void moneyIn(double cash) {
        budget += cash;
    }

    void moneyOut(double cash) {
        budget -= cash;
        if (budget < 0) {
            budget += 50000;
            out("***Budget overrun*** Added 50K, budget now: " + Utility.asDollar(budget));
        }
    }

    void openEveryDay(Enums.DayOfWeek day) {
        out("------------ DOUGH MASTERS -------------");
        out("Our Pizza store is opening...");
        out("Checking our inventory...\n");
        out("Checking our Staff...\n");
        hireNewStaff();
    }

    // see if we need any new hires
    void hireNewStaff() {
        final int numberInStaff = 3;
        for (Enums.StaffType t : Enums.StaffType.values()) {
            int typeInList = Staff.howManyStaffByType(staff, t);
            int need = numberInStaff - typeInList;
            for (int i = 1; i<=need; ++i) addStaff(t);
        }
    }

    void addStaff(Enums.StaffType t) {
        Staff newStaff = null;
        if (t == Enums.StaffType.Cook) newStaff = new Cook();
        if (t == Enums.StaffType.Cashier) newStaff = new Cashier();
        if (t == Enums.StaffType.Driver) newStaff = new Driver();
        if (t == Enums.StaffType.Manager) newStaff = new Manager();
        out("Hired a new "+newStaff.type+" named "+ newStaff.name);
        staff.add(newStaff);
    }

    void setPizza() {
        Map<Enums.Topping, Integer> toppingsInventory = new HashMap<>();
        toppingsInventory.put(Enums.Topping.mushroom, 5);
        toppingsInventory.put(Enums.Topping.olives, 3);
        toppingsInventory.put(Enums.Topping.bacon, 7);

        Map<Enums.Sauce, Integer> saucesInventory = new HashMap<>();
        saucesInventory.put(Enums.Sauce.marinara, 10);
        saucesInventory.put(Enums.Sauce.alfredo, 2);

        Map<Enums.Crust, Integer> crustsInventory = new HashMap<>();
        crustsInventory.put(Enums.Crust.thin, 8);
        crustsInventory.put(Enums.Crust.regular, 5);

        Inventory inventory = new Inventory(toppingsInventory, saucesInventory, crustsInventory);

        // Create a pizza and check if the inventory gets updated
        List<Enums.Size> pizzaSizes = List.of(Enums.Size.xLarge);
        List<Enums.Crust> crusts = List.of(Enums.Crust.regular);
        List<Enums.Sauce> sauces = List.of(Enums.Sauce.alfredo);
        List<Enums.Topping> toppings = List.of(Enums.Topping.bacon, Enums.Topping.mushroom, Enums.Topping.olives);

        Pizza pizza = new customerPizza(pizzaSizes, crusts, crustsInventory, sauces, saucesInventory, toppings, toppingsInventory);

        pizza.makePizza(Enums.Crust.thin, Enums.Sauce.alfredo, toppings);

        String toppingsList = String.join(", ", toppings.stream().map(Enum::toString).toArray(String[]::new));

        out("Toppings inventory after making pizza: " + inventory.getToppingsInventory());
        out("Sauces inventory after making pizza: " + inventory.getSaucesInventory());
        out("Crusts inventory after making pizza: " + inventory.getCrustsInventory());
        out("This is the inventory" + inventory.getInventory());
        out("The customer ordered a "+ pizzaSizes.get(0) +" pizza, that has " + crusts.get(0) + " crust, It has " + sauces.get(0) + " sauce, with " + toppings.size() + " toppings being: " + toppingsList + " All for " + Utility.asDollar(pizza.getPrice(toppings, pizzaSizes)));

    }
}
