package notDeafult;

public class keyVal{
    private final int key;
    private final String value;
    public keyVal(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public String toString() {
        return value;
    }
}