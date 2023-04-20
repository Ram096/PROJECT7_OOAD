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
        List<String> toppings = new ArrayList<>();
        List<String> crusts = new ArrayList<>();
        List<String> sauces = new ArrayList<>();

        toppings.add("pepperoni");
        toppings.add("mushrooms");
        toppings.add("olives");

        crusts.add("thin crust");
        crusts.add("deep dish");
        crusts.add("stuffed crust");

        sauces.add("tomato sauce");
        sauces.add("ranch");
        sauces.add("alfredo");

        Map<String, Integer> toppingsInventory = new HashMap<>();
        Map<String, Integer> crustsInventory = new HashMap<>();
        Map<String, Integer> saucesInventory = new HashMap<>();
        toppingsInventory.put("pepperoni", 5);
        toppingsInventory.put("mushrooms", 7);
        toppingsInventory.put("olives", 3);

        crustsInventory.put("thin crust", 6);
        crustsInventory.put("deep dish", 7);
        crustsInventory.put("stuffed crust", 9);

        saucesInventory.put("tomato sauce", 4);
        saucesInventory.put("ranch", 3);
        saucesInventory.put("alfredo", 2);


;
        Pizza pizza = new Pizza(crusts, crustsInventory, sauces, saucesInventory, toppings, toppingsInventory);

        out(pizza.getToppingsInventory());
        out("Crust type: " + pizza.getCrustType());
        out("Sauce type: " + pizza.getSauceType());
        out("Toppings choices: " + pizza.getToppings());

        pizza.addTopping(toppings.get(0));

        out("We added this "+ toppings.get(0) + " topping to the pizza!");

        out(pizza.getToppingsInventory());

    }
}
