// Component, This interface is used in the observer pattern, where an object (the subject) maintains a list of its dependents (observers)
// and notifies them automatically of any state changes, by calling a method on each observer.
interface Observer {
    void update(String message);
}
