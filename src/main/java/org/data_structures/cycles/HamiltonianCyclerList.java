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

@Examined
public class HamiltonianCyclerList extends Structure {
    private final int numOfVertices;

    @Getter
    private List<Integer> path;
    private final List<List<Integer>> adjacencyList;

    @Scale("size")
    public Long getSize() {
        return (long) numOfVertices;
    }

    private final Long fillingFactor;

    @Factor("filling")
    public Long getFilling() {
        return fillingFactor;
    }

    private Long traceCycleTime = 0L;

    @Duration("trace_cycle_time")
    public Long getTraceCycleTime() {
        return traceCycleTime;
    }

    public HamiltonianCyclerList(List<List<Integer>> edges) {
        this.path = new ArrayList<>();
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
        this.visited = new ArrayList<>(numOfVertices);
        this.fillingFactor = numOfVertices / edges.getFirst().getLast() * 100L;
    }

    private int visitedCount;
    private int start;
    private final List<Boolean> visited;

    @Exam
    public List<Integer> traceCycle(){
        for (int i = 0;  i < numOfVertices; i++)
            visited.add(false);

        start = 0;
        visitedCount = 0;
        path.add(0);
        traceCycleTime = System.nanoTime();
        if(hamiltonian(start)) {
            traceCycleTime = System.nanoTime() - traceCycleTime;
            path = path.reversed();
            return path;
        }

        traceCycleTime = System.nanoTime() - traceCycleTime;
        return Collections.emptyList();
    }

    private boolean hamiltonian(int v){
        visited.set(v, true);
        visitedCount++;

        for (int i = 0; i < adjacencyList.get(v).size(); i++) {
            if(isDeepable(v, i)) {
                path.add(v);
                return true;
            }
        }

        visited.set(v, false);
        visitedCount--;

        return false;
    }

    private boolean isDeepable(int v, int i) {
        return adjacencyList.get(v).get(i) == start
                && visitedCount == numOfVertices
                || !visited.get(adjacencyList.get(v).get(i))
                && hamiltonian(adjacencyList.get(v).get(i));
    }


    public static Structure of(List<Integer> input) {
        return new HamiltonianCyclerList(MatrixGenerator.map(input));
    }
}
