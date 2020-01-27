package main;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Graphs {
    private static class Node {
        private List<Integer> children;
        private int parent;

        public Node() {
            this.parent = -1;
            this.children = new ArrayList<>();
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
                if (!visited[next]) {
                    visited[next] = true;
                    toVisit.add(next);
                    // Do something with the current (parent) node and next (child) node
                }
            }
        }
    }
}
