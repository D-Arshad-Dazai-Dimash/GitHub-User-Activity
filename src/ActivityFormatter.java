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

        String message = switch (eventType) {
            case "WatchEvent" -> "Starred " + repoName;
            case "PushEvent" -> "Pushed commits to " + repoName;
            case "IssuesEvent" -> "Worked with issues in " + repoName;
            default -> eventType + " in " + repoName;
        };

        System.out.println(message);
    }

    public static void printActivities(String json, int limit) {
        int index = json.indexOf("{");
        int count = 0;

        while (index != -1 && count < limit) {
            int eventStart = index;
            int braceLevel = 0;
            int i = eventStart;
            boolean started = false;

            for (; i < json.length(); i++) {
                char c = json.charAt(i);
                if (c == '{') {
                    braceLevel++;
                    started = true;
                } else if (c == '}') {
                    braceLevel--;
                }
                if (started && braceLevel == 0) {
                    break;
                }
            }


            if (!started) {
                break;
            }

            int eventEnd = i;
            String eventJson = json.substring(eventStart, eventEnd + 1);

//            System.out.println("DEBUG: eventJson length=" + eventJson.length()); // ДОБАВЬ

            String message = formatSingleEvent(eventJson);

            if (message != null && !message.isEmpty()) {
                System.out.println(" - " + message);
                count++;
            }

            index = json.indexOf("{", eventEnd + 1);
        }

        if (count == 0) {
            System.out.println("No event found");
        }

    }

    public static String formatSingleEvent(String json) {
        int typeKey = json.indexOf("\"type\":");
        if (typeKey == -1) {
            return null;
        }

        int typeFirstQuote = json.indexOf("\"", typeKey + 7);
        int typeSecondQuote = json.indexOf("\"", typeFirstQuote + 1);
        String eventType = json.substring(typeFirstQuote + 1, typeSecondQuote);

        int repoKey = json.indexOf("\"name\":", typeSecondQuote);
        if (repoKey == -1) {
            return null;
        }
        int repoFirstQuote = json.indexOf("\"", repoKey + 7);
        int repoSecondQuote = json.indexOf("\"", repoFirstQuote + 1);
        String repoName = json.substring(repoFirstQuote + 1, repoSecondQuote);

        String message = switch (eventType) {
            case "WatchEvent" -> "Starred " + repoName;
            case "PushEvent" -> "Pushed commits to " + repoName;
            case "IssuesEvent" -> "Worked with issues in " + repoName;
            default -> eventType + " in " + repoName;
        };

        return message;
    }
}