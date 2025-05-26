/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Spring 2025
 *
 * Name: Aiden Kim and Andrew Bond
 * Date: 3/19/25
 * Time: 3:28 PM
 *
 * Project: csci205_hw
 * Package: org.ABAK
 * Class: CodeBreaker
 *
 * Description:
 *
 * ****************************************
 */

package org.ABAK;

import java.util.Scanner;

/**
 * CodeBreaker is a class that handles user input for the Mastermind game.
 * It prompts the user for guesses and returns them as strings.
 * @Author Aiden Kim
 */
public class CodeBreaker {
    private Scanner scanner;

    /**
     * Constructor for the CodeBreaker class.
     * Initializes the scanner for user input.
     * @Author Aiden Kim
     */
    public CodeBreaker() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Prompts the user for a guess and returns it as a string.
     * The guess is trimmed of leading and trailing whitespace.
     * @return The user's guess as a string
     * @Author Aiden Kim
     */
    public String makeGuess() {
        return scanner.nextLine().trim();
    }

}