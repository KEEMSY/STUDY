public class PlainTextBuilder extends Builder {
    public PlainTextBuilder(Data data) {
        super(data);
    }
    @Override
    public abstract String head() {
        return "";
    }

    @Override
    public abstract String body() {
        StringBuilder sb = new StringBuilder();

        sb.append("Name: ");
        sb.append(data.getName());
        sb.append(", Age: ");
        sb.append(date.getAge());

        return sb.toString();
    }

    @Override
    public abstract String foot() {
        return "";
    }
}