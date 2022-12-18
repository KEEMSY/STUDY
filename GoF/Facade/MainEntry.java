//  Facade 패턴을 적용하기 전 모습
// 데이터를 조회하고 출력하기까지, 여러개의 객체가 사용되고 있다.
//  DBMS를 통해 조회된 데이터는 나중을 위해서 Cache에 저장하는 것을 잊지 않아야하며, 데이터를 가공하기 위해서는 Message 클래스를 사용해야한다는 것을 알아두어야한다.
// Facade 패턴은 클래스를 하나 추가하여 이를 단순화한다.


// public class MainEntry {
//     public static void main(String[] args) {
//         DBMS dbms = new DBMS();
//         Cache cache = new Cache();

//         // 조회 할 값
//         String name = "Dorosh";

//         Row row = cache.get(name);
//         if(row == null) {
//             row = dbms.query(name);
//             if(row != null) {
//                 cache.put(row);
//             }
//         }

//         if(row != null) {
//             Message message = new Message(row);

//             System.out.println(message.makeName());
//             System.out.println(message.makeBirthday());
//             System.out.println(message.makeEmail());

//         } else {
//             System.out.println(name + " is not exists.");
//         }
//     }
// }


public class MainEntry {
    public static void main(String[] args) {
        // DBMS dbms = new DBMS();
        // Cache cache = new Cache();
        Facade facade = new Facade();

        // 조회 할 값
        String name = "Dorosh";

        facade.run();
    }
}