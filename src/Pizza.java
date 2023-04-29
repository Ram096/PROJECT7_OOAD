import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Pizza implements SysOut{
    private List<Enums.Size> pizzaSize;
    private Map<Enums.Crust, Integer> crustsInventory;
    private List<Enums.Crust> crusts;
    private Map<Enums.Sauce, Integer> saucesInventory;
    private List<Enums.Sauce> sauces;
    private Map<Enums.Topping, Integer> toppingsInventory;
    private List<Enums.Topping> toppings;

    Pizza(List<Enums.Size> pizzaSize, List<Enums.Crust> crusts, Map<Enums.Crust, Integer> crustsInventory, List<Enums.Sauce> sauces, Map<Enums.Sauce, Integer> saucesInventory, List<Enums.Topping> toppings, Map<Enums.Topping, Integer> toppingsInventory) {
        this.pizzaSize = pizzaSize;
        this.crusts = crusts;
        this.crustsInventory = crustsInventory;
        this.sauces = sauces;
        this.saucesInventory = saucesInventory;
        this.toppings = toppings;
        this.toppingsInventory = toppingsInventory;
    }

    public void makePizza(Enums.Crust crustType, Enums.Sauce sauceType, List<Enums.Topping> toppings) {
        if (!crustsInventory.containsKey(crustType) || crustsInventory.get(crustType) < 1) {
            out("Sorry, we are out of " + crustType + " crust.");
            return;
        }
        if (!saucesInventory.containsKey(sauceType) || saucesInventory.get(sauceType) < 1) {
            out("Sorry, we are out of " + sauceType + " sauce.");
            return;
        }
        Map<Enums.Topping, Integer> toppingsUsed = new HashMap<>();
        for (Enums.Topping topping : toppings) {
            if (!toppingsInventory.containsKey(topping) || toppingsInventory.get(topping) < 1) {
                out("Sorry, we are out of " + topping + ".");
                return;
            }
            toppingsUsed.put(topping, toppingsUsed.getOrDefault(topping, 0) + 1);
        }
        crustsInventory.put(crustType, crustsInventory.get(crustType) - 1);
        saucesInventory.put(sauceType, saucesInventory.get(sauceType) - 1);
        for (Map.Entry<Enums.Topping, Integer> entry : toppingsUsed.entrySet()) {
            Enums.Topping topping = entry.getKey();
            int quantity = entry.getValue();
            toppingsInventory.put(topping, toppingsInventory.get(topping) - quantity);
        }
    }
    public double getPrice(List<Enums.Topping> toppings, List<Enums.Size> pizzaSize) {
        if (pizzaSize.get(0) == Enums.Size.small) {
            double total = (1.99 * toppings.size()) +8.99;
            return total;
        }
        else if (pizzaSize.get(0) == Enums.Size.medium) {
            double total = (1.99 * toppings.size()) +10.99;
            return total;
        }
        else if (pizzaSize.get(0) == Enums.Size.large) {
            double total = (1.99 * toppings.size()) +13.99;
            return total;
        }
        else {
            double total = (1.99 * toppings.size()) +17.99;
            return total;
        }
    }
}

class customerPizza extends Pizza {
    customerPizza(List<Enums.Size> pizzaSize, List<Enums.Crust> crusts, Map<Enums.Crust, Integer> crustsInventory, List<Enums.Sauce> sauces, Map<Enums.Sauce, Integer> saucesInventory, List<Enums.Topping> toppings, Map<Enums.Topping, Integer> toppingsInventory) {
        super(pizzaSize, crusts, crustsInventory, sauces, saucesInventory, toppings, toppingsInventory);
    }
}
