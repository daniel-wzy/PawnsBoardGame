Hello TAs! Here is the key information needed:
To Use JAR File
    - run the following command "java -jar PawnBoardGame.jar
     deckConfigFiles\example1.txt deckConfigFiles\example1.txt human human" for human vs. human.
     or "java -jar PawnBoardGame.jar
     deckConfigFiles\example1.txt deckConfigFiles\example1.txt human fillfirst" for
      human vs machine.
     or "java -jar PawnBoardGame.jar deckConfigFiles\example1.txt
     deckConfigFiles\example1.txt fillfirst fillfirst" for machine vs machine.

Overview
The PawnsBoard Game is a two-player card game where each player (Red or Blue) manages a board
composed of cells that may contain pawns or cards. Each card has a name, a cost, a value, and a 5×5
influence grid that determines how it affects surrounding cells when played. Pawns are used to
“pay” for cards, and card placement may add or convert pawns on the board. The game is played in
turns and scored by comparing the total value of cards placed in each row. The player with the
higher row score wins that row and accumulates those points toward their total score.

Changes for part 3:
- Added turn change and game over listeners support. These addListener methods allows an observer
  to subscribe to the model to receive associated notifications.

New classes designed:
    - Added ReplicablePawnsBoardController: Allows two controllers to run the same game. Acts as an
    obsever for both model and player actions
    - Added PlayerAction Listner: Listens for actions from player.
    - Added PlayerActions: an interface for players that can do things within the game.
    - Added HumanPlayer class that is a PlayerAction: a player that is controlled by a human.
    - Added MachinePlayer class: a machine that makes moves based on a strategy.

This project is organized into four primary components:
    Model – Implements the game’s logic and state (board setup, card mechanics, pawn interactions,
    scoring, and deck management).
    Controller – Processes user input (both keyboard and mouse) and coordinates updates between the
    model and the view.
    View – Provides both textual (console) and graphical (GUI) representations of the game using
    Java Swing.
    Strategies – Implements computer player strategies including a basic “Fill First” strategy and
    a more advanced “Maximize Row Score” strategy.

Quick Start
To Use JAR File
    - run the following command "java -jar PawnBoardGame.jar
     deckConfigFiles\example1.txt deckConfigFiles\example1.txt human human" for human vs. human.
     or "java -jar PawnBoardGame.jar
     deckConfigFiles\example1.txt deckConfigFiles\example1.txt human fillfirst" for
      human vs machine.
     or "java -jar PawnBoardGame.jar deckConfigFiles\example1.txt
     deckConfigFiles\example1.txt fillfirst fillfirst" for machine vs machine.

Console (Textual) Mode
To run a console-based simulation:
    1.	Open the PawnsBoard class.
    2.	Run its main method:

public static void main(String[] args) {
    IPawnsBoard game = new BasicPawnsBoard("deckConfigFiles" + File.separator + "example1.txt",
     "deckConfigFiles" + File.separator + "example1.txt",
            false, 5, new Random(0226), 5, 3);
    PawnsBoardTextView view = new BasicPawnsBoardTextView(game);
    view.render(System.out);
}

GUI Mode
To test the graphical interface:
	1.	Open the PawnsBoardGameOld class.
	2.	Run its main method:
public static void main(String[] args) {
    IPawnsBoard model = new BasicPawnsBoard("deckConfigFiles" + File.separator + "example1.txt",
    "deckConfigFiles" + File.separator + "example1.txt",
            false, 5, new Random(0226), 7, 5);
    PawnsBoardGuiView viewRed = new BasicPawnsBoardFrame(model, PlayerColor.RED);
    PawnsBoardGuiView viewBlue = new BasicPawnsBoardFrame(model, PlayerColor.BLUE);
    PawnsBoardStubController controller = new PawnsBoardStubController(model, viewRed, viewBlue);
    controller.gameStart();
}

Key Components

Model:
BasicPawnsBoard, BasicBoard, BasicPlayer, BasicCard, BasicPawn
These classes implement the game’s state and rules:
    Board & Cell: The grid is initialized with default pawn placements (red pawns in the first
    column and blue in the last) and cells can contain up to three pawns or a card.
    Cards: Each card is defined by its name, cost, value, and influence grid. Cards are read from a
    deck configuration file (see below).
    Deck & Hand Management: Each player’s deck is read from a file
    (e.g., deckConfigFiles/example1.txt), and players draw cards to form their hand.
    Scoring: Row scores are computed by summing the value of cards in that row, and the winner of
    each row is determined by the higher score.
