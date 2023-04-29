import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private Map<Enums.Topping, Integer> toppingsInventory;
    private Map<Enums.Sauce, Integer> saucesInventory;
    private Map<Enums.Crust, Integer> crustsInventory;

    public Inventory(Map<Enums.Topping, Integer> toppingsInventory, Map<Enums.Sauce, Integer> saucesInventory, Map<Enums.Crust, Integer> crustsInventory) {
        this.toppingsInventory = toppingsInventory;
        this.saucesInventory = saucesInventory;
        this.crustsInventory = crustsInventory;
    }

    public boolean checkInventory(Enums.Topping topping) {
        return toppingsInventory.getOrDefault(topping, 0) > 0;
    }

    public boolean checkInventory(Enums.Sauce sauce) {
        return saucesInventory.getOrDefault(sauce, 0) > 0;
    }

    public boolean checkInventory(Enums.Crust crust) {
        return crustsInventory.getOrDefault(crust, 0) > 0;
    }

    public void removeInventory(Enums.Topping topping) {
        toppingsInventory.put(topping, toppingsInventory.getOrDefault(topping, 0) - 1);
    }

    public void removeInventory(Enums.Sauce sauce) {
        saucesInventory.put(sauce, saucesInventory.getOrDefault(sauce, 0) - 1);
    }

    public void removeInventory(Enums.Crust crust) {
        crustsInventory.put(crust, crustsInventory.getOrDefault(crust, 0) - 1);
    }

    public void restockInventory(Map<Enums.Topping, Integer> newToppings, Map<Enums.Sauce, Integer> newSauces, Map<Enums.Crust, Integer> newCrusts) {
        for (Map.Entry<Enums.Topping, Integer> entry : newToppings.entrySet()) {
            Enums.Topping topping = entry.getKey();
            int quantity = entry.getValue();
            toppingsInventory.put(topping, toppingsInventory.getOrDefault(topping, 0) + quantity);
        }
        for (Map.Entry<Enums.Sauce, Integer> entry : newSauces.entrySet()) {
            Enums.Sauce sauce = entry.getKey();
            int quantity = entry.getValue();
            saucesInventory.put(sauce, saucesInventory.getOrDefault(sauce, 0) + quantity);
        }
        for (Map.Entry<Enums.Crust, Integer> entry : newCrusts.entrySet()) {
            Enums.Crust crust = entry.getKey();
            int quantity = entry.getValue();
            crustsInventory.put(crust, crustsInventory.getOrDefault(crust, 0) + quantity);
        }
    }

    public Map<Enums.Topping, Integer> getToppingsInventory() {
        return toppingsInventory;
    }

    public Map<Enums.Sauce, Integer> getSaucesInventory() {
        return saucesInventory;
    }

    public Map<Enums.Crust, Integer> getCrustsInventory() {
        return crustsInventory;
    }

    public Map<String, Map<String, Integer>> getInventory() {
        Map<String, Map<String, Integer>> inventory = new HashMap<>();
        inventory.put("toppings", new HashMap<>());
        inventory.put("sauces", new HashMap<>());
        inventory.put("crusts", new HashMap<>());

        for (Map.Entry<Enums.Topping, Integer> entry : toppingsInventory.entrySet()) {
            inventory.get("toppings").put(entry.getKey().toString(), entry.getValue());
        }

        for (Map.Entry<Enums.Sauce, Integer> entry : saucesInventory.entrySet()) {
            inventory.get("sauces").put(entry.getKey().toString(), entry.getValue());
        }

        for (Map.Entry<Enums.Crust, Integer> entry : crustsInventory.entrySet()) {
            inventory.get("crusts").put(entry.getKey().toString(), entry.getValue());
        }

        return inventory;
    }

}
