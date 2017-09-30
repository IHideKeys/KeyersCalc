package keyers.dg.calc;

import sun.jvm.hotspot.debugger.cdbg.Sym;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.util.StringTokenizer;

/**
 * Created by Sajiel.
 */

class BackEnd {
    // Array with all XP per level within it
    private static final int[] LEVEL_XP = {0, 83, 174, 276, 388, 512, 650, 801, 969, 1154, 1358, 1584, 1833, 2107, 2411, 2746, 3115, 3523, 3973, 4470, 5018, 5624, 6291, 7028, 7842, 8740, 9730, 10824, 12031, 13363, 14833, 16456, 18247, 20224, 22406, 24815, 27473, 30408, 33648, 37224, 41171, 45529, 50339, 55649, 61512, 67983, 75127, 83014, 91721, 101333, 111945, 123660, 136594, 150872, 166636, 184040, 203254, 224466, 247886, 273742, 302288, 333804, 368599, 407015, 449428, 496254, 547953, 605032, 668051, 737627, 814445, 899257, 992895, 1096278, 1210421, 1336443, 1475581, 1629200, 1798808, 1986068, 2192818, 2421087, 2673114, 2951373, 3258594, 3597792, 3972294, 4385776, 4842295, 5346332, 5902831, 6517253, 7195629, 7944614, 8771558, 9684577, 10692629, 11805606, 13034431, 14391160, 15889109, 17542976, 19368992, 21385073, 23611006, 26068632, 28782069, 31777943, 35085654, 38737661, 42769801, 47221641, 52136869, 57563718, 63555443, 70170840, 77474828, 85539082, 94442737, 104273167};
    // Base XP per floor where key = floor, FLOOR_XP[floor] = Base XP
    private static final int[] FLOOR_XP = {2215, 2762, 3278, 3600, 4000, 4734, 5220, 5705, 6129, 6600, 7200, 7821, 8179, 8776, 9130, 9773, 10000, 10335, 11482, 11980, 12909, 13258, 15076, 15582, 17513, 19544, 20191, 21853, 24259, 25096, 27631, 29574, 31664, 33226, 35271, 37791, 40623, 42603, 45510, 47667, 50270, 55576, 56148, 60755, 63073, 68338, 71283, 76750, 80250, 85373, 88733, 92830, 96604, 102897, 104426, 112690, 119660, 125061, 133613, 138984};
    // Average floor modifier used for calculation
    private static final double AVERAGE_MODIFIER = 133.211;
    // Used for run time.
    private static long startTime;

