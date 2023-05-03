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