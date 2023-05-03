// Simulator to cycle for selected number of days
/*
Class Simulator implements the interface of the pizza store

 */



public class Simulator implements SysOut {

    int numDays;
    Enums.DayOfWeek dayOfWeek;

    Simulator() {
        numDays = 30;
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
            if (dayOfWeek == Enums.DayOfWeek.Fri || dayOfWeek == Enums.DayOfWeek.Sat || dayOfWeek == Enums.DayOfWeek.Sun) {
                pizza.weekendDays(dayOfWeek);
            } else {
                pizza.weekDays(dayOfWeek);
            }
                out("Closing... come back tomorrow!\n");
                dayOfWeek = getNextDay(dayOfWeek);
        }
    }
}