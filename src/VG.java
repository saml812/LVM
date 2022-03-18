public class VG extends COMMON{
    private double total;
    private double avaliable;
    private String name;

    public VG(String name, double total, double avaliable){
        super();
        this.total = total;
        this.avaliable = avaliable;
        this.name = name;
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
