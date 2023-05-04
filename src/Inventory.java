import java.util.HashMap;
import java.util.Map;

public class Inventory {
    public Map<Enums.Topping, Integer> toppingsInventory;
    public Map<Enums.Sauce, Integer> saucesInventory;
    public Map<Enums.Crust, Integer> crustsInventory;

    public Inventory(Map<Enums.Topping, Integer> toppingsInventory, Map<Enums.Sauce, Integer> saucesInventory, Map<Enums.Crust, Integer> crustsInventory) {
        this.toppingsInventory = toppingsInventory;
        this.saucesInventory = saucesInventory;
        this.crustsInventory = crustsInventory;
    }

    public void removeInventory(Enums.Topping topping) {
        toppingsInventory.put(topping, toppingsInventory.getOrDefault(topping, 0) - 1);
    }

    public Map<String, Map<String, Integer>> getInventory() { // Allowing the user to see the inventory for each of the ingredients
        Map<String, Map<String, Integer>> inventory = new HashMap<>();
        inventory.put("toppings", new HashMap<>());
        inventory.put("sauces", new HashMap<>());
        inventory.put("crusts", new HashMap<>());

        // Showing inventory of all toppings
        for (Map.Entry<Enums.Topping, Integer> entry : toppingsInventory.entrySet()) {
            inventory.get("toppings").put(entry.getKey().toString(), entry.getValue());
        }

        // Showing inventory of all sauces
        for (Map.Entry<Enums.Sauce, Integer> entry : saucesInventory.entrySet()) {
            inventory.get("sauces").put(entry.getKey().toString(), entry.getValue());
        }

        // Showing inventory of all crusts
        for (Map.Entry<Enums.Crust, Integer> entry : crustsInventory.entrySet()) {
            inventory.get("crusts").put(entry.getKey().toString(), entry.getValue());
        }

        return inventory;
    }

}
