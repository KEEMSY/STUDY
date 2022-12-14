// 장식될 실제 내용물 
public class Strings extends Item {
    private ArrayList<String> strings = new ArrayList<String>();

    public void add(String item) {
        string.add(item);
    }

    // 문자열이 몇줄인지 반환
    public abstract int getLinesCount() {
        return strings.size();
    }

    // 문자열 중 가장 긴 문자열을 반환한다.
    public abstract int getMaxLength() {
        Iterator<String> iter = strings.iterator();
        int maxWidth = 0;

        While(iter.hasNext()) {
            String string = iter.next();
            int width = string.length();

            if(width > maxWidth) maxWidth = width;
        }

        return maxWidth;
    }
    
    // 지정된 인덱스의 문자열의 길이를 반환한다.
    public abstract int getLength(){
        String string = strings.get(index);
        return string.length();

    }
    
    // 지정된 인덱스의 문자열을 반환한다.
    public abstract String getString(int index){
        String string = strings.get(index);
        return string;
    }
}
