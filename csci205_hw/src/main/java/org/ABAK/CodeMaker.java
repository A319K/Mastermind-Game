/* *****************************************
 * CSCI 205 - Software Engineering and Design
 * Spring 2025
 *
 * Name: Aiden Kim and Andrew Bond
 * Date: 3/19/25
 * Time: 3:27 PM
 *
 * Project: csci205_hw
 * Package: org.ABAK
 * Class: CodeMaker
 *
 * Description:
 *
 * ****************************************
 */

package org.ABAK;

import java.util.ArrayList;
import java.util.Random;

/**
 * CodeMaker is a class that generates a secret code for the Mastermind game.
 * It also evaluates guesses against the secret code and provides feedback.
 * The feedback includes exact matches (correct position and value)
 * and color matches (correct value, wrong position).
 * @Author Aiden Kim and Andrew Bond
 */
public class CodeMaker {
    private String secretCode;
    private final int codeLength;
    private final int colorCount;

    /**
     * Constructor for the CodeMaker class.
     * Initializes the code length and color count.
     * @param codeLength Length of the secret code
     * @param colorCount Number of colors available
     * @Author Aiden Kim and Andrew Bond
     */
    public CodeMaker(int codeLength, int colorCount) {
        this.codeLength = codeLength;
        this.colorCount = colorCount;
    }

    /**
     * Generates a new secret code using random colors.
     * The code is a string of digits representing colors.
     * @Author Aiden Kim and Andrew Bond
     */
    public void generateNewCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < codeLength; i++) {
            int color = random.nextInt(colorCount) + 1;
            code.append(color);
        }

        this.secretCode = code.toString();
    }

    /**
     * Returns the secret code.
     * @return The secret code as a string
     * @Author Aiden Kim and Andrew Bond
     */
    public String getSecretCode() {
        return secretCode;
    }

    /**
     * Evaluates a guess against the secret code.
     * Counts exact matches (correct position and value) and
     * color matches (correct value, wrong position).
     * Returns a string representing the score: '*'
     * for exact matches, '+' for color matches, '-' for no match.
     * @param guess The guess to evaluate
     * @return A string representing the score
     * @Author Aiden Kim and Andrew Bond
     */
    public String evaluateGuess(String guess) {
        int exactMatches = 0;
        int colorMatches = 0;

        // Arrays to track which positions have been matched
        boolean[] secretUsed = new boolean[codeLength];
        boolean[] guessUsed = new boolean[codeLength];

        // First pass: Count exact matches (correct position and value)
        for (int i = 0; i < codeLength; i++) {
            if (guess.charAt(i) == secretCode.charAt(i)) {
                exactMatches++;
                secretUsed[i] = true;
                guessUsed[i] = true;
            }
        }

        // Second pass: Count color matches (correct value, wrong position)
        for (int i = 0; i < codeLength; i++) {
            if (!guessUsed[i]) {  // Only check unmatched guess positions
                for (int j = 0; j < codeLength; j++) {
                    // Only check unmatched secret positions with matching value
                    if (!secretUsed[j] && guess.charAt(i) == secretCode.charAt(j)) {
                        colorMatches++;
                        secretUsed[j] = true;
                        break;  // Only match each guess digit once
                    }
                }
            }
        }

        // Build the score string in correct order: *, then +, then -
        StringBuilder score = new StringBuilder();
        for (int i = 0; i < exactMatches; i++) {
            score.append('*');
        }
        for (int i = 0; i < colorMatches; i++) {
            score.append('+');
        }
        for (int i = 0; i < codeLength - exactMatches - colorMatches; i++) {
            score.append('-');
        }

        return score.toString();
    }
}