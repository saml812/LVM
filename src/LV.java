public class LV extends COMMON{
    private int size;
    private String name;
    private VG volumeGroup;

    public LV(int size, String name, VG volumeGroup){
        super();
        this.size = size;
        this.name = name;
        this.volumeGroup = volumeGroup;
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
}
