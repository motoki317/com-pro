package main;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class GraphsTest {
    @Test
    void testTopologicalSort1() {
        Graphs.Node[] nodes = new Graphs.Node[]{
                new Graphs.Node(-1, Arrays.asList(3)),
                new Graphs.Node(-1, Arrays.asList(3, 4)),
                new Graphs.Node(-1, Arrays.asList(4, 7)),
                new Graphs.Node(-1, Arrays.asList(5, 6, 7)),
                new Graphs.Node(-1, Arrays.asList(6)),
                new Graphs.Node(-1, Arrays.asList()),
                new Graphs.Node(-1, Arrays.asList()),
                new Graphs.Node(-1, Arrays.asList()),
        };

        List<Integer> res = Graphs.topologicalSort(nodes);
        System.out.println(res);
        assert res != null;
    }

    @Test
    void testTopologicalSort2() {
        Graphs.Node[] nodes = new Graphs.Node[]{
                new Graphs.Node(-1, Arrays.asList(3)),
                new Graphs.Node(-1, Arrays.asList(3, 4)),
                new Graphs.Node(-1, Arrays.asList(4, 7)),
                new Graphs.Node(-1, Arrays.asList(5, 6, 7)),
                new Graphs.Node(-1, Arrays.asList(6)),
                new Graphs.Node(-1, Arrays.asList()),
                new Graphs.Node(-1, Arrays.asList()),
                new Graphs.Node(-1, Arrays.asList(0)),
        };

        List<Integer> res = Graphs.topologicalSort(nodes);
        System.out.println(res);
        assert res == null;
    }
}