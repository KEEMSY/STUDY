import java.util.ArrayList;

public class User {
    public static void main(String[] args) {
        ArrayList<Animal> animals = new ArrayList<Animal>();
        animals.add(new Dog("댕이"));
        animals.add(new Cat("냐옹이"));
        animals.add(new Cat("집냐옹이"));
        animals.add(new TigerAdapter("타이온"));

        animals.forEach(animal -> {
            animal.sound();
        });
    }
}