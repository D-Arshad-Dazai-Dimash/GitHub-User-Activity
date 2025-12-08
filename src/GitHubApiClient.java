import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GitHubApiClient {
    static String fetch(String apiURL) throws IOException {
        URL url = new URL(apiURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/vnd.github.v3+json");

        int status = connection.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        status >= 200 && status < 300
                                ? connection.getInputStream()
                                : connection.getErrorStream()
                )
        );

        StringBuilder response = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();
        connection.disconnect();

        if (status == 404) {
            throw new IOException("User not found: " + apiURL);
        } else if (status >= 400) {
            throw new IOException("GitHub API error, status " + status + ": " + response);
        }


        return response.toString();
    }
}
