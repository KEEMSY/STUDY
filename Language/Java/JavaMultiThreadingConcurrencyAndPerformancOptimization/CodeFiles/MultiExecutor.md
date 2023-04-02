# **MultiExecutor 솔루션**

*멀티코어 CPU를 최대한 활용하기 위해, 우리는 각 작업을 서로 다른 스레드로 전달해서 MultiExecutor가 모든 작업을 동시에 진행하게 한다..*

```java
import java.util.ArrayList;
import java.util.List;

public class MultiExecutor {

    private final List<Runnable> tasks;

    /*
     * @param tasks to executed concurrently
     */
    public MultiExecutor(List<Runnable> tasks) {
        this.tasks = tasks;
    }

    /**
     * Executes all the tasks concurrently
     */
    public void executeAll() {
        List<Thread> threads = new ArrayList<>(tasks.size());

        for (Runnable task : tasks) {
            Thread thread = new Thread(task);
            threads.add(thread);
        }

        for (Thread thread : threads) {
            thread.start();
        }
    }
}
```

- `MultiExecutor` 클래스의 클라이언트는 `Runnable` 작업의 목록을 생성해서 해당 목록을 `MultiExecutor` 의 생성자에게 제공한다.
- 클라이언트가 `executeAll()`을 실행하면, `MultiExecutor` 가 주어진 모든 작업을 실행하게 된다..