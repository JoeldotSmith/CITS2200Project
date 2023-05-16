import java.io.*;
// import java.util.*;

public class CITS2200ProjectTester {
	public static void loadGraph(CITS2200Project project, String path) {
		// The graph is in the following format:
		// Every pair of consecutive lines represent a directed edge.
		// The edge goes from the URL in the first line to the URL in the second line.
		try {
			try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
				while (reader.ready()) {
					String from = reader.readLine();
					String to = reader.readLine();
					System.out.println("Adding edge from " + from + " to " + to);
					project.addEdge(from, to);
				}
			}
		} catch (Exception e) {
			System.out.println("There was a problem:");
			System.out.println(e.toString());
		}
	}

	public static void main(String[] args) {
		// Change this to be the path to the graph file.
		String pathToGraphFile = "example_graph.txt";
		// Create an instance of your implementation.
	    Project proj = new Project();
		// Load the graph into the project.
		loadGraph(proj, pathToGraphFile);

		// Write your own tests!
        addEdgeTest(proj);
        getShortestPathTest(proj, pathToGraphFile);
        getHamiltonianPathTest(proj);
        getStronglyConnectedComponentsTest(proj);
	}


    public static void addEdgeTest(Project project){
        int count = 0;
        System.out.println("\naddEdgeTest\n");
        for (Project.Vertex vert : project.getTree()){
            System.out.println(vert.name());
            for (int i = 0; i < vert.getAllLinks().size(); i++){
                Project.Edge link = vert.getlink(i);
                System.out.println("      " + link.getVertName() + " " + link.getIndex());
            }
            System.out.println("\n");
            count++;
        }
        if (Project.getNumofVertex() == count){
            System.out.println("Passed");
        } else{
            System.out.println("Failed");
        }
        System.out.println("\n\n");
    }


    public static void getShortestPathTest(Project project, String path){
        System.out.println("\ngetShortestPathTest\n");
        if (path.equals("example_graph.txt")){
            int test0 = project.getShortestPath("/wiki/Maximum_flow_problem", "/wiki/Maximum_flow_problem");
            if (test0 == 0){
                System.out.println("Passed 0: needed 0 got " + Integer.toString(test0)+ "\n");
            } else{
                System.out.println("Failed: needed 0 got " + Integer.toString(test0)+ "\n");
            }
            int test1 = project.getShortestPath("/wiki/Maximum_flow_problem", "/wiki/Multi-commodity_flow_problem");
            if (test1 == 2){
                System.out.println("Passed 1: needed 2 got " + Integer.toString(test1)+ "\n");
            } else{
                System.out.println("Failed: needed 2 got " + Integer.toString(test1)+ "\n");
            }
            int test2 = project.getShortestPath("/wiki/Flow_network", "/wiki/Nowhere-zero_flow");
            if (test2 == -1){
                System.out.println("Passed 2: needed -1 got " + Integer.toString(test2)+ "\n");
            } else{
                System.out.println("Failed: needed -1 got " + Integer.toString(test2)+ "\n");
            }
            int test3 = project.getShortestPath("/wiki/Nowhere-zero_flow", "/wiki/Flow_network");
            if (test3 == 1){
                System.out.println("Passed 3: needed 1 got " + Integer.toString(test3)+ "\n");
            } else{
                System.out.println("Failed: needed 1 got " + Integer.toString(test3)+ "\n");
            }
            int test4 = project.getShortestPath("/wiki/Nowhere-zero_flow", "/wiki/Gomory%E2%80%93Hu_tree");
            if (test4 == 6){
                System.out.println("Passed 4: needed 6 got " + Integer.toString(test4)+ "\n");
            } else{
                System.out.println("Failed: needed 6 got " + Integer.toString(test4)+ "\n");
            } 
            int test5 = project.getShortestPath("/wiki/Nowhere-zero_flow", "/wiki/Braess%27_paradox");
            if (test5 == 2){
                System.out.println("Passed 5: needed 2 got " + Integer.toString(test5)+ "\n");
            } else{
                System.out.println("Failed: needed 2 got " + Integer.toString(test5)+ "\n");
            }
        }

        if (path.equals("testData.txt")){
            int test0 = project.getShortestPath("/wiki/test14", "/wiki/test6");
            if (test0 == 8){
                System.out.println("Passed 0: needed 8 got " + Integer.toString(test0) + "\n");
            } else{
                System.out.println("Failed: needed 8 got " + Integer.toString(test0)+ "\n");
            }
            int test1 = project.getShortestPath("/wiki/test1", "/wiki/test19");
            if (test1 == 17){
                System.out.println("Passed 1: needed 17 got " + Integer.toString(test1) + "\n");
            } else{
                System.out.println("Failed: needed 17 got " + Integer.toString(test1) + "\n");
            }
        }
        
        
    }

    public static void getHamiltonianPathTest(Project project){
        System.out.println("\ngetHamiltonianPathTest\n");
        String[] result = project.getHamiltonianPath();
        if (result.length == 0){
            System.out.println("No Hamiltonian path found");
        } else{
            for (String s : result){
                System.out.println(s);
            }
        }
    }


    public static void getStronglyConnectedComponentsTest(Project project){
        System.out.println("\ngetStronglyConnectedComponentsTest\n");

        String[][] result = project.getStronglyConnectedComponents();
        if (result.length == 0){
            System.out.println("No Strongly Connected Componants Found");
        } else{
            for (String[] sArr : result){
                for (String str : sArr){
                    System.out.println(str);
                }
            }
        }
    }
}