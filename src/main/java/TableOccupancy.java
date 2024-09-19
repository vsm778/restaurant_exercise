import java.util.HashSet;
import java.util.Set;

public class TableOccupancy {
    private final Table table;
    private final Set<ClientsGroup> clientsGroupSet;

    public TableOccupancy(Table table) {
        this.table = table;
        this.clientsGroupSet = new HashSet<>();
    }

    public Table getTable() {
        return table;
    }

    public int getAvailableSeats() {
        return table.size - calcOccupiedSeats();
    }

    public boolean canAccommodateGroup(ClientsGroup group) {
        return getAvailableSeats() >= group.size;
    }

    public void seatGroup(ClientsGroup group) {
        this.clientsGroupSet.add(group);
    }

    public void removeGroup(ClientsGroup group) {
        this.clientsGroupSet.remove(group);
    }

    public boolean isEmpty() {
        return clientsGroupSet.isEmpty();
    }

    private int calcOccupiedSeats() {
        return clientsGroupSet.stream()
                .map(clientsGroup -> clientsGroup.size)
                .reduce(Integer::sum).orElse(0);
    }
}
