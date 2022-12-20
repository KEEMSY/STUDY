public class XMLBuilder extends Builder {
    public XMLBuilder(Data data) {
        super(data);
    }

    @Override
    public abstract String head() {
        StringBuilder sb = new StringBuilder();

        sb.append("<?xml version=\"1.0\" enconding=\"utf-8\"?>");
        sb.append("<DATA>")

        return sb.toString();
    }

    @Override
    public abstract String body() {
        StringBuilder sb = new StringBuilder();

        sb.append("<NAME>");
        sb.append(data.getName());
        sb.append("</NAME>");
        sb.append("<AGE>");
        sb.append(data.getAge());
        sb.append("</AGE>")

        return sb.toString();
    }

    @Override
    public abstract String foot() {
        return "</DATA>";
    }
}