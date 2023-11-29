package org.pradeep;

import java.util.Random;
import java.util.Scanner;

//TicTacToe class representing the game board and logic
class TicTocToe {
 static char[][] board;

 // Constructor initializes the game board and displays it
 public TicTocToe() {
     board = new char[3][3];
     initBoard();
     display();
 }

 // Initializes the game board with empty spaces
 public static void initBoard() {
     for (int i = 0; i < board.length; i++) {
         for (int j = 0; j < board[i].length; j++) {
             board[i][j] = ' ';
         }
     }
 }

 // Checks for a win in any column
 public static boolean checkColWin() {
     for (int j = 0; j <= 2; j++) {
         if (board[0][j] != ' ' && board[0][j] == board[1][j] && board[1][j] == board[2][j]) {
             return true;
         }
     }
     return false;
 }

 // Checks for a win in any row
 public static boolean checkRowWin() {
     for (int i = 0; i <= 2; i++) {
         if (board[i][0] != ' ' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
             return true;
         }
     }
     return false;
 }

 // Checks for a win in any diagonal
 public static boolean checDiag() {
     if ((board[0][0] != ' ' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) ||
         (board[0][2] != ' ' && board[0][2] == board[1][1] && board[1][1] == board[2][0])) {
         return true;
     } else {
         return false;
     }
 }

 // Displays the current state of the game board
 public static void display() {
     System.out.println("———————");
     for (int i = 0; i < board.length; i++) {
         System.out.print("|");
         for (int j = 0; j < board[i].length; j++) {
             System.out.print(board[i][j] + "|");
         }
         System.out.println();
         System.out.println("———————");
     }
 }

 // Places the player's mark on the board if the position is valid
 static void placeMark(int i, int j, char ch) {
     if (i >= 0 && i <= 2 && j >= 0 && j <= 2) {
         board[i][j] = ch;
     } else {
         System.out.println("Invalid Position");
     }
 }

 // Checks if the game is a draw
 static boolean checkDraw() {
     for (int i = 0; i <= 2; i++) {
         for (int j = 0; j <= 2; j++) {
             if (board[i][j] == ' ') {
                 return false;
             }
         }
     }
     return true;
 }
}

//Abstract Player class with common attributes and an abstract method for making a move
abstract class Player {
 String name;
 char symbol;

 public abstract void makeMove();

 // Checks if the move is valid within the bounds and the cell is empty
 boolean isValidMove(int i, int j) {
     if (i >= 0 && i <= 2 && j >= 0 && j <= 2) {
         if (TicTocToe.board[i][j] == ' ')
             return true;
     }
     return false;
 }
}

//HumanPlayer class representing a player controlled by a human
class HumanPlayer extends Player {
 public HumanPlayer(String name, char symbol) {
     this.name = name;
     this.symbol = symbol;
 }

 // Takes input from the user for the row and column to make a move
 public void makeMove() {
     Scanner sc = new Scanner(System.in);
     int row;
     int col;
     do {
         System.out.println("Please enter the row and column numbers separated by space:");
         row = sc.nextInt();
         col = sc.nextInt();
     } while (isValidMove(row, col) == false);
     TicTocToe.placeMark(row, col, symbol);
 }
}

//AIPlayer class representing a player controlled by the computer
class AIPlayer extends Player {
 public AIPlayer(String name, char symbol) {
     this.name = name;
     this.symbol = symbol;
 }

 // Generates random row and column values for the AI's move
 public void makeMove() {
     int row;
     int col;
     do {
         Random r = new Random();
         row = r.nextInt(3);
         col = r.nextInt(3);
     } while (isValidMove(row, col) == false);
     TicTocToe.placeMark(row, col, symbol);
 }
}

//Main class to launch and play the TicTacToe game
public class LaunchGame {
 public static void main(String[] args) {
     TicTocToe t = new TicTocToe();

     HumanPlayer p1 = new HumanPlayer("Pradeep", 'X');
     AIPlayer p2 = new AIPlayer("Robo", 'O');
     Player cp;
     cp = p1;

     // Game loop for alternating turns between players
     while (true) {
         System.out.println("It's " + cp.name + "'s turn:");
         cp.makeMove();
         TicTocToe.display();

         // Check for a win or draw after each move
         if (TicTocToe.checDiag() || TicTocToe.checkColWin() || TicTocToe.checkRowWin()) {
             System.out.println(cp.name + " has won");
             return;
         } else if (TicTocToe.checkDraw()) {
             System.out.println("Game is a draw");
             break;
         } else {
             // Switch to the other player for the next turn
             if (cp == p1) {
                 cp = p2;
             } else {
                 cp = p1;
             }
         }
     }
 }
}
