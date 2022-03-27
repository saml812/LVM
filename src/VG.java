import java.util.ArrayList;

public class VG extends COMMON{
    private int total;
    private int available;
    private String name;
    private ArrayList<PV> pvList = new ArrayList<PV>();

    public VG(String name, PV PVname){
        super();
        pvList.add(PVname);
        this.name = name;
        for (PV pv : pvList)
        {
            total += pv.getDrive().getSize();
            available += pv.getDrive().getSize();
        }
    }

    public int getTotal() {
        return total;
    }

    public int getAvailable() {
        return available;
    }

    public String getName() {
        return name;
    }

    public void setAvailable(int available) {
        this.available -= available;
    }

    public ArrayList<PV> getPvList() {
        return pvList;
    }

    public void updateSize(int n){
        total += n;
        available += n;
    }
}
