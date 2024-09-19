import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RestManagerTest {

    private RestManager restManager;

    @BeforeEach
    public void setUp() {
        initRestManager(createDefaultTableList());
    }

    @Test
    public void should_take_table2() {

        UUID uuidGroup = UUID.randomUUID();
        restManager.onArrive(new ClientsGroup(2, uuidGroup));
        Table table = restManager.lookup(new ClientsGroup(uuidGroup));

        assertEquals(2, table.size);
    }

    @Test
    public void should_occupy_all_tables_with_one_group() {

        UUID uuidGroup21 = UUID.randomUUID();
        UUID uuidGroup31 = UUID.randomUUID();
        UUID uuidGroup41 = UUID.randomUUID();
        UUID uuidGroup51 = UUID.randomUUID();
        UUID uuidGroup61 = UUID.randomUUID();

        List<UUID> uuids = List.of(uuidGroup21, uuidGroup31, uuidGroup41, uuidGroup51, uuidGroup61);

        restManager.onArrive(new ClientsGroup(2, uuidGroup21));
        restManager.onArrive(new ClientsGroup(3, uuidGroup31));
        restManager.onArrive(new ClientsGroup(4, uuidGroup41));
        restManager.onArrive(new ClientsGroup(5, uuidGroup51));
        restManager.onArrive(new ClientsGroup(6, uuidGroup61));

        int i = 2;
        for (UUID uuid : uuids) {
            Table table = restManager.lookup(new ClientsGroup(uuid));
            assertEquals(i, table.size);
            i++;
        }
    }

    @Test
    public void should_handle_removal_from_table_fill_from_queue() {

        UUID uuidGroup21 = UUID.randomUUID();
        UUID uuidGroup22 = UUID.randomUUID();
        UUID uuidGroup61 = UUID.randomUUID();
        UUID uuidGroup62 = UUID.randomUUID();

        restManager.onArrive(new ClientsGroup(2, uuidGroup21));
        restManager.onArrive(new ClientsGroup(6, uuidGroup61));
        restManager.onArrive(new ClientsGroup(6, uuidGroup62));
        restManager.onArrive(new ClientsGroup(2, uuidGroup22));

        restManager.onLeave(new ClientsGroup(6, uuidGroup61));


        Table table = restManager.lookup(new ClientsGroup(uuidGroup62));
        assertNotNull(table);
        assertEquals(6, table.size);
    }

    @Test
    public void should_fill_all_tables_from_queue() {

        UUID uuidGroup21 = UUID.randomUUID();
        UUID uuidGroup31 = UUID.randomUUID();
        UUID uuidGroup41 = UUID.randomUUID();
        UUID uuidGroup51 = UUID.randomUUID();
        UUID uuidGroup61 = UUID.randomUUID();

        UUID uuidGroup22 = UUID.randomUUID();
        UUID uuidGroup32 = UUID.randomUUID();

        List<UUID> uuids = List.of(uuidGroup21, uuidGroup31, uuidGroup41, uuidGroup51, uuidGroup61);

        restManager.onArrive(new ClientsGroup(2, uuidGroup21));
        restManager.onArrive(new ClientsGroup(3, uuidGroup31));
        restManager.onArrive(new ClientsGroup(4, uuidGroup41));
        restManager.onArrive(new ClientsGroup(5, uuidGroup51));
        restManager.onArrive(new ClientsGroup(6, uuidGroup61));

        restManager.onArrive(new ClientsGroup(2, uuidGroup22));
        restManager.onArrive(new ClientsGroup(3, uuidGroup32));

        restManager.onLeave(new ClientsGroup(6, uuidGroup61));

        Table table32 = restManager.lookup(new ClientsGroup(uuidGroup32));
        Table table22 = restManager.lookup(new ClientsGroup(uuidGroup22));

        assertEquals(table22, table32);

    }

    @Test
    public void should_fill_all_tables_from_queue_3x3() {

        UUID uuidGroup21 = UUID.randomUUID();
        UUID uuidGroup31 = UUID.randomUUID();
        UUID uuidGroup41 = UUID.randomUUID();
        UUID uuidGroup51 = UUID.randomUUID();
        UUID uuidGroup61 = UUID.randomUUID();

        UUID uuidGroup32 = UUID.randomUUID();
        UUID uuidGroup33 = UUID.randomUUID();



        restManager.onArrive(new ClientsGroup(2, uuidGroup21));
        restManager.onArrive(new ClientsGroup(3, uuidGroup31));
        restManager.onArrive(new ClientsGroup(4, uuidGroup41));
        restManager.onArrive(new ClientsGroup(5, uuidGroup51));
        restManager.onArrive(new ClientsGroup(6, uuidGroup61));

        restManager.onArrive(new ClientsGroup(2, uuidGroup32));
        restManager.onArrive(new ClientsGroup(3, uuidGroup33));

        restManager.onLeave(new ClientsGroup(6, uuidGroup61));

        Table table32 = restManager.lookup(new ClientsGroup(uuidGroup32));
        Table table33 = restManager.lookup(new ClientsGroup(uuidGroup33));

        assertEquals(table32, table33);

    }


    private void initRestManager(List<Table> tableList) {
        restManager = new RestManager(tableList);
    }

    private List<Table> createDefaultTableList() {
        return List.of(new Table(2), new Table(3), new Table(4), new Table(5), new Table(6));
    }

}