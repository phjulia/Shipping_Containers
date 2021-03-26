
import java.io.*;

public class s19516 {

    public static void main(String[] args) {
        int flatRackCount = 214;
        int halfHeightCount = 214;
        int tankCount = 214;
        int reeferCount = 214;
        int dryVanCount = 214;
        int acCount = 215;//animal container count
        int carContCount = 215;
        ContainerShip myShip = new ContainerShip();
        Container temp[] = new Container[myShip.capacity];
        String containers[] = new String[myShip.capacity];
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("ships.txt"));
            Container flat;
            Container hContainer;
            Container tank;
            Container reeferTank;
            Container dryVan;
            Container animalsCont;
            Container carContainer;
            while (flatRackCount > 0) {
                flat = new FlatRack();
                temp[flat.getThisContainerId()] = flat;
                flatRackCount--;
                myShip.capacity--;
            }
            while (halfHeightCount > 0) {
                 hContainer = new HalfHeightContainer();
                temp[hContainer.getThisContainerId()] = hContainer;
                halfHeightCount--;
                myShip.capacity--;
            }
            while (tankCount > 0) {
                 tank = new Tank();
                temp[tank.getThisContainerId()] = tank;
                tankCount--;
                myShip.capacity--;
            }
            while (reeferCount > 0) {
                reeferTank = new ReeferTank();
                temp[reeferTank.getThisContainerId()] = reeferTank;
                reeferCount--;
                myShip.capacity--;
            }
            while (dryVanCount > 0) {
                dryVan = new DryVan();
                temp[dryVan.getThisContainerId()] = dryVan;
                dryVanCount--;
                myShip.capacity--;
            }
            while (acCount > 0) {
                animalsCont = new AnimalContainer();
                temp[animalsCont.getThisContainerId()] = animalsCont;
                acCount--;
                myShip.capacity--;
            }
            while (carContCount > 0) {
                carContainer = new CarCarrier();
                temp[carContainer.getThisContainerId()] = carContainer;
                carContCount--;
                myShip.capacity--;
            }
            //sorting by mass(descending)
            for (int i = 0; i < temp.length - 1; i++) {
                int max = i;
                for (int j = i + 1; j < temp.length; j++) {
                    if (temp[j].getMass() > temp[max].getMass()) {
                        max = j;
                    }
                }
                Container tmp = temp[max];
                temp[max] = temp[i];
                temp[i] = tmp;

            }


