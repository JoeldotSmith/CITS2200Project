import java.util.ArrayList;

// to compile and run/test
// javac CITS220ProjectTester.java
// java CITS220ProjectTester.java


public class Project implements CITS2200Project{

    ArrayList<Vertex> tree = new ArrayList<>();
    static int numOfVertex = 0;
    ArrayList<String> HamiltonianPath = new ArrayList<>();
    ArrayList<Vertex> L = new ArrayList<>();
    int numComponants = -1;

    public ArrayList<Vertex> getTree(){
        return tree;
    }
    public static int getNumofVertex(){
        return numOfVertex;
    }




    public void addEdge(String urlFrom, String urlTo) {
        boolean fromInTree = false;
        boolean toInTree = false;
        int toIndex = -1;

        if (numOfVertex != 0){
            for (Vertex vert : tree){
                if (vert.name().equals(urlFrom)){
                    fromInTree = true;
                }
                if (vert.name().equals(urlTo)){
                    toInTree = true;
                    toIndex = vert.getVertNum();
                }

            }
        }

        if (!fromInTree){
            Vertex fromVertex = new Vertex(urlFrom, numOfVertex, new ArrayList<Edge>());
            numOfVertex += 1;
            tree.add(fromVertex);
        }
        if(!toInTree){
            Vertex toVertex = new Vertex(urlTo, numOfVertex, new ArrayList<Edge>());
            toIndex = numOfVertex;
            numOfVertex += 1;
            tree.add(toVertex);
        }



        

        for (int i = 0; i < tree.size(); i++){
            Vertex vert = tree.get(i);
            if (vert.name().equals(urlFrom)){
                vert.addLink(new Edge(urlTo, toIndex));
            }
            
        }
    }

    /*
     * Part 1
     * Tree is a non weighted graph so dikstra, a-star ect wont work so breadth first search it is complexity O(E+V)
     * https://en.wikipedia.org/wiki/Breadth-first_search
     * 
     * Return -1 if no path found
     * 
     */
    public int getShortestPath(String urlFrom, String urlTo) {
        // TODO fix bug where returns a path not the smallest path see CITS2200ProjectTester.java running example_graph.txt test4

        if (tree.isEmpty()){
            throw new IllegalStateException("Tree size = 0 need to addEdge() first");
        }

        if (urlFrom.equals(urlTo)){
            return 0;
        }

        for (Vertex reset: tree){
            reset.setExplored(0);
        }

        ArrayList<Vertex> queue = new ArrayList<>();

        int rootIndex = -1;
        int finalIndex = -1;

        int count = 0;
        boolean stop = false;


        for (int i = 0; i < tree.size(); i++){
            Vertex ver = tree.get(i);
            if (ver.name().equals(urlFrom)){
               
                rootIndex = ver.getVertNum();
            }
            if (ver.name().equals(urlTo)){
                
                finalIndex = ver.getVertNum();
            }
        }

        if (rootIndex == -1 || finalIndex == -1){
            throw new IllegalArgumentException("urlFrom or urlTo not in tree");
        }


        // System.out.println("RootIndex = " + rootIndex);
        // System.out.println("FinalIndex = " + finalIndex + "\n");


        tree.get(rootIndex).setExplored(1);
        queue.add(tree.get(rootIndex));

        while (!queue.isEmpty() && !stop){
            Vertex v = queue.remove(0);



            // System.out.println("Current location: " + v.getVertNum());



            if (v.getVertNum() == finalIndex){
                stop = true;
                break;
            }

      tree.get(v.getVertNum()).setExplored(1);



            for (int eachEdge = 0; eachEdge < v.links.size(); eachEdge++){
                Edge w = v.getlink(eachEdge);
                int wInt = 0;
                for (Vertex vert : tree){
                    if (vert.name().equals(w.getVertName())){
                        wInt = vert.getVertNum();
                    }
                }



                // System.out.println("Checking if index " + wInt + "(" + w.getVertName() + ")" + " has been explored");


                if (tree.get(wInt).getExplored() == 0){
                    
                    tree.get(wInt).setParent(v.getVertNum());
                    // System.out.println("Setting index " + wInt + " parent to " + v.getVertNum());
                    if (tree.get(wInt).getVertNum() == finalIndex){
                        stop = true;
                        break;
                    }
                    queue.add(tree.get(wInt));
                    
                    
                }
            }
        }

        Vertex path = tree.get(finalIndex);

        if (path.getParent() == -1){
            return -1;
        }

        
        while (path.getVertNum() != rootIndex){
            System.out.println(path.name());
            // System.out.println(Integer.toString(path.parentNum));
            count += 1;
            path = tree.get(path.getParent());
        }

        System.out.println(path.name());
        return count;
    }

