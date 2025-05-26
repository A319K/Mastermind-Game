/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Spring 2025
 *
 * Name: Aiden Kim and Andrew Bond
 * Date: 3/31/25
 * Time: 9:26 PM
 *
 * Project: csci205_hw
 * Package: org.ABAK
 * Class: MiniMaxSolver
 *
 * Description:
 *
 * ****************************************
 */

package org.ABAK;

import java.util.ArrayList;
import java.util.List;

/**
 * MiniMaxSolver is a class that implements a solver
 * for the Mastermind game using the minimax algorithm.
 * It generates all possible codes and uses feedback
 * from the CodeMaker to narrow down the possibilities.
 *
 * @Author Aiden Kim, Andrew Bond
 */
public class MiniMaxSolver {
    private CodeMaker codeMaker;
    private Board board;
    private final int CODE_LENGTH = 4;
    private final int COLOR_COUNT = 6;
    private boolean gameActive;

    /**
     * Constructor for the MiniMaxSolver class.
     * Initializes the CodeMaker and Board instances.
     *
     * @Author Aiden Kim, Andrew Bond
     */
    public MiniMaxSolver() {
        this.codeMaker = new CodeMaker(CODE_LENGTH, COLOR_COUNT);
        this.board = new Board();
        this.gameActive = false;
    }

    /**
     * The main method that runs the solver.
     * It generates a new code, clears the board, and starts the guessing process.
     *
     * @Author Aiden Kim, Andrew Bond
     */
    public void solve() {
        gameActive = true;
        codeMaker.generateNewCode();
        board.clearBoard();

        List<String> possibleCodes = new ArrayList<>();
        generateAllPossibleCodes(possibleCodes);


        String guess = "1122";
        int guessCount = 1;

        // Start the guessing process

        while (gameActive) {

            board.placeCodePegs(guess);
            String score = codeMaker.evaluateGuess(guess);
            board.placeScoringPegs(score);

            // Print the guess and score

            if (score.equals("****")) {
                System.out.println("YOU WON! You guessed the code in " + guessCount + " moves.");
                gameActive = false;

                break;
            }
            // Filter out codes that would not give the same score
            List<String> newPossibleCodes = new ArrayList<>();
            for (String code : possibleCodes) {
                if (!code.equals(guess) && wouldGiveSameScore(guess, code, score)) {
                    newPossibleCodes.add(code);
                }
            }
            possibleCodes = newPossibleCodes;

            if (possibleCodes.size() == 1) {
                guess = possibleCodes.get(0);
            } else if (!possibleCodes.isEmpty()) {
                guess = possibleCodes.get(0);
            }

            guessCount++;
        }
    }

    /**
     * This method checks if a guess would give the same score as the correct score.
     * It compares the number of exact matches and color matches between the guess and the code.
     *
     * @param guess        The guessed code.
     * @param code         The actual code.
     * @param correctScore The correct score for the guess.
     * @return true if the guess would give the same score, false otherwise.
     * @Author Aiden Kim, Andrew Bond
     */
    private boolean wouldGiveSameScore(String guess, String code, String correctScore) {
        int exactMatches = 0;
        int colorMatches = 0;

        boolean[] codeUsed = new boolean[CODE_LENGTH];
        boolean[] guessUsed = new boolean[CODE_LENGTH];

        // First pass: Count exact matches (correct position and value)
        for (int i = 0; i < CODE_LENGTH; i++) {
            if (guess.charAt(i) == code.charAt(i)) {
                exactMatches++;
                codeUsed[i] = true;
                guessUsed[i] = true;
            }
        }
        // Second pass: Count color matches (correct value, wrong position)

        for (int i = 0; i < CODE_LENGTH; i++) {
            if (!guessUsed[i]) {
                for (int j = 0; j < CODE_LENGTH; j++) {
                    if (!codeUsed[j] && guess.charAt(i) == code.charAt(j)) {
                        colorMatches++;
                        codeUsed[j] = true;
                        break;
                    }
                }
            }
        }
        // Build the score string in correct order: *, then +, then -

        char[] scoreArray = new char[CODE_LENGTH];
        int index = 0;

        for (int i = 0; i < exactMatches; i++) {
            scoreArray[index++] = '*';
        }

        for (int i = 0; i < colorMatches; i++) {
            scoreArray[index++] = '+';
        }

        for (int i = 0; i < CODE_LENGTH - exactMatches - colorMatches; i++) {
            scoreArray[index++] = '-';
        }

        return new String(scoreArray).equals(correctScore);
    }

