import java.util.Collections;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Pizza implements SysOut{
    private List<Enums.Size> pizzaSize;
    static public Map<Enums.Crust, Integer> crustsInventory;
    private List<Enums.Crust> crusts;
    static public Map<Enums.Sauce, Integer> saucesInventory;
    private List<Enums.Sauce> sauces;
    static public Map<Enums.Topping, Integer> toppingsInventory;
    private List<Enums.Topping> toppings;
    private Enums.cookCond cookCond;
    double tip;
    double total;

    Pizza(Map<Enums.Crust, Integer> crustsInventory, Map<Enums.Sauce, Integer> saucesInventory, Map<Enums.Topping, Integer> toppingsInventory) {
        this.crustsInventory = crustsInventory;
        this.saucesInventory = saucesInventory;
        this.toppingsInventory = toppingsInventory;
        this.tip = 0;
        this.total = 0;
    }
    public void setCond(Enums.cookCond cond) {
        this.cookCond = cond;
    }
    public Enums.cookCond getCookCond() {
        return cookCond;
    }
    public void setTip(double tip) {
        this.tip = tip;
    }


    public Enums.Crust replaceCrust(Enums.Crust crust) {
        List<Enums.Crust> remainingCrusts = new ArrayList<>(Arrays.asList(Enums.Crust.values()));
        remainingCrusts.remove(crust);

        // Shuffle the list of remaining crusts and check if any are in stock
        Collections.shuffle(remainingCrusts);
        for (Enums.Crust newCrust : remainingCrusts) {
            if (crustsInventory.get(newCrust) > 0) {
                return newCrust;
            }
        }

        // No other crusts are available to replace the original crust
        out("The person has no other choices for "+crust+" and won't replace the crust!");
        return null;
    }

    public Enums.Sauce replaceSauce(Enums.Sauce sauce) {
        List<Enums.Sauce> remainingSauces = new ArrayList<>(Arrays.asList(Enums.Sauce.values()));
        remainingSauces.remove(sauce);

        // Shuffle the list of remaining sauces and check if any are in stock
        Collections.shuffle(remainingSauces);
        for (Enums.Sauce newSauce : remainingSauces) {
            if (saucesInventory.get(newSauce) > 0) {
                return newSauce;
            }
        }

        // No other sauces are available to replace the original sauce
        out("The person has no other choices for " + sauce + " and won't replace the sauce!");
        return null;
    }

    public Enums.Topping replaceTopping(Enums.Topping topping, List<Enums.Topping> preferredToppings) {
        List<Enums.Topping> remainingToppings = new ArrayList<>(Arrays.asList(Enums.Topping.values()));
        remainingToppings.remove(topping);

        // Check if any of the preferred toppings are available
        for (Enums.Topping prefTopping : preferredToppings) {
            if (toppingsInventory.get(prefTopping) > 0) {
                return prefTopping;
            }
        }

        // Shuffle the list of remaining toppings and check if any are in stock
        Collections.shuffle(remainingToppings);
        for (Enums.Topping newTopping : remainingToppings) {
            if (toppingsInventory.get(newTopping) > 0) {
                return newTopping;
            }
        }

        // No other toppings are available to replace the original topping
        out("The person has no other choices and won't replace that topping!");
        return null;
    }


    public double getPrice(List<Enums.Topping> toppings, Enums.Size pizzaSize, Enums.cookCond cond, Customer c) {
        if (cond == Enums.cookCond.under_cooked) {
            if (pizzaSize == Enums.Size.small) {
                double total = ((1.99 * toppings.size()) + 8.99) * 0.4;
                return total;
            } else if (pizzaSize == Enums.Size.medium) {
                double total = ((1.99 * toppings.size()) + 10.99) * 0.4;
                return total;
            } else if (pizzaSize == Enums.Size.large) {
                double total = ((1.99 * toppings.size()) + 13.99) * 0.4;
                return total;
            } else {
                double total = ((1.99 * toppings.size()) + 17.99) * 0.4;
                return total;
            }
        } else {
            if (pizzaSize == Enums.Size.small) {
                double total = ((1.99 * toppings.size()) + 8.99) * 0.9;
                return total;
            } else if (pizzaSize == Enums.Size.medium) {
                double total = ((1.99 * toppings.size()) + 10.99) * 0.9;
                return total;
            } else if (pizzaSize == Enums.Size.large) {
                double total = ((1.99 * toppings.size()) + 13.99) * 0.9;
                return total;
            } else {
                double total = ((1.99 * toppings.size()) + 17.99) * 0.9;
                return total;
            }
        }

    }
}

class customerPizza extends Pizza {
    customerPizza(Map<Enums.Crust, Integer> crustsInventory, Map<Enums.Sauce, Integer> saucesInventory, Map<Enums.Topping, Integer> toppingsInventory) {
        super(crustsInventory, saucesInventory, toppingsInventory);
    }
}
