public class abstract Handler {
    protected Handler nextHandler = null;

    public Handler setNext(Handler handler)
    {
        this.nextHandler = handler;
        return handler;
    }
    // 구체적인 책임에 대한 구현 코드
    protected abstract void process(String url); 

    public void run(Sring url) {
        process(url);
        if(nextHandler != null) nextHandler.run(url);
    }
}