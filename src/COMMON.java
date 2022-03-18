import java.util.UUID;
public class COMMON {

    private UUID id;

    public COMMON(){
        id = UUID.randomUUID();
    }

    public UUID getId(){
        return id;
    }
}
