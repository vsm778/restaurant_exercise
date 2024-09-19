import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeMap;

public interface SeatingStrategy {
    boolean seatGroup(ClientsGroup group, TreeMap<Integer, Queue<Table>> emptyTables,
                      List<TableOccupancy> partiallyFilledTables,
                      Map<ClientsGroup, TableOccupancy> seatedGroups);
}