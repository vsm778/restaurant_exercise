import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

public class DefaultSeatingStrategy implements SeatingStrategy {
    @Override
    public boolean seatGroup(ClientsGroup group, TreeMap<Integer, Queue<Table>> emptyTables,
                             List<TableOccupancy> partiallyFilledTables,
                             Map<ClientsGroup, TableOccupancy> seatedGroups) {

        Map.Entry<Integer, Queue<Table>> entry = emptyTables.ceilingEntry(group.size);
        if (entry != null && !entry.getValue().isEmpty()) {
            Table table = entry.getValue().poll();
            if (entry.getValue().isEmpty()) {
                emptyTables.remove(entry.getKey());
            }

            TableOccupancy occupancy = new TableOccupancy(table);
            occupancy.seatGroup(group);
            seatedGroups.put(group, occupancy);
            partiallyFilledTables.add(occupancy);
            return true;
        }

        for (TableOccupancy occupancy : partiallyFilledTables) {
            if (occupancy.canAccommodateGroup(group)) {
                occupancy.seatGroup(group);
                seatedGroups.put(group, occupancy);
                return true;
            }
        }

        return false;
    }
}
