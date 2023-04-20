// Simulator to cycle for selected number of days
/*
Class Simulator implements the interface of the pizza store

 */



public class Simulator implements SysOut {

    int numDays;
    Enums.DayOfWeek dayOfWeek;

    Simulator() {
        numDays = 1;
        dayOfWeek = Utility.randomEnum(Enums.DayOfWeek.class);
    }

    public Enums.DayOfWeek getNextDay(Enums.DayOfWeek e)
    {
        int index = e.ordinal();
        int nextIndex = index + 1;
        Enums.DayOfWeek[] days = Enums.DayOfWeek.values();
        nextIndex %= days.length;
        return days[nextIndex];
    }

    void run () {

        out("------------ DOUGH MASTERS -------------");
        out("Checking our inventory...\n");
        out("Welcome to Dough Masters");

        DoughMasters pizza = new DoughMasters();
        for (int day = 1; day <= numDays; day++) {
            out(">>> Start Simulation Day " + day + " " + dayOfWeek);
                pizza.openEveryDay(dayOfWeek);
                pizza.setPizza();
                out("Closing... come back tomorrow!\n");
                dayOfWeek = getNextDay(dayOfWeek);
        }
    }
}