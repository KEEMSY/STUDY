public interface Iterator{
    // next(): Iterator 인터페이스를 통해 Aggreator의 다음 구성 데이터를 얻을 수 있도록 하고, 얻을 수 있다면 True를 반환
    boolean next();

    // current(): 구성데이터를 하나 얻어 받환, 구성데이터에 대한 타입은 정해지지 않아야하므로 Object type
    Object current();
}

