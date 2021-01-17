package main;

import com.sun.istack.internal.Nullable;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Graphs {
    public static class Graph {
        protected final Node[] nodes;

        public Graph(int n) {
            this.nodes = new Node[n];
            for (int i = 0; i < this.nodes.length; i++) {
                this.nodes[i] = new Node(i);
            }
        }

        public Graph(Node[] nodes) {
            this.nodes = nodes;
        }

        public void addUndirectedEdge(int v, int w) {
            this.nodes[v].addChild(w);
            this.nodes[w].addChild(v);
        }

        public void addDirectedEdge(int v, int w) {
            this.nodes[v].addChild(w);
            this.nodes[w].parent = v;
        }

        public int root(int node) {
            if (this.nodes[node].parent == -1) {
                return node;
            }
            return root(this.nodes[node].parent);
        }

        public Stream<Integer> nodesToRoot(int node) {
            if (this.nodes[node].parent == -1) {
                return Stream.of(node);
            }
            return Stream.concat(
                    Stream.of(node),
                    this.nodesToRoot(this.nodes[node].parent)
            );
        }

        public int distanceToTreeRoot(int node) {
            if (this.nodes[node].parent == -1) {
                return 0;
            }
            return 1 + this.distanceToTreeRoot(this.nodes[node].parent);
        }

        public Stream<Node> treeNodes(int root) {
            return Stream.concat(
                    Stream.of(nodes[root]),
                    nodes[root].children.stream().flatMap(this::treeNodes)
            );
        }

        public List<Pair> allMatching() {
            List<Pair> matching = new ArrayList<>();
            for (Node node : this.nodes) {
                if (node.matching == -1) continue;
                matching.add(new Pair(node.id, node.matching));
            }
            return matching;
        }
    }

    public static class Node {
        private final int id;
        private final List<Integer> children;
        private int parent;
        private int matching;

        public Node(int id) {
            this.id = id;
            this.children = new ArrayList<>();
            this.parent = -1;
            this.matching = -1;
        }

        public Node(int id, List<Integer> children) {
            this.id = id;
            this.children = children;
            this.parent = -1;
            this.matching = -1;
        }

        public Node(int id, List<Integer> children, int parent, int matching) {
            this.id = id;
            this.children = children;
            this.parent = parent;
            this.matching = matching;
        }

        public void addChild(int child) {
            this.children.add(child);
        }

        public int getMatching() {
            return matching;
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

    /**
     * Finds maximum cardinality matching of general graph, using Blossom algorithm.
     * https://en.wikipedia.org/wiki/Blossom_algorithm
     * (NOT bipartite graph, there is a better algorithm)
     * @param g Graph G with matching M
     */
    public static void findMaximumMatching(Graph g) {
        while (true) {
            List<Integer> path = findAugmentingPath(g);
            if (path.isEmpty()) {
                // no more augmenting paths, g has maximum matching
                return;
            }
            // augment matching via path
            // System.out.println("path: " + path.toString());
            assert path.size() % 2 == 0;
            for (int i = 0; i < path.size() / 2; i++) {
                Node n1 = g.nodes[path.get(i * 2)];
                Node n2 = g.nodes[path.get(i * 2 + 1)];
                n1.matching = n2.id;
                n2.matching = n1.id;
            }
        }
    }

    private static class Forest extends Graph {
        private final List<Integer> roots;

        public Forest(int n) {
            super(n);
            this.roots = new ArrayList<>();
        }

        public void addRoot(int root) {
            this.roots.add(root);
        }

        public boolean hasNode(int id) {
            return this.allNodes().anyMatch(n -> n.id == id);
        }

        public Stream<Node> allNodes() {
            return this.roots.stream().flatMap(this::treeNodes);
        }
    }

    private static class Blossom {
        private final Graph g;
        private final List<Integer> nodes;
        public final int contractTo;

        public Blossom(Graph g, List<Integer> nodes) {
            if (nodes.isEmpty()) throw new Error("empty blossom nodes");
            this.g = g;
            this.nodes = nodes;
            this.contractTo = this.root();
        }

        public boolean contains(int node) {
            return this.nodes.contains(node);
        }

        private int root() {
            return this.nodes.stream()
                    .filter(n -> !contains(this.g.nodes[n].matching))
                    .findFirst()
                    .orElse(-1);
        }

        public Graph contract(Graph g) {
            Node[] nodes = new Node[g.nodes.length];
            for (int i = 0; i < g.nodes.length; i++) {
                Node n = g.nodes[i];
                if (this.contains(i)) {
                    // dont pass information of contracted nodes to contracted graph
                    nodes[i] = new Node(i);
                    continue;
                }
                nodes[i] = new Node(
                        n.id,
                        n.children.stream()
                                .map(c -> this.contains(c) ? this.contractTo : c)
                                .distinct()
                                .collect(Collectors.toList()),
                        this.contains(n.parent) ? this.contractTo : n.parent,
                        this.contains(n.parent) ? this.contractTo : n.matching
                );
            }
            // contracted node
            Node n = g.nodes[this.contractTo];
            nodes[this.contractTo] = new Node(
                    n.id,
                    this.nodes.stream().flatMap(c -> g.nodes[c].children.stream()) // add up blossom's edges
                            .filter(c -> !this.contains(c)) // exclude edges to blossom self
                            .distinct() // exclude duplicates
                            .collect(Collectors.toList()),
                    this.contains(n.parent) ? this.contractTo : n.parent,
                    this.contains(n.parent) ? this.contractTo : n.matching
            );
            return new Graph(nodes);
        }

        private boolean ensureAlternating(List<Integer> ids) {
            assert ids.size() > 2;
            boolean last = g.nodes[ids.get(0)].matching == ids.get(1);
            for (int i = 1; i < ids.size() - 1; i++) {
                boolean next = g.nodes[ids.get(i)].matching == ids.get(i + 1);
                if (last == next) {
                    return false;
                }
                last = next;
            }
            return true;
        }

        public List<Integer> lift(int to) {
            int innerFrom = root();
            int innerTo = nodes.stream().filter(id -> g.nodes[id].children.contains(to)).findFirst().orElse(-1);
            if (innerTo == -1) throw new Error("could not find inner to node in blossom");

            if (innerFrom == innerTo) {
                return Collections.singletonList(innerFrom);
            }
            final int iFrom = this.nodes.indexOf(innerFrom);
            final int iTo = this.nodes.indexOf(innerTo);

            // try 2 cases and choose valid path
            // case: step: 1
            List<Integer> nodeIds = new ArrayList<>();
            for (int i = iFrom; i != iTo;) {
                nodeIds.add(nodes.get(i));
                i++;
                i %= nodes.size();
            }
            nodeIds.add(innerTo);
            nodeIds.add(to);
            if (ensureAlternating(nodeIds)) {
                return nodeIds.subList(0, nodeIds.size() - 1);
            }
            // case: step: -1
            nodeIds = new ArrayList<>();
            for (int i = iFrom; i != iTo;) {
                nodeIds.add(nodes.get(i));
                i--;
                i += nodes.size();
                i %= nodes.size();
            }
            nodeIds.add(innerTo);
            nodeIds.add(to);
            assert ensureAlternating(nodeIds);
            return nodeIds.subList(0, nodeIds.size() - 1);
        }

        public List<Integer> lift(int from, int to) {
            int innerFrom = nodes.stream().filter(id -> g.nodes[id].children.contains(from)).findFirst().orElse(-1);
            int innerTo = nodes.stream().filter(id -> g.nodes[id].children.contains(to)).findFirst().orElse(-1);

            if (innerFrom == -1 || innerTo == -1) throw new Error("could not find inner from/to node in blossom");
            if (innerFrom == innerTo) {
                return Collections.singletonList(innerFrom);
            }
            final int iFrom = this.nodes.indexOf(innerFrom);
            final int iTo = this.nodes.indexOf(innerTo);

            // try 2 cases and choose valid path
            // case: step: 1
            List<Integer> nodeIds = new ArrayList<>();
            nodeIds.add(from);
            for (int i = iFrom; i != iTo;) {
                nodeIds.add(nodes.get(i));
                i++;
                i %= nodes.size();
            }
            nodeIds.add(innerTo);
            nodeIds.add(to);
            if (ensureAlternating(nodeIds)) {
                return nodeIds.subList(1, nodeIds.size() - 1);
            }
            // case: step: -1
            nodeIds = new ArrayList<>();
            nodeIds.add(from);
            for (int i = iFrom; i != iTo;) {
                nodeIds.add(nodes.get(i));
                i--;
                i += nodes.size();
                i %= nodes.size();
            }
            nodeIds.add(innerTo);
            nodeIds.add(to);
            assert ensureAlternating(nodeIds);
            return nodeIds.subList(1, nodeIds.size() - 1);
        }
    }

    /**
     * Finds augmenting path in G and matching M of G, if any.
     * https://en.wikipedia.org/wiki/Blossom_algorithm
     * @param g Graph G with matching M
     * @return Augmenting path P in G, or empty path if none found.
     */
    @Nullable
    private static List<Integer> findAugmentingPath(Graph g) {
        Forest f = new Forest(g.nodes.length);
        // unmark all vertices and edges in G, mark all edges of M
        Set<Integer> markedVertices = new HashSet<>();
        Set<Pair> markedEdges = new HashSet<>(g.allMatching());
        for (int i = 0; i < g.nodes.length; i++) {
            // for each exposed vertex v, create an empty tree { v } and add to forest f.
            if (g.nodes[i].matching == -1) {
                f.addRoot(i);
            }
        }
        // while there is an unmarked vertex v in F with distance(v, root(v)) even:
        while (true) {
            Node vv = f.allNodes()
                    .filter(n -> !markedVertices.contains(n.id))
                    .filter(n -> f.distanceToTreeRoot(n.id) % 2 == 0)
                    .findFirst().orElse(null);
            // else, return empty path
            if (vv == null) {
                return new ArrayList<>();
            }

            // while there exists an unmarked edge e = { v, w } do
            Node v = g.nodes[vv.id];
            for (int wi : v.children) {
                Pair edge = new Pair(vv.id, wi);
                if (markedEdges.contains(edge)) continue;
                Node w = g.nodes[wi];

                if (!f.hasNode(w.id)) {
                    // w is not in F,
                    // x = vertex matched to w
                    int xi = w.matching;
                    // add edges { v, w } and { w, x } to the tree of v
                    f.addDirectedEdge(v.id, wi);
                    f.addDirectedEdge(wi, xi);
                } else {
                    // if distance(w, root(w)) is even
                    if (f.distanceToTreeRoot(wi) % 2 == 0) {
                        if (f.root(v.id) != f.root(wi)) {
                            // return path (root(v) → ... → v) → (w → ... → root(w))
                            List<Integer> vPath = f.nodesToRoot(v.id).collect(Collectors.toList());
                            Collections.reverse(vPath);
                            List<Integer> path = new ArrayList<>(vPath);
                            path.addAll(f.nodesToRoot(wi).collect(Collectors.toList()));
                            return path;
                        } else {
                            // Contract a blossom in G and look for the path in the contracted graph
                            List<Integer> vPath = f.nodesToRoot(v.id).collect(Collectors.toList());
                            List<Integer> wPath = f.nodesToRoot(wi).collect(Collectors.toList());
                            for (int i = 0; i < wPath.size(); i++) {
                                int lcaOfVPath = vPath.indexOf(wPath.get(i));
                                if (lcaOfVPath != -1) {
                                    vPath = vPath.subList(0, lcaOfVPath + 1);
                                    wPath = wPath.subList(0, i);
                                    break;
                                }
                            }
                            // blossom formed by e and edges on the path v → w in T
                            List<Integer> blossomNodes = new ArrayList<>(vPath);
                            Collections.reverse(wPath);
                            blossomNodes.addAll(wPath);
                            // System.out.println("blossom: " + blossomNodes);
                            assert blossomNodes.size() > 1 && blossomNodes.size() % 2 == 1;
                            Blossom blossom = new Blossom(g, blossomNodes);
                            Graph contractedGraph = blossom.contract(g);
                            List<Integer> path = findAugmentingPath(contractedGraph);
                            if (path.contains(blossom.contractTo)) {
                                // lift P’ to G
                                int indexBlossom = path.indexOf(blossom.contractTo);
                                if (indexBlossom > 0 && indexBlossom < path.size() - 1) {
                                    // if the path traverses through blossom:
                                    int from = path.get(indexBlossom - 1);
                                    int to = path.get(indexBlossom + 1);
                                    List<Integer> partialLiftedPath = blossom.lift(from, to);
                                    List<Integer> liftedPath = new ArrayList<>(path.subList(0, indexBlossom));
                                    liftedPath.addAll(partialLiftedPath);
                                    liftedPath.addAll(path.subList(indexBlossom + 1, path.size()));
                                    return liftedPath;
                                } else {
                                    // if the blossom is an endpoint (either starting point or ending point)
                                    int to = indexBlossom == 0 ? path.get(1) : path.get(path.size() - 2);
                                    List<Integer> partialLiftedPath = blossom.lift(to);
                                    List<Integer> liftedPath = new ArrayList<>();
                                    if (indexBlossom == 0) {
                                        liftedPath.addAll(partialLiftedPath);
                                        liftedPath.addAll(path.subList(1, path.size()));
                                    } else {
                                        liftedPath.addAll(path.subList(0, path.size() - 1));
                                        Collections.reverse(partialLiftedPath);
                                        liftedPath.addAll(partialLiftedPath);
                                    }
                                    return liftedPath;
                                }
                            } else {
                                return path;
                            }
                        }
                    }
                }
                // mark edge e
                markedEdges.add(edge);
                markedEdges.add(edge.reverse());
            }

            // mark vertex v
            markedVertices.add(v.id);
        }
    }
}
