# CITS2200Project

README how the code is set up

tree is the graph of Vertex' it is an ArrayList<Vertex> so you can use .size(), .get(i) ect
  
A Vertex is a local class that I've written its set up to store its name, index in tree, edges, whether or not its in a stack, whether or not its been explored and whats its parent index
  
An Edge is another local class which stores the name of the vertex it goes to, the index of the vertex it goes to and whether or not it has been explored;
  
  
  
so the graph is set up like this
```
  ArrayList<Vertex> tree = {
                              Vertex1,
                              Vertex2{
                                  String nameOfFromVertex;
                                  int indexOfFromVertex;
                                  ArrayList<Edge> links = {
                                                            Edge1,
                                                            Edge2{
                                                                  String nameOfToVertex;
                                                                  int indexOfToVertex;
                                                            },
                                                            Edge3,
                                                            ...,
                                                            EdgeN
                                                          }
                              },
                              Vertex3,
                              ...,
                              VertexN
  }
  
```
