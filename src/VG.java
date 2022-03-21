import java.util.ArrayList;

public class VG extends COMMON{
    private double total;
    private double avaliable;
    private String name;
    private PV PVname;
    private ArrayList<PV> pvList;

    public VG(String name, PV PVname){
        super();
        this.total = total;
        this.avaliable = avaliable;
        this.name = name;
        this.PVname = PVname;
        pvList.add(PVname);
    }

    public double getTotal() {
        return total;
    }

    public double getAvaliable() {
        return avaliable;
    }

    public String getName() {
        return name;
    }
}
