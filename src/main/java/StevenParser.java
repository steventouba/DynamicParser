import java.io.*;

public class StevenParser {

    private BufferedReader in;

    public StevenParser(String filePath) {
        try {
            this.in = new BufferedReader(new FileReader(filePath));
        } catch (Exception e) { System.out.println("Error:" + e.getMessage());}
    }

    public void read() throws IOException {

        String[] headers = in.readLine().split(",");
        String row;

        while ((row = in.readLine()) != null) {
            String[] data = row.split(",");
            System.out.printf("%s\n", String.join(", ", data));
        }

        in.close();
    }

    public void writeTo(String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            String[] headers = in.readLine().split(",");
            int i;
            for (i = 0; i < headers.length; i++) {
                if (headers[i].toLowerCase().equals("name")) break;
            }

            String row;
            while ((row = in.readLine()) != null) {
                String col = row.split(",")[i];
                writer.append(col);
                writer.newLine();
            }
            in.close();
            writer.close();
        } catch (Exception e) { System.out.println("Error" + e.getMessage());}

    }
}
