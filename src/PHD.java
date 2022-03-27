public class PHD extends COMMON{
    private String name;
    private int size;
    private String type;

    public PHD(String name, int size, String type){
        this.name = name;
        this.size = size;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public String getType() { return type; }
}
