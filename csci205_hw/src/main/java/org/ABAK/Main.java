package org.ABAK;

import java.util.Scanner;

/**
 * Main is the entry point for the Mastermind game.
 * It allows the user to choose between Solver mode and Game mode.
 * In Solver mode, the user can run different solvers.
 * In Game mode, the user can play the game against the computer.
 *
 * @Author Aiden Kim and Andrew Bond
 */
public class Main {

    /**
     * The main method that runs the Mastermind game.
     * It prompts the user for the mode (Solver or Game) and starts the appropriate mode.
     *
     * @param args Command line arguments
     * @Author Aiden Kim and Andrew Bond
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Would you like to run Mastermind in Solver mode or Game mode?[0/1]: ");
        boolean valid = false;
        while (!valid) {
            if (in.hasNextInt()) {
                int mode = in.nextInt();
                if (mode == 0) {
                    boolean runagain = true;
                    while (runagain) {
                        System.out.println("Running in Solver mode...");
                        System.out.println("Would you like to run the RandomSolver,MinimaxSolver, "
                                + "AKABSolver, or end?[0/1/2/3]: ");
                        if (in.hasNextInt()) {
                            int solverMode = in.nextInt();
                            if (solverMode == 0) {
                                System.out.println("Running RandomSolver...");
                                RandomSolver randomSolver = new RandomSolver();
                                System.out.println("Enter the number of simulations to run: ");
                                int simCount = in.nextInt();
                                randomSolver.runSimulations(simCount);
                                randomSolver.solve();
                            } else if (solverMode == 1) {
                                System.out.println("Running MinimaxSolver...");
                                MiniMaxSolver minimaxSolver = new MiniMaxSolver();
                                System.out.println("Enter the number of simulations to run: ");
                                int simCount = in.nextInt();
                                minimaxSolver.runSimulations(simCount);
                            } else if (solverMode == 2) {
                                System.out.println("Running NewSolver...");
                                AKABSolver solver = new AKABSolver();
                                System.out.println("Enter the number of simulations to run: ");
                                int simCount = in.nextInt();
                                solver.runSimulations(simCount);

                            } else if (solverMode == 3) {
                                System.out.println("Exiting Solver mode...");
                                runagain = false;
                            } else {
                                System.out.println("Invalid input. Please enter 0 for "
                                        + "RandomSolver or 1 for MinimaxSolver.");
                            }
                        } else {
                            System.out.println("Invalid input. Please enter a number.");
                        }
                    }


                    valid = true;
                } else if (mode == 1) {
                    System.out.println("Running in Game mode...");
                    GameManager gameManager = new GameManager();
                    gameManager.startGame();
                    valid = true;
                } else {
                    System.out.println("Invalid input. Please enter"
                            + "or Solver mode or 1 for Game mode.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                in.next(); // Clear the invalid input
            }
        }

    }
}