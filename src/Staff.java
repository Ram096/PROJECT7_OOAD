// Staff class
/*
Staff class would be in control of all staff types which includes manager, cook, driver and cashier
Anything relevant to staff would also be included, such as salary, tips and days working
 */

import java.util.*;

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
        salary = 150;
    }

    public double managerRestock(Inventory inventory) {
        int minQuantity = 7;
        int buying = 15;
        double moneyOut = 0;
        int buyCount = 0;
        for (Enums.Topping topping : inventory.toppingsInventory.keySet()) {
            int quantity = inventory.toppingsInventory.get(topping);
            if (quantity < minQuantity) {
                inventory.toppingsInventory.put(topping, quantity + buying);
                out("The manager has added "+buying+" of "+topping+" to the inventory...");
                moneyOut += (.99 * minQuantity);
                buyCount++;
            }
        }

        for (Enums.Sauce sauce : inventory.saucesInventory.keySet()) {
            int quantity = inventory.saucesInventory.get(sauce);
            if (quantity < minQuantity) {
                inventory.saucesInventory.put(sauce, quantity + buying);
                out("The manager has added "+buying+" of "+sauce+" to the inventory...");
                if(sauce == Enums.Sauce.bbq) {
                    moneyOut += (1.46 * buying);
                    buyCount++;
                } else if (sauce == Enums.Sauce.marinara) {
                    moneyOut += (1.96 * buying);
                    buyCount++;
                } else if (sauce == Enums.Sauce.ranch) {
                    moneyOut += (1.99 * buying);
                    buyCount++;
                } else {
                    moneyOut += (1.50 * buying);
                    buyCount++;
                }
            }
        }

        for (Enums.Crust crust : inventory.crustsInventory.keySet()) {
            int quantity = inventory.crustsInventory.get(crust);
            if (quantity < minQuantity) {
                inventory.crustsInventory.put(crust, quantity + buying);
                out("The manager has added "+buying+" of "+crust+" to the inventory...");
                if(crust == Enums.Crust.deep_dish) {
                    moneyOut += (1.50 * buying);
                    buyCount++;
                } else if (crust == Enums.Crust.thin) {
                    moneyOut += (1.50 * buying);
                    buyCount++;
                } else {
                    moneyOut += (1.00 * buying);
                    buyCount++;
                }
            }
        }
        out("The manager "+name+" has restocked "+buyCount+" different things!");
        return moneyOut;
    }
    //implement manager functions
}
class Cook extends Staff{
    static List<String> names = Arrays.asList("Steph", "Klay", "Jordan", "Steve");
    static Namer namer = new Namer(names);
    public Enums.cook cookExp;
    Cook(){
        super();
        type = Enums.StaffType.Cook;
        name = namer.getNext(); // Every new cook gets a new name
        salary = 100;
        cookExp = getCook();
    }
    public Enums.cook getCook() {
        double rand = Utility.rnd();
        Enums.cook cook;

        if (rand < 0.25) {
            cook = Enums.cook.bad_cook;
        } else if (rand < 0.65) {
            cook = Enums.cook.average_cook;
        } else {
            cook = Enums.cook.expert_cook;
        }
        return cook;
    }
    public Boolean makePizza(Enums.Crust crustType, Enums.Sauce sauceType, List<Enums.Topping> toppings, Customer c, Inventory i, Pizza pizza) {
        Map<Enums.Topping, Integer> toppingsUsed = new HashMap<>();
        List<Enums.Topping> newToppings = new ArrayList<>();
        boolean canMakePizza = true;

        if (i.crustsInventory.get(crustType) < 1) {
            Enums.Crust newCrust = pizza.replaceCrust(crustType);
            if (newCrust != null) {
                out("We have replaced " + crustType + " with the person's 2nd choice " + newCrust + ".");
                crustType = newCrust;
                c.prefCrust = newCrust;
            } else {
                canMakePizza = false;
            }
        }

        if (canMakePizza) {
            if (i.saucesInventory.get(sauceType) < 1) {
                Enums.Sauce newSauce = pizza.replaceSauce(sauceType);
                if (newSauce != null) {
                    out("We have replaced " + sauceType + " with the person's 2nd choice " + newSauce + ".");
                    sauceType = newSauce;
                    c.prefSauce = newSauce;
                } else {
                    canMakePizza = false;
                }
            }
        }

        if (canMakePizza) {
            for (Enums.Topping topping : toppings) {
                if (i.toppingsInventory.get(topping) < 1) {
                    Enums.Topping newTopping = pizza.replaceTopping(topping, toppings);
                    if (newTopping != null) {
                        out("We have replaced " + topping + " with the person's 2nd choice " + newTopping + ".");
                        topping = newTopping;
                    } else {
                        canMakePizza = false;
                    }
                }
                toppingsUsed.put(topping, toppingsUsed.getOrDefault(topping, 0) + 1);
                newToppings.add(topping);
            }
            c.prefTopping = newToppings;
        }

        if (canMakePizza) {
            i.crustsInventory.put(crustType, i.crustsInventory.get(crustType) - 1);
            i.saucesInventory.put(sauceType, i.saucesInventory.get(sauceType) - 1);
            for (Map.Entry<Enums.Topping, Integer> entry : toppingsUsed.entrySet()) {
                Enums.Topping topping = entry.getKey();
                int quantity = entry.getValue();
                i.toppingsInventory.put(topping, i.toppingsInventory.get(topping) - quantity);
            }
        }

        return canMakePizza;

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
        salary = 75; // daily salary
    }
    //implement cashier function
    public void calculateOrderCost(Pizza pizza, Customer c) {
        List<Enums.Topping> toppings = c.prefTopping;
        boolean pay = true;
        double totalCost = pizza.getPrice(c.prefTopping, c.prefSize, pizza.getCookCond(), c);

        if(pay){
            out("Cashier: If there will be no more add-ons, your total will be: "+ Utility.asDollar(totalCost)+ " Thank you for ordering at Dough Masters!");
        }
        else{
            out("Sorry, your payment is insufficient. Your order cannot be completed.");
        }

    }
}
class Driver extends Staff {
    static List<String> names = Arrays.asList("Anthony","Austin","Rui","James");
    static Namer namer = new Namer(names);
    Driver() {
        super();
        type = Enums.StaffType.Driver;
        name = namer.getNext();  // Every Driver gets a new name
        salary = 120; // daily salary
    }
    //add function for delivery orders
}