public class ActivityFormatter {
    public static void printFirstActivity(String json) {
        int typeKey = json.indexOf("\"type\":");
        if (typeKey == -1) {
            System.out.println("No events found.");
            return;
        }

        int typeFirstQuote = json.indexOf("\"", typeKey + 7);
        int typeSecondQuote = json.indexOf("\"", typeFirstQuote + 1);
        String eventType = json.substring(typeFirstQuote + 1, typeSecondQuote);

        int repoKey = json.indexOf("\"name\":", typeSecondQuote);
        int repoFirstQuote = json.indexOf("\"", repoKey + 7);
        int repoSecondQuote = json.indexOf("\"", repoFirstQuote + 1);
        String repoName = json.substring(repoFirstQuote + 1, repoSecondQuote);

        String message;
        if (eventType.equals("WatchEvent")) {
            message = "Starred " + repoName;
        } else if (eventType.equals("PushEvent")) {
            message = "Pushed commits to " + repoName;
        } else if (eventType.equals("IssuesEvent")) {
            message = "Worked with issues in " + repoName;
        } else {
            message = eventType + " in " + repoName;
        }

        System.out.println(message);
    }
}
