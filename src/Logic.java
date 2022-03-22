import java.util.ArrayList;
import java.util.Scanner;

public class Logic {
    private PHD drive;
    ArrayList<PHD> driveList = new ArrayList<PHD>();
    private PV volume;
    ArrayList<PV> volumeList = new ArrayList<PV>();
    private VG volumeGroup;
    ArrayList<VG> groupList = new ArrayList<VG>();

    public void run(){
        System.out.println("Welcome to the LVM system! Enter your commands:");
        Scanner s = new Scanner(System.in);
        String userChoice = s.nextLine();
        while (!(userChoice.equals("exit"))){
            if (userChoice.contains("install-drive"))
            {
                userChoice = userChoice.substring(14);
                String name = userChoice.substring(0, userChoice.indexOf(" "));
                userChoice = userChoice.substring(userChoice.indexOf(" ") + 1);
                int size = Integer.parseInt(userChoice.substring(0, userChoice.length()-1));
                String type = userChoice.substring(userChoice.length()-1);

                boolean dup = false;
                for (PHD phd : driveList) {
                    if (phd.getName().equals(name)) {
                        dup = true;
                        break;
                    }
                }
                if (!dup)
                {
                    createHD(name, size, type);
                    System.out.println("Drive: " + name + " installed");
                }
                else {
                    System.out.println("Drive: " + name + " already exists");
                }
            }
            else if (userChoice.equals("list-drives"))
            {
                listDrive();
            }
            else if (userChoice.contains("pvcreate")){
                userChoice = userChoice.substring(9);
                String name = userChoice.substring(0, userChoice.indexOf(" "));
                userChoice = userChoice.substring(userChoice.indexOf(" ") + 1);
                String PHDname = userChoice.substring(0);

                boolean exist = false;
                PHD object = null;
                for (PHD phd : driveList) {
                    if (phd.getName().equals(PHDname)) {
                        exist = true;
                        object = phd;
                    }
                }
                boolean dup = false;
                for (PV pv : volumeList) {
                    if (pv.getName().equals(name)) {
                        dup = true;
                        break;
                    }
                }
                if (exist && !dup)
                {
                    createPV(name, object);
                    System.out.println("Physical Volume " + name + " installed");
                }
                else {
                    System.out.println("Drive: " + PHDname + " does not exist or already linked to a Physical Volume or Physical Volume: " + name + " already exist");
                }
            }
            else if (userChoice.equals("pvlist")){
                listVolume();
            }
            else if (userChoice.contains("vgcreate")){
                userChoice = userChoice.substring(9);
                String name = userChoice.substring(0, userChoice.indexOf(" "));
                userChoice = userChoice.substring(userChoice.indexOf(" ") + 1);
                String PVname = userChoice.substring(0);

                boolean exist = false;
                PV object = null;
                for (PV pv : volumeList) {
                    if (pv.getName().equals(PVname)) {
                        exist = true;
                        object = pv;
                    }
                }
                boolean dup = false;
                for (VG vg : groupList) {
                    if (vg.getName().equals(name)) {
                        dup = true;
                        break;
                    }
                }
                if (exist && !dup)
                {
                    createVG(name, object);
                    System.out.println("Volume Group " + name + " installed");
                }
                else {
                    System.out.println("Physical Volume: " + PVname + " does not exist or already linked to a Volume Group or Volume Group: " + name + " already exist");
                }
            }
            else if (userChoice.contains("vglist"))
            {

            }
            userChoice = s.nextLine();
        }
    }

    public void createHD(String name, int size, String type){
        drive = new PHD(name, size, type);
        driveList.add(drive);
    }

    public void listDrive(){
        for (PHD phd : driveList) {
            System.out.println(phd.getName() + " [" + phd.getSize() + phd.getType() + "]");
        }
    }

    public void createPV(String name, PHD PHDname){
        volume = new PV(name, PHDname);
        volumeList.add(volume);
    }

    public void listVolume(){
        for (PV pv : volumeList) {
            System.out.println(pv.getName() + ":[" + pv.getDrive().getSize() + pv.getDrive().getType() + "] [" + pv.getDrive().getName() + "] [" + pv.getId() + "]");
        }
    }

    public void createVG(String name, PV volume){
        volumeGroup = new VG(name, volume);
        groupList.add(volumeGroup);
    }

    public void vglist(){
        for (VG vg : groupList){
            System.out.println(vg.getName() + ": total: [" + vg.getTotal() + " available: [");
        }
    }
}
