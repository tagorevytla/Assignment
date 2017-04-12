/**
 * Created by Vytlasai on 4/12/2017.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

    class GraphFindAllPaths<T> implements Iterable<T> {


        private final Map<T, Map<T, Double>> graph = new HashMap<T, Map<T, Double>>();


        public boolean addNode(T node) {
            if (node == null) {
                throw new NullPointerException("The input node cannot be null.");
            }
            if (graph.containsKey(node)) return false;

            graph.put(node, new HashMap<T, Double>());
            return true;
        }


        public void addEdge (T source, T destination, double length) {
            if (source == null || destination == null) {
                throw new NullPointerException("Source and Destination, both should be non-null.");
            }
            if (!graph.containsKey(source) || !graph.containsKey(destination)) {
                throw new NoSuchElementException("Source and Destination, both should be part of graph");
            }
            graph.get(source).put(destination, length);
        }

        public void removeEdge (T source, T destination) {
            if (source == null || destination == null) {
                throw new NullPointerException("Source and Destination, both should be non-null.");
            }
            if (!graph.containsKey(source) || !graph.containsKey(destination)) {
                throw new NoSuchElementException("Source and Destination, both should be part of graph");
            }
            graph.get(source).remove(destination);
        }


        public Map<T, Double> edgesFrom(T node) {
            if (node == null) {
                throw new NullPointerException("The node should not be null.");
            }
            Map<T, Double> edges = graph.get(node);
            if (edges == null) {
                throw new NoSuchElementException("Source node does not exist.");
            }
            return Collections.unmodifiableMap(edges);
        }

        @Override public Iterator<T> iterator() {
            return graph.keySet().iterator();
        }
    }


    public class FindAllPaths<T> {

        private final GraphFindAllPaths<T> graph;

        public FindAllPaths(GraphFindAllPaths<T> graph) {
            if (graph == null) {
                throw new NullPointerException("The input graph cannot be null.");
            }
            this.graph = graph;
        }

        private void validate (T source, T destination) {

            if (source == null) {
                throw new NullPointerException("The source: " + source + " cannot be  null.");
            }
            if (destination == null) {
                throw new NullPointerException("The destination: " + destination + " cannot be  null.");
            }
            if (source.equals(destination)) {
                throw new IllegalArgumentException("The source and destination: " + source + " cannot be the same.");
            }
        }


        public List<List<T>> getAllPaths(T source, T destination) {
            validate(source, destination);

            List<List<T>> paths = new ArrayList<List<T>>();
            recursive(source, destination, paths, new LinkedHashSet<T>());
            System.out.println(paths);
            return paths;
        }

        private void recursive (T current, T destination, List<List<T>> paths, LinkedHashSet<T> path) {
            path.add(current);

            if (current.equals(destination)) {
                paths.add(new ArrayList<T>(path));
                path.remove(current);
                return;
            }

            final Set<T> edges  = graph.edgesFrom(current).keySet();

            for (T t : edges) {
                if (!path.contains(t)) {
                    recursive (t, destination, paths, path);

                }
            }

            path.remove(current);
        }

        public static void main(String[] args) {
            GraphFindAllPaths<String> graphFindAllPaths = new GraphFindAllPaths<String>();
            graphFindAllPaths.addNode("A");
            graphFindAllPaths.addNode("B");
            graphFindAllPaths.addNode("C");
            graphFindAllPaths.addNode("D");

            graphFindAllPaths.addEdge("A", "B", 10);
            graphFindAllPaths.addEdge("A", "C", 10);
            graphFindAllPaths.addEdge("B", "D", 10);
            graphFindAllPaths.addEdge("C", "D", 10);

            graphFindAllPaths.addEdge("B", "C", 10);
            graphFindAllPaths.addEdge("C", "B", 10);

            FindAllPaths<String> findAllPaths = new FindAllPaths<String>(graphFindAllPaths);

            findAllPaths.getAllPaths("A", "D");

        }
    }
