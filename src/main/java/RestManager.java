import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeMap;

public class RestManager {
    private final TreeMap<Integer, Queue<Table>> emptyTables;
    private final List<TableOccupancy> partiallyFilledTables;
    private final Map<ClientsGroup, TableOccupancy> seatedGroups;
    private final PriorityQueue<ClientsGroup> waitingQueue;
    private final SeatingStrategy seatingStrategy;

    public RestManager(List<Table> tables) {
        this.emptyTables = new TreeMap<>();
        this.partiallyFilledTables = new ArrayList<>();
        this.seatedGroups = new HashMap<>();
        this.waitingQueue = new PriorityQueue<>(Comparator.comparingInt(g -> g.size));
        this.seatingStrategy = new DefaultSeatingStrategy();

        for (Table table : tables) {
            emptyTables.computeIfAbsent(table.size, k -> new LinkedList<>()).add(table);
        }
    }

    public void onArrive(ClientsGroup group) {
        if (!seatingStrategy.seatGroup(group, emptyTables, partiallyFilledTables, seatedGroups)) {
            waitingQueue.offer(group);
        }
    }

    public void onLeave(ClientsGroup group) {
        TableOccupancy occupancy = seatedGroups.remove(group);
        if (occupancy != null) {
            occupancy.removeGroup(group); // Remove group from table

            if (occupancy.isEmpty()) {
                partiallyFilledTables.remove(occupancy);
                emptyTables.computeIfAbsent(occupancy.getTable().size, k -> new LinkedList<>()).add(occupancy.getTable());
            }

            seatWaitingGroups();
        } else {
            waitingQueue.remove(group);
        }
    }

    public Table lookup(ClientsGroup group) {
        TableOccupancy occupancy = seatedGroups.get(group);
        return occupancy != null ? occupancy.getTable() : null;
    }

    private void seatWaitingGroups() {
        while (!waitingQueue.isEmpty()) {
            ClientsGroup nextGroup = waitingQueue.peek();
            if (seatingStrategy.seatGroup(nextGroup, emptyTables, partiallyFilledTables, seatedGroups)) {
                waitingQueue.poll();
            } else {
                break;
            }
        }
    }
}
