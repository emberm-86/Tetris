package com.games.tetris.service;

import com.games.tetris.ui.CustomDialog;

import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Util class for maintaining the high scores.
 * They are stored under the local folder in a .csv.
 * The parsing of the .csv is simplified but it is
 * enough in this case.
 */
public class HighScoreService {

    private static final String SCORE_LIST_CSV_FILE_NAME = "highscores.csv";

    private static final String CSV_PARSE_REGEX = "[,]";

    private static final Integer HIGH_SCORE_LIST_SIZE = 5;

    /**
     * If a game session is over and the user is new
     * on the high score list, then he can enter his name
     * in a popup dialog and the result
     * can be saved into a .csv file.
     */
    public static void showNewHighScoreEntryDialog(JRootPane rootPane,
                                                   int score) {
        if (score == 0) {
            return;
        }

        String[][] highScores = readHighScoreList();

        boolean isBiggerThanLastOnScoreList = false;

        Comparator<String[]> comparator = (o1, o2) -> Integer.valueOf(o2[1])
                .compareTo(Integer.valueOf(o1[1]));

        if (highScores.length != 0) {
            Arrays.sort(highScores, comparator);

            String[] lastHighScore = highScores[highScores.length - 1];

            int lastHighScoreValue = Integer.parseInt(lastHighScore[1]);

            if (score > lastHighScoreValue) {
                isBiggerThanLastOnScoreList = true;
            }
        }

        if (highScores.length == HIGH_SCORE_LIST_SIZE
                && !isBiggerThanLastOnScoreList) {
            return;
        }

        String title = "Congratulations!";
        String labelText = "Please enter your name:";

        JTextField inputName = new JTextField();

        CustomDialog customDialog =
                new CustomDialog(rootPane, title,
                        new String[]{"Save", "Cancel"}, labelText, inputName);

        int input = customDialog.show();

        if (input != JOptionPane.OK_OPTION) {
            return;
        }

        List<String[]> highScoreList =
                Arrays.stream(highScores).collect(Collectors.toList());

        highScoreList.add((new String[]{inputName.getText(), "" + score}));

        highScoreList.sort(comparator);

        String[][] updatedHighScores =
                highScoreList.stream().limit(HIGH_SCORE_LIST_SIZE)
                        .toArray(String[][]::new);

        writeHighScore(updatedHighScores);
        showHighScores(rootPane);
    }

    /**
     * The high scores are shown in a popup.
     */
    public static void showHighScores(JRootPane rootPane) {
        String title = "High scores";

        String[][] highScores = readHighScoreList();

        CustomDialog customDialog =
                new CustomDialog(rootPane, title,
                        new String[]{"OK"}, null, (JComponent[]) null);

        StringBuilder scoreList = new StringBuilder();

        scoreList.append("<table>");

        if (highScores.length == 0) {
            scoreList.append("<tr><td/><td>Nobody has played yet.</td></tr>");
        } else {
            scoreList.append("<tr><td/><td>Name</td><td>Score</td></tr>");

            IntStream.range(0, highScores.length)
                    .forEach(i -> {
                        String[] scoreListItem = highScores[i];
                        scoreList.append("<tr>");
                        scoreList.append("<td>")
                                .append(i + 1 + ".")
                                .append("</td>")
                                .append("<td style=\"text-align:justify; " +
                                        "width:60px;\">")
                                .append(scoreListItem[0])
                                .append("</td>")
                                .append("<td style=\"text-align:justify; " +
                                        "width:60px;\">")
                                .append(scoreListItem[1])
                                .append("</td>").append("</tr>");
                    });
        }
        scoreList.append("</table>");
        customDialog.addMessageText(scoreList.toString());

        customDialog.show();
    }

    /**
     * The high scores are written to the csv.
     */
    private static void writeHighScore(String[][] highScores) {
        try {
            File csv = new File(SCORE_LIST_CSV_FILE_NAME);

            FileOutputStream outputStream = new FileOutputStream(csv);
            PrintWriter pw = new PrintWriter(outputStream, true);
            pw.println("name,score");

            Arrays.stream(highScores).forEach(
                    scoreItem -> pw.println(scoreItem[0] + "," + scoreItem[1]));

        } catch (IOException e) {
            throw new RuntimeException(e.getLocalizedMessage(), e);
        }
    }

    /**
     * The existing high score list is parsed here.
     */
    private static String[][] readHighScoreList() {
        File csv = new File(SCORE_LIST_CSV_FILE_NAME);

        if (!csv.exists()) {
            return new String[0][0];
        }

        try (BufferedReader br = new BufferedReader(
                new FileReader(csv))) {

            return br.lines().skip(1).map(x -> x.split(CSV_PARSE_REGEX))
                    .toArray(String[][]::new);

        } catch (IOException e) {
            throw new RuntimeException(e.getLocalizedMessage(), e);
        }
    }
}
