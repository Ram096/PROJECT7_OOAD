import java.util.Arrays;
import java.util.List;


public class Delivery implements SysOut{
    String location;
    private Customer customer;
    static List<String> address = Arrays.asList("123 Main St, Boulder, CO 80302",    "456 Pearl St, Boulder, CO 80301",    "789 Walnut St, Boulder, CO 80302",    "1011 Canyon Blvd, Boulder, CO 80302",    "1213 Spruce St, Boulder, CO 80302");
    static int deliveryTime;
    static Namer namer = new Namer(address);

    public Delivery() {
        this.customer = customer;
        this.address = address;
    }

    public void deliver(Customer c) {

        deliveryTime = Utility.rndFromRange(20, 50);
        location = namer.getNext();

        out("Customer " + c.name + " wants delivery, so we are delivering the pizza to " + c.name + " at " + location);
        out("Expected wait time " + deliveryTime + " mins");
        out("Pizza delivered successfully.");
    }


}

