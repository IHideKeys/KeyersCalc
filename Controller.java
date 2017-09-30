package keyers.dg.calc;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Objects;

/**
 * Created by Sajiel.
 * Controller of JavaFX application.
 */
public class Controller {

    // Variables
    private static int floors[] = null;
    private static int cost = 0;
    public Label title;
    public TextField enterRSN;
    public RadioButton c1Skipped;
    public Button calculateButton;
    public Label costLabel;
    public Label tokensLabel;
    public TextField goalXpField;
    public AnchorPane mainPane;
    public TextField enterLvl;
    public RadioButton xpLvlSelection;
    public RadioButton rsnSelection;
    public RadioButton afkC1Selection;
    public RadioButton nonAfkC1Selection;
    public Label xpGainedLabel;
    public Label timeLabel;
    private int totalTime = 0;
    private int mins = 0;
    private int hours = 0;
    private int gainedXp;


    @FXML
    /**
     * Sets everything up.
     */
    public void initialize() {

        // Initial setup
        ToggleGroup c1Tg = new ToggleGroup();
        ToggleGroup xpRsnTg = new ToggleGroup();
        xpLvlSelection.setToggleGroup(xpRsnTg);
        rsnSelection.setToggleGroup(xpRsnTg);
        rsnSelection.setSelected(true);

        mainPane.setStyle("-fx-background-color: linear-gradient(to left, #8e9eab , #eef2f3);");

        title.setStyle("-fx-text-fill: linear-gradient(#2980b9 , #2c3e50);");

        costLabel.setStyle("-fx-text-fill: linear-gradient(#2980b9 , #2c3e50); -fx-font-weight: bold;");
        tokensLabel.setStyle("-fx-text-fill: linear-gradient(#2980b9 , #2c3e50); -fx-font-weight: bold;");

        timeLabel.setStyle("-fx-text-fill: linear-gradient(#2980b9 , #2c3e50); -fx-font-weight: bold;");
        xpGainedLabel.setStyle("-fx-text-fill: linear-gradient(#2980b9 , #2c3e50); -fx-font-weight: bold;");

        afkC1Selection.setToggleGroup(c1Tg);
        nonAfkC1Selection.setToggleGroup(c1Tg);
        nonAfkC1Selection.setSelected(true);
        c1Skipped.setToggleGroup(c1Tg);

        afkC1Selection.setStyle("-fx-mark-color: linear-gradient(#2980b9 , #2c3e50); ");
        nonAfkC1Selection.setStyle("-fx-mark-color: linear-gradient(#2980b9 , #2c3e50);");
        c1Skipped.setStyle("-fx-mark-color: linear-gradient(#2980b9 , #2c3e50);");
        xpLvlSelection.setStyle("-fx-mark-color: linear-gradient(#2980b9 , #2c3e50);");
        rsnSelection.setStyle("-fx-mark-color: linear-gradient(#2980b9 , #2c3e50);");


        // Only allow numbers to be input
        enterLvl.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                enterLvl.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        // Only allow numbers to be input
        goalXpField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                goalXpField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        enterRSN.setStyle("-fx-text-box-border: blue ; -fx-focus-color: blue; -fx-control-inner-background: #8e9eab;");
        enterLvl.setStyle("-fx-text-box-border: blue ; -fx-focus-color: blue; -fx-control-inner-background: #8e9eab;");
        goalXpField.setStyle("-fx-text-box-border: blue ; -fx-focus-color: blue; -fx-control-inner-background: #8e9eab;");

        enterRSN.setTooltip(new Tooltip("Enter your RSN"));
        enterLvl.setTooltip(new Tooltip("Enter your DG Level or current DG XP"));
        goalXpField.setTooltip(new Tooltip("Enter your goal xp or level"));
        calculateButton.setStyle("-fx-background-color: linear-gradient(to left, #2980b9 , #2c3e50);" +
        "-fx-background-radius: 30;"
        +"-fx-background-insets: 0;" +
        "-fx-text-fill: white;"
        );


        // Level check-box is false by default
        enterLvl.setVisible(false);
        enterRSN.requestFocus();
    }

