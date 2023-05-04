// Simulator to cycle for selected number of days
/*
Class Simulator implements the interface of the pizza store
The class has three instance variables: numDays which is an integer that represents the number of days the simulation will run for;
dayOfWeek which is an enumeration of type Enums.DayOfWeek that represents the current day of the week;
and pizza which is an instance of the DoughMasters class.
The constructor initializes the numDays variable to 30, and the dayOfWeek variable is randomly set to an enumeration value from Enums.DayOfWeek using the Utility.randomEnum() method.

The getNextDay() method takes an enumeration value of type Enums.DayOfWeek as input and returns the next day of the week based on the input value.

The run() method is the main method of the class.
It prints some introductory messages, creates an instance of the DoughMasters class, and then loops through the simulation for numDays days.
It checks the current day of the week and calls the appropriate method of the DoughMasters class depending on whether it is a weekday or weekend day.
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
