import java.io.PrintStream;
import java.util.*;

public class MyAI implements AI {
    public String getName() {
        return "MinMaxTree";
    }

    //Return the best move myPiece can make using a MinMaxTree
    public Point getNextMove(TicTacToe game, TicTacToePiece myPiece) {
        //if game is over, reutrn null
        if (game.isGameOver())
            return null;

        //Generate a MinMax tree using the TicTacToe game parameter
        TNode<MinMaxNode> root = generateMinMaxTree(game, null);
        //Score the MinMax tree
        scoreMinMaxTree(root, game.currentPlayer);

        //Create a list of MinMaxNodes from the children of the root of the MinMax tree you just generated
        //if the root node has no children, return null
        List<MinMaxNode> children = new ArrayList<>();
        for (TNode<MinMaxNode> child : root.getChildren())
            children.add(child.getData());
        if (children.size() == 0)
            return null;

        //Determine what the best (highest) score is among all of the MinMax nodes in the list your just created
        int highest = -10;
        for (MinMaxNode child : children)
            if (child.getScore() > highest)
                highest = child.getScore();

        //Create a list to hold all of the moves (Point objects) that are rated with the best score
        List<Point> possMoves = new ArrayList<>();
        //loop through all of the children of the root of hte MinMax tree and add the Point from the ones with the "best score" to the list of Points you just created
        for (MinMaxNode child : children)
            if (child.getScore() == highest)
                possMoves.add(child.getMove());

        int rand = (int) (Math.random() * possMoves.size());
        return possMoves.get(rand);
    }

    //Generate a MinMaxTree consisting of a root node containing game, and children nodes
    //containing all possible moves the current player can make
    private TNode<MinMaxNode> generateMinMaxTree(TicTacToe game, Point move) {
        //make a copy of game (so you can modify the copy without changing game)
        TicTacToe gCopy = game.copy();
        //if move is not null
        //	apply move to the copy (addPiece and nextPlayer)
        if (move != null) {
            gCopy.addPiece(move.getRow(), move.getCol());
            gCopy.nextPlayer();
        }

        //Make a MinMaxNode with the copy and move
        MinMaxNode curState = new MinMaxNode(gCopy, move);
        //Make a TNode with the MinMaxNode you just created
        TNode<MinMaxNode> root = new TNode<>(curState);

        //recursively call generateMinMaxTree for each legal move that the new current player can make on copy (every empty space is a legal move)
        //	add the TNode returned by the recursive generateMinMaxTree calls as a child to the TNode you created above
        for (Point possMove : gCopy.getEmptySpaces()) {
            root.addChild(generateMinMaxTree(gCopy, possMove));
        }

        //return the TNode you created above
        return root;
    }

    //Generate a score for every node in the MinMaxTree (from the point of view of player)
    private void scoreMinMaxTree(TNode<MinMaxNode> root, TicTacToePiece player) {
        //get the MinMaxNode out of the root node
        MinMaxNode rootNode = root.getData();
        //get the game out of the MinMaxNode
        TicTacToe gCopy = rootNode.getGame();

        //if the game is over
        //	use the setScore method to score the MinMaxNode based on who one the gmae
        //		if player is the winner -> 	10 points
        //		if the game is tied -> 		0 points
        //		if player is the loser ->	-10 points

        //if the game is not over
        //	recursively call scoreMinMaxTree on all of the root node's children
        //	determine the lowest and highest scores among all of the root node's children
        //	if it is player's turn
        //		set this MinMaxNode's score to the highest score
        //	if it is NOT player's turn
        //		set this MinMaxNode's score to the lowest score

        if (gCopy.isGameOver()) {
            TicTacToePiece winner = gCopy.getWinner();
//            System.out.println(winner);
            if (winner == null) {
                rootNode.setScore(0);
            } else if (winner == TicTacToePiece.X) {
                rootNode.setScore(player.equals(TicTacToePiece.X) ? 10 : -10);
            } else if (winner == TicTacToePiece.O) {
                rootNode.setScore(player.equals(TicTacToePiece.O) ? 10 : -10);
            }
        } else {
            int lowestScore = 10;
            int highScore = -10;
            for (TNode<MinMaxNode> child : root.getChildren()) {
                scoreMinMaxTree(child, player);
            }
            for (TNode<MinMaxNode> child : root.getChildren()) {
                int score = child.getData().getScore();
                if (score < lowestScore)
                    lowestScore = score;
                if (score > highScore)
                    highScore = score;
            }
            rootNode.setScore(gCopy.getCurrentPlayer().equals(player) ? highScore : lowestScore);
        }
    }

}
