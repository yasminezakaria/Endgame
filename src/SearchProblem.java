import java.util.ArrayList;

public abstract class SearchProblem {
/*    Initial State
    State Space
    Goal Test
    Path Cost Function
    Operators/ Actions*/
//initial state, set of finite operators, transition function, goal test function, and path cost function
    ArrayList<String> initialState;
    ArrayList<String> operators;
    abstract ArrayList<String> transition();
    abstract boolean goalTest();
    abstract int pathCost();

}
