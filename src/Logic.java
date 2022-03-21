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
            if (userChoice.indexOf("install-drive") > -1)
            {
                userChoice = userChoice.substring(14);
                String name = userChoice.substring(0, userChoice.indexOf(" "));
                userChoice = userChoice.substring(userChoice.indexOf(" ") + 1);
                int size = Integer.parseInt(userChoice.substring(0, userChoice.length()-1));
                String type = userChoice.substring(userChoice.length()-1);

                boolean dup = false;
                for (int i = 0; i < driveList.size(); i++)
                {
                    if (driveList.get(i).getName().equals(name))
                    {
                        dup = true;
                    }
                }
                if (dup == false)
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
            else if (userChoice.indexOf("pvcreate") > -1){
                userChoice = userChoice.substring(9);
                String name = userChoice.substring(0, userChoice.indexOf(" "));
                userChoice = userChoice.substring(userChoice.indexOf(" ") + 1);
                String PHDname = userChoice.substring(0);

                boolean exist = false;
                PHD object = null;
                for (int i = 0; i < driveList.size(); i++)
                {
                    if (driveList.get(i).getName().equals(PHDname))
                    {
                        exist = true;
                        object = driveList.get(i);
                    }
                }
                boolean dup = false;
                for (int i = 0; i < volumeList.size(); i++)
                {
                    if (volumeList.get(i).getName().equals(name))
                    {
                        dup = true;
                    }
                }
                if (exist == true && dup == false)
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
            
            userChoice = s.nextLine();
        }
    }

    public void createHD(String name, int size, String type){
        drive = new PHD(name, size, type);
        driveList.add(drive);
    }

    public void listDrive(){
        for (int i = 0; i < driveList.size(); i++)
        {
            System.out.println(driveList.get(i).getName() + " [" + driveList.get(i).getSize() + driveList.get(i).getType() + "]");
        }
    }

    public void createPV(String name, PHD PHDname){
        volume = new PV(name, PHDname);
        volumeList.add(volume);
    }

    public void listVolume(){
        for (int i = 0; i < volumeList.size(); i++)
        {
            System.out.println(volumeList.get(i).getName() + ":[" + volumeList.get(i).getDrive().getSize() + volumeList.get(i).getDrive().getType() + "] [" + volumeList.get(i).getDrive().getName() + "] [" + volumeList.get(i).getId() + "]");
        }
    }

    public void createVG(){

    }
}
