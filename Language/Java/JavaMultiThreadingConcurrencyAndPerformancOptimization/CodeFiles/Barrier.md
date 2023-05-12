```java
public static void main(String [] args){
    int numberOfThreads = 200; //or any number you'd like 
 
    List<Thread> threads = new ArrayList<>();
 
    Barrier barrier = new Barrier(numberOfThreads);
    for (int i = 0; i < numberOfThreads; i++) {
        threads.add(new Thread(new CoordinatedWorkRunner(barrier)));
    }
 
    for(Thread thread: threads) {
        thread.start();
    }
}

public static class Barrier {
    private final int numberOfWorkers;
    private Semaphore semaphore = new Semaphore( //** blank 1 **/);
    private int counter = //** blank 2 **/;
    private Lock lock = new ReentrantLock();
 
    public Barrier(int numberOfWorkers) {
        this.numberOfWorkers = numberOfWorkers;
    }
 
    public void barrier() {
        lock.lock();
        boolean isLastWorker = false;
        try {
            counter++;
 
            if (counter == numberOfWorkers) {
                isLastWorker = true;
            }
        } finally {
            lock.unlock();
        }
 
        if (isLastWorker) {
            semaphore.release(//** blank 3 **/);
        } else {
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
            }
        }
    }
}
 
public static class CoordinatedWorkRunner implements Runnable {
    private Barrier barrier;
 
    public CoordinatedWorkRunner(Barrier barrier) {
        this.barrier = barrier;
    }
 
    @Override
    public void run() {
        try {
            task();
        } catch (InterruptedException e) {
        }
    }
 
    private void task() throws InterruptedException {
        // Performing Part 1
        System.out.println(Thread.currentThread().getName() 
                + " part 1 of the work is finished");
 
        barrier.barrier();
 
        // Performing Part2
        System.out.println(Thread.currentThread().getName() 
                + " part 2 of the work is finished");
    }
}

/* 
[ 요구사항 ]

 해당 작업을 동시에 실행하는 3개의 스레드가 있는 경우, 결괏값이 다음과 같아지도록 하려고 한다.

 thread1 part 1 of the work is finished
 thread2 part 1 of the work is finished
 thread3 part 1 of the work is finished
 
 thread2 part 2 of the work is finished
 thread1 part 2 of the work is finished
 thread3 part 2 of the work is finished
 각 파트의 실행 순서는 중요하지 않다. 단,  다른 스레드가 계속해서 part2를 수행하기 전에, 모든 스레드가 반드시 part1을 완료하도록 만들어야 한다.

*/
```

`Semaphore` 를 **0 으로 초기화**하여, **`Semaphore` 를 획득하려는 모든 스레드가 Block** 될 수 있도록 한다.
그리고 **numberOfWorkers - 1** Thread 는 `Semaphore` 에서 Block 된 상태이므로, `bariier`에 도달한 마지막 스레드가 **numberOfWorkers - 1** `Semaphore` 에 릴리스 한다.

- `blank 1`: 0
- `blank 2`: 0
- `blank 3`: numberOfWorkers - 1