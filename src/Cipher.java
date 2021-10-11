import java.util.ArrayList;
import java.util.Scanner;

public class Cipher {
    private final int ALPHABET_SIZE = 25;
    private final char[] ALPHABET = new char[ALPHABET_SIZE];
    private final char[][] SQUARE = new char[5][5];

    public Cipher(boolean keyGeneration) {
        init();
        if(keyGeneration) {
            char[] arrayKey = new char[5];
            int max = 'z';
            int min = 'a';
            for (int i = 0; i < arrayKey.length; ++i) {
                char tmp = (char)( min + (int) (Math.random() * ((max - min) + 1)));
                if (tmp == 'j') {
                    tmp = 'i';
                }
                if (!isUsed(tmp, arrayKey)) {
                    arrayKey[i] = tmp;
                } else {
                    i--;
                }
            }
            initSquare(new String(arrayKey));
        } else {
            Scanner scanner = new Scanner(System.in);
            String key = scanner.next();
            initSquare(key);
        }
    }

    private void initSquare(String key) {
        if (key.length() != SQUARE.length) {
            throw new RuntimeException("Error length of key!");
        }
        char[] array = key.toCharArray();
        for (int i = 0, iAlphabet = 0; i < SQUARE.length; ++i) {
            if (i == 0) {
                for (int iKey = 0; iKey < array.length; ++iKey) {
                    SQUARE[i][iKey] = array[iKey];
                }
            } else {
                for (int j = 0; j < SQUARE[i].length; ++j, ++iAlphabet) {
                    while (isUsed(ALPHABET[iAlphabet], array)) {
                        iAlphabet++;
                    }
                    SQUARE[i][j] = ALPHABET[iAlphabet];
                }
            }
        }
    }

    public Cipher(String key) {
        if (key.length() != SQUARE.length) {
            throw new RuntimeException("Error length of key!");
        }
        char[] array = key.toCharArray();
        for ( int i = 0, iAlphabet = 0; i < SQUARE.length; ++i) {
            if (i == 0) {
                for (int iKey = 0; iKey < array.length; ++iKey) {
                    SQUARE[i][iKey] = array[iKey];
                }
                continue;
            } else {
                for (int j = 0; j < SQUARE[i].length; ++j, ++iAlphabet) {
                    while (isUsed(ALPHABET[iAlphabet], array)) {
                        iAlphabet++;
                    }
                    SQUARE[i][j] = ALPHABET[iAlphabet];
                }
            }
        }
    }

    public String getFrequencyAnalysis(String text) {
        StringBuilder stringBuilder = new StringBuilder();
        double[] letterFrequency = new double[ALPHABET_SIZE + 1];
        int numberOfLetters = 0;
        char[] array = text.toCharArray();
        for (int i = 0; i < text.length(); ++i) {
            if (array[i] >= 'a' && array[i] <= 'z') {
                numberOfLetters++;
            }
        }
        for (int i = 0; i < ALPHABET_SIZE + 1; ++i) {
            letterFrequency[i] = ((double) (countFrequencyLetter(text, (char) (i + 'a'))) / numberOfLetters ) * 100;
        }
        stringBuilder.append("Frequency analysis:\n");
        double result = 0;
        for (int i = 0; i < ALPHABET_SIZE + 1; ++i) {
            char c = (char) ('a' + i);
            if (letterFrequency[i] > 0) {
                stringBuilder.append(c).append(" -> ").append(String.format("%.2f", letterFrequency[i])).append("%\n");
                result += letterFrequency[i];
            }
        }
        stringBuilder.append("total percent: ").append(String.format("%.2f", result)).append("%");
        return stringBuilder.toString();
    }

    private int countFrequencyLetter(String text, char letter) {
        char[] array = text.toCharArray();
        int counter = 0;
        for (int i = 0; i < array.length; ++i) {
            if (array[i] == letter) {
                counter++;
            }
        }
        return counter;
    }
    private boolean isUsed(char letter, char[] text) {
        for (int i = 0; i < text.length; ++i) {
            if (letter == text[i]) {
                return true;
            }
        }
        return false;
    }
    private void init() {
        for (int i = 0, i2 = 0; i < ALPHABET_SIZE; ++i2) {
            if ('a' + i2 != 'j') {
                ALPHABET[i] = (char) ((int) 'a' + i2);
                i++;
            }
        }
    }

    public String encryption(String message) {
        char[] array = message.toCharArray();
        ArrayList<Character> arrayLetters = new ArrayList<>();
        for (char c : array) {
            if (isLetter(c)) {
                if (c != 'j') {
                    arrayLetters.add(c);
                } else {
                    arrayLetters.add('i');
                }
            }
        }

        char[] encryptedMessage = new char[arrayLetters.size()];
        for (int i = 0; i < arrayLetters.size(); ++i) {
            encryptedMessage[i] = getLetterFromSquareToEncryption(arrayLetters.get(i));
        }

        return new String(encryptedMessage);
    }

    public String decryption(String message) {
        char[] array = message.toCharArray();
        char[] decryptedMessage = new char[message.length()];
        for (int i = 0; i < decryptedMessage.length; ++i) {
            decryptedMessage[i] = getLetterFromSquareToDecryption(array[i]);
        }
        return new String(decryptedMessage);
    }

    private char getLetterFromSquareToEncryption(char letter) {
        if (letter == 'j') {
            return 'i';
        }

        for (int i = 0; i < SQUARE.length; ++i) {
            for (int j = 0; j < SQUARE[i].length; ++j) {
                if(letter == SQUARE[i][j]) {
                    if (i + 1 != SQUARE.length) {
                        return SQUARE[i + 1][j];
                    } else {
                        return SQUARE[0][j];
                    }
                }
            }
        }

        throw new RuntimeException("Error!");
    }


    private char getLetterFromSquareToDecryption(char letter) {
        if (letter == 'j') {
            return 'i';
        }

        for (int i = 0; i < SQUARE.length; ++i) {
            for (int j = 0; j < SQUARE[i].length; ++j) {
                if(letter == SQUARE[i][j]) {
                    if (i - 1 >= 0) {
                        return SQUARE[i - 1][j];
                    } else {
                        return SQUARE[SQUARE.length - 1][j];
                    }
                }
            }
        }

        throw new RuntimeException("Error!");
    }

    private boolean isLetter(char c) {
        return ((c >= 'a') && (c <= 'z'));
    }

}
