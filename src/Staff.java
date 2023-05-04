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
    Manager() {
        super();
        type = Enums.StaffType.Manager;
        salary = 150;
    }

    // Restocks the inventory if certain things are under stock at the beginning of the day and returns the amount of money needed to pay for it all
    public double managerRestock(Inventory inventory) {
        int minQuantity = 7; // There should be a minimum of 7 of each item
        int buying = 15; // Buys in quantity of 10
        double moneyOut = 0;
        int buyCount = 0;

        // Checking each topping if they are above the min quanity
        for (Enums.Topping topping : inventory.toppingsInventory.keySet()) {
            int quantity = inventory.toppingsInventory.get(topping);
            // Each topping is .99 cents
            if (quantity < minQuantity) {
                inventory.toppingsInventory.put(topping, quantity + buying);
                out("The manager has added "+buying+" of "+topping+" to the inventory...");
                moneyOut += (.99 * minQuantity);
                buyCount++;
            }
        }

        // Checking each sauce if they are above the min quanity
        for (Enums.Sauce sauce : inventory.saucesInventory.keySet()) {
            int quantity = inventory.saucesInventory.get(sauce);
            if (quantity < minQuantity) {
                inventory.saucesInventory.put(sauce, quantity + buying);
                out("The manager has added "+buying+" of "+sauce+" to the inventory...");
                // Sauces prices vary depending on each one of them
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

        // Checking each crust if they are above the min quanity
        for (Enums.Crust crust : inventory.crustsInventory.keySet()) {
            int quantity = inventory.crustsInventory.get(crust);
            if (quantity < minQuantity) {
                inventory.crustsInventory.put(crust, quantity + buying);
                out("The manager has added "+buying+" of "+crust+" to the inventory...");
                // Crust prices vary depending on each one of them
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
        // Determining number of items bought for the day
        out("The manager "+name+" has restocked "+buyCount+" different things!");
        return moneyOut;
    }
    //implement manager functions
}
class Cook extends Staff{
    public Enums.cook cookExp;
    Cook(){
        super();
        type = Enums.StaffType.Cook;
        salary = 100;
        cookExp = getCook();
    }
    // Determining what type of cook the person is
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
    // Creates the pizza the customer wants and if the pizza is able to be made then it will return true otherwise false
    public Boolean makePizza(Enums.Crust crustType, Enums.Sauce sauceType, List<Enums.Topping> toppings, Customer c, Inventory i, Pizza pizza) {
        Map<Enums.Topping, Integer> toppingsUsed = new HashMap<>();
        List<Enums.Topping> newToppings = new ArrayList<>();
        boolean canMakePizza = true;

        // Checking whether the customers crust choice is in stock if not replace with a random option
        if (i.crustsInventory.get(crustType) < 1) {
            Enums.Crust newCrust = pizza.replaceCrust(crustType);
            if (newCrust != null) {
                out("We have replaced " + crustType + " with the person's 2nd choice " + newCrust + ".");
                crustType = newCrust;
                c.prefCrust = newCrust;
            } else { // No other crusts so pizza can't be made
                canMakePizza = false;
            }
        }

        // Checking whether the customers sauce choice is in stock if not replace with a random option
        if (canMakePizza) {
            if (i.saucesInventory.get(sauceType) < 1) {
                Enums.Sauce newSauce = pizza.replaceSauce(sauceType);
                if (newSauce != null) {
                    out("We have replaced " + sauceType + " with the person's 2nd choice " + newSauce + ".");
                    sauceType = newSauce;
                    c.prefSauce = newSauce;
                } else { // No other sauces available so pizza can't be made
                    canMakePizza = false;
                }
            }
        }

        // Checking for each topping choice whether it is in stock if not replace with a random option
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
                } // Use the new toppings
                toppingsUsed.put(topping, toppingsUsed.getOrDefault(topping, 0) + 1);
                newToppings.add(topping);
            }
            c.prefTopping = newToppings;
        }

        // Pizza is able to be made meaning there was a sauce, crust, and toppings where available
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
    Cashier(){
        super();
        type = Enums.StaffType.Cashier;
        salary = 75; // daily salary
    }
    //implement cashier function
    public void calculateOrderCost(Pizza pizza, Customer c) {//cashier will read order total to customer
        boolean pay = true;
        double totalCost = pizza.getPrice(c.prefTopping, c.prefSize, pizza.getCookCond(), c);

        if(pay){ //if customer can pay
            out("Cashier: If there will be no more add-ons, your total will be: "+ Utility.asDollar(totalCost)+ " Thank you for ordering at Dough Masters!");
        }
        else{ //if customer cannot pay
            out("Sorry, your payment is insufficient. Your order cannot be completed.");
        }

    }
}
class Driver extends Staff {
    Driver() {
        super();
        type = Enums.StaffType.Driver;
        salary = 120; // daily salary
    }
    //add function for delivery orders
}