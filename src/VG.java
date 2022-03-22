import java.util.ArrayList;

public class VG extends COMMON{
    private double total;
    private double avaliable;
    private String name;
    private ArrayList<PV> pvList = new ArrayList<PV>();

    public VG(String name, PV PVname){
        super();
        for (PV pv : pvList)
        {
            total+= pv.getDrive().getSize();
        }
        this.avaliable = 0;
        this.name = name;
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
