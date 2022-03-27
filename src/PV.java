public class PV extends COMMON {

    private String name;
    private PHD drive;

    public PV(String name, PHD drive){
        super();
        this.drive = drive;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public PHD getDrive() { return drive; }

}
