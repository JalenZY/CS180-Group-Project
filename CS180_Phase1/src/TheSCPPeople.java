import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class TheSCPPeople {
    private List<SCP> scps;

    public TheSCPPeople() {
        scps = new ArrayList<>();
    }

    public void loadSCPData(String filePath) throws BadDataException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length != 3) {
                    throw new BadDataException("Invalid data format for SCP entry: " + line);
                }
                scps.add(new SCP(parts[0], parts[1], parts[2]));
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public void printSCPData() {
        for (SCP scp : scps) {
            System.out.println(scp);
        }
    }

    public static void main(String[] args) {
        TheSCPPeople db = new TheSCPPeople();
        try {
            db.loadSCPData("scps.txt");
            db.printSCPData();
        } catch (BadDataException e) {
            System.err.println("Failed to load SCP data: " + e.getMessage());
        }
    }
}
