import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.logging.Logger;
import java.net.HttpURLConnection;
import java.util.zip.*;

/*
@author Andreas Denger
Requires Java 8
*/
public class RepoKeywordSearcher {
    private static final Logger log = Logger.getLogger(RepoKeywordSearcher.class.getName());
    private static final Set<String> VALID_EXT = new HashSet<>(Arrays.asList("java", "cpp", "py"));
    private static final int FILE_MAX_BYTES = 20000;

    public Map<String, List<Integer>> search(String pattern, String user, String repo) throws IOException {
        Map<String, List<Integer>> matches = new HashMap<>();
        log.info("Searching for word \"" + pattern + "\" in repository " + repo + " of user " + user);
        ZipInputStream zipInputStream = establishConnection(user, repo);
        for (ZipEntry zipEntry; (zipEntry = zipInputStream.getNextEntry()) != null; ) {
            String fileName = zipEntry.getName();
            if (!checkZipEntry(zipEntry)) {
                zipInputStream.skip(zipEntry.getSize());
                continue;
            }
            log.info("parsing file: " + fileName);
            List<Integer> matchingLines = getMatches(zipInputStream, pattern);
            if (!matchingLines.isEmpty()) {
                matches.put(fileName, matchingLines);
            }
        }
        return matches;
    }

    private ZipInputStream establishConnection(String user, String repo) throws IOException {
        URL url = new URL("https://api.github.com/repos/" + user + "/" + repo + "/zipball/master");
        log.info("Querying GitHub API with URL " + url.toString());
        HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
        int httpResponse = httpConnection.getResponseCode();
        if (httpResponse != 200) {
            log.info("Connection failed: HTTP response code " + httpResponse);
            throw new IOException("HTTP response " + httpResponse);
        }
        log.info("Connection accepted, server said OK");
        // files and lines
        return new ZipInputStream(httpConnection.getInputStream());
    }

    private boolean checkZipEntry(ZipEntry zipEntry) {
        String fileName = zipEntry.getName();
        if (zipEntry.isDirectory()) {
            //automatically searches recursively
            log.info("Entering directory " + fileName);
            return false;
        }
        log.info("checking file: " + fileName);
        //valid file extension?
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        if (!VALID_EXT.contains(extension)) {
            log.info("file extension " + extension + " not supported, skipping file");
            return false;
        }
        //checks file size in bytes
        if (zipEntry.getSize() > FILE_MAX_BYTES) {
            //TODO returns -1 if file size unknown (did not happen so far)
            log.info("file size was larger than predefined size of " + FILE_MAX_BYTES + " bytes, skipping file");
            return false;
        }
        return true;
    }

    private List<Integer> getMatches(ZipInputStream zipInputStream, String pattern) {
        List<Integer> matchingLines = new LinkedList<>();
        Scanner scanner = new Scanner(zipInputStream);
        for (int lineCounter = 1; scanner.hasNextLine(); lineCounter++) {
            //TODO more efficient algorithm like shift-and or KMP? regex patterns?
            if (scanner.nextLine().contains(pattern)) {
                log.info("Found match in line " + lineCounter);
                //save match
                matchingLines.add(lineCounter);
            }
        }
        return matchingLines;
    }

    public static void main(String[] args) {
        String pattern, user, repo;
        if (args.length >= 3) {
            pattern = args[0];
            user = args[1];
            repo = args[2];
        } else {
            //TODO test case, remove
            pattern = "Mutation";
            user = "adenger";
            repo = "thesis-program";
        }
        try {
            Map<String, List<Integer>> results = new RepoKeywordSearcher().search(pattern, user, repo);
            for (Map.Entry<String, List<Integer>> entry : results.entrySet()) {
                System.out.println(entry.getKey() + " " + entry.getValue().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
