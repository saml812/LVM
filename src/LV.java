public class LV extends COMMON{
    private int size;
    private String name;
    private VG volumeGroup;
    private String type;

    public LV(int size, String name, VG volumeGroup, String type){
        super();
        this.size = size;
        this.name = name;
        this.volumeGroup = volumeGroup;
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

    public VG getVolumeGroup() {
        return volumeGroup;
    }

    public String getType() {
        return type;
    }

}
