public class GitHubActivity {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java GitHubActivity <username>");
            return;
        }

        String username = args[0];
        String apiURL = "https://api.github.com/users/" + username + "/events";

        try {
            String json = GitHubApiClient.fetch(apiURL);
            ActivityFormatter.printFirstActivity(json);
            ActivityFormatter.printActivities(json, 10);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}