package bomb;

import common.IntList;

import java.util.ArrayList;
import java.util.List;

public class BombMain {
    public static void main(String[] args) {
        int phase = 2;
        String str = new String();
        for(int i = 0;i<1337;i++){
            str += "0 ";
        }
        str += "-81201430";

        if (args.length > 0) {
            phase = Integer.parseInt(args[0]);
        }
        // TODO: Find the correct inputs (passwords) to each phase using debugging techniques
        Bomb b = new Bomb();
        if (phase >= 0) {
            b.phase0("39291226");
        }
        if (phase >= 1) {
            b.phase1(IntList.of(0,9,3,0,8)); // Figure this out too
        }
        if (phase >= 2) {
            b.phase2(str);
        }
    }
}
