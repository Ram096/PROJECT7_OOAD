// Dough Masters pizza class
/*
Dough Masters class represents the logistics of staff, inventory and pizza orders

 */
import javax.security.sasl.SaslException;
import javax.swing.*;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
public class DoughMasters implements SysOut {
    ArrayList<Staff> staff;
    ArrayList<Staff> departedStaff;
    static ArrayList<Staff> inventory1;
    private double budget;
    Map<Enums.Topping, Integer> toppingsInventory = new HashMap<>();
    Map<Enums.Sauce, Integer> saucesInventory = new HashMap<>();
    Map<Enums.Crust, Integer> crustsInventory = new HashMap<>();
    Inventory inventory = new Inventory(toppingsInventory, saucesInventory, crustsInventory);
    Enums.BuyerType orderMethod;

    DoughMasters() {
        staff = new ArrayList<>();
        departedStaff = new ArrayList<>();
        inventory1 = new ArrayList<>();
        budget = 100000;
        toppingsInventory.put(Enums.Topping.mushroom, 0);
        toppingsInventory.put(Enums.Topping.olives, 0);
        toppingsInventory.put(Enums.Topping.bacon, 0);
        toppingsInventory.put(Enums.Topping.pepperoni, 0);
        toppingsInventory.put(Enums.Topping.onion, 0);
        toppingsInventory.put(Enums.Topping.peppers, 0);
        toppingsInventory.put(Enums.Topping.sausage, 0);
        saucesInventory.put(Enums.Sauce.marinara, 0);
        saucesInventory.put(Enums.Sauce.alfredo, 0);
        saucesInventory.put(Enums.Sauce.bbq, 0);
        saucesInventory.put(Enums.Sauce.ranch, 0);
        crustsInventory.put(Enums.Crust.thin, 0);
        crustsInventory.put(Enums.Crust.regular, 0);
        crustsInventory.put(Enums.Crust.deep_dish, 0);
    }

    double getBudget() {
        return budget;
    }
    double getMoneySpent() {
        double moneyOut = budget - 100000;
        return moneyOut;
    }

    void moneyIn(double cash) {
        budget += cash;
    }


    void moneyOut(double cash) {
        budget -= cash;
        if (budget < 0) {
            budget += 50000;
            out("***Budget overrun*** Added 50K, budget now: " + Utility.asDollar(budget));
        }
    }
    void payStaff() {
        for (Staff s: staff) {
            moneyOut(s.salary);  // money comes from the FNCD
            s.salaryEarned += s.salary;  // they get paid
            s.daysWorked += 1;// they worked another day
        }
    }

    void weekDays(Enums.DayOfWeek day) {
        out("------------ DOUGH MASTERS -------------");
        out("Our Pizza store is opening...");
        out("Checking our inventory...\n");
        out("Checking our Staff...\n");
        hireNewStaff();
        out("The store is starting to sell...");

        startRestock();
        ArrayList<Customer> customer = getCustomers(day);
        ArrayList<Staff> cooks = Staff.getStaffByType(staff, Enums.StaffType.Cook);
        OrderData orderData = new OrderData();
        PizzaOrderDisplay orderDisplay = new PizzaOrderDisplay(orderData);

        for (Customer c: customer) {
            orderMethod = Utility.randomEnum(Enums.BuyerType.class);
            if (orderMethod == Enums.BuyerType.delivery) {
                startPizza(day, c, cooks);
                Delivery pizzaDelivery = new Delivery();
                pizzaDelivery.deliver(c);
                orderData.addOrder(c.name);
                orderData.getOrders();
            } else {
                startPizza(day, c, cooks);
                orderData.addOrder(c.name);
                orderData.getOrders();
            }

        }

        if (day == Enums.DayOfWeek.Sun) {
            payStaff();
        }
        out("Orders completed today: " + orderData.getTotalOrders());

        out("Current Profit: "+ Utility.asDollar(getMoneySpent()));

        out("The current Inventory at the end of the day"+inventory.getInventory());
    }