    /**
     * It is imperative that you send the start *XP*, desired *XP* and prestige.
     * This subtly means you must convert any levels to XP and calculate prestige elsewhere.
     * This is to simplify the complexity of this method.
     * (Essentially by lessening extraneous code)
     *
     * This version uses a more logical highest floor first approach, as many leeches would choose
     * to emulate. However, in versions to follow this will be replaced with a more realistic
     * randomized floor in theme (similar to what a keyer would pick).
     *
     * @param startXp - The starting experience of the leech
     * @param desiredXp - The desired goal experience of the leech
     *
     * @return floorsRequired - Array containing floors/c1 sets required
     */
    public static int[] calculate(int startXp, int desiredXp) {

        // Various variables implemented in the loop
        // Initialize current floor for loop.
        int maxFloor, currentLevel, currentFloor = 1, froz, ab1, furn,
                ab2, occ, warp, hw;
        double c1;
        int levelThresh = 1;

        int gainedXp = desiredXp-startXp;

        // Initialize all counter variables to 0
        froz = 0; ab1 = 0; furn = 0; ab2 = 0; occ = 0; warp = 0; hw = 0; c1 = 0;

        // Assume all floors are open to start
        boolean hasPrestiged = true;

        // The fun part! The logic behind the calculator!
        while (startXp < desiredXp) {
            // Recalculate current level.
            currentLevel = getLevel(startXp);

            // Initial max floor calculation.
            maxFloor = (int) Math.ceil(currentLevel / 2.0);

            // Set floor accordingly, not set in stone. Just a quick thought.
            if (currentLevel < 50 && currentLevel >= 1 && hasPrestiged) {
                currentFloor = maxFloor;
                hasPrestiged = false;
                levelThresh = 1;
            }
            else if (currentLevel < 65 && currentLevel > 50 && hasPrestiged) {
                currentFloor = maxFloor;
                hasPrestiged = false;
                c1 += 2/3;
                levelThresh = 20;
            } else if (currentLevel >= 65 && hasPrestiged) {
                currentFloor = maxFloor;
                levelThresh = 30;
                hasPrestiged = false;
                c1 += 1;
            }


            // Run until reach desired xp. Base cases: level 1 or level 2.
            while (currentFloor > levelThresh && startXp < desiredXp || currentLevel == 1 || currentLevel == 2) {
                if (currentFloor == 0) {
                    hasPrestiged = true;
                    break;
                }
                // Recalculate current level.
                currentLevel = getLevel(startXp);

                // Add the current floor's xp gained to the start xp.
              //  System.out.println(currentFloor + " " + maxFloor);
                startXp += getFloorXp(currentFloor, maxFloor);

                // Increase floor counters accordingly.
                if (currentFloor < 12)
                    froz++;
                else if (currentFloor < 18)
                    ab1++;
                else if (currentFloor < 30)
                    furn++;
                else if(currentFloor < 36)
                    ab2++;
                else if(currentFloor < 48)
                    occ++;
                else if(currentFloor < 57)
                    warp++;
                else if (currentFloor <= 60)
                    hw++;

                    // Decrease current floor
                    currentFloor--;

                if (currentFloor == levelThresh) {
                    // Prestiged
                    hasPrestiged = true;
                }
            }
        }

        // Stores, # of Frozens, Abd1's, Furns,
        // Abds, Occs, Warps, High Warps, and sets of C1's
        int[] floorsRequired = {
                froz, ab1, furn, ab2, occ, warp, hw, (int)Math.ceil(c1), gainedXp
        };

        // End time for run-time
        long endTime = System.currentTimeMillis();


        // Total amount.
        int total = (int) (floorsRequired[0]*1.5+floorsRequired[1]*1.5+floorsRequired[2]*1.5+floorsRequired[3]*1.75+floorsRequired[4]*2+floorsRequired[5]*2.5+floorsRequired[6]*2.75+floorsRequired[7]*4.5);

        // Print out each number of floors required
       /* for (int i = 0; i < floorsRequired.length; i++) {
            switch (i) {
                case 0: System.out.print("Frozens: ");
                    break;
                case 1: System.out.print("Abd1: ");
                    break;
                case 2: System.out.print("Furns: ");
                    break;
                case 3: System.out.print("Abds: ");
                    break;
                case 4: System.out.print("Occs: ");
                    break;
                case 5: System.out.print("Warps: ");
                    break;
                case 6: System.out.print("High Warps: ");
                    break;
                case 7: System.out.print("C1's: ");
                    break;
                default:
                    break;
            }
            //System.out.println(floorsRequired[i]);
        }*/

        //Print total
        //System.out.printf("Total: %dM\n", total);


        // Return array with pertinent floors.
        return floorsRequired;
    }

    public static int[] getDGData(String rsn) throws IOException {

        DecimalFormat df = new DecimalFormat();
        int[] ifFail = {-1};

        // Start time for run-time
        startTime = System.currentTimeMillis();

        // Replace spaces with an underscore for the proper URL formation
        rsn = rsn.replace(' ', '_');

        // Obvious variables
        URL url;
        String line;
        StringTokenizer st;
        int[] dgData = new int[2];

        // Start counting the number of lines from 1
        int count = 1;

        // a URL for the RuneScape lite hiscores
        url = new URL("http://services.runescape.com/m=hiscore/index_lite.ws?player=" + rsn);

        // Create a connection
        URLConnection con;

        con = url.openConnection();

        // Create a stream to receive data
        InputStream is = null;
        try {
            is = con.getInputStream();
        } catch (FileNotFoundException e) {
            return ifFail;
        }
        // Create a reader to read input stream
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        // Read the DG level
        while ((line = br.readLine()) != null) {
            if (count == 26) {
                // Tokenize the line
                st = new StringTokenizer(line,",");
                // Consume (DG Rank) first token
                st.nextToken();
                // Get DG Level
                dgData[0] = Integer.parseInt(st.nextToken());
                // Get DG Xp
                dgData[1] = Integer.parseInt(st.nextToken());
                break;
            }
            else
                // Increase counter if dg level isn't found
                count++;
        }

        // Make sure RSN existed
        if (dgData[0] > 0) {
            //System.out.println("DG Level: " + dgData[0] + "\nDG XP: " + df.format(dgData[1]) + "\n");
        }
        //else
            //System.out.println("Sorry RSN doesn't exist");



        return dgData;
    }

    //Determines the player's DG level by finding the range startXp lies in
    public static int getLevel(int startXp) {
        for (int i = 0; i < LEVEL_XP.length - 1; i++) {
            if (startXp >= LEVEL_XP[119])
                return 120;

            else if (startXp >= LEVEL_XP[i] && startXp < LEVEL_XP[i + 1])
                return i + 1;
        }
        return 0;
    }

    //Calculates the floor XP based on averaged data
    public static int getFloorXp(int currentFloor, int maxFloor) {
        return (int) ((FLOOR_XP[currentFloor - 1] + FLOOR_XP[maxFloor - 1])/2 * AVERAGE_MODIFIER/100);
    }

    public static int getXP(int input) {
        return LEVEL_XP[input-1];
    }
}