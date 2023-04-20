// Dough Masters pizza class
/*
Dough Masters class represents the logistics of staff, inventory and pizza orders

 */
import javax.security.sasl.SaslException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class DoughMasters implements SysOut {
    ArrayList<Staff> staff;
    ArrayList<Staff> departedStaff;
    static ArrayList<Staff> inventory;
    private double budget;


    void getPizza() {
        List<String> toppings = new ArrayList<>();
        toppings.add("pepperoni");
        toppings.add("mushrooms");
        toppings.add("olives");

        Map<String, Integer> toppingsInventory = new HashMap<>();
        toppingsInventory.put("pepperoni", 5);
        toppingsInventory.put("mushrooms", 7);
        toppingsInventory.put("olives", 3);

        Pizza pizza = new Pizza("thin crust", "tomato sauce", toppings, toppingsInventory);

        out("Crust type: " + pizza.getCrustType());
        out("Sauce type: " + pizza.getSauceType());
        out("Toppings: " + pizza.getToppings());
    }

}
