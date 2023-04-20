// Simulator to cycle for selected number of days
/*
Class Simulator implements the interface of the pizza store

 */



public class Simulator implements SysOut {

    int numDays;
    Enums.DayOfWeek dayOfWeek;

    Simulator() {
        numDays = 2;
        dayOfWeek = Utility.randomEnum(Enums.DayOfWeek.class);

        }
    void run () {

        out("------------ DOUGH MASTERS -------------");
        out("Checking our inventory...\n");
        out("Welcome to Dough Masters");
        DoughMasters dough = new DoughMasters();

        dough.getPizza();
    }
}