    @Override
    public String[] getCenters() {
        // TODO part 4 getCenters()
        throw new UnsupportedOperationException("Unimplemented method 'getCenters'");
    }
    
    /*
     * Part 3
     * Two pass algorithm utilising depth first search
     * https://en.wikipedia.org/wiki/Kosaraju%27s_algorithm
     * 
     */
    public String[][] getStronglyConnectedComponents() {
        if (tree.size() == 0){
            throw new IllegalStateException("Tree size = 0 need to addEdge() first");
        }


        // for each vertex of the graph set as unvisted
        for (int i = 0; i < tree.size(); i++){
            tree.get(i).setExplored(0);
        }

        // for each vertex visit it
        for (int i = 0; i < tree.size(); i++){
            
            visit(tree.get(i));
        }
        // System.out.println("L:");
        // for (Vertex vert : L){
        //     System.out.println(vert.name());
        // }
        // System.out.println("\n");

        // for each element u in order of L assign it
        for (int i = 0; i < L.size(); i++){
            //System.out.println("Now creating new componant starting with "+ tree.get(L.get(i).getVertNum()).name() + " index in tree = " + tree.get(L.get(i).getVertNum()).getVertNum());
            assign(tree.get(L.get(i).getVertNum()), 1);

        }







        // Converting to correct type String[][] seperate from algorithm
        ArrayList<ArrayList<String>> strongComponants = new ArrayList<>();
        int largestComponant = 0;
        for (int i = 0; i <=
         numComponants; i++){
            ArrayList<String> temp = new ArrayList<>();
            int count = 0;
            for (Vertex vert : tree){
                
                if (vert.getComponant() == i){
                    temp.add(tree.get(vert.getVertNum()).name());
                    count++;
                }
            }
            if (count > largestComponant){
                largestComponant = count;
            }
            strongComponants.add(temp);

        }
        String[][] comp = new String[numComponants+1][largestComponant];
        
        for (int i = 0; i < strongComponants.size(); i++){
            for (int j = 0; j< strongComponants.get(i).size();j++){
                comp[i][j] = strongComponants.get(i).get(j);
            }
        }
        
        

        return comp;
    }

    /*
     * Part 2
     * Implimentation of a depth first search + backtracking to find if a hamilton path exits or not
     * Complexity O(N!)
     * 
     * https://www.hackerearth.com/practice/algorithms/graphs/hamiltonian-path/tutorial/#:~:text=Hamiltonian%20Path%20is%20a%20path,Hamiltonian%20Paths%20in%20a%20graph.
     */
    public String[] getHamiltonianPath() {
        if (tree.size() > 20){
            throw new IllegalStateException("Tree too large for Hamiltonian path: tree.size() must be <20");
        }
        if (tree.size() == 0){
            throw new IllegalStateException("Tree size = 0 need to addEdge() first");
        }

        for (int i = 0; i < tree.size(); i++){
            tree.get(i).setStack(1);
            // System.out.println("Now checking " + tree.get(i).name());
            if (dfs(tree.get(i), 1, tree.size())){
                break;
            }
        }

        String[] result = new String[HamiltonianPath.size()];
        
        for (int i = 0; i < HamiltonianPath.size(); i++){
            result[i] = HamiltonianPath.get(HamiltonianPath.size() - 1 - i);
        }

        return result;
    }

