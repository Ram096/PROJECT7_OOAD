// OrderData class & PizzaOrderDisplay
/*
The OrderData class implements the Subject interface, which defines methods for registering, removing, and notifying observers of changes to the subject.
It maintains a list of observers, a list of orders, and an order number counter.
It has methods to add orders, get the list of orders, get the total number of orders, and clear the orders.
When a new order is added, it creates a message string and adds it to the list of orders, then calls the notifyObservers method to update all registered observers.

The PizzaOrderDisplay class implements the Observer interface,
which defines a single update method that is called when the subject notifies observers of changes.
The PizzaOrderDisplay constructor takes a Subject object as a parameter and registers itself as an observer of that subject.
When the update method is called, it simply displays the message on the console.
 */

import java.util.List;
import java.util.ArrayList;

class OrderData implements Subject {
    private List<Observer> observers;
    private List<String> orders;
    private int orderNumber;

    public OrderData() {
        observers = new ArrayList<>();
        orders = new ArrayList<>();
        orderNumber = 1;
    }

    public void registerObserver(Observer o) {
        observers.add(o);
    }

    public void removeObserver(Observer o) {
        int i = observers.indexOf(o);
        if (i >= 0) {
            observers.remove(i);
        }
    }

    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    public void addOrder(String orderDesc) {
        String message = "Order number " + orderNumber + " completed: " + orderDesc;
        orders.add(message);
        notifyObservers(message);
        orderNumber++;
    }

    public List<String> getOrders() {
        return orders;
    }
    public int getTotalOrders() {
        return orders.size();
    }
    public void clearOrders() {
        orderNumber = 1;
        orders.clear();
    }
}

class PizzaOrderDisplay implements Observer {
    private Subject orderData;

    public PizzaOrderDisplay(Subject orderData) {
        this.orderData = orderData;
        orderData.registerObserver(this);
    }

    public void update(String message) {
        display(message);
    }

    public void display(String message) {
        System.out.println(message);
    }
}
