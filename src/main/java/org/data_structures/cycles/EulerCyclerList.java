package org.data_structures.cycles;

import lombok.Getter;
import org.data_structures.data.MatrixGenerator;
import org.data_structures.utility.Structure;
import org.data_structures.utility.annotation.Duration;
import org.data_structures.utility.annotation.Exam;
import org.data_structures.utility.annotation.Examined;
import org.data_structures.utility.annotation.Factor;
import org.data_structures.utility.annotation.Measurement;
import org.data_structures.utility.annotation.Scale;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

@Examined
public class EulerCyclerList extends Structure {
    @Getter
    private final List<Integer> path = new ArrayList<>();
    private final List<List<Integer>> adjacencyList;
    private final int numOfVertices;

    private final Long fillingFactor;

    @Factor("filling")
    public Long getFilling() {
        return fillingFactor;
    }

    @Scale("size")
    public long getSize() {
        return numOfVertices;
    }

    public EulerCyclerList(List<List<Integer>> edges) {
        Map<Integer, List<Integer>> adjacences = new HashMap<>();
        for (int i = 1; i < edges.size(); i++) {
            List<Integer> edge = edges.get(i);
            adjacences.putIfAbsent(edge.get(0) - 1, new ArrayList<>());
            adjacences.putIfAbsent(edge.get(1) - 1, new ArrayList<>());
            adjacences.get(edge.get(0) - 1).add(edge.get(1) - 1);
            adjacences.get(edge.get(1) - 1).add(edge.get(0) - 1);
        }

        this.adjacencyList = adjacences.values().stream().toList();
        this.numOfVertices = this.adjacencyList.size() + 1;
        this.fillingFactor = numOfVertices / edges.getFirst().getLast() * 100L;
    }

    public int getIn(int k) {
        int c = 0;

        for (int i = 0; i < adjacencyList.size(); i++) {
            if(i==k)
                continue;

            for (int suc: adjacencyList.get(i))
                if (suc==k)
                    c++;

        }

        return c;
    }

    public int getOut(int k) {
        return adjacencyList.get(k).size();
    }

    @Exam
    public List<Integer> traceCycle() {
        traceCycleTime = System.nanoTime();

        for (int i = 0; i < adjacencyList.size(); i++) {
            if(getIn(i) != getOut(i)) {
                traceCycleTime = -1L;
                return Collections.emptyList();
            }
        }

        var successorsCopy = adjacencyList.stream().map(ArrayList::new).toList();

        var edgeCounter = new HashMap<Integer,Integer>();
        for (int i = 0; i <successorsCopy.size(); i++)
            edgeCounter.put(i,successorsCopy.get(i).size());


        var tmpPath = new Stack<Integer>();
        tmpPath.push(0);
        int curr = -1;
        for (int i = 0; i < adjacencyList.size(); i++)
            if(getIn(i) != 0){
                curr = i;
                break;
            }

        while (!tmpPath.isEmpty()) {
            if (edgeCounter.get(curr) != 0) {
                tmpPath.push(curr);
                int nextV = successorsCopy.get(curr).getLast();
                edgeCounter.put(curr,edgeCounter.get(curr) - 1);
                successorsCopy.get(curr).removeLast();
                curr = nextV;
            } else {
                path.add(curr);
                curr = tmpPath.pop();
            }
        }

        for (int i = 0; i < edgeCounter.size(); i++)
            if (edgeCounter.get(i) > 0) {
                traceCycleTime = -1L;
                return Collections.emptyList();
            }

        traceCycleTime = System.nanoTime() - traceCycleTime;
        return path;
    }
    private Long traceCycleTime = 0L;

    @Duration("trace_cycle_time")
    public Long getTraceCycleTime() {
        return traceCycleTime;
    }

    public static Structure of(List<Integer> input) {
        return new EulerCyclerList(MatrixGenerator.map(input));
    }
}

