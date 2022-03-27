import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.UUID;

public class Logic {
    private PHD drive;
    ArrayList<PHD> driveList = new ArrayList<PHD>();
    private PV volume;
    ArrayList<PV> volumeList = new ArrayList<PV>();
    private VG volumeGroup;
    ArrayList<VG> groupList = new ArrayList<VG>();
    private LV logicalVolume;
    ArrayList<LV> logicalList = new ArrayList<LV>();
    
    Data save = new Data();

    public void run() throws FileNotFoundException {

        File myObj = new File("src/Data");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            if (data.contains("PHD")){
                String[] driveData = data.split("\\|");
                createHD(driveData[1], Integer.parseInt(driveData[2]), driveData[3]);
            }
            if (data.contains("PV")){
                String[] driveData = data.split("\\|");
                boolean exist = false;
                PHD object = null;
                for (PHD phd : driveList) {
                    if (phd.getName().equals(driveData[2])) {
                        exist = true;
                        object = phd;
                    }
                }
                createPV(driveData[1], object, driveData[3]);
            }
            if (data.contains("VG")){
                String[] driveData = data.split("\\|");
                boolean exist = false;
                PV object = null;
                for (PV pv : volumeList) {
                    if (driveData[2].contains(",")){
                        if (pv.getName().equals(driveData[2].substring(0,driveData[2].indexOf(",")))){
                            exist = true;
                            object = pv;
                        }
                    }
                    else if (pv.getName().equals(driveData[2])) {
                        exist = true;
                        object = pv;
                    }
                }
                createVG(driveData[1], object, driveData[3]);
                if (driveData[2].contains(",")){
                    String[] drives = driveData[2].split("\\,");
                    {
                        for (int i = 1; i < drives.length; i++){
                            VG object1 = null;
                            for (VG vg : groupList){
                                if (vg.getName().equals(driveData[1])){
                                    object1 = vg;
                                }
                            }
                            PV object2 = null;
                            for (PV pv : volumeList){
                                if (pv.getName().equals(drives[i])){
                                    object2 = pv;
                                }
                            }
                            vgextend(object1, object2);
                        }
                    }
                }
            }
            if (data.contains("LV")){
                String[] driveData = data.split("\\|");
                boolean exist = false;
                VG object = null;
                for (VG vg : groupList) {
                    if (vg.getName().equals(driveData[4])) {
                        exist = true;
                        object = vg;
                    }
                }
                lvcreate((driveData[1]), Integer.parseInt(driveData[2]), object, driveData[3], driveData[5]);
            }
        }
        myReader.close();


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
                if (exist && !dup && !object.isLinked())
                {
                    createPV(name, object);
                    object.setLinked(true);
                    System.out.println("Physical Volume " + name + " installed");
                }
                else if (!exist){
                    System.out.println("Drive: " + PHDname + " does not exist");
                }
                else if (dup) {
                    System.out.println("Physical Volume " + name + " already exist");
                }
                else {
                    System.out.println("Drive: " + PHDname + " is linked to other Physical Volume");
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
                if (exist && !dup && !object.isLinked())
                {
                    createVG(name, object);
                    object.setLinked(true);
                    System.out.println("Volume Group " + name + " installed");
                }
                else if (!exist){
                    System.out.println("Physical Volume: " + PVname + " does not exist");
                }
                else if (dup) {
                    System.out.println("Volume Group " + name + " already exist");
                }
                else {
                    System.out.println("Physical Volume: " + PVname + " is linked to other Volume Group");
                }
            }
            else if (userChoice.contains("vgextend")){
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
                VG object1 = null;
                for (VG vg : groupList) {
                    if (vg.getName().equals(name)) {
                        object1 = vg;
                    }
                }
                if (exist && !object.isLinked())
                {
                    vgextend(object1, object);
                    System.out.println("Volume Group " + name + " updated");
                }
                else if (!exist){
                    System.out.println("Physical Volume: " + PVname + " does not exist");
                }
                else {
                    System.out.println("Physical Volume: " + PVname + " is linked to other Volume Group");
                }
            }
            else if (userChoice.contains("vglist"))
            {
                vglist();
            }
            else if (userChoice.contains("lvcreate")){
                userChoice = userChoice.substring(9);
                String name = userChoice.substring(0, userChoice.indexOf(" "));
                userChoice = userChoice.substring(userChoice.indexOf(" ") + 1);
                int size = Integer.parseInt(userChoice.substring(0, userChoice.indexOf(" ")-1));
                String type = userChoice.substring(userChoice.indexOf(" ")-1 ,userChoice.indexOf(" "));
                userChoice = userChoice.substring(userChoice.indexOf(" ") + 1);
                String VGname = userChoice.substring(0);

                boolean exist = false;
                VG object = null;
                for (VG vg : groupList) {
                    if (vg.getName().equals(VGname)) {
                        exist = true;
                        object = vg;
                    }
                }
                boolean dup = false;
                for (LV lv : logicalList) {
                    if (lv.getName().equals(name)) {
                        dup = true;
                        break;
                    }
                }

                if (exist && !dup && size <= object.getAvaliable())
                {
                    lvcreate(name, size, object, type);
                    object.setAvaliable(size);
                    System.out.println("Logical Volume " + name + " installed");
                }
                else if (!exist){
                    System.out.println("Volume Group: " + VGname + " does not exist");
                }
                else if (dup) {
                    System.out.println("Logical Volume " + name + " already exist");
                }
                else {
                    System.out.println("Volume Group: " + VGname + " does not have enough space");
                }

            }
            else if (userChoice.contains("lvlist")){
                lvlist();
            }
            else{
                System.out.println("Invalid command");
            }
            userChoice = s.nextLine();
        }
        System.out.println("Saving data. Good-bye!");
        save.writeToFile(driveList, volumeList, groupList, logicalList);
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

    public void createPV(String name, PHD PHDname, String id){
        volume = new PV(name, PHDname);
        volume.setId(UUID.fromString(id));
        volumeList.add(volume);
    }

    public void listVolume(){
        for (PV pv : volumeList) {
            if (groupList.isEmpty()){
                System.out.println(pv.getName() + ":[" + pv.getDrive().getSize() + pv.getDrive().getType() + "] [" + pv.getId() + "]");
            }
            else
            for (VG vg : groupList) {
                for (int i = 0; i < vg.getPvList().size(); i++)
                {
                    if (vg.getPvList().get(i).getName().equals(pv.getName()))
                    {
                        System.out.println(pv.getName() + ":[" + pv.getDrive().getSize() + pv.getDrive().getType() + "] [" + vg.getName() + "] [" + pv.getId() + "]");
                    }
                }
            }
        }
    }

    public void createVG(String name, PV volume){
        volumeGroup = new VG(name, volume);
        groupList.add(volumeGroup);
    }

    public void createVG(String name, PV volume, String id){
        volumeGroup = new VG(name, volume);
        volumeGroup.setId(UUID.fromString(id));
        groupList.add(volumeGroup);
    }

    public void vgextend(VG name, PV volume) {
        name.getPvList().add(volume);
        name.updateSize(volume.getDrive().getSize());
    }

    public void vglist(){
        for (VG vg : groupList){
            String display = vg.getName() + ": total:[" + vg.getTotal() + vg.getPvList().get(0).getDrive().getType() + "] available:[" + vg.getAvaliable() + vg.getPvList().get(0).getDrive().getType()+ "] [";
            for (int i = 0; i < vg.getPvList().size(); i++){
                String drive = vg.getPvList().get(i).getName();
                display += drive + ",";
            }
            display = display.substring(0, display.length()-1);
            display += "] [" + vg.getId() + "]";
            System.out.println(display.toString());
        }
    }

    public void lvcreate(String name, int size, VG VGname, String type){
        logicalVolume = new LV(size, name, VGname, type);
        logicalList.add(logicalVolume);
    }

    public void lvcreate(String name, int size, VG VGname, String type, String id){
        logicalVolume = new LV(size, name, VGname, type);
        logicalVolume.setId(UUID.fromString(id));
        logicalList.add(logicalVolume);
    }

    public void lvlist(){
        for (LV logical : logicalList){
            System.out.println(logical.getName() + ": [" + logical.getSize() + logical.getType() + "] [" + logical.getVolumeGroup().getName() + "] [" + logical.getId() + "]");
        }
    }
    
}
