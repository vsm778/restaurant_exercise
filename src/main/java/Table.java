import java.util.Objects;
import java.util.UUID;

public class Table {
    public final int size;
    private final UUID id;

    public Table(int size) {
        this.size = size;
        this.id = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Table table = (Table) o;
        return Objects.equals(id, table.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Table{" +
                "size=" + size +
                ", id=" + id +
                '}';
    }
}