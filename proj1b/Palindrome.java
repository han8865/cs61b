public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> deque = new ArrayDeque<>();
        for (int i = 0; i < word.length(); i += 1) {
            deque.addLast(word.charAt(i));
        }
        return deque;
    }

    private boolean isPalindromeForDeque(Deque<Character> deque) {
        if (deque.size() <= 1) {
            return true;
        }
        if (deque.removeLast() != deque.removeFirst()) {
            return false;
        }
        return isPalindromeForDeque(deque);
    }

    private boolean isPalindromeForDeque(Deque<Character> deque, CharacterComparator cc) {
        if (deque.size() <= 1) {
            return true;
        }
        if (!cc.equalChars(deque.removeLast(), deque.removeFirst())) {
            return false;
        }
        return isPalindromeForDeque(deque, cc);
    }

    public boolean isPalindrome(String word) {
        return isPalindromeForDeque(wordToDeque(word));
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        return isPalindromeForDeque(wordToDeque(word), cc);
    }
}
