import java.util.UUID;
public class COMMON {

    private UUID id;
    private boolean linked;

    public COMMON(){
        id = UUID.randomUUID();
        linked = false;
    }

    public UUID getId(){
        return id;
    }

    public boolean isLinked() {
        return linked;
    }

    public void setLinked(boolean linked) {
        this.linked = linked;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