Deck Configuration File
The deck file (located in deckConfigFiles/example1.txt) specifies cards in the following format:
Security 1 6
XXXXX
XXIXX
XICIX
XXIXX
XXXXX
Bee 1 1
XXIXX
XXXXX
XXCXX
XXXXX
XXIXX

Controller:
    PawnsBoardStubController & ViewActions
    The controller processes user input from the GUI. It handles:
    Mouse clicks on board cells and cards in the hand.
    Keyboard commands (e.g., “M” to confirm a move, “P” to pass).
    It then delegates the appropriate move or pass to the model and refreshes the view.

View
    Textual View (BasicPawnsBoardTextView)
    - Renders the game state to the console. It uses simple text symbols (e.g., “__” for empty
    cells, “*” for pawn cells, “R”/“B” for cards) along with row scores.
    GUI View (Swing-based: BasicPawnsBoardFrame, BasicPawnsBoardPanel, BasicPawnsHandPanel,
    BasicPawnsScoreColumn)
    - Provides an interactive graphical display of the game:
    Board Panel: Displays the grid and pawns/cards.
    Hand Panel: Displays the player’s hand, including card details and influence grids.
    Score Columns: Display row scores for each player.
The GUI supports resizing and interactive selection.

Strategies
    Fill First Strategy (FillFirst)
    - Chooses the first legal move by scanning the player’s hand and then the board in a row‑by‑row,
    left‑to‑right order.
    Maximize Row Score Strategy (MaximizeRowScore)
    - Scans each row (from top to bottom) and, if the current player’s row score is less than or
    equal to the opponent’s, it simulates moves (on a deep copy of the model) that would increase
    the player’s score enough to win the row. A new check ensures that a cell has enough pawns (≥
    card cost) before attempting a move. If no move can improve the score, the strategy returns
    null (indicating a pass).

src/
  model/
    BasicPawnsBoard.java       // Implements game state, turn management, and scoring.
    BasicBoard.java            // Represents the board grid and cell interactions.
    BasicPlayer.java           // Manages deck and hand for a player.
    BasicCard.java             // Card implementation (name, cost, value, influence).
    BasicPawn.java             // Pawn cell representation.
    IPawnsBoard.java           // Mutable interface for the game model.
    ReadonlyPawnsBoard.java    // Read-only view of the model.
    PlayerColor.java           // Enum for RED and BLUE.
  controller/
    PawnsBoardStubController.java  // Processes GUI input and delegates moves.
    ViewActions.java               // Interface for handling view events.
  view/
    BasicPawnsBoardFrame.java      // Main GUI frame.
    BasicPawnsBoardPanel.java      // Panel for the board grid.
    BasicPawnsHandPanel.java       // Panel for the player’s hand.
    BasicPawnsScoreColumn.java     // Panel for displaying row scores.
    BasicPawnsBoardTextView.java   // Console textual view.
    PawnsBoardGuiView.java         // Interface for GUI view.
    PawnsBoardTextView.java        // Interface for textual view.
  strategy/
    FillFirst.java                // Simple strategy: first legal move.
    MaximizeRowScore.java         // Strategy to improve row score.
    Move.java                     // Encapsulates a move (hand index, row, column).
    PawnsBoardStrategy.java       // Strategy interface.
docs/
  deckConfigFiles/
    example1.txt                // Deck configuration file with card definitions.
test/
  (JUnit tests for model, view, and strategies)

Invariants and Design Decisions
    Invariants:
    - Key invariants include the board dimensions, hand size being greater than 0, and each cell
    holding at most 3 pawns. These invariants are documented.
    Separation of Concerns:
    - The model is split into mutable (IPawnsBoard) and read-only (ReadonlyPawnsBoard) interfaces
    to ensure that the view cannot accidentally modify the game state.
    - Deep Copy for Strategy Simulation:
    Strategies that simulate moves (e.g., MaximizeRowScore) use deep copies of the model to
    evaluate potential moves without affecting the live game state.

Human and Multiplayer Interfaces
Our game supports both local human interfaces (textual and GUI) and is designed with the strategies
that we implemented within our game.

Running the Game
    Textual Mode: Run the PawnsBoard main method.
    GUI Mode: Run the PawnsBoardGameOld main method.
