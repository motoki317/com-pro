package main;

import com.sun.istack.internal.Nullable;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Graphs {
    public static class Node {
        private final List<Integer> children;
        private int parent;

        public Node() {
            this.parent = -1;
            this.children = new ArrayList<>();
        }

        public Node(int parent, List<Integer> children) {
            this.parent = parent;
            this.children = children;
        }

        public void addChild(int child) {
            this.children.add(child);
        }
    }

    // Tree dfs, expected no loop in the graph
    // First call: treeDfs(nodes, -1, root); (root is the root index)
    private static void treeDfs(Node[] nodes, int parent, int current) {
        if (parent >= 0) {
            // Do something from their parent & current node
        }

        for (int child : nodes[current].children) {
            if (child != parent) {
                nodes[child].parent = current;
                treeDfs(nodes, current, child);
            }
        }
    }

    // treeBfs
    // First call: treeBfs(nodes, root); (root is the root index)
    private static void treeBfs(Node[] nodes, int root) {
        Queue<Integer> toVisit = new ArrayDeque<>();
        toVisit.add(root);

        while (!toVisit.isEmpty()) {
            int current = toVisit.poll();
            Node node = nodes[current];

            // Do something with the current node

            for (int next : node.children) {
                if (next != nodes[current].parent) {
                    toVisit.add(next);
                    nodes[next].parent = current;
                    // Do something with the current (parent) node and next (child) node
                }
            }
        }
    }

    // bfs
    // First call: bfs(nodes, root); (root is the root index)
    private static void bfs(Node[] nodes, int root) {
        boolean[] visited = new boolean[nodes.length];
        Queue<Integer> toVisit = new ArrayDeque<>();

        visited[root] = true;
        toVisit.add(root);

        while (!toVisit.isEmpty()) {
            int current = toVisit.poll();
            Node node = nodes[current];

            // Do something with the current node

            for (int next : node.children) {
                // Do something with the current (parent) node and next (child) node

                if (!visited[next]) {
                    visited[next] = true;
                    toVisit.add(next);
                }
            }
        }
    }

    /**
     * Checks if the given graph is DAG (Directed Acyclic Graph).
     * @param nodes List of nodes
     * @return true if the graph is DAG.
     */
    public static boolean isDAG(Node[] nodes) {
        return topologicalSort(nodes) != null;
    }

    /**
     * Topologically sort the graph according to Kahn's algorithm.
     * https://en.wikipedia.org/wiki/Topological_sorting
     * @param nodesArray array of all nodes
     * @return Non-null topologically ordered indices; returns null if the graph is *not* DAG.
     */
    @Nullable
    public static List<Integer> topologicalSort(Node[] nodesArray) {
        class INode {
            private final int i;
            private final Set<Integer> parents;
            private final Set<Integer> children;

            private INode(int i, Set<Integer> children) {
                this.i = i;
                this.parents = new HashSet<>();
                this.children = children;
            }
        }

        List<INode> nodes = IntStream.range(0, nodesArray.length)
                .mapToObj(i -> new INode(i, new HashSet<>(nodesArray[i].children)))
                .collect(Collectors.toList());
        // add parents info
        nodes.forEach(n -> n.children.forEach(child -> nodes.get(child).parents.add(n.i)));

        // Empty list that will contain the sorted elements
        List<Integer> ordering = new ArrayList<>();
        // Set of all nodes with no incoming edge
        Queue<INode> next = nodes.stream().filter(n -> n.parents.isEmpty())
                .collect(Collectors.toCollection(ArrayDeque::new));

        while (!next.isEmpty()) {
            INode n = next.poll();
            ordering.add(n.i);

            for (int child : n.children) {
                INode m = nodes.get(child);
                m.parents.remove(n.i);
                if (m.parents.isEmpty()) {
                    next.add(m);
                }
            }
            n.children.clear();
        }

        if (nodes.stream().map(n -> n.parents.isEmpty() && n.children.isEmpty())
                .reduce(true, (acc, elt) -> acc && elt)) {
            return ordering;
        } else {
            return null;
        }
    }

    // Dijkstra's algorithm for finding the shortest path in graph

    private static class Edge {
        private final int length;

        Edge(int length) {
            this.length = length;
        }
    }

    private static class GraphNode {
        private final List<Map.Entry<Integer, Edge>> children;
        private int parent;

        public GraphNode() {
            this.parent = -1;
            this.children = new ArrayList<>();
        }

        public void addChild(int child, int length) {
            this.children.add(new AbstractMap.SimpleEntry<>(child, new Edge(length)));
        }
    }

    // Finds the shortest path from `from` node to `to` node, using dijkstra's algorithm.
    private static int dijkstra(GraphNode[] nodes, int from, int to) {
        boolean[] visited = new boolean[nodes.length];
        Queue<Integer> toVisit = new ArrayDeque<>();
        int[] distances = new int[nodes.length];

        Arrays.fill(distances, Integer.MAX_VALUE / 2);
        distances[from] = 0;

        visited[from] = true;
        toVisit.add(from);

        while (!toVisit.isEmpty()) {
            int current = toVisit.poll();
            if (visited[current]) continue;

            GraphNode node = nodes[current];

            // Do something with the current node

            for (Map.Entry<Integer, Edge> edge : node.children) {
                int nextDistance = distances[current] + edge.getValue().length;
                int next = edge.getKey();
                distances[next] = Math.min(distances[next], nextDistance);

                toVisit.add(next);
            }

            visited[current] = true;
            if (current == to) {
                return distances[to];
            }
        }
        return -1;
    }
}
