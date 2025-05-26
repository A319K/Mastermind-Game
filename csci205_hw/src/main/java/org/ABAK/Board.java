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
 * Class: Board
 *
 * Description:
 *
 * ****************************************
 */

package org.ABAK;

import java.util.ArrayList;
import java.util.List;

/**
 * Board is a class that represents the game board for the Mastermind game.
 * It stores the code pegs and scoring pegs used in the game.
 * @Author Aiden Kim and Andrew Bond
 */
public class Board {
    private List<String> codePegs;
    private List<String> scoringPegs;

    /**
     * Constructor for the Board class.
     * Initializes the code pegs and scoring pegs lists.
     * @Author Aiden Kim
     */
    public Board() {
        this.codePegs = new ArrayList<>();
        this.scoringPegs = new ArrayList<>();
    }

    /**
     * Clears the board by resetting the code pegs and scoring pegs lists.
     * @Author Aiden Kim
     */
    public void clearBoard() {
        codePegs.clear();
        scoringPegs.clear();
    }

    /**
     * Places a guess on the board.
     * @param guess The guess to be placed
     * @Author Aiden Kim
     */
    public void placeCodePegs(String guess) {
        codePegs.add(guess);
    }

    /**
     * @Author Aiden Kim
     */
    public void placeScoringPegs(String score) {
        scoringPegs.add(score);
    }

    /**
     * Returns the list of scoring pegs.
     * @return The list of scoring pegs
     * @Author Aiden Kim
     */
    public List<String> getScoringPegs() {
        return scoringPegs;
    }
}