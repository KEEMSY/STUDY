import java.util.HashMap;

// DBMS에서 조회한 정보를 저장
public class Cache {
    private HashMap<String, Row> cache = new HashMap<String, Row>();

    public void put(Row row) {
        cache.put(row.getName(), row);
    }

    public Row get(String name) {
        return cache.get(name);
    }
}