            for (int i = 0; i < temp.length; i++) {
                out.write(temp[i].toString());
                out.write('\n');
            }


            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader("ships.txt"));
            String line;

            {
                int i = 0;
                while ((line = reader.readLine()) != null) {
                    containers[i] = line;
                    System.out.println(containers[i++]);

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        {

            //Arranging containers on the ship - 3D array, starting from the bottom layer of containers,
            //Before writing the String representation of containers to a file, I've sorted them - therefore,
            // I will be loading heavier containers first, and then lighter.
            //containers are added in lines, one in the front, and one at the end, until we reach the middle of the line
            //then load containers in the second line and so on until we fill our whole floor
            try {
                BufferedWriter out = new BufferedWriter(new FileWriter("manifest.txt"));
                int i = 0;
                for (int tierNum = myShip.getTiers() - 1; tierNum >= 0; tierNum--) {
                    for (int rowNum = 0; rowNum < myShip.getRows(); rowNum++) {
                        for (int bayNum = 0; bayNum < myShip.getBays() ; bayNum++) {
                            myShip.addContainer(containers[i], tierNum, rowNum, bayNum);
                            out.write("Pos: ["+tierNum+"]["+rowNum+"]["+bayNum+"]\t"+containers[i++]);
                            out.write("\n");
                        }
                    }
                }


                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}

    class ContainerShip {
        public int capacity = 1500;
        int x=4,y=15,z=25;
        String containers[][][] = new String[x][y][z];//the boat is rectangular(
        // x- height; y-width; z-length;
        public void addContainer(String container, int x, int y, int z) {
            containers[x][y][z] = container;
            System.out.println("Added to" + x + " " + y + " " + z + ";");
        }
        public int getTiers() {
            return containers.length;
        }

        public int getRows() {
            return containers[0].length;
        }

        public int getBays() {
            return containers[0][1].length;
        }

    }

class Container{
    protected double width;//in meters
    protected double height;
    protected double length;
    protected int mass;
    protected String description;
    protected String cargo;
    private static int containerId;
    private int thisContainerId;

    public Container() {
        thisContainerId=containerId++;
    }
    public int getMass() {
        return mass;
    }

    public int getThisContainerId() {
        return thisContainerId;
    }

    @Override
    public String toString() {
        return  "Container id: " +thisContainerId+
                ";\tMass: " + mass +";\tCargo: "+cargo;
    }
}
class FlatRack extends Container{
    private String possibleCargoTypes[]={"machinery", "timber", "pipes", "buses", "boats"};

    public FlatRack() {
        width=2.228;
        height=1.981;
        length=11.832;
        cargo= possibleCargoTypes[(int)(Math.random()*possibleCargoTypes.length)];
        mass=(int)(Math.random() * ((50_000 - 40_000) + 1) + 40_000);
    }
    public String[] getPossibleCargoTypes() {
        return possibleCargoTypes;
    }
}//2
class HalfHeightContainer extends Container {
    private String possibleCargoTypes[] = {"coal", "stones", "vehicles", "pipes", "tools"};

    public HalfHeightContainer() {
        width=3;
        height=4.5;
        length=6;
        mass = (int) (Math.random() * ((35000 - 30_000) + 1) + 30_000);
        cargo = possibleCargoTypes[(int) (Math.random() * possibleCargoTypes.length)];
        description = "These containers are half height of a regular container";
    }

    public String[] getPossibleCargoTypes() {
        return possibleCargoTypes;
    }
}
class Tank extends Container{

    private String possibleCargoTypes[]={"Water","Hydrogen Peroxide",
            "Sulphuric Acid",
            "Nitric Acid",
            "Mining chemicals",
            "Food products",
            "Solvents",
            "Oils",
            "Resins",
            "Alcoholic beverages"};
    public Tank() {
        width=2.43;
        height=2.59;
        length=6.05;
        mass=(int)(Math.random() * ((32_000 - 25_800) + 1) + 25_800);
        cargo= possibleCargoTypes[(int)(Math.random()*possibleCargoTypes.length)];
        description="This type of container is used for transporting liquid materials";
    }
    public String[] getPossibleCargoTypes() {
        return possibleCargoTypes;
    }
}
class ReeferTank extends Container{
    private String possibleCargoTypes[]={"frozen fruit","frozen vegetables","bananas","meat",
            "seafood","dairy","fish"};
    private double minTemperature=-30;//in degrees C
    private double maxTemperature=30;//in degrees C

    public ReeferTank() {
        width=2.29 ;
        height=2.27;
        length=5.44;
        cargo= possibleCargoTypes[(int)(Math.random()*possibleCargoTypes.length)];
        mass=(int)(Math.random() * ((30_000 - 25_000) + 1) + 25_000);
    }
    public String[] getPossibleCargoTypes() {
        return possibleCargoTypes;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }
}
class DryVan extends Container{
    private String possibleCargoTypes[]={"clothes","electronics","canned goods","furniture",
            "cartoons","boxes"};

    public DryVan() {
        width=2.35;
        height=2.39;
        length=5.9;
      cargo= possibleCargoTypes[(int)(Math.random()*possibleCargoTypes.length)];
      mass=(int)(Math.random() * ((20_000 - 15_000) + 1) + 15_000);
    }

    public String[] getPossibleCargoTypes() {
        return possibleCargoTypes;
    }
}

class AnimalContainer extends Container{
    private String possibleCargoTypes[]={"sheep","cattle","goats"};
    public AnimalContainer(){
        cargo= possibleCargoTypes[(int)(Math.random()*possibleCargoTypes.length)];
        mass=(int)(Math.random() * ((15000 - 10000) + 1) + 10000);
    }

}
class CarCarrier extends Container{
//we're assuming that our container always contains 2 cars
    public CarCarrier(){
        cargo="cars";
        mass=(int)(Math.random() * ((9600 - 7600) + 1) + 7600);//5000 weight of container
        //assuming a car weights 1300kg-2300kg; 7600+5000
    }
}



