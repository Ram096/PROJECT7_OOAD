import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pizza {

    private Map<String, Integer> crustsInventory;
    private List<String> crusts;
    private Map<String, Integer> saucesInventory;
    private List<String> sauces;
    private Map<String, Integer> toppingsInventory;
    private List<String> toppings;

    public Pizza(List<String> crusts, Map<String, Integer> crustsInventory, List<String> sauces, Map<String, Integer> saucesInventory, List<String> toppings, Map<String, Integer> toppingsInventory) {
        this.crusts = crusts;
        this.crustsInventory = crustsInventory;
        this.sauces = sauces;
        this.saucesInventory = saucesInventory;
        this.toppings = toppings;
        this.toppingsInventory = toppingsInventory;
    }

    public List<String> getCrustType() {
        return crusts;
    }

    public List<String> getSauceType() {
        return sauces;
    }

    public List<String> getToppings() {
        return toppings;
    }

    public void addTopping(String topping) {
        if (toppingsInventory.containsKey(topping) && toppingsInventory.get(topping) > 0) {
            toppings.add(topping);
            toppingsInventory.put(topping, toppingsInventory.get(topping) - 1);
        } else {
            System.out.println("Sorry, we are out of " + topping + ".");
        }
    }

    public void removeTopping(String topping) {
        toppings.remove(topping);
        if (toppingsInventory.containsKey(topping)) {
            toppingsInventory.put(topping, toppingsInventory.get(topping) + 1);
        } else {
            toppingsInventory.put(topping, 1);
        }
    }

    public void restockToppings(Map<String, Integer> newInventory) {
        for (Map.Entry<String, Integer> entry : newInventory.entrySet()) {
            String topping = entry.getKey();
            int quantity = entry.getValue();
            if (toppingsInventory.containsKey(topping)) {
                toppingsInventory.put(topping, toppingsInventory.get(topping) + quantity);
            } else {
                toppingsInventory.put(topping, quantity);
            }
        }
    }
    public String getToppingsInventory() {
        StringBuilder inventory = new StringBuilder();
        inventory.append("Toppings Inventory:\n");
        for (Map.Entry<String, Integer> entry : toppingsInventory.entrySet()) {
            inventory.append(entry.getKey() + ": " + entry.getValue() + "\n");
        }
        return inventory.toString();
    }

}