    /**
     * This method generates all possible codes based on the number of colors and code length.
     * It populates the provided list with all combinations of codes.
     *
     * @param possibleCodes The list to store all possible codes.
     * @Author Aiden Kim, Andrew Bond
     */
    public void generateAllPossibleCodes(List<String> possibleCodes) {
        // Generate all possible codes
        for (int i = 1; i <= COLOR_COUNT; i++) {
            for (int j = 1; j <= COLOR_COUNT; j++) {
                for (int k = 1; k <= COLOR_COUNT; k++) {
                    for (int l = 1; l <= COLOR_COUNT; l++) {
                        String code = "" + i + j + k + l;
                        possibleCodes.add(code);
                    }
                }
            }
        }
    }

    /**
     * This method runs a number of simulations to determine the
     * average, minimum, and maximum number of guesses
     * required to solve the code using the minimax algorithm.
     *
     * @param simCount The number of simulations to run.
     * @Author Aiden Kim, Andrew Bond
     */
    public void runSimulations(int simCount) {
        int totalGuesses = 0;
        int minGuesses = Integer.MAX_VALUE;
        int maxGuesses = 0;

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < simCount; i++) {
            gameActive = true;
            codeMaker.generateNewCode();
            board.clearBoard();

            List<String> possibleCodes = new ArrayList<>();
            // Generate all possible codes
            for (int j = 1; j <= COLOR_COUNT; j++) {
                for (int k = 1; k <= COLOR_COUNT; k++) {
                    for (int l = 1; l <= COLOR_COUNT; l++) {
                        for (int m = 1; m <= COLOR_COUNT; m++) {
                            String code = "" + j + k + l + m;
                            possibleCodes.add(code);
                        }
                    }
                }
            }

            String guess = "1122";
            int guessCount = 1;

            // Start the guessing process
            while (gameActive) {
                board.placeCodePegs(guess);
                String score = codeMaker.evaluateGuess(guess);
                board.placeScoringPegs(score);

                if (score.equals("****")) {
                    gameActive = false;

                    totalGuesses += guessCount;
                    if (guessCount < minGuesses) {
                        minGuesses = guessCount;
                    }
                    if (guessCount > maxGuesses) {
                        maxGuesses = guessCount;
                    }

                    break;

                }
                // Filter out codes that would not give the same score
                List<String> newPossibleCodes = new ArrayList<>();
                for (String code : possibleCodes) {
                    if (!code.equals(guess) && wouldGiveSameScore(guess, code, score)) {
                        newPossibleCodes.add(code);
                    }
                }

                possibleCodes = newPossibleCodes;

                // If there is only one possible code left, set it as the guess
                if (possibleCodes.size() == 1) {
                    guess = possibleCodes.get(0);
                } else if (!possibleCodes.isEmpty()) {
                    guess = possibleCodes.get(0);
                }

                guessCount++;
            }
        }
        // Calculate the total time taken for the simulations
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        double totalTimeInSeconds = totalTime / 1000.0;

        // Calculate the average number of guesses
        double averageGuesses = (double) totalGuesses / simCount;
        System.out.println("Total simulations: " + simCount);
        System.out.println("Average guesses to solve: " + averageGuesses);
        System.out.println("Minimum guesses to solve: " + minGuesses);
        System.out.println("Maximum guesses to solve: " + maxGuesses);
        System.out.println("Total time taken: " + totalTimeInSeconds + " seconds");
    }

}