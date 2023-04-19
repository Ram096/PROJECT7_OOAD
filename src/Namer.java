import java.util.Arrays;
import java.util.List;

// A silly little helper class for doing unique names
// a Namer gets initialized with an ArrayList of Strings of any length
// the Namer will provide getNext to return the next name in the list until it runs out
// then it will start over adding a number to the name

// Typical use:
// import java.util.List;
// import java.util.Arrays;
// List<String> folks = Arrays.asList("Fred","Ethel","Lucy","Desi");
// Namer n = new Namer(folks);
// n.getNext();  // gets the next name

<<<<<<< HEAD
=======
//Works Cited:  Namer Class belongs to Bruce Montgomery - OOAD Spring 2023 Project 2


>>>>>>> 0f5faf014af074c8285acac0249fd0c9bb858c13
public class Namer {
    List<String> names;  // names to choose from
    int pass; // how many times we've used the names
    int nextName;  // a pointer to which name we're on 0 to n-1
    Namer(List<String> n){
        names = n;
        pass = 1;
        nextName = 0;
    }
    String getNext() {
        String name = names.get(nextName);
        if (pass>1) name = name + "-" + pass;
        nextName += 1;
        if (nextName == names.size()) {
            nextName = 0;
            pass = pass + 1;
        }
        return name;
    }

}
<<<<<<< HEAD








=======
>>>>>>> 0f5faf014af074c8285acac0249fd0c9bb858c13
