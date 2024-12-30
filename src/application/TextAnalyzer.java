package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.*;
import java.util.stream.Collectors;

public class TextAnalyzer extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Elieser's Text Analyzer");

        // Create UI components
        Label inputLabel = new Label("Enter a paragraph:");
        TextArea inputTextArea = new TextArea();
        Label charLabel = new Label("Enter a character to check frequency:");
        TextField charTextField = new TextField();
        Label wordLabel = new Label("Enter a word to check frequency:");
        TextField wordTextField = new TextField();
        Button analyzeButton = new Button("Analyze");
        TextArea resultTextArea = new TextArea();
        resultTextArea.setEditable(false);

        // Set up layout
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.add(inputLabel, 0, 0);
        gridPane.add(inputTextArea, 1, 0);
        gridPane.add(charLabel, 0, 1);
        gridPane.add(charTextField, 1, 1);
        gridPane.add(wordLabel, 0, 2);
        gridPane.add(wordTextField, 1, 2);
        gridPane.add(analyzeButton, 1, 3);
        gridPane.add(resultTextArea, 1, 4);

        // Set up button action
        analyzeButton.setOnAction(e -> {
            String text = inputTextArea.getText();
            String charToCheck = charTextField.getText();
            String wordToCheck = wordTextField.getText();

            if (text.isEmpty() || charToCheck.isEmpty() || wordToCheck.isEmpty()) {
                resultTextArea.setText("Please fill in all fields.");
                return;
            }

            // Perform analysis
            int charCount = text.length();
            int wordCount = text.split("\\s+").length;
            char mostCommonChar = findMostCommonChar(text);
            int charFrequency = countCharFrequency(text, charToCheck.charAt(0));
            int wordFrequency = countWordFrequency(text, wordToCheck);
            int uniqueWordCount = countUniqueWords(text);

            // Display results
            resultTextArea.setText(String.format("Character Count: %d\nWord Count: %d\nMost Common Character: %c\n" +
                    "Frequency of '%s': %d\nFrequency of \"%s\": %d\nUnique Words: %d",
                    charCount, wordCount, mostCommonChar, charToCheck, charFrequency, wordToCheck, wordFrequency, uniqueWordCount));
        });

        // Set up scene and stage
        Scene scene = new Scene(gridPane, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private char findMostCommonChar(String text) {
        Map<Character, Long> charFrequency = text.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()));

        return Collections.max(charFrequency.entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    private int countCharFrequency(String text, char ch) {
        return (int) text.chars()
                .filter(c -> Character.toLowerCase(c) == Character.toLowerCase(ch))
                .count();
    }

    private int countWordFrequency(String text, String word) {
        String[] words = text.split("\\s+");
        return (int) Arrays.stream(words)
                .filter(w -> w.equalsIgnoreCase(word))
                .count();
    }

    private int countUniqueWords(String text) {
        String[] words = text.split("\\s+");
        Set<String> uniqueWords = new HashSet<>(Arrays.asList(words));
        return uniqueWords.size();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