    void weekendDays(Enums.DayOfWeek day) {
        out("------------ DOUGH MASTERS -------------");
        out("Our Pizza store is opening...");
        out("Checking our inventory...\n");
        out("Checking our Staff...\n");
        hireNewStaff();
        out("The store is starting to sell...");

        startRestock();
        ArrayList<Customer> customer = getCustomers(day);
        ArrayList<Staff> cooks = Staff.getStaffByType(staff, Enums.StaffType.Cook);
        OrderData orderData = new OrderData();
        PizzaOrderDisplay orderDisplay = new PizzaOrderDisplay(orderData);

        for (Customer c: customer) {
            orderMethod = Utility.randomEnum(Enums.BuyerType.class);
            if (orderMethod == Enums.BuyerType.delivery) {
                startPizza(day, c, cooks);
                Delivery pizzaDelivery = new Delivery();
                pizzaDelivery.deliver(c);
                orderData.addOrder(c.name);
                orderData.getOrders();
            } else {
                startPizza(day, c, cooks);
                orderData.addOrder(c.name);
                orderData.getOrders();
            }
        }

        if (day == Enums.DayOfWeek.Sun) {
            payStaff();
        }

        out("Orders completed today: " + orderData.getTotalOrders());

        out("Current Profit: "+ Utility.asDollar(getMoneySpent()));

        out("The current Inventory at the end of the day"+inventory.getInventory());
    }

    // see if we need any new hires
    void hireNewStaff() {
        final int numberInStaff = 3;
        for (Enums.StaffType t : Enums.StaffType.values()) {
            int typeInList = Staff.howManyStaffByType(staff, t);
            int need = numberInStaff - typeInList;
            for (int i = 1; i<=need; ++i) addStaff(t);
        }
    }

    void addStaff(Enums.StaffType t) {
        Staff newStaff = null;
        if (t == Enums.StaffType.Cook) newStaff = new Cook();
        if (t == Enums.StaffType.Cashier) newStaff = new Cashier();
        if (t == Enums.StaffType.Driver) newStaff = new Driver();
        if (t == Enums.StaffType.Manager) newStaff = new Manager();
        out("Hired a new "+newStaff.type+" named "+ newStaff.name);
        staff.add(newStaff);
    }

