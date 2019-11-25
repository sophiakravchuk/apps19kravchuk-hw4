package ua.edu.ucu.autocomplete;

import ua.edu.ucu.tries.Trie;
import ua.edu.ucu.tries.Tuple;
import ua.edu.ucu.utils.Queue;

import java.util.Iterator;

/**
 *
 * @author andrii
 */
public class PrefixMatches {

    private Trie trie;

    public PrefixMatches(Trie trie) {
        this.trie = trie;
    }

    public int load(String... strings) {
        int amountOfWords = 0;
        for (String str : strings) {
            int wordStart = 0;
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) == ' ') {
                    if (i - wordStart > 0) {
                        String s1 = str.substring(wordStart, i - wordStart - 1);
                        trie.add(new Tuple(s1, s1.length()));
                        amountOfWords++;
                    }
                    wordStart = i + 1;
                }
            }
            if (str.length() - wordStart > 0) {
                String s1 = str.substring(wordStart, str.length() - wordStart);
                trie.add(new Tuple(s1, s1.length()));
                amountOfWords++;
            }
        }
        return amountOfWords;
    }

    public boolean contains(String word) {
        return trie.contains(word);
    }

    public boolean delete(String word) {
        return trie.delete(word);
    }

    public Iterable<String> wordsWithPrefix(String pref) {
        if (pref.length() < 2) {
            return null;
        }
        return trie.wordsWithPrefix(pref);
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {
        if (pref.length() < 2) {
            return null;
        }
        int mimalLength = Math.max(3, pref.length());
        Iterable<String> q = trie.wordsWithPrefix(pref);
        Iterator<String> iter = q.iterator();
        Queue<String> result = new Queue<>();
        int lengthChecker = 0;
        int wordLen = 0;
        while (iter.hasNext()) {
            String str = iter.next();
            if (lengthChecker >= k) {
                if (str.length() <= wordLen) {
                result.enqueue(str);
            }
                continue;
            }
            if (mimalLength <= str.length()) {
                result.enqueue(str);
                if (wordLen != str.length())
                    wordLen = str.length();
                    lengthChecker++;
            }
        }

        return result;
    }

    public int size() {
        return trie.size();
    }
}
