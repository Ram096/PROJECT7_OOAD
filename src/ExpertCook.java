import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExpertCook implements CookMethod, SysOut{
    public void cook(Pizza pizza, Customer c, Inventory i) {
        double rand1 = Math.random();
        double rand2 = Math.random();

        if (rand1 < 0.1) {
            pizza.setCond(Enums.cookCond.under_cooked);
            out("The pizza was sadly undercooked by our expert chef and the person got a 50% discount off and no tip was given");
        } else if (rand1 < 0.9) {
            pizza.setCond(Enums.cookCond.just_Right);
            int tip = Utility.rndFromRange(15,25);
            pizza.setTip(tip/100.0);
            out("The pizza was cooked amazingly by our expert chef and the person gave a "+tip+"% tip being "+Utility.asDollar(pizza.getPrice(c.prefTopping, c.prefSize, pizza.getCookCond(), c) * (tip/100.0))+" to the user");
        } else {
            pizza.setCond(Enums.cookCond.over_cooked);
            int tip = Utility.rndFromRange(0,20);
            pizza.setTip(tip/100.0);
            if (tip < 10) {
                out("The pizza was over cooked by our expert chef and the person was not happy and gave a bad tip being a "+tip+"% tip being "+Utility.asDollar(pizza.getPrice(c.prefTopping, c.prefSize, pizza.getCookCond(), c) * (tip/100.0))+" to the user");
            } else if (tip < 15) {
                out("The pizza was over cooked by our expert chef and the person felt generous by giving a "+tip+"% tip being "+Utility.asDollar(pizza.getPrice(c.prefTopping, c.prefSize, pizza.getCookCond(), c) * (tip/100.0))+" to the user");
            } else {
                out("The pizza was over cooked by our expert chef and the person luckily gave a nice tip being a "+tip+"% tip being "+Utility.asDollar(pizza.getPrice(c.prefTopping, c.prefSize, pizza.getCookCond(), c) * (tip/100.0))+" to the user");
            }
        }

        if (rand2 < 0.1) {
            int randomTopNum = Utility.rndFromRange(1, c.prefTopping.size());

            List<Enums.Topping> shuffleList = new ArrayList<>(c.prefTopping);
            List<Enums.Topping> extraToppings = new ArrayList<>();
            Collections.shuffle(shuffleList);

            for (int j = randomTopNum - 1; j >= 0; j--) {
                i.removeInventory(shuffleList.get(j));
                extraToppings.add(shuffleList.get(j));
                shuffleList.remove(j);
            }

            String toppingsList = String.join(", ", extraToppings.stream().map(Enum::toString).toArray(String[]::new));
            out("Sadly the expert chef had a few problems and used up 1 extra of each of these ingredients: "+toppingsList);
        }
    }
}
