public class BadCook implements CookMethod, SysOut{
    public void cook(Pizza pizza, Customer c) {
        double rand1 = Math.random();

        if (rand1 < 0.3) {
            pizza.setCond(Enums.cookCond.under_cooked);
            out("The pizza was sadly undercooked and the person got a 50% discount off and no tip was given");
        } else if (rand1 < 0.7) {
            pizza.setCond(Enums.cookCond.just_Right);
            int tip = Utility.rndFromRange(15,25);
            out("The pizza was cooked amazingly and the person gave a "+tip+"% tip being "+Utility.asDollar(pizza.getPrice(c.prefTopping, c.prefSize) * tip)+" to the user");
        } else {
            pizza.setCond(Enums.cookCond.over_cooked);
            int tip = Utility.rndFromRange(0,20);
            if (tip < 10) {
                out("The pizza was cooked amazingly and the person gave a "+tip+"% tip being "+Utility.asDollar(pizza.getPrice(c.prefTopping, c.prefSize) * tip)+" to the user");
            } else if (tip < 15) {
                out("The pizza was cooked amazingly and the person gave a "+tip+"% tip being "+Utility.asDollar(pizza.getPrice(c.prefTopping, c.prefSize) * tip)+" to the user");
            } else {
                out("The pizza was cooked amazingly and the person gave a "+tip+"% tip being "+Utility.asDollar(pizza.getPrice(c.prefTopping, c.prefSize) * tip)+" to the user");
            }
        }


    }
}
