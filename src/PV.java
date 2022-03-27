public class PV extends COMMON {

    private String name;
    private PHD drive;
    private boolean linkedtoVG;

    public PV(String name, PHD drive){
        super();
        this.drive = drive;
        this.name = name;
        linkedtoVG = false;
    }

    public String getName() {
        return name;
    }

    public PHD getDrive() { return drive; }

    public boolean isLinkedtoVG() {
        return linkedtoVG;
    }

    public void setLinkedtoVG(boolean linkedtoVG) {
        this.linkedtoVG = linkedtoVG;
    }
}
