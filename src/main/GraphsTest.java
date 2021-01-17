package main;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

class GraphsTest {
    @Test
    void testTopologicalSort1() {
        Graphs.Node[] nodes = new Graphs.Node[]{
                new Graphs.Node(0, Arrays.asList(3)),
                new Graphs.Node(1, Arrays.asList(3, 4)),
                new Graphs.Node(2, Arrays.asList(4, 7)),
                new Graphs.Node(3, Arrays.asList(5, 6, 7)),
                new Graphs.Node(4, Arrays.asList(6)),
                new Graphs.Node(5, Arrays.asList()),
                new Graphs.Node(6, Arrays.asList()),
                new Graphs.Node(7, Arrays.asList()),
        };

        List<Integer> res = Graphs.topologicalSort(nodes);
        System.out.println(res);
        assert res != null;
    }

    @Test
    void testTopologicalSort2() {
        Graphs.Node[] nodes = new Graphs.Node[]{
                new Graphs.Node(0, Arrays.asList(3)),
                new Graphs.Node(1, Arrays.asList(3, 4)),
                new Graphs.Node(2, Arrays.asList(4, 7)),
                new Graphs.Node(3, Arrays.asList(5, 6, 7)),
                new Graphs.Node(4, Arrays.asList(6)),
                new Graphs.Node(5, Arrays.asList()),
                new Graphs.Node(6, Arrays.asList()),
                new Graphs.Node(7, Arrays.asList(0)),
        };

        List<Integer> res = Graphs.topologicalSort(nodes);
        System.out.println(res);
        assert res == null;
    }

