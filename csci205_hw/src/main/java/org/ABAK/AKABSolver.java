/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Spring 2025
 *
 * Name: Aiden Kim and Andrew Bond
 * Date: 3/31/25
 * Time: 9:54 PM
 *
 * Project: csci205_hw
 * Package: org.ABAK
 * Class: AKABSolver
 *
 * Description:
 *
 * ****************************************
 */

package org.ABAK;

import java.util.ArrayList;

/**
 * AKABSolver is a class that implements the AKAB (Aiden Kim and Andrew Bond) solver for the game.
 * It generates a new code, evaluates guesses, and keeps track of the game state.
 * The solver uses a brute-force approach to find the correct code.
 *
 * @Author Aiden Kim, Andrew Bond
 */
public class AKABSolver {
    private CodeMaker codeMaker;
    private Board board;
    private final int CODE_LENGTH = 4;
    private final int COLOR_COUNT = 6;
    private boolean gameActive;

    /**
     * Constructor for the AKABSolver class.
     * Initializes the CodeMaker and Board instances.
     * @Author Aiden Kim, Andrew Bond
     */
    public AKABSolver() {
        this.codeMaker = new CodeMaker(CODE_LENGTH, COLOR_COUNT);
        this.board = new Board();
        this.gameActive = false;
    }

    /**
     * The main method that runs the solver.
     * It generates a new code, clears the board, and starts the guessing process.
     * @Author Aiden Kim, Andrew Bond
     */
    public void solve() {
        gameActive = true;
        codeMaker.generateNewCode();
        board.clearBoard();
        ArrayList<String> potentialGuesses = new ArrayList<>();

        int guessCount = 1;

        for (int i = 1; i <= 6; i++) {
            String guess = String.valueOf(i);
            String fullGuess = guess + guess + guess + guess;
            board.placeCodePegs(fullGuess);

            String score = codeMaker.evaluateGuess(fullGuess);
            board.placeScoringPegs(score);

            if (score.equals("****")) {
                System.out.println("YOU WON! You guessed the code in " + guessCount + " moves.");
                gameActive = false;
                guessCount++;
                break;
            } else if (score.equals("***-")) {
                potentialGuesses.add(String.valueOf(i));
                potentialGuesses.add(String.valueOf(i));
                potentialGuesses.add(String.valueOf(i));
                guessCount++;
            } else if (score.equals("**--")) {
                potentialGuesses.add(String.valueOf(i));
                potentialGuesses.add(String.valueOf(i));
                guessCount++;
            } else if (score.equals("*---")) {
                potentialGuesses.add(String.valueOf(i));
                guessCount++;
            }

        }
        while (gameActive) {
            ArrayList<String> permutations = generatePermutations(potentialGuesses);
            for (String guess : permutations) {
                board.placeCodePegs(guess);

                String score = codeMaker.evaluateGuess(guess);
                board.placeScoringPegs(score);

                if (score.equals("****")) {
                    System.out.println("YOU WON! You guessed the code in "
                            + guessCount + " moves.");
                    gameActive = false;
                    break;
                } else {
                    guessCount++;
                }
            }

        }
    }


    /**
     * Generates all possible permutations of the given list of strings.
     * This method is used to generate all possible guesses based on the potential digits.
     *
     * @param list The list of strings to generate permutations from.
     * @return An ArrayList containing all possible permutations.
     * @Author Aiden Kim, Andrew Bond
     */
    public ArrayList<String> generatePermutations(ArrayList<String> list) {
        ArrayList<String> permutations = new ArrayList<>();
        int n = list.size();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 0; k < n; k++) {
                    for (int l = 0; l < n; l++) {
                        String permutation = list.get(i) + list.get(j) + list.get(k) + list.get(l);
                        permutations.add(permutation);
                    }
                }
            }
        }
        return permutations;
    }

    /**
     * Runs a specified number of simulations of the AKAB solver.
     * It generates a new code, evaluates guesses,
     * and keeps track of the number of guesses needed to solve the code.
     *
     * @param simCount The number of simulations to run.
     * @Author Aiden Kim, Andrew Bond
     */
    public void runSimulations(int simCount) {
        int totalGuesses = 0;
        int minGuesses = Integer.MAX_VALUE;
        int maxGuesses = 0;

        // Run multiple simulations to gather statistics on the random guessing strategy
        System.out.println("Running " + simCount + " simulations of AKAB Solver...");

        long startTime = System.currentTimeMillis();
        // Initialize the game state
        for (int i = 0; i < simCount; i++) {
            gameActive = true;
            codeMaker.generateNewCode();
            board.clearBoard();
            ArrayList<String> potentialDigits = new ArrayList<>();

            int guessCount = 1;
            // Generate the first guess
            for (int j = 1; j <= 6; j++) {
                String guess = String.valueOf(j);
                String fullGuess = guess + guess + guess + guess;
                board.placeCodePegs(fullGuess);

                String score = codeMaker.evaluateGuess(fullGuess);
                board.placeScoringPegs(score);

                if (score.equals("****")) {
                    gameActive = false;
                    break;
                } else if (score.equals("***-")) {
                    potentialDigits.add(String.valueOf(j));
                    potentialDigits.add(String.valueOf(j));
                    potentialDigits.add(String.valueOf(j));
                    guessCount++;
                } else if (score.equals("**--")) {
                    potentialDigits.add(String.valueOf(j));
                    potentialDigits.add(String.valueOf(j));
                    guessCount++;
                } else if (score.equals("*---")) {
                    potentialDigits.add(String.valueOf(j));
                    guessCount++;
                } else {
                    guessCount++;
                }
            }
            // Generate all possible permutations of the potential digits
            while (potentialDigits.size() < 4) {
                if (!potentialDigits.isEmpty()) {
                    potentialDigits.add(potentialDigits.get(0));
                } else {
                    potentialDigits.add("1");
                }
            }
            // Limit the size of potential digits to 4
            while (potentialDigits.size() > 4) {
                potentialDigits.remove(potentialDigits.size() - 1);
            }

            if (gameActive) {
                ArrayList<String> permutations = generatePermutations(potentialDigits);

                for (String permutation : permutations) {
                    board.placeCodePegs(permutation);
                    String score = codeMaker.evaluateGuess(permutation);
                    board.placeScoringPegs(score);

                    guessCount++;

                    if (score.equals("****")) {
                        gameActive = false;
                        break;
                    }
                }
            }

            totalGuesses += guessCount - 1;

            if (guessCount - 1 < minGuesses) {
                minGuesses = guessCount - 1;
            }
            if (guessCount - 1 > maxGuesses) {
                maxGuesses = guessCount - 1;
            }

        }


        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        double totalTimeInSeconds = totalTime / 1000.0;

        double averageGuesses = (double) totalGuesses / simCount;
        System.out.println("\nSimulation results:");
        System.out.println("Average guesses needed: " + averageGuesses);
        System.out.println("Minimum guesses needed: " + minGuesses);
        System.out.println("Maximum guesses needed: " + maxGuesses);
        System.out.println("Total execution time: " + totalTimeInSeconds + " seconds");
    }
}

