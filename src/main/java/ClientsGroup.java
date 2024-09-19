import java.util.Objects;
import java.util.UUID;

public class ClientsGroup {
    public final int size;
    private final UUID id;

    public ClientsGroup(UUID id) {
        this.id = id;
        this.size = 0;
    }

    public ClientsGroup(int size, UUID id) {
        this.size = size;
        this.id = id;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientsGroup that = (ClientsGroup) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public String toString() {
        return "ClientsGroup{" +
                "size=" + size +
                ", id=" + id +
                '}';
    }
}