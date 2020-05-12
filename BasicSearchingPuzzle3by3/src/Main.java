import java.util.Vector;

public class Main
{

    public static void main(String[] args)
    {
        State initialState;
        State finalState;
        Node root;
        Node finalNode;
        Result result;
        //

        // 4 movements apart
        initialState = new State();
        initialState.matrix[0][0] = 0;
        initialState.matrix[0][1] = 8;
        initialState.matrix[0][2] = 7;
        initialState.matrix[1][0] = 6;
        initialState.matrix[1][1] = 5;
        initialState.matrix[1][2] = 4;
        initialState.matrix[2][0] = 3;
        initialState.matrix[2][1] = 2;
        initialState.matrix[2][2] = 1;

        finalState = new State();
        finalState.matrix[0][0] = 1;
        finalState.matrix[0][1] = 2;
        finalState.matrix[0][2] = 3;
        finalState.matrix[1][0] = 4;
        finalState.matrix[1][1] = 5;
        finalState.matrix[1][2] = 6;
        finalState.matrix[2][0] = 7;
        finalState.matrix[2][1] = 8;
        finalState.matrix[2][2] = 0;

        root = new Node();
        root.setState(initialState);
        finalNode = new Node();
        finalNode.setState(finalState);
        //result = DepthFirst.search(root, finalNode);
        //result = BreadthFirst.search(root, finalNode);
        result = BestFirst.search(root, finalNode);
        System.out.println(result.getPlan());
        System.out.print("Number of Moves: ");
        System.out.println(result.getPlan().size()-1);

    }//end main
}//end class Main
