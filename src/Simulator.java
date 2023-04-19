<<<<<<< HEAD
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
=======
// Simulator to cycle for select number of days
/*
Class Simulator implements the SysOut interface.
The Simulator class has a constructor that initializes the number of simulation days to run and sets the starting day of the week to a random day using the Utility.randomEnum() method.

The getNextDay() method cycles endlessly through the enum values of Enums.DayOfWeek,
which is an enumerated type that represents the days of the week.
The run() method is the main method of the Simulator class that runs the simulation for the specified number of days.
It creates an instance of the FNCD class and calls either the specialDay() or normalDay() method of the FNCD class depending on whether the current day of the week is Sunday or Wednesday.
The out() method is called before and after each simulation day to output the simulation status.
After simulating for 30 days, the run() method creates an instance of the Buyer class and calls its humanInterface() method to select customers, and then terminates the simulation.
 */

public class Simulator implements SysOut {
    int numDays;

    Enums.DayOfWeek dayOfWeek;
    Simulator() {
        numDays = 31;  // magic number for days to run here
        dayOfWeek = Utility.randomEnum(Enums.DayOfWeek.class);  // we'll start on a random day (for fun)
>>>>>>> 0f5faf014af074c8285acac0249fd0c9bb858c13
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
<<<<<<< HEAD
        out("------------ DOUGH MASTERS -------------");
        out("Checking our inventory...\n");
        out("Welcome to Dough Masters");
        DoughMasters dough = new DoughMasters();

    }
}
=======
        DoughMasters pizzeria = new DoughMasters(); // Pozzeroa
        out("Pizzeria is now opening....");
        for (int day = 1; day <= numDays; ++day) {
            if (day <= 30) {
                out(">>> Start Simulation Day " + day + " " + dayOfWeek);
                if (dayOfWeek == Enums.DayOfWeek.Sun || dayOfWeek == Enums.DayOfWeek.Wed) { // Runs two FNCD in parallel using single threaded approach
                    // Special case where there is a race during these two days
                    out("-------------------------Checking Inventory for South FNCD-------------------------");
//                    pizzeria.updateStaffandInventory();

                    out("-------------------------Starting Washing for South FNCD-------------------------");
                    south.startWash();
                    out("-------------------------Starting Washing for North FNCD-------------------------");
                    north.startWash();

                    out("-------------------------Starting Repair for South FNCD-------------------------");
                    south.startRepair();
                    out("-------------------------Starting Repair for North FNCD-------------------------");
                    north.startRepair();

                    out("-------------------------Starting Race for South FNCD-------------------------");
                    south.startRace();
                    out("-------------------------Starting Race for North FNCD-------------------------");
                    north.startRace();

                    out("-------------------------Starting Sales for South FNCD-------------------------");
                    south.startSales(dayOfWeek);
                    out("-------------------------Starting Sales for North FNCD-------------------------");
                    north.startSales(dayOfWeek);

                    south.payStaff();
                    north.payStaff();

                    out("-------------------------Checking Quitters for South FNCD-------------------------");
                    south.checkForQuitters();
                    out("-------------------------Checking Quitters for North FNCD-------------------------");
                    north.checkForQuitters();

                    out("---------------------------------South FNCD Report---------------------------------");
                    south.reportOut();
                    out("---------------------------------North FNCD Report---------------------------------");
                    north.reportOut();
                } else {
                    out("-------------------------Checking Inventory for South FNCD-------------------------");
//                    south.updateStaffandInventory();
//                    out("-------------------------Checking Inventory for North FNCD-------------------------");
//                    north.updateStaffandInventory();

                    out("-------------------------Starting Washing for South FNCD-------------------------");
                    south.startWash();
                    out("-------------------------Starting Washing for North FNCD-------------------------");
                    north.startWash();

                    out("-------------------------Starting Repair for South FNCD-------------------------");
                    south.startRepair();
                    out("-------------------------Starting Repair for North FNCD-------------------------");
                    north.startRepair();

                    out("-------------------------Starting Sales for South FNCD-------------------------");
                    south.startSales(dayOfWeek);
                    out("-------------------------Starting Sales for North FNCD-------------------------");
                    north.startSales(dayOfWeek);

                    south.payStaff();
                    north.payStaff();

                    out("-------------------------Checking Quitters for South FNCD-------------------------");
                    south.checkForQuitters();
                    out("-------------------------Checking Quitters for North FNCD-------------------------");
                    north.checkForQuitters();

                    out("---------------------------------South FNCD Report---------------------------------");
                    south.reportOut();
                    out("---------------------------------North FNCD Report---------------------------------");
                    north.reportOut();
                }  // normal stuff on other days
                out(">>> End Simulation Day " + day + " " + dayOfWeek + "\n");
                dayOfWeek = getNextDay(dayOfWeek);  // increment to the next day
            }
            if (day == 31) {
                out(">>> Day 31 Customer Selection " + day + " " + dayOfWeek);
                Buyer buy = new Buyer();
                buy.humanInterface();
                out (">>> Ending Customer Selection ");
                break;
            }
        }


    }

}


>>>>>>> 0f5faf014af074c8285acac0249fd0c9bb858c13