    /**
     * Handles button click.
     * @param actionEvent
     * @throws IOException
     * @throws InterruptedException
     */
    public void calculate(ActionEvent actionEvent) throws IOException, InterruptedException {
        int startXp = 0;
        int goalXp = 0;

        if (Objects.equals(goalXpField.getText(), "")) {
            goalXpField.setText("");
            goalXpField.setStyle(
                    "-fx-focus-color: red ; -fx-control-inner-background: #FFD0D0; -fx-text-box-border: red;"
            );
            goalXpField.setPromptText("Goal must be non-zero");

            goalXpField.setOnMouseClicked(e -> { goalXpField.setStyle("-fx-text-box-border: blue ; -fx-focus-color: blue; -fx-control-inner-background: #8e9eab;");
                goalXpField.setPromptText("Goal XP or Level");enterRSN.setStyle("-fx-text-box-border: blue ; -fx-focus-color: blue; -fx-control-inner-background: #8e9eab;");
                enterRSN.setPromptText("RSN");enterLvl.setStyle("-fx-text-box-border: blue ; -fx-focus-color: blue; -fx-control-inner-background: #8e9eab;");
                enterLvl.setPromptText("Goal XP or Level");});
            mainPane.requestFocus();
        }

        if (Objects.equals(enterLvl.getText(), "")) {
            enterLvl.setText("");
            enterLvl.setStyle(
                    "-fx-focus-color: red ; -fx-control-inner-background: #FFD0D0; -fx-text-box-border: red;"
            );
            enterLvl.setPromptText("XP must be non-zero");

            enterLvl.setOnMouseClicked(e -> { enterLvl.setStyle("-fx-text-box-border: blue ; -fx-focus-color: blue; -fx-control-inner-background: #8e9eab;");
                enterLvl.setPromptText("Goal XP or Level");goalXpField.setStyle("-fx-text-box-border: blue ; -fx-focus-color: blue; -fx-control-inner-background: #8e9eab;");
                goalXpField.setPromptText("Goal XP or Level");});
            mainPane.requestFocus();
        }

        if (!(goalXpField.getText().length() > 9)) {
            if (!enterLvl.getText().equals("") || Integer.parseInt(goalXpField.getText()) <= 200_000_000) {
                goalXp = Integer.parseInt(goalXpField.getText());
            }
        }
        else if (enterLvl.getText().length() > 9) {
            enterLvl.setText("");
            enterLvl.setStyle(
                    "-fx-focus-color: red ; c #FFD0D0; -fx-text-box-border: red;"
            );
            enterLvl.setPromptText("Start XP too high");

            enterLvl.setOnMouseClicked(e -> {
                enterLvl.setStyle("-fx-text-box-border: blue ; -fx-focus-color: blue; -fx-control-inner-background: #8e9eab;");
                enterLvl.setPromptText("XP or Level");
                goalXpField.setStyle("-fx-text-box-border: blue ; -fx-focus-color: blue; -fx-control-inner-background: #8e9eab;");
                goalXpField.setPromptText("Goal XP or Level");
            });
            mainPane.requestFocus();
        }
        if (goalXpField.getText().length() > 9) {
            goalXpField.setText("");
            goalXpField.setStyle(
                    "-fx-focus-color: red ; -fx-control-inner-background: #FFD0D0; -fx-text-box-border: red;"
            );
            goalXpField.setPromptText("Goal XP too high");

            goalXpField.setOnMouseClicked(e -> {
                goalXpField.setStyle("-fx-text-box-border: blue ; -fx-focus-color: blue; -fx-control-inner-background: #8e9eab;");
                goalXpField.setPromptText("Goal XP or Level");
                enterLvl.setStyle("-fx-text-box-border: blue ; -fx-focus-color: blue; -fx-control-inner-background: #8e9eab;");
                enterLvl.setPromptText("XP or Level");
                enterRSN.setStyle("-fx-text-box-border: blue ; -fx-focus-color: blue; -fx-control-inner-background: #8e9eab;");
                enterRSN.setPromptText("RSN");
            });
            mainPane.requestFocus();
        }

        int input;
        int[] testFail = {-1};

        if (goalXp <= 120 && goalXp != 0) {
            goalXp = BackEnd.getXP(goalXp);
            System.out.println(goalXp);
        }

        if (rsnSelection.isSelected()) {
            testFail = BackEnd.getDGData(enterRSN.getText());
            if (testFail[0] == -1) {
                enterRSN.setText("");
                enterRSN.setStyle(
                        "-fx-focus-color: red ; -fx-control-inner-background: #FFD0D0; -fx-text-box-border: red;"
                );
                enterRSN.setPromptText("RSN not found; Use XP");

                enterRSN.setOnMouseClicked(e -> { enterRSN.setStyle("-fx-text-box-border: blue ; -fx-focus-color: blue; -fx-control-inner-background: #8e9eab;");
                enterRSN.setPromptText("RSN");goalXpField.setStyle("-fx-text-box-border: blue ; -fx-focus-color: blue; -fx-control-inner-background: #8e9eab;");
                    goalXpField.setPromptText("Goal XP or Level");});
                mainPane.requestFocus();

            } else if (goalXp > testFail[1]) {
                floors = BackEnd.calculate(testFail[1], goalXp);
                System.out.println(floors[4]);
                gainedXp = goalXp-testFail[1];
            } else {
                System.out.println(goalXp + " " + testFail[1]);
                goalXpField.setText("");
                goalXpField.setStyle(
                        "-fx-focus-color: red ; -fx-control-inner-background: #FFD0D0; -fx-text-box-border: red;"
                );
                goalXpField.setPromptText("Invalid Goal");

                goalXpField.setOnMouseClicked(e -> { goalXpField.setStyle("-fx-text-box-border: blue ; -fx-focus-color: blue; -fx-control-inner-background: #8e9eab");
                    goalXpField.setPromptText("Goal XP or Level");enterRSN.setStyle("-fx-text-box-border: blue ; -fx-focus-color: blue; -fx-control-inner-background: #8e9eab");
                    enterRSN.setPromptText("RSN");});
                mainPane.requestFocus();

                enterRSN.setOnMouseClicked(e -> { enterRSN.setStyle("-fx-text-box-border: blue ; -fx-focus-color: blue; -fx-control-inner-background: #8e9eab;");
                    enterRSN.setPromptText("RSN");goalXpField.setStyle("-fx-text-box-border: blue ; -fx-focus-color: blue; -fx-control-inner-background: #8e9eab;");
                    goalXpField.setPromptText("Goal XP or Level");});
                mainPane.requestFocus();
            }
        }
        if (xpLvlSelection.isSelected()) {
            testFail[0] = 0;
            if (!enterLvl.getText().equals("") && enterLvl.getText().length() <= 9) {
                input = Integer.parseInt(enterLvl.getText());
                if (input == 0) {
                    startXp = 0;
                } else if (input <= 120) {
                    startXp = BackEnd.getXP(input);
                    System.out.println(startXp);
                } else if (input > 120) {
                    startXp = input;
                } else
                    startXp = 0;

                if (goalXp > 200_000_000) {
                    goalXp = 200_000_000;
                    goalXpField.setText("" + goalXp);
                }
                if (startXp > 200_000_000) {
                    startXp = 200_000_000;
                    enterLvl.setText("" + startXp);
                }
            }
            if (goalXp > startXp) {
                System.out.println(startXp + " " + goalXp);
                floors = BackEnd.calculate(startXp, goalXp);
                gainedXp = goalXp-startXp;
            }
            else if (goalXp < startXp) {
                goalXpField.setText("");
                goalXpField.setStyle(
                        "-fx-focus-color: red ; -fx-control-inner-background: #FFD0D0; -fx-text-box-border: red;"
                );
                goalXpField.setPromptText("Invalid Goal");

                goalXpField.setOnMouseClicked(e -> {
                    goalXpField.setStyle("-fx-text-box-border: blue ; -fx-focus-color: blue; -fx-control-inner-background: #8e9eab");
                    goalXpField.setPromptText("Goal XP or Level");
                });
                enterLvl.setOnMouseClicked(e -> {
                    goalXpField.setStyle("-fx-text-box-border: blue ; -fx-focus-color: blue; -fx-control-inner-background: #8e9eab");
                    goalXpField.setPromptText("Goal XP or Level");
                });
                mainPane.requestFocus();
            }
        }

        int tokens = 0;

        if (floors != null) {
            tokens = floors[8] / 10;

            totalTime = (floors[0] + floors[1] + floors[2] + floors[3] + floors[4] + floors[5] + floors[6]) * 9;
            hours = totalTime / 60;
            mins = totalTime - hours * 60;
            System.out.println(testFail[0]);
        }
        if (testFail[0] != -1 && (!enterLvl.getText().equals("") || !enterRSN.getText().equals("")) && !goalXpField.getText().equals("")) {
            // Label cost accordingly
            if (nonAfkC1Selection.isSelected()) {
                cost = (int) ((floors[0] + floors[1] + floors[2]) * 1.5 + floors[3] * 1.75 + floors[4] * 2 + floors[5] * 2.5 + floors[6] * 2.75 + floors[7] * 4.35);
                costLabel.setText("Cost: " + DecimalFormat.getNumberInstance().format(cost) + "M");
                tokensLabel.setText("Tokens: " + DecimalFormat.getNumberInstance().format(tokens));
                xpGainedLabel.setText("Gained XP\n" + DecimalFormat.getNumberInstance().format(gainedXp));
                timeLabel.setText("Estimated Time\n" + hours + " hrs " + mins + " mins");
            } else if (afkC1Selection.isSelected()) {
                cost = (int) ((floors[0] + floors[1] + floors[2]) * 1.5 + floors[3] * 1.75 + floors[4] * 2 + floors[5] * 2.5 + floors[6] * 2.75 + floors[7] * 8.7);
                costLabel.setText("Cost: " + DecimalFormat.getNumberInstance().format(cost) + "M");
                tokensLabel.setText("Tokens: " + DecimalFormat.getNumberInstance().format(tokens));
                xpGainedLabel.setText("Gained XP\n" + DecimalFormat.getNumberInstance().format(gainedXp));
                timeLabel.setText("Estimated Time\n" + hours + " hrs " + mins + " mins");
            } else if (c1Skipped.isSelected()){
                cost = (int) ((floors[0] + floors[1] + floors[2]) * 1.5 + floors[3] * 1.75 + floors[4] * 2 + floors[5] * 2.5 + floors[6] * 2.75 + (tokens-floors[7]*313_200 < 0 ? (-1*(tokens-floors[7]*313_200))/313_200 : 0)*4.35);
                costLabel.setText("Cost: " + DecimalFormat.getNumberInstance().format(cost) + "M");
                xpGainedLabel.setText("Gained XP\n" + DecimalFormat.getNumberInstance().format(gainedXp));
                timeLabel.setText("Estimated Time\n" + hours + " hrs " + mins + " mins");
                if ((tokens-floors[7]*313_200) > 0)
                    tokensLabel.setText("Tokens: " + DecimalFormat.getNumberInstance().format((tokens - floors[7]*313_200)));
                else
                    tokensLabel.setText("Tokens: " + DecimalFormat.getNumberInstance().format(0));
            }
        }
    }

