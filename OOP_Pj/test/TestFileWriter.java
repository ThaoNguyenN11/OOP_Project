
import java.io.FileWriter;
import java.io.IOException;



public class TestFileWriter {
    public static void main(String[] args) throws IOException {
        FileWriter fw = new FileWriter("./json/test.txt");
        fw.write("Welcome to java.");
        fw.close();
    }
}
/*

*/
