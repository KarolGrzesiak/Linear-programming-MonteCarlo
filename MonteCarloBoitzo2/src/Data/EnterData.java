package Data;

import java.io.*;

public class EnterData {
    public int numberOfVariables;
    public int numberOfConstrains;
    public String objective;
    File fileWithData;
    public double[][] boundaries;
    public String[] variables;
    public String[] constrains;
    public String objectiveFunction;

    public EnterData(String path) {
        fileWithData = new File(path);
    }

    public void ReadData() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileWithData));
        SetNumberOfVariables(reader);
        SetNumberOfConstrains(reader);
        SetBoundariesAndVariables(reader);
        SetCoinstrains(reader);
        SetObjectiveFunction(reader);
        SetObjective(reader);

    }

    void SetNumberOfConstrains(BufferedReader reader) throws IOException {
        numberOfConstrains = Integer.parseInt(reader.readLine());
    }

    void SetNumberOfVariables(BufferedReader reader) throws IOException {
        numberOfVariables = Integer.parseInt(reader.readLine());
    }

    void SetObjectiveFunction(BufferedReader reader) throws IOException {
        String lineInFile = reader.readLine();
        objectiveFunction=lineInFile;
    }

    void SetObjective(BufferedReader reader) throws IOException {
        objective = reader.readLine();
    }


    void SetBoundariesAndVariables(BufferedReader reader) throws IOException {
        String lineInFile;
        boundaries = new double[numberOfVariables][2];
        variables = new String[numberOfVariables];
        for (int i = 0; i < numberOfVariables; i++) {
            lineInFile = reader.readLine().replaceAll("\\s","");
            String[] parts = lineInFile.split("<=", 3);
            double lowerBound = Double.parseDouble(parts[0]);
            double upperBound = Double.parseDouble(parts[2]);
            boundaries[i][0]=lowerBound;
            boundaries[i][1]=upperBound;
            variables[i] = parts[1];
        }
    }

    void SetCoinstrains(BufferedReader reader) throws IOException {
        String lineInFile;
        constrains = new String[numberOfConstrains];
        for(int i=0;i<numberOfConstrains;i++){
            lineInFile=reader.readLine();
            constrains[i]=lineInFile;
        }
    }

}
