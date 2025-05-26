/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Spring 2025
 *
 * Name: Aiden Kim and Andrew Bond
 * Date: 3/19/25
 * Time: 3:25 PM
 *
 * Project: csci205_hw
 * Package: org.ABAK
 * Class: GameManager
 *
 * Description:
 *
 * ****************************************
 */

package org.ABAK;

import java.util.Scanner;

/**
 * GameManager is a class that manages the game flow for the Mastermind game.
 * It initializes the CodeMaker, CodeBreaker, and Board instances,
 * and handles user input and game logic.
 *
 * @Author Aiden Kim and Andrew Bond
 */
public class GameManager {
    private CodeMaker codeMaker;
    private CodeBreaker codeBreaker;
    private Board board;
    private final int MAX_GUESSES = 12;
    private final int CODE_LENGTH = 4;
    private final int COLOR_COUNT = 6;
    private boolean gameActive;

    /**
     * Constructor for the GameManager class.
     * Initializes the CodeMaker, CodeBreaker, and Board instances.
     *
     * @Author Aiden Kim and Andrew Bond
     */
    public GameManager() {
        this.codeMaker = new CodeMaker(CODE_LENGTH, COLOR_COUNT);
        this.board = new Board();
        this.codeBreaker = new CodeBreaker();
        this.gameActive = false;
    }

    /**
     * The main method that runs the game.
     * It generates a new code, clears the board, and starts the guessing process.
     *
     * @Author Aiden Kim and Andrew Bond
     */
    public void startGame() {
        gameActive = true;
        codeMaker.generateNewCode();
        board.clearBoard();

        System.out.println("Guess my code, using numbers between 1 and "
                + COLOR_COUNT + ". You have " + MAX_GUESSES + " guesses.");

        int guessCount = 1;
        // Generate a random guess
        while (gameActive && guessCount <= MAX_GUESSES) {
            System.out.print("Guess " + guessCount + ": ");
            String guess = codeBreaker.makeGuess();
            // Check if the guess is valid
            if (isValidGuess(guess)) {
                board.placeCodePegs(guess);

                String score = codeMaker.evaluateGuess(guess);
                board.placeScoringPegs(score);

                System.out.print(guess + " --> " + score);

                if (score.equals("****")) {
                    System.out.println("YOU WON! You guessed the code in "
                            + guessCount + " moves.");
                    gameActive = false;
                } else if (guessCount == MAX_GUESSES) {
                    System.out.println(" Game over! You've used all your guesses. The code was: "
                            + codeMaker.getSecretCode());
                    gameActive = false;
                } else {
                    System.out.println(" Try again. " + (MAX_GUESSES - guessCount)
                            + " guesses left.");
                    guessCount++;
                }
            } else {
                System.out.println("Invalid guess. Please enter " + CODE_LENGTH
                        + " digits between 1 and " + COLOR_COUNT + ".");
            }
        }

        offerNewGame();
    }

    /**
     * Checks if the guess is valid.
     * A valid guess is a string of digits of length CODE_LENGTH,
     * with each digit between 1 and COLOR_COUNT.
     *
     * @param guess The guess to validate
     * @return true if the guess is valid, false otherwise
     * @Author Aiden Kim and Andrew Bond
     */
    private boolean isValidGuess(String guess) {
        if (guess.length() != CODE_LENGTH) {
            return false;
        }

        for (char c : guess.toCharArray()) {
            if (guess.length() != CODE_LENGTH) {
                return false;
            }
        }

        for (char c : guess.toCharArray()) {
            if (c < '1' || c > (char) ('0' + COLOR_COUNT)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Offers the player a chance to play again after the game ends.
     * Asks for user input and starts a new game if the user agrees.
     *
     * @Author Aiden Kim and Andrew Bond
     */
    private void offerNewGame() {
        System.out.print("Would you like to play again? [Y/N]: ");
        Scanner scanner = new Scanner(System.in);
        String response = scanner.nextLine().trim().toUpperCase();

        if (response.equals("Y")) {
            startGame();
        } else {
            System.out.println("Thanks for playing!");
        }

    }

}