    @Test
    void testMaximumMatching() {
        class TestCase {
            private final String name;
            private final Graphs.Graph g;
            private final List<Integer> matching;

            private TestCase(String name, Graphs.Graph g, List<Integer> matching) {
                this.name = name;
                this.g = g;
                this.matching = matching;
            }
        }

        List<TestCase> testCases = new ArrayList<>();

        {
            Graphs.Graph g = new Graphs.Graph(2);
            List<Integer> matching = Arrays.asList(-1, -1);
            testCases.add(new TestCase("empty", g, matching));
        }

        {
            Graphs.Graph g = new Graphs.Graph(2);
            g.addUndirectedEdge(1, 0);
            List<Integer> matching = Arrays.asList(1, 0);
            testCases.add(new TestCase("one pair", g, matching));
        }

        {
            Graphs.Graph g = new Graphs.Graph(10);
            g.addUndirectedEdge(0, 1);
            g.addUndirectedEdge(2, 3);
            g.addUndirectedEdge(4, 5);
            g.addUndirectedEdge(5, 6);
            g.addUndirectedEdge(6, 7);
            g.addUndirectedEdge(7, 8);
            g.addUndirectedEdge(8, 9);
            List<Integer> matching = Arrays.asList(1, 0, 3, 2, 5, 4, 7, 6, 9, 8);
            testCases.add(new TestCase("simple", g, matching));
        }

        {
            Graphs.Graph g = new Graphs.Graph(8);
            g.addUndirectedEdge(0, 1);
            g.addUndirectedEdge(1, 2);
            g.addUndirectedEdge(2, 3);
            g.addUndirectedEdge(3, 4);
            g.addUndirectedEdge(4, 5);
            g.addUndirectedEdge(5, 6);
            g.addUndirectedEdge(6, 7);
            List<Integer> matching = Arrays.asList(1, 0, 3, 2, 5, 4, 7, 6);
            testCases.add(new TestCase("contains loop", g, matching));
        }

        {
            Graphs.Graph g = new Graphs.Graph(6);
            g.addUndirectedEdge(0, 3);
            g.addUndirectedEdge(0, 4);
            g.addUndirectedEdge(0, 5);
            g.addUndirectedEdge(1, 2);
            g.addUndirectedEdge(1, 4);
            g.addUndirectedEdge(1, 5);
            g.addUndirectedEdge(2, 5);
            g.addUndirectedEdge(3, 4);
            g.addUndirectedEdge(3, 5);
            List<Integer> matching = Arrays.asList(5, 2, 1, 4, 3, 0);
            testCases.add(new TestCase("dense", g, matching));
        }

        {
            Graphs.Graph g = new Graphs.Graph(8);
            g.addUndirectedEdge(0, 1);
            g.addUndirectedEdge(1, 2);
            g.addUndirectedEdge(2, 3);
            g.addUndirectedEdge(3, 4);
            g.addUndirectedEdge(4, 5);
            g.addUndirectedEdge(5, 6);
            g.addUndirectedEdge(6, 2);
            g.addUndirectedEdge(4, 7);
            List<Integer> matching = Arrays.asList(1, 0, 3, 2, 7, 6, 5, 4);
            testCases.add(new TestCase("through blossom 1", g, matching));
        }

        {
            Graphs.Graph g = new Graphs.Graph(8);
            g.addUndirectedEdge(0, 1);
            g.addUndirectedEdge(1, 2);
            g.addUndirectedEdge(2, 3);
            g.addUndirectedEdge(3, 4);
            g.addUndirectedEdge(4, 5);
            g.addUndirectedEdge(5, 6);
            g.addUndirectedEdge(6, 2);
            g.addUndirectedEdge(5, 7);
            List<Integer> matching = Arrays.asList(1, 0, 6, 4, 3, 7, 2, 5);
            testCases.add(new TestCase("through blossom 2", g, matching));
        }

        {
            Graphs.Graph g = new Graphs.Graph(6);
            g.addUndirectedEdge(0, 1);
            g.addUndirectedEdge(1, 2);
            g.addUndirectedEdge(2, 3);
            g.addUndirectedEdge(3, 4);
            g.addUndirectedEdge(4, 0);
            g.addUndirectedEdge(2, 5);
            List<Integer> matching = Arrays.asList(1, 0, 5, 4, 3, 2);
            testCases.add(new TestCase("starting blossom 1", g, matching));
        }

        {
            Graphs.Graph g = new Graphs.Graph(6);
            g.addUndirectedEdge(0, 1);
            g.addUndirectedEdge(1, 2);
            g.addUndirectedEdge(2, 3);
            g.addUndirectedEdge(3, 4);
            g.addUndirectedEdge(4, 0);
            g.addUndirectedEdge(3, 5);
            List<Integer> matching = Arrays.asList(4, 2, 1, 5, 0, 3);
            testCases.add(new TestCase("starting blossom 2", g, matching));
        }

        {
            Graphs.Graph g = new Graphs.Graph(6);
            g.addUndirectedEdge(5, 4);
            g.addUndirectedEdge(4, 3);
            g.addUndirectedEdge(3, 2);
            g.addUndirectedEdge(2, 1);
            g.addUndirectedEdge(1, 5);
            g.addUndirectedEdge(3, 0);
            List<Integer> matching = Arrays.asList(3, 2, 1, 0, 5, 4);
            testCases.add(new TestCase("ending blossom 1", g, matching));
        }

        {
            Graphs.Graph g = new Graphs.Graph(6);
            g.addUndirectedEdge(5, 4);
            g.addUndirectedEdge(4, 3);
            g.addUndirectedEdge(3, 2);
            g.addUndirectedEdge(2, 1);
            g.addUndirectedEdge(1, 5);
            g.addUndirectedEdge(2, 0);
            List<Integer> matching = Arrays.asList(2, 5, 0, 4, 3, 1);
            testCases.add(new TestCase("ending blossom 2", g, matching));
        }

        {
            Graphs.Graph g = new Graphs.Graph(27);
            g.addUndirectedEdge(0, 1);
            g.addUndirectedEdge(1, 2);
            g.addUndirectedEdge(0, 3);
            g.addUndirectedEdge(3, 4);
            g.addUndirectedEdge(4, 5);
            g.addUndirectedEdge(4, 7);
            g.addUndirectedEdge(4, 9);
            g.addUndirectedEdge(5, 6);
            g.addUndirectedEdge(7, 8);
            g.addUndirectedEdge(9, 10);
            g.addUndirectedEdge(11, 12);
            g.addUndirectedEdge(12, 13);
            g.addUndirectedEdge(13, 14);
            g.addUndirectedEdge(14, 15);
            g.addUndirectedEdge(15, 17);
            g.addUndirectedEdge(14, 10);
            g.addUndirectedEdge(13, 16);
            g.addUndirectedEdge(16, 17);
            g.addUndirectedEdge(13, 18);
            g.addUndirectedEdge(18, 19);
            g.addUndirectedEdge(20, 21);
            g.addUndirectedEdge(22, 23);
            g.addUndirectedEdge(24, 25);
            List<Integer> matching = Arrays.asList(
                    3, 2, 1, 0, 9, 6, 5, 8, 7, 4,
                    14, 12, 11, 16, 10, 17, 13, 15, 19, 18,
                    21, 20, 23, 22, 25, 24, -1
            );
            testCases.add(new TestCase("complicated 1", g, matching));
        }

        // optional manual test case filter
        // testCases = testCases.stream().filter(t -> t.name.equals("through blossom 1")).collect(Collectors.toList());

        for (TestCase t : testCases) {
            System.out.println("Test case: " + t.name);
            Graphs.findMaximumMatching(t.g);
            System.out.println("Got matching: " + Arrays.stream(t.g.nodes).map(Graphs.Node::getMatching).collect(Collectors.toList()));
            System.out.println("Want matching: " + t.matching);
            for (int i = 0; i < t.matching.size(); i++) {
                assert t.g.nodes[i].getMatching() == t.matching.get(i);
            }
        }
    }
}