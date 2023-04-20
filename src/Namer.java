// Namer class
/*

 */

import java.util.Arrays;
import java.util.List;

// Typical use:
// import java.util.List;
// import java.util.Arrays;
// List<String> folks = Arrays.asList("Fred","Ethel","Lucy","Desi");
// Namer n = new Namer(folks);
// n.getNext();  // gets the next name

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