    private boolean dfs(Vertex v, int inStackCount, int n){
        if (inStackCount == n){
            //System.out.println("Found Path adding " + v.name());
            HamiltonianPath.add(v.name());
            return true;
        }
        // System.out.println("       v.getAllLinks().size = " + Integer.toString(v.getAllLinks().size()));
        for (int i = 0; i < v.getAllLinks().size(); i++){
            //System.out.println("          v.getlink(i).getIndex() = " + Integer.toString(v.getlink(i).getIndex()));
            //System.out.println("             tree.get(v.getlink(i).getIndex()).getStack() = " + Integer.toString(tree.get(v.getlink(i).getIndex()).getStack()));
            if (tree.get(v.getlink(i).getIndex()).getStack() == 0){
                tree.get(v.getlink(i).getIndex()).setStack(1);
                // System.out.println("Now checking " + tree.get(v.getlink(i).getIndex()).name());
                if (dfs(tree.get(v.getlink(i).getIndex()), inStackCount+1, n)){
                    HamiltonianPath.add(v.name());
                    return true;
                }
                tree.get(v.getlink(i).getIndex()).setStack(0);
            }

        }

        return false;
    }

    private void visit(Vertex v){
        // if unvisted
            // set as visited
            // for each neighbour of vertex visit it
            // prepend the vertex v to L
        //System.out.println("Visiting " + v.name());
        if (v.getExplored() == 0){
            tree.get(v.getVertNum()).setExplored(1);
            for (int i = 0; i < v.getAllLinks().size(); i++){
                visit(tree.get(v.getlink(i).getIndex()));
            }
            L.add(tree.get(v.getVertNum()));
            
        }
        //System.out.println("Returning " + v.name());
        return;
    }

    private void assign(Vertex u, int firstPart){
        // if u hasnt been assigned to a componant
            // assign u as componant root
            // for each neighbour of u, v assign(v, root)
        //System.out.println("Assign " + u.name() + " to " + Integer.toString(numComponants));
        if (tree.get(u.getVertNum()).getComponant() == -1){
            if (firstPart == 1){
                numComponants += 1;
            }
            //System.out.println("         adding " + tree.get(u.getVertNum()).name());
            tree.get(u.getVertNum()).setComponant(numComponants);
            for (int i = 0; i < u.getAllLinks().size(); i++){
                assign(tree.get(u.getlink(i).getIndex()), 0);
            }
        }

        return;
    }

    public class Vertex{
        private String name;
        private int vertNum;
        private ArrayList<Edge> links;
        private int explored = 0; // 0 = notExplored, 1 = explored
        private int parentNum = -1; // Parent index, -1 if orphan
        private int inStack = 0; // 0 = notInStack, 1 = inStack
        private int componant = -1; // -1 if has no componant
        

        /*
         * Constructor
         */
        public Vertex(String name, int vertex, ArrayList<Edge> link){
            this.name = name;
            vertNum = vertex;
            links = link;

        }

        /*
         * Gets vertex name;
         */
        public String name(){
            return this.name;
        }

        /*
         * Gets vertexs' vertNum
         */
        public int getVertNum(){
            return vertNum;
        }

        /*
         * Gets explored
         */
        public int getExplored(){
            return explored;
        }

        /*
         * Returns Edge i from links
         */
        public Edge getlink(int i){
            return links.get(i);
        }

        public ArrayList<Edge> getAllLinks(){
            return links;
        }

        /*
         * Gets parent index
         */
        public int getParent(){
            return parentNum;
        }

        /*
         * gets whether vertex is in stack or not 
         */
        public int getStack(){
            return inStack;
        }

        /*
         * gets componant int
         */
        public int getComponant(){
            return componant;
        }

        /*
         * sets componant to i
         */
        public void setComponant(int i){
            componant = i;
        }

        /*
         * Sets stack to i
         */
        public void setStack(int i){
            inStack = i;
        }

        /*
         * sets parent index
         */
        public void setParent(int i){
            parentNum = i;
        }

        /*
         * Adds a link to links
         */
        public void addLink(Edge link){
            links.add(link);
        }

        /*
         * Sets explored to i
         */
        public void setExplored(int i){
            explored = i;
        }

    }


    public class Edge {
        private String toVertex;
        private int vertNum;
        private int explored = 0;


        public Edge(String to, int i){
            toVertex = to;
            vertNum = i;
        }

        public void setExplored(int i){
            explored = i;
        }

        public int getIndex(){
            return vertNum;
        }

        public String getVertName(){
            return toVertex;
        }

        public int getExplored(){
            return explored;
        }


    }
    
}
