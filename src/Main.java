import java.util.*;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Type integers of your AVL: ");
        ArrayList<Integer> array = new ArrayList<>();
        while (sc.hasNextInt() || !sc.next().equals(".")) {
            array.add(sc.nextInt());
        }
        AVLTree<Integer> tree = new AVLTree<>(array);

        AVLTree<Integer> tree2 = new AVLTree<>();
        for(int i=0; i<7; i++)
            tree2.insert(i);
        tree2.PrintTree();

        System.out.print("\nTree is AVL: "+tree.isAVL(tree.root));          //isAVL?
        System.out.print("\nTree2 is AVL: "+tree2.isAVL(tree2.root));           //isAVL?
        System.out.print(tree.r_search(6));                         //recursive
        System.out.print(tree.i_search(2));                         //iterative
        System.out.print(tree2.r_search(6));                         //iterative
        System.out.print(tree2.i_search(2));                         //iterative

    }
}