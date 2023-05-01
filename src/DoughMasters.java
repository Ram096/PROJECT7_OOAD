// Dough Masters pizza class
/*
Dough Masters class represents the logistics of staff, inventory and pizza orders

 */
import javax.security.sasl.SaslException;
import javax.swing.*;
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
import java.util.Random;
public class DoughMasters implements SysOut {
    ArrayList<Staff> staff;
    ArrayList<Staff> departedStaff;
    static ArrayList<Staff> inventory1;
    private double budget;
    Map<Enums.Topping, Integer> toppingsInventory = new HashMap<>();
    Map<Enums.Sauce, Integer> saucesInventory = new HashMap<>();
    Map<Enums.Crust, Integer> crustsInventory = new HashMap<>();
    Inventory inventory = new Inventory(toppingsInventory, saucesInventory, crustsInventory);
    DoughMasters() {
        staff = new ArrayList<>();
        departedStaff = new ArrayList<>();
        inventory1 = new ArrayList<>();
        budget = 100000;
        toppingsInventory.put(Enums.Topping.mushroom, 1);
        toppingsInventory.put(Enums.Topping.olives, 1);
        toppingsInventory.put(Enums.Topping.bacon, 1);
        toppingsInventory.put(Enums.Topping.pepperoni, 1);
        toppingsInventory.put(Enums.Topping.onion, 1);
        toppingsInventory.put(Enums.Topping.peppers, 1);
        toppingsInventory.put(Enums.Topping.sausage, 1);
        saucesInventory.put(Enums.Sauce.marinara, 1);
        saucesInventory.put(Enums.Sauce.alfredo, 1);
        saucesInventory.put(Enums.Sauce.bbq, 1);
        saucesInventory.put(Enums.Sauce.ranch, 1);
        crustsInventory.put(Enums.Crust.thin, 1);
        crustsInventory.put(Enums.Crust.regular, 1);
        crustsInventory.put(Enums.Crust.deep_dish, 1);
    }

    double getBudget() {
        return budget;
    }
    double getMoneySpent() {
        double moneyOut = budget - 50000;
        return moneyOut;
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
        out("The store is starting to sell...");

        ArrayList<Customer> customers = getCustomers(day);

        // managerRestock(inventory);
        for (Customer c:customers) {
            out("Customer "+c.name+"is buying right now...");
            startPizza(c);
        }

        out("The money spent today is "+ Utility.asDollar(getMoneySpent()));

        out("The current Inventory at the end of the day"+inventory.getInventory());
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

    void startPizza(Customer c) {
        Enums.Crust crusts = c.prefCrust;
        Enums.Sauce sauces = c.prefSauce;
        List<Enums.Topping> toppings = c.prefTopping;

        Pizza pizza = new customerPizza(crustsInventory, saucesInventory, toppingsInventory);

        Boolean pizzaMake = pizza.makePizza(crusts, sauces, toppings, c);

        if(pizzaMake) {
            pizza.total = pizza.getPrice(c.prefTopping, c.prefSize);
            if(Math.random() <= 0.65){
                out("Extra sauce for 2% of pizza total has been added! Old pizza total: "+ Utility.asDollar(pizza.total));
                ExtraSauce extraSauce = new ExtraSauce(pizza);
                pizza.total = extraSauce.getPrice();
                out("New Pizza Total: "+ Utility.asDollar(pizza.total));
            }
            if(Math.random() <= .10){
                out("Fast Order for 50% of pizza total has been added! Pizza order will now take priority and be made fast! Old pizza total: " + Utility.asDollar(pizza.total));
                FastOrder fastOrder =  new FastOrder(pizza);
                pizza.total = fastOrder.getPrice();
                out("New Pizza Total: "+ pizza.total);
            }
            if(Math.random() <= .15){
                out("Extra meat toppings for 20% of pizza total has been added! More sausage, bacon and pepperoni have been added Old pizza total: "+ Utility.asDollar(pizza.total));
                MeatToppings meatToppings = new MeatToppings(pizza);
                pizza.total = meatToppings.getPrice();
                out("New Pizza Total: " + Utility.asDollar(pizza.total));
            }
            if(Math.random() <= .20){
                out("Extra cheese for 10% of pizza total has been added! Old pizza total: "+ Utility.asDollar(pizza.total));
                ExtraCheese extraCheese = new ExtraCheese(pizza);
                pizza.total = extraCheese.getPrice();
                out("New Pizza Total: "+ Utility.asDollar(pizza.total));
            }
            String toppingsList = String.join(", ", c.prefTopping.stream().map(Enum::toString).toArray(String[]::new));
            out("The customer ordered a " + c.prefSize + " pizza, that has " + c.prefCrust + " crust, It has " + c.prefCrust + " sauce, with " + c.prefTopping.size() + " toppings being: " + toppingsList + " All for " + Utility.asDollar(pizza.total));
            moneyIn(pizza.getPrice(c.prefTopping, c.prefSize));
        }
    }

    ArrayList<Customer> getCustomers(Enums.DayOfWeek day) {
        // 0 to 5 buyers arrive (2-8 on Fri/Sat)
        int customerMin = 2;  //normal Mon-Thur
        int customerMax = 2;
        if (day == Enums.DayOfWeek.Fri || day == Enums.DayOfWeek.Sat || day == Enums.DayOfWeek.Sun) {
            customerMin = 2;
            customerMax = 2;
        }
        ArrayList<Customer> buyers = new ArrayList<Customer>();
        int customerCount = Utility.rndFromRange(customerMin,customerMax);
        for (int i=1; i<=customerCount; ++i) buyers.add(new Customer());
        out("The DoughMasters has "+customerCount+" buyers today...");
        return buyers;
    }

    public void managerRestock(Inventory inventory) {
        int minQuantity = 1;
        int buying = 15;
        for (Enums.Topping topping : inventory.toppingsInventory.keySet()) {
            int quantity = inventory.toppingsInventory.get(topping);
            if (quantity < minQuantity) {
                inventory.toppingsInventory.put(topping, quantity + buying);
                out("The manager has added "+buying+" of "+topping+" to the inventory...");
                moneyOut(.99 * minQuantity);
            }
        }

        for (Enums.Sauce sauce : inventory.saucesInventory.keySet()) {
            int quantity = inventory.saucesInventory.get(sauce);
            if (quantity < minQuantity) {
                inventory.saucesInventory.put(sauce, quantity + buying);
                out("The manager has added "+buying+" of "+sauce+" to the inventory...");
                if(sauce == Enums.Sauce.bbq) {
                    moneyOut(3.46 * buying);
                } else if (sauce == Enums.Sauce.marinara) {
                    moneyOut(2.96 * buying);
                } else if (sauce == Enums.Sauce.ranch) {
                    moneyOut(3.99 * buying);
                } else {
                    moneyOut(4.50 * buying);
                }
            }
        }

        for (Enums.Crust crust : inventory.crustsInventory.keySet()) {
            int quantity = inventory.crustsInventory.get(crust);
            if (quantity < minQuantity) {
                inventory.crustsInventory.put(crust, quantity + buying);
                out("The manager has added "+buying+" of "+crust+" to the inventory...");
                if(crust == Enums.Crust.deep_dish) {
                    moneyOut(2.50 * buying);
                } else if (crust == Enums.Crust.thin) {
                    moneyOut(1.50 * buying);
                } else {
                    moneyOut(2.00 * buying);
                }
            }
        }
    }
}
