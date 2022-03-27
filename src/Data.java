import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors
import java.util.ArrayList;

public class Data {

    //public Object writeToFile;

    public static void writeToFile(ArrayList<PHD> driveList, ArrayList<PV> volumeList, ArrayList<VG> groupList, ArrayList<LV> logicalList){
        try {
            FileWriter myWriter = new FileWriter("src/Data");
            for (PHD phd : driveList){
                String PHD = "PHD|" + phd.getName() + "|" + phd.getSize() + "|" + phd.getType() + "\n";
                myWriter.write(PHD);
            }
            for (PV pv : volumeList){
                String PV = "PV|" + pv.getName() + "|" + pv.getDrive().getName() + "|" + pv.getId() + "\n";
                myWriter.write(PV);
            }
            for (VG vg : groupList){
                String VG = "VG|" + vg.getName() + "|";
                for (PV pv : vg.getPvList())
                {
                    VG +=  pv.getName() + ",";
                }
                VG = VG.substring(0,VG.length()-1);
                VG += "|" + vg.getId() + "\n";
                myWriter.write(VG);
            }
            for (LV lv : logicalList){
                String LV = "LV|" + lv.getName() + "|" + lv.getSize() + "|" + lv.getType() + "|" + lv.getVolumeGroup().getName() + "|" + lv.getId() + "\n";
                myWriter.write(LV);
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
