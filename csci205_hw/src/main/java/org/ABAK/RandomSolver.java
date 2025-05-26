/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Spring 2025
 *
 * Name: Aiden Kim and Andrew aBond
 * Date: 3/31/25
 * Time: 8:45 PM
 *
 * Project: csci205_hw
 * Package: org.ABAK
 * Class: RandomSolver
 *
 * Description:
 *
 * ****************************************
 */

package org.ABAK;

import java.util.Random;

/**
 * RandomSolver is a class that simulates a random guessing strategy
 * for the Mastermind game.
 * It generates random guesses and evaluates them against the
 * secret code until the code is guessed correctly.
 */
public class RandomSolver {
    private CodeMaker codeMaker;
    private CodeBreaker codeBreaker;
    private Board board;
    private final int CODE_LENGTH = 4;
    private final int COLOR_COUNT = 6;
    private boolean gameActive;
    private Random random;

    /**
     * Constructor for the RandomSolver class.
     * Initializes the CodeMaker, CodeBreaker, and Board instances.
     *
     * @Author Aiden Kim and Andrew Bond
     */
    public RandomSolver() {
        this.codeMaker = new CodeMaker(CODE_LENGTH, COLOR_COUNT);
        this.board = new Board();
        this.codeBreaker = new CodeBreaker();
        this.gameActive = false;
        this.random = new Random();
    }

    /**
     * The main method that runs the solver.
     * It generates a new code, clears the board, and starts the guessing process.
     *
     * @Author Aiden Kim and Andrew Bond
     */
    public void solve() {
        gameActive = true;
        codeMaker.generateNewCode();
        board.clearBoard();

        int guessCount = 1;

        // Generate a random guess
        while (gameActive) {
            String guess = generateRandomGuess();

            board.placeCodePegs(guess);

            String score = codeMaker.evaluateGuess(guess);
            board.placeScoringPegs(score);

            if (score.equals("****")) {

                gameActive = false;
            }

            guessCount++;
        }


    }

    /**
     * Generates a random guess of the specified length using digits between 1 and COLOR_COUNT.
     *
     * @return A string representing the random guess.
     * @Author Aiden Kim and Andrew Bond
     */
    private String generateRandomGuess() {
        // Generate a random guess of the specified length
        StringBuilder guess = new StringBuilder();
        for (int i = 0; i < CODE_LENGTH; i++) {
            int color = random.nextInt(COLOR_COUNT) + 1;
            guess.append(color);
        }
        return guess.toString();
    }

    /**
     * Runs multiple simulations to gather statistics on the random guessing strategy.
     *
     * @param simCount The number of simulations to run.
     * @Author Aiden Kim and Andrew Bond
     */
    public void runSimulations(int simCount) {
        // Run multiple simulations to gather statistics on the random guessing strategy
        int totalGuesses = 0;
        int maxGuesses = 0;
        int minGuesses = Integer.MAX_VALUE;

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < simCount; i++) {
            codeMaker.generateNewCode();
            board.clearBoard();

            int guessCount = 1;
            boolean solved = false;

            while (!solved) {
                String guess = generateRandomGuess();
                board.placeCodePegs(guess);

                String score = codeMaker.evaluateGuess(guess);
                board.placeScoringPegs(score);

                if (score.equals("****")) {
                    totalGuesses += guessCount;
                    solved = true;

                    if (guessCount < minGuesses) {
                        minGuesses = guessCount;
                    }
                    if (guessCount > maxGuesses) {
                        maxGuesses = guessCount;
                    }
                }

                guessCount++;
            }
        }
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        double totalTimeInSeconds = totalTime / 1000.0;

        double averageGuesses = (double) totalGuesses / simCount;
        System.out.println("Total simulations: " + simCount);
        System.out.println("Average guesses to solve: " + averageGuesses);
        System.out.println("Minimum guesses to solve: " + minGuesses);
        System.out.println("Maximum guesses to solve: " + maxGuesses);
        System.out.println("Total time taken: " + totalTimeInSeconds + " seconds");

    }
}



