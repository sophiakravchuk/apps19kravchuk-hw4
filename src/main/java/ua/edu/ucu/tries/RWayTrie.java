package ua.edu.ucu.tries;

import ua.edu.ucu.utils.Queue;

public class RWayTrie implements Trie {
    private static int R = 26;
    private Node root;

    private int charToIndex(char c) {
        int result = c - (c > 'Z' ? 'a' : 'A');
        if (result >= 26) {
            result = 0;
        }
        return result;
    }

    private static class Node {
        private Tuple tuple;
        private Node[] next = new Node[R];
    }

    @Override
    public void add(Tuple t) {
        String key = t.term;
        root = add(root, key, t, 0);
    }

    private Node add(Node x, String key, Tuple val, int d) {
        if (x == null) {
            x = new Node();
        }
        if (d == key.length()) {
            x.tuple = new Tuple(val.term, val.weight);
            return x;
        }
        int i = charToIndex(key.charAt(d));
        x.next[i] = add(x.next[i], key, val, d + 1);
        return x;
    }

    @Override
    public boolean contains(String word) {
        Node x = get(root, word, 0);
        if (x == null) {
            return false;
        }
        return true;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) {
            return null;
        }
        if (d == key.length()) {
            return x;
        }
        int i = charToIndex(key.charAt(d));
        return get(x.next[i], key, d + 1);
    }


    @Override
    public boolean delete(String word) {
        root = delete(root, word, 0);
        return root != null;
    }

    private Node delete(Node x, String key, int d) {
        if (x == null) {
            return null;
        }
        if (d == key.length()) {
            x.tuple = null;
        } else {
            int i = charToIndex(key.charAt(d));
            x.next[i] = delete(x.next[i], key, d + 1);
        }
        if (x.tuple != null) {
            return x;
        }
        for (char c = 0; c < R; c++) {
            if (x.next[c] != null) {
                return x;
            }
        }
        return null;
    }

    @Override
    public Iterable<String> words() {
        return wordsWithPrefix("");
    }

    @Override
    public Iterable<String> wordsWithPrefix(String s) {
        Queue<String> q = new Queue<>();
        collect(get(root, s, 0), s, q);
        return q;
    }

    private void collect(Node x, String pre, Queue<String> q) {
        if (x == null) {
            return;
        }
        if (x.tuple != null) {
            q.enqueue(pre);
        }
        for (char c = 0; c < R; c++)
            collect(x.next[c], pre + (char) (c + 'a'), q);
    }

    @Override
    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) {
            return 0;
        }
        int cnt = 0;
        if (x.tuple != null) {
            cnt++;
        }
        for (char c = 0; c < R; c++)
            cnt += size(x.next[c]);
        return cnt;
    }
}
