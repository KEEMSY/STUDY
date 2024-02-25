# **Thread 생성하기 방법 2**

```java
public class Main1 {
    public static void main(String[] args) {
            Thread thread = new NewThread();

            thread.start();
        }

    private static class NewThread extends Thread {
        @Override
        public void run() {
            System.out.println("we are now in thread "+this.getName());
        }
    }
}
```

`Thread` 를 생성하는 방법에는 2가지를 이야기 할 수 있다.

- `Runnable` 인터페이스를 구현하는 클래스의 객체를 스레드 객체 생성자에 전달하여 다른 스레드에서 실행되는 코드를 스케줄링 한다.

    `run` 메서드 내의 코드는 새로운 스레드에서 실행되어 쉽고 빠르게 코딩할 수 있다.

- `Thread`를 확장하는 새 클래스를 생성한다.

    `Thread` 클래스는  Runnable 인터페이스 자체가 구현되어 있어, `Override`만 진행하면 된다.

    이를 통해 새 스레드에 실행하고 싶은 로직을 옮겨 실행한다.

    현재 스레드와 직접적으로 관련된 많은 데이터와 메서드에 액세스 할 수 있다.(방법1로는 불가능한 부분)

<br><hr><hr>

## **예시: 금고 설계-경잘과 도둑**

### **컨텍스트**

중요한 데이터가 담긴 금고가 존재하는데, 이를 해커가 훔치려고한다. 해커가 훔치기 시작하면, 경찰은 도착하는데 10초가 걸린다.

### **결정**

- 경찰스레드는 도착상황을 화면에 띄뭐준다.
- 금고 클래스는는 비밀번호를 랜덤으로 부여한다.
- 10초동안 해커가 돈을 훔쳐서 도망가지 못한다면 경찰은 해커를 체포한다.

### **결과**

```java
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static final int MAX_PASSWORD = 9999;

    public static void main(String[] args) {
        Random random = new Random();

        Vault vault = new Vault(random.nextInt(MAX_PASSWORD));

        List<Thread> threads = new ArrayList<>();

        threads.add(new AscendingHackerThread(vault));
        threads.add(new DescendingHackerThread(vault));
        threads.add(new PoliceThread());

        for (Thread thread : threads) {
            thread.start();
        }
    }

    private static class Vault {
        private int password;

        public Vault(int password) {
            this.password = password;
        }

        public boolean isCorrectPassword(int guess) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
            }
            return this.password == guess;
        }
    }

    private static abstract class HackerThread extends Thread {
        protected Vault vault;

        public HackerThread(Vault vault) {
            this.vault = vault;
            this.setName(this.getClass().getSimpleName());
            this.setPriority(Thread.MAX_PRIORITY);
        }

        @Override
        public void start() {
            System.out.println("Starting thread " + this.getName());
            super.start();
        }
    }

    private static class AscendingHackerThread extends HackerThread {

        public AscendingHackerThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for (int guess = 0; guess < MAX_PASSWORD; guess++) {
                if (vault.isCorrectPassword(guess)) {
                    System.out.println(this.getName() + " guessed the password " + guess);
                    System.exit(0);
                }
            }
        }
    }

    private static class DescendingHackerThread extends HackerThread {

        public DescendingHackerThread(Vault vault) {
            super(vault);
        }

        @Override
        public void run() {
            for (int guess = MAX_PASSWORD; guess >= 0; guess--) {
                if (vault.isCorrectPassword(guess)) {
                    System.out.println(this.getName() + " guessed the password " + guess);
                    System.exit(0);
                }
            }
        }
    }

    private static class PoliceThread extends Thread {
        @Override
        public void run() {
            for (int i = 10; i > 0; i--) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
                System.out.println(i);
            }

            System.out.println("Game over for you hackers");
            System.exit(0);
        }
    }
}
```

![relationWithVault](/img/relationWithVault.png)

- 제니릭한 `HackerThread` 를 위한 `abstract class HackerThread` 를 생성하여 `HackerThread` 를 구현한다.
  - `AscendingHackerThread`: 비밀번호를 추측하는데 모든 숫자를 오름차순으로 평가한다.
  - `DescendingHackerThread`: 비밀번호를 추측하는데 모든 숫자를 내림차순으로 평가한다.
- `PoliceThread` 는 `Thread`를 직접 확장한다.