    /**
     * Handles all click events.
     * @param actionEvent - event generated by click
     */
    public void onClick(ActionEvent actionEvent) {
        // Switch from Xp to RSN accordingly
        if (actionEvent.getSource() == xpLvlSelection) {
            enterRSN.setStyle("-fx-text-box-border: blue ; -fx-focus-color: blue; -fx-control-inner-background: #8e9eab");
            enterLvl.setStyle("-fx-text-box-border: blue ; -fx-focus-color: blue; -fx-control-inner-background: #8e9eab");
            goalXpField.setStyle("-fx-text-box-border: blue ; -fx-focus-color: blue; -fx-control-inner-background: #8e9eab");
            enterRSN.setPromptText("RSN");
            enterLvl.setPromptText("Level or XP");
            goalXpField.setPromptText("Goal XP or Level");
            enterLvl.clear();
            goalXpField.clear();
            enterRSN.setVisible(false);
            enterLvl.setVisible(true);
            enterLvl.requestFocus();
        }

        // Use RSN to do the calculations
        if (actionEvent.getSource() == rsnSelection) {
            enterRSN.setPromptText("RSN");
            enterLvl.setPromptText("Level or XP");
            goalXpField.setPromptText("Goal XP or Level");
            enterRSN.setStyle("-fx-text-box-border: blue ; -fx-focus-color: blue; -fx-control-inner-background: #8e9eab");
            enterLvl.setStyle("-fx-text-box-border: blue ; -fx-focus-color: blue; -fx-control-inner-background: #8e9eab");
            goalXpField.setStyle("-fx-text-box-border: blue ; -fx-focus-color: blue; -fx-control-inner-background: #8e9eab");
            enterRSN.clear();
            goalXpField.clear();
            enterLvl.setVisible(false);
            enterRSN.setVisible(true);
            enterRSN.requestFocus();
        }

        int tokens = 0;

        if (floors != null)
            tokens = floors[8] / 10;

        // Dynamically change tokens / cost based on type of C1's
        if ((actionEvent.getSource() == nonAfkC1Selection || actionEvent.getSource() == afkC1Selection || actionEvent.getSource() == c1Skipped) && floors != null ) {
            if (nonAfkC1Selection.isSelected()) {
                cost = (int) ((floors[0] + floors[1] + floors[2]) * 1.5 + floors[3] * 1.75 + floors[4] * 2 + floors[5] * 2.35 + floors[6] * 2.6 + floors[7] * 4.35);
                costLabel.setText("Cost: " + DecimalFormat.getNumberInstance().format(cost) + "M");
                tokensLabel.setText("Tokens: " + DecimalFormat.getNumberInstance().format(tokens));
                xpGainedLabel.setText("Gained XP\n" + DecimalFormat.getNumberInstance().format(gainedXp));
                timeLabel.setText("Estimated Time\n" + hours + " hrs " + mins + " mins");
            } else if (afkC1Selection.isSelected()) {
                cost = (int) ((floors[0] + floors[1] + floors[2]) * 1.5 + floors[3] * 1.6 + floors[4] * 1.9 + floors[5] * 2. + floors[6] * 2.75 + floors[7] * 8.7);
                costLabel.setText("Cost: " + DecimalFormat.getNumberInstance().format(cost) + "M");
                tokensLabel.setText("Tokens: " + DecimalFormat.getNumberInstance().format(tokens));
                xpGainedLabel.setText("Gained XP\n" + DecimalFormat.getNumberInstance().format(gainedXp));
                timeLabel.setText("Estimated Time\n" + hours + " hrs " + mins + " mins");
            } else if (c1Skipped.isSelected()){
                cost = (int) ((floors[0] + floors[1] + floors[2]) * 1.5 + floors[3] * 1.75 + floors[4] * 2 + floors[5] * 2.5 + floors[6] * 2.75 + (tokens-floors[7]*313_200 < 0 ? (int)Math.floor((-1*(tokens-floors[7]*313_200))/313_200) : 0)*4.35);
                costLabel.setText("Cost: " + DecimalFormat.getNumberInstance().format(cost) + "M");
                xpGainedLabel.setText("Gained XP\n" + DecimalFormat.getNumberInstance().format(gainedXp));
                timeLabel.setText("Estimated Time\n" + hours + " hrs " + mins + " mins");
                if ((tokens-floors[7]*313_200) > 0)
                    tokensLabel.setText("Tokens: " + DecimalFormat.getNumberInstance().format((tokens - floors[7]*313_200)));
                else
                    tokensLabel.setText("Tokens: " + DecimalFormat.getNumberInstance().format(0));
            }
        }
    }

    public void onEnter(ActionEvent actionEvent) throws IOException, InterruptedException {

        // Use enter for various things for ease.
        if (actionEvent.getSource() == goalXpField)
            calculate(actionEvent);
        if (actionEvent.getSource() == enterLvl)
            goalXpField.requestFocus();
        if (actionEvent.getSource() == enterRSN)
            goalXpField.requestFocus();
    }
}