    void startPizza(Enums.DayOfWeek day, Customer c, ArrayList<Staff> cooks) {

            Enums.Crust crusts = c.prefCrust;
            Enums.Sauce sauces = c.prefSauce;
            List<Enums.Topping> toppings = c.prefTopping;

            int randomCook = Utility.rndFromRange(0, cooks.size() - 1);
            Cook cook = (Cook) cooks.get(randomCook);

            String toppingsList = String.join(", ", c.prefTopping.stream().map(Enum::toString).toArray(String[]::new));
            if(c.studentStat == Enums.Student.not_student && (day == Enums.DayOfWeek.Fri || day == Enums.DayOfWeek.Sat || day == Enums.DayOfWeek.Sun)) {
                out("The customer " + c.name + " is a student and is getting 10% off and is looking for a " + c.prefSize + ", " + c.prefCrust + ", " + c.prefSauce + " pizza with " + toppingsList + " for the toppings.");
            } else {
                out("The customer " + c.name + " is looking for a " + c.prefSize + ", " + c.prefCrust + ", " + c.prefSauce + " pizza with " + toppingsList + " for the toppings.");
            }
            Pizza pizza = new customerPizza(crustsInventory, saucesInventory, toppingsInventory);

            boolean pizzaMake = cook.makePizza(crusts, sauces, toppings, c, inventory, pizza);


            if (pizzaMake) {
                Cashier cashier = new Cashier();
                double rand = Utility.rnd();
                pizza.total = pizza.getPrice(c.prefTopping, c.prefSize, pizza.getCookCond(), c);
                if (rand <= 0.65) {
                    out("Extra sauce for 2% of pizza total has been added! Old pizza total: " + Utility.asDollar(pizza.total));
                    ExtraSauce extraSauce = new ExtraSauce(pizza);
                    pizza.total = extraSauce.getPrice();
                    out("This brings the total of the pizza to: " + Utility.asDollar(pizza.total));
                    if (cook.getCook() == Enums.cook.bad_cook) {
                        BadCook badCook = new BadCook();
                        badCook.cook(pizza, c, inventory);
                        pizza.total = extraSauce.getPrice();
                        out("The customer " + c.name + " got a " + c.prefSize + " pizza, that has " + c.prefCrust + " crust, It has " + c.prefCrust + " sauce, with " + c.prefTopping.size() + " toppings being: " + toppingsList + " All for " + Utility.asDollar(pizza.total));
                        out("Total pizza price + tip is: " + Utility.asDollar(pizza.total + pizza.tip));
                    } else if (cook.getCook() == Enums.cook.average_cook) {
                        AverageCook averageCook = new AverageCook();
                        averageCook.cook(pizza, c, inventory);
                        pizza.total = extraSauce.getPrice();
                        out("The customer " + c.name + " got a " + c.prefSize + " pizza, that has " + c.prefCrust + " crust, It has " + c.prefCrust + " sauce, with " + c.prefTopping.size() + " toppings being: " + toppingsList + " All for " + Utility.asDollar(pizza.total));
                        out("Total pizza price + tip is: " + Utility.asDollar(pizza.total + pizza.tip));
                    } else {
                        ExpertCook expertCook = new ExpertCook();
                        expertCook.cook(pizza, c, inventory);
                        pizza.total = extraSauce.getPrice();
                        out("The customer " + c.name + " got a " + c.prefSize + " pizza, that has " + c.prefCrust + " crust, It has " + c.prefCrust + " sauce, with " + c.prefTopping.size() + " toppings being: " + toppingsList + " All for " + Utility.asDollar(pizza.total));
                        out("Total pizza price + tip is: " + Utility.asDollar(pizza.total + pizza.tip));
                    }
                } else if (rand <= .10) {
                    out("Fast Order for 50% of pizza total has been added! Pizza order will now take priority and be made fast! Old pizza total: " + Utility.asDollar(pizza.total));
                    FastOrder fastOrder = new FastOrder(pizza);
                    out("This brings the total of the pizza to: " + pizza.total);
                    if (cook.getCook() == Enums.cook.bad_cook) {
                        BadCook badCook = new BadCook();
                        badCook.cook(pizza, c, inventory);
                        pizza.total = fastOrder.getPrice();
                        out("The customer " + c.name + " got a " + c.prefSize + " pizza, that has " + c.prefCrust + " crust, It has " + c.prefCrust + " sauce, with " + c.prefTopping.size() + " toppings being: " + toppingsList + " All for " + Utility.asDollar(pizza.total));
                        out("Total pizza price + tip is: " + Utility.asDollar(pizza.total + pizza.tip));
                    } else if (cook.getCook() == Enums.cook.average_cook) {
                        AverageCook averageCook = new AverageCook();
                        averageCook.cook(pizza, c, inventory);
                        pizza.total = fastOrder.getPrice();
                        out("The customer " + c.name + " got a " + c.prefSize + " pizza, that has " + c.prefCrust + " crust, It has " + c.prefCrust + " sauce, with " + c.prefTopping.size() + " toppings being: " + toppingsList + " All for " + Utility.asDollar(pizza.total));
                        out("Total pizza price + tip is: " + Utility.asDollar(pizza.total + pizza.tip));
                    } else {
                        ExpertCook expertCook = new ExpertCook();
                        expertCook.cook(pizza, c, inventory);
                        pizza.total = fastOrder.getPrice();
                        out("The customer " + c.name + " got a " + c.prefSize + " pizza, that has " + c.prefCrust + " crust, It has " + c.prefCrust + " sauce, with " + c.prefTopping.size() + " toppings being: " + toppingsList + " All for " + Utility.asDollar(pizza.total));
                        out("Total pizza price + tip is: " + Utility.asDollar(pizza.total + pizza.tip));
                    }
                } else if (rand <= .15) {
                    out("Extra meat toppings for 20% of pizza total has been added! More sausage, bacon and pepperoni have been added Old pizza total: " + Utility.asDollar(pizza.total));
                    MeatToppings meatToppings = new MeatToppings(pizza);
                    out("This brings the total of the pizza to: " + Utility.asDollar(pizza.total));
                    if (cook.getCook() == Enums.cook.bad_cook) {
                        BadCook badCook = new BadCook();
                        badCook.cook(pizza, c, inventory);
                        pizza.total = meatToppings.getPrice();
                        out("The customer " + c.name + " got a " + c.prefSize + " pizza, that has " + c.prefCrust + " crust, It has " + c.prefCrust + " sauce, with " + c.prefTopping.size() + " toppings being: " + toppingsList + " All for " + Utility.asDollar(pizza.total));
                        out("Total pizza price + tip is: " + Utility.asDollar(pizza.total + pizza.tip));
                    } else if (cook.getCook() == Enums.cook.average_cook) {
                        AverageCook averageCook = new AverageCook();
                        averageCook.cook(pizza, c, inventory);
                        pizza.total = meatToppings.getPrice();
                        out("The customer " + c.name + " got a " + c.prefSize + " pizza, that has " + c.prefCrust + " crust, It has " + c.prefCrust + " sauce, with " + c.prefTopping.size() + " toppings being: " + toppingsList + " All for " + Utility.asDollar(pizza.total));
                        out("Total pizza price + tip is: " + Utility.asDollar(pizza.total + pizza.tip));
                    } else {
                        ExpertCook expertCook = new ExpertCook();
                        expertCook.cook(pizza, c, inventory);
                        pizza.total = meatToppings.getPrice();
                        out("The customer " + c.name + " got a " + c.prefSize + " pizza, that has " + c.prefCrust + " crust, It has " + c.prefCrust + " sauce, with " + c.prefTopping.size() + " toppings being: " + toppingsList + " All for " + Utility.asDollar(pizza.total));
                        out("Total pizza price + tip is: " + Utility.asDollar(pizza.total + pizza.tip));
                    }
                } else if (rand <= .20) {
                    out("Extra cheese for 10% of pizza total has been added! Old pizza total: " + Utility.asDollar(pizza.total));
                    ExtraCheese extraCheese = new ExtraCheese(pizza);
                    out("This brings the total of the pizza to: " + Utility.asDollar(pizza.total));
                    if (cook.getCook() == Enums.cook.bad_cook) {
                        BadCook badCook = new BadCook();
                        badCook.cook(pizza, c, inventory);
                        pizza.total = extraCheese.getPrice();
                        out("The customer " + c.name + " got a " + c.prefSize + " pizza, that has " + c.prefCrust + " crust, It has " + c.prefCrust + " sauce, with " + c.prefTopping.size() + " toppings being: " + toppingsList + " All for " + Utility.asDollar(pizza.total));
                        out("Total pizza price + tip is: " + Utility.asDollar(pizza.total + pizza.tip));
                    } else if (cook.getCook() == Enums.cook.average_cook) {
                        AverageCook averageCook = new AverageCook();
                        averageCook.cook(pizza, c, inventory);
                        pizza.total = extraCheese.getPrice();
                        out("The customer " + c.name + " got a " + c.prefSize + " pizza, that has " + c.prefCrust + " crust, It has " + c.prefCrust + " sauce, with " + c.prefTopping.size() + " toppings being: " + toppingsList + " All for " + Utility.asDollar(pizza.total));
                        out("Total pizza price + tip is: " + Utility.asDollar(pizza.total + pizza.tip));
                    } else {
                        ExpertCook expertCook = new ExpertCook();
                        expertCook.cook(pizza, c, inventory);
                        pizza.total = extraCheese.getPrice();
                        out("The customer " + c.name + " got a " + c.prefSize + " pizza, that has " + c.prefCrust + " crust, It has " + c.prefCrust + " sauce, with " + c.prefTopping.size() + " toppings being: " + toppingsList + " All for " + Utility.asDollar(pizza.total));
                        out("Total pizza price + tip is: " + Utility.asDollar(pizza.total + pizza.tip));
                    }
                } else {
                    if (cook.getCook() == Enums.cook.bad_cook) {
                        BadCook badCook = new BadCook();
                        badCook.cook(pizza, c, inventory);
                        pizza.total = pizza.getPrice(c.prefTopping, c.prefSize, pizza.getCookCond(), c);
                        out("The customer " + c.name + " got a " + c.prefSize + " pizza, that has " + c.prefCrust + " crust, It has " + c.prefCrust + " sauce, with " + c.prefTopping.size() + " toppings being: " + toppingsList);
                        cashier.calculateOrderCost(pizza, c);
                        out("Total pizza price + tip is: " + Utility.asDollar(pizza.total + pizza.tip));
                    } else if (cook.getCook() == Enums.cook.average_cook) {
                        AverageCook averageCook = new AverageCook();
                        averageCook.cook(pizza, c, inventory);
                        pizza.total = pizza.getPrice(c.prefTopping, c.prefSize, pizza.getCookCond(), c);
                        out("The customer " + c.name + " got a " + c.prefSize + " pizza, that has " + c.prefCrust + " crust, It has " + c.prefCrust + " sauce, with " + c.prefTopping.size() + " toppings being: " + toppingsList);
                        cashier.calculateOrderCost(pizza, c);
                        out("Total pizza price + tip is: " + Utility.asDollar(pizza.total + pizza.tip));
                    } else {
                        ExpertCook expertCook = new ExpertCook();
                        expertCook.cook(pizza, c, inventory);
                        pizza.total = pizza.getPrice(c.prefTopping, c.prefSize, pizza.getCookCond(), c);
                        out("The customer " + c.name + " got a " + c.prefSize + " pizza, that has " + c.prefCrust + " crust, It has " + c.prefCrust + " sauce, with " + c.prefTopping.size() + " toppings being: " + toppingsList);
                        cashier.calculateOrderCost(pizza, c);
                        out("Total pizza price + tip is: " + Utility.asDollar(pizza.total + pizza.tip));
                    }
                }
                moneyIn(pizza.total + pizza.tip);
            }

    }

