import java.util.ArrayList;

public class VG extends COMMON{
    private int total;
    private int avaliable;
    private String name;
    private ArrayList<PV> pvList = new ArrayList<PV>();

    public VG(String name, PV PVname){
        super();
        pvList.add(PVname);
        this.name = name;
        for (PV pv : pvList)
        {
            total += pv.getDrive().getSize();
            avaliable += pv.getDrive().getSize();
        }
    }

    public int getTotal() {
        return total;
    }

    public int getAvaliable() {
        return avaliable;
    }

    public String getName() {
        return name;
    }

    public void setAvaliable(int avaliable) {
        this.avaliable -= avaliable;
    }

    public ArrayList<PV> getPvList() {
        return pvList;
    }

    public void updateSize(int n){
        total += n;
        avaliable += n;
    }
}
