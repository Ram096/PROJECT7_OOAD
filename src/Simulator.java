// Simulator to cycle for selected number of days
/*
Class Simulator implements the interface of the pizza store

 */



public class Simulator implements SysOut{

    int numDays;
    Enums.DayOfWeek dayOfWeek;

    Simulator() {
        numDays = 2;
        dayOfWeek = Utility.randomEnum(Enums.DayOfWeek.class);
    }

    // cycling endlessly through enum values
    // https://stackoverflow.com/questions/34159413/java-get-next-enum-value-or-start-from-first
    public Enums.DayOfWeek getNextDay(Enums.DayOfWeek e)
    {
        int index = e.ordinal();
        int nextIndex = index + 1;
        Enums.DayOfWeek[] days = Enums.DayOfWeek.values();
        nextIndex %= days.length;
        return days[nextIndex];
    }

    void run() {
        out("------------ DOUGH MASTERS -------------");
        out("Checking our inventory...\n");
        out("Welcome to Dough Masters");
        DoughMasters dough = new DoughMasters();

    }
}
