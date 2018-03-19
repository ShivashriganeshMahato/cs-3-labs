import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class MarkovChain
{
	//INSTANCE VARIABLES
	/**
	 *	This holds all of the word relationships
	 */
	private WordGraph wg;

	/**
	 *	This remembers the lastWord that this Markov Chain generated
	 */
	private String lastWord;

	//CONSTRUCTOR
    public MarkovChain()
    {
    	//initialize instance variables
    	wg = new WordGraph();
    	lastWord = null;
    }

    //METHODS
    /**
     *	Add word relationships from the specified file
     */
    public void train(String filename)
    {
        Scanner s = null;
        try {
            s = new Scanner(new File(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (s.hasNext())
            wg.addWord(s.next());
    }

    /**
     *	Get a list of words that follow lastWord.
	 *	words with more *weight* will appear more times in the list.
	 *  if lastWord is null, then return the words that are neighbors of [START]
     */
    public List<String> getNextWords()
    {
		List<String> nextWords = new ArrayList<>();
		String word = lastWord == null ? "[START]" : lastWord;

		for (String neighbor : wg.getGraph().getNeighbors(word)) {
			for (int i = 0; i < wg.getGraph().getWeight(word, neighbor); i++) {
				nextWords.add(neighbor);
			}
		}

    	return nextWords;
    }

    /**
     *	Get a word that follows lastWord
     *	Use a weighted random number to pick the next word from the list of all of lastWord's neighbors in wordGraph
     *	If lastWord is null or [END], this should generate a word that *starts* a sentence
     *	Remember the word that is about to be returned in the appropriate instance variable
     */
    public String getNextWord()
    {
		List<String> nextWords = getNextWords();
		String nextWord = nextWords.get((int) (Math.random() * nextWords.size()));
		lastWord = nextWord.equals("[END]") ? null : nextWord;
    	return nextWord;
    }

    /**
     *	Generate a sentence using the data in the wordGraph.
     */
    public String generateSentence()
    {
		StringBuilder sentence = new StringBuilder();

		String nextWord = getNextWord();
		while (!nextWord.equals("[END]")) {
			sentence.append(nextWord + " ");
			nextWord = getNextWord();
		}

    	return sentence.toString();
    }


}
