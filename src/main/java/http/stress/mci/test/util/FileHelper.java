package http.stress.mci.test.util;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FileHelper {

    private static final String FILE_DOMAINS_NAME = "top-domains.txt";

    private File loadFileAsAResource() throws URISyntaxException, FileNotFoundException {
        URL resource = getClass().getClassLoader().getResource(FILE_DOMAINS_NAME);
        if (resource == null) {
            throw new IllegalArgumentException("file not found!");
        } else {
            return new File(resource.toURI());
        }
    }

    public List<String> readFileByLines() throws FileNotFoundException, URISyntaxException {
        List<String> result = new ArrayList<>();
        BufferedReader reader;
        try {
            File initialFile = loadFileAsAResource();
            initialFile.createNewFile();
            reader = new BufferedReader(new FileReader(initialFile));
            String line = reader.readLine();
            while (line != null) {
                result.add(line);
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;

    }
}
