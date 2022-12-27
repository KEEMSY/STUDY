import java.util.HashMap;

public class DBMS {
    private HashMap<String, Row> db = new HashMap<String, Row>();
    
    public DBMS() {
        db.put("Jane", new Row("Jane", "2022-12-18", "jane@gmail.com"));
        db.put("Robert", new Row("Robert", "2022-12-19", "robert@gmail.com"));
        db.put("Dorosh", new Row("Dorosh", "2022-12-20", "dorosh@gmail.com"));
    }

    public Row query(String name) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    return db.get(name.toLowerCase());

}