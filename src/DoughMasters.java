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
        toppingsInventory.put(Enums.Topping.mushroom, 0);
        toppingsInventory.put(Enums.Topping.olives, 0);
        toppingsInventory.put(Enums.Topping.bacon, 0);
        toppingsInventory.put(Enums.Topping.pepperoni, 0);
        toppingsInventory.put(Enums.Topping.onion, 0);
        toppingsInventory.put(Enums.Topping.peppers, 0);
        toppingsInventory.put(Enums.Topping.sausage, 0);
        saucesInventory.put(Enums.Sauce.marinara, 0);
        saucesInventory.put(Enums.Sauce.alfredo, 0);
        saucesInventory.put(Enums.Sauce.bbq, 0);
        saucesInventory.put(Enums.Sauce.ranch, 0);
        crustsInventory.put(Enums.Crust.thin, 0);
        crustsInventory.put(Enums.Crust.regular, 0);
        crustsInventory.put(Enums.Crust.deep_dish, 0);
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

        managerRestock(inventory);
        for (Customer c:customers) {
            out("Customer "+c.name+"is buying right now...");
            startPizza(c);
        }

        out("The money spent today is "+getMoneySpent());

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
        double rand1 = Utility.rnd();
        Enums.Crust crusts = c.prefCrust;
        Enums.Sauce sauces = c.prefSauce;
        List<Enums.Topping> toppings = c.prefTopping;

        String toppingsList = String.join(", ", c.prefTopping.stream().map(Enum::toString).toArray(String[]::new));
        out("The customer "+c.name+" is looking for a "+c.prefSize+", "+c.prefCrust+", "+c.prefSauce+" pizza with "+toppingsList+" for the toppings.");
        Pizza pizza = new customerPizza(crustsInventory, saucesInventory, toppingsInventory);

        Boolean pizzaMake = pizza.makePizza(crusts, sauces, toppings, c);

        if(pizzaMake) {
            if(Math.random() <= 10){
                out("Pizza order is taking priority! Fast order option has been added to pizza for 50% of pizza sale price. Old price: "+ Utility.asDollar(pizza.getPrice(c.prefTopping, c.prefSize, pizza.getCookCond())));
                FastOrder fastOrder =  new FastOrder(pizza);
                if (rand1 < 0.25) {
                    BadCook badCook = new BadCook();
                    badCook.cook(pizza, c, inventory);
                    pizza.total = pizza.getPrice(c.prefTopping, c.prefSize, pizza.getCookCond());
                    pizza.total = fastOrder.getPrice();
                    out("The customer "+c.name+" got a "+ c.prefSize +" pizza, that has " + c.prefCrust + " crust, It has " + c.prefCrust + " sauce, with " + c.prefTopping.size() + " toppings being: " + toppingsList + " All for " + Utility.asDollar(pizza.getPrice(c.prefTopping, c.prefSize, pizza.getCookCond())));
                    out("Total pizza price + tip is: " + Utility.asDollar(pizza.total + pizza.tip));
                } else if (rand1 < 0.65) {
                    AverageCook averageCook = new AverageCook();
                    averageCook.cook(pizza, c, inventory);
                    pizza.total = pizza.getPrice(c.prefTopping, c.prefSize, pizza.getCookCond());
                    pizza.total = fastOrder.getPrice();
                    out("The customer "+c.name+" got a "+ c.prefSize +" pizza, that has " + c.prefCrust + " crust, It has " + c.prefCrust + " sauce, with " + c.prefTopping.size() + " toppings being: " + toppingsList + " All for " + Utility.asDollar(pizza.getPrice(c.prefTopping, c.prefSize, pizza.getCookCond())));
                    out("Total pizza price + tip is: " + Utility.asDollar(pizza.total + pizza.tip));
                } else {
                    ExpertCook expertCook = new ExpertCook();
                    expertCook.cook(pizza, c, inventory);
                    pizza.total = fastOrder.getPrice();
                    out("The customer "+c.name+" got a "+ c.prefSize +" pizza, that has " + c.prefCrust + " crust, It has " + c.prefCrust + " sauce, with " + c.prefTopping.size() + " toppings being: " + toppingsList + " All for " + Utility.asDollar(pizza.getPrice(c.prefTopping, c.prefSize, pizza.getCookCond())));
                    out("Total pizza price + tip is: " + Utility.asDollar(pizza.total + pizza.tip));
                }
            } else {
                if (rand1 < 0.25) {
                    BadCook badCook = new BadCook();
                    badCook.cook(pizza, c, inventory);
                    out("The customer "+c.name+" got a "+ c.prefSize +" pizza, that has " + c.prefCrust + " crust, It has " + c.prefCrust + " sauce, with " + c.prefTopping.size() + " toppings being: " + toppingsList + " All for " + Utility.asDollar(pizza.getPrice(c.prefTopping, c.prefSize, pizza.getCookCond())));
                    out("Total pizza price + tip is: " + Utility.asDollar(pizza.total + pizza.tip));
                } else if (rand1 < 0.65) {
                    AverageCook averageCook = new AverageCook();
                    averageCook.cook(pizza, c, inventory);
                    out("The customer "+c.name+" got a "+ c.prefSize +" pizza, that has " + c.prefCrust + " crust, It has " + c.prefCrust + " sauce, with " + c.prefTopping.size() + " toppings being: " + toppingsList + " All for " + Utility.asDollar(pizza.getPrice(c.prefTopping, c.prefSize, pizza.getCookCond())));
                    out("Total pizza price + tip is: " + Utility.asDollar(pizza.total + pizza.tip));
                } else {
                    ExpertCook expertCook = new ExpertCook();
                    expertCook.cook(pizza, c, inventory);
                    out("The customer "+c.name+" got a "+ c.prefSize +" pizza, that has " + c.prefCrust + " crust, It has " + c.prefCrust + " sauce, with " + c.prefTopping.size() + " toppings being: " + toppingsList + " All for " + Utility.asDollar(pizza.getPrice(c.prefTopping, c.prefSize, pizza.getCookCond())));
                    out("Total pizza price + tip is: " + Utility.asDollar(pizza.total + pizza.tip));
                }
            }

            moneyIn(pizza.total + pizza.tip);
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
        int minQuantity = 7;
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