    public ArrayList<Customer> getCustomers(Enums.DayOfWeek day) {
        // 15 to 20 buyers arrive (2-8 on Fri/Sat/Sun)
        ArrayList<Customer> buyers = new ArrayList<Customer>();
        int customerMin;  //normal Mon-Thur
        int customerMax;
        int customerCount;
        int studentCount = 0;
        if (day == Enums.DayOfWeek.Fri || day == Enums.DayOfWeek.Sat || day == Enums.DayOfWeek.Sun) {
            customerMin = 8;
            customerMax = 14;
            customerCount = Utility.rndFromRange(customerMin,customerMax);
            for (int i=1; i<=customerCount; ++i) {
                buyers.add(new Customer());
                if (buyers.get(i - 1).studentStat == Enums.Student.student) {
                    studentCount++;
                }
            }
            out("The DoughMasters has "+customerCount+" buyers with "+studentCount+" of them being students today...");
            return buyers;
        } else {
            customerMin = 15;
            customerMax = 21;
            customerCount = Utility.rndFromRange(customerMin,customerMax);
            for (int i=1; i<=customerCount; ++i) buyers.add(new Customer());
            out("The DoughMasters has "+customerCount+" buyers today...");
            return buyers;
        }
    }

    public void startRestock() {
        ArrayList<Staff> manager = Staff.getStaffByType(staff, Enums.StaffType.Manager);

        int randomManager = Utility.rndFromRange(0, manager.size() - 1);
        Manager managers = (Manager) manager.get(randomManager);

        out("The manager "+managers.name+" is currently checking the inventory...");
        moneyOut(managers.managerRestock(inventory));
    }
}
