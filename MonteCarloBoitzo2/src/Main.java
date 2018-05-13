import Data.*;
import Solver.MonteCarlo;

import java.io.IOException;

public class Main {
    public static void main (String[] args){
        EnterData data = new EnterData("/home/karolgrzesiak/IdeaProjects/MonteCarloBoitzo2/test1");
        try {
            data.ReadData();
            MonteCarlo solver = new MonteCarlo(data);
            solver.Solve();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
