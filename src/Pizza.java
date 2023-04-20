import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pizza {

    private String crustType;
    private String sauceType;
    private Map<String, Integer> toppingsInventory;
    private List<String> toppings;

    public Pizza(String crustType, String sauceType, List<String> toppings, Map<String, Integer> toppingsInventory) {
        this.crustType = crustType;
        this.sauceType = sauceType;
        this.toppings = toppings;
        this.toppingsInventory = toppingsInventory;
    }

    public String getCrustType() {
        return crustType;
    }

    public String getSauceType() {
        return sauceType;
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

}
