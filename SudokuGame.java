import java.util.Scanner;

/**
 * Classe principal do Jogo de Sudoku
 * Implementa um jogo de Sudoku interativo no terminal
 */
public class SudokuGame {
    private static final int SIZE = 9;
    private static final int SUBGRID_SIZE = 3;
    private int[][] board;
    private int[][] solution;
    private Scanner scanner;

    public SudokuGame() {
        this.board = new int[SIZE][SIZE];
        this.solution = new int[SIZE][SIZE];
        this.scanner = new Scanner(System.in);
        initializeGame();
    }

    /**
     * Inicializa o jogo com um tabuleiro pré-definido
     */
    private void initializeGame() {
        // Tabuleiro inicial com algumas células preenchidas
        int[][] initialBoard = {
            {5, 3, 0, 0, 7, 0, 0, 0, 0},
            {6, 0, 0, 1, 9, 5, 0, 0, 0},
            {0, 9, 8, 0, 0, 0, 0, 6, 0},
            {8, 0, 0, 0, 6, 0, 0, 0, 3},
            {4, 0, 0, 8, 0, 3, 0, 0, 1},
            {7, 0, 0, 0, 2, 0, 0, 0, 6},
            {0, 6, 0, 0, 0, 0, 2, 8, 0},
            {0, 0, 0, 4, 1, 9, 0, 0, 5},
            {0, 0, 0, 0, 8, 0, 0, 7, 9}
        };

        // Solução completa
        int[][] completeSolution = {
            {5, 3, 4, 6, 7, 8, 9, 1, 2},
            {6, 7, 2, 1, 9, 5, 3, 4, 8},
            {1, 9, 8, 3, 4, 2, 5, 6, 7},
            {8, 5, 9, 7, 6, 1, 4, 2, 3},
            {4, 2, 6, 8, 5, 3, 7, 9, 1},
            {7, 1, 3, 9, 2, 4, 8, 5, 6},
            {9, 6, 1, 5, 3, 7, 2, 8, 4},
            {2, 8, 7, 4, 1, 9, 6, 3, 5},
            {3, 4, 5, 2, 8, 6, 1, 7, 9}
        };

        // Copia os arrays para o jogo
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                this.board[i][j] = initialBoard[i][j];
                this.solution[i][j] = completeSolution[i][j];
            }
        }
    }

    /**
     * Exibe o tabuleiro atual
     */
    public void displayBoard() {
        System.out.println("\n  1 2 3   4 5 6   7 8 9");
        System.out.println(" ┌─────┬─────┬─────┐");
        
        for (int i = 0; i < SIZE; i++) {
            if (i == 3 || i == 6) {
                System.out.println(" ├─────┼─────┼─────┤");
            }
            
            System.out.print((i + 1) + "│");
            for (int j = 0; j < SIZE; j++) {
                if (j == 3 || j == 6) {
                    System.out.print("│");
                }
                
                if (board[i][j] == 0) {
                    System.out.print(" ");
                } else {
                    System.out.print(board[i][j]);
                }
                
                if (j < SIZE - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println("│");
        }
        System.out.println(" └─────┴─────┴─────┘");
    }

    /**
     * Verifica se um número pode ser colocado em uma posição específica
     */
    public boolean isValidMove(int row, int col, int num) {
        // Verifica linha
        for (int j = 0; j < SIZE; j++) {
            if (board[row][j] == num) {
                return false;
            }
        }

        // Verifica coluna
        for (int i = 0; i < SIZE; i++) {
            if (board[i][col] == num) {
                return false;
            }
        }

        // Verifica subgrade 3x3
        int startRow = (row / SUBGRID_SIZE) * SUBGRID_SIZE;
        int startCol = (col / SUBGRID_SIZE) * SUBGRID_SIZE;
        
        for (int i = startRow; i < startRow + SUBGRID_SIZE; i++) {
            for (int j = startCol; j < startCol + SUBGRID_SIZE; j++) {
                if (board[i][j] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Faz uma jogada no tabuleiro
     */
    public boolean makeMove(int row, int col, int num) {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
            System.out.println("Posição inválida! Use números de 1 a 9.");
            return false;
        }

        if (board[row][col] != 0) {
            System.out.println("Esta posição já está preenchida!");
            return false;
        }

        if (num < 1 || num > 9) {
            System.out.println("Número inválido! Use números de 1 a 9.");
            return false;
        }

        if (!isValidMove(row, col, num)) {
            System.out.println("Movimento inválido! Este número já existe na linha, coluna ou subgrade.");
            return false;
        }

        board[row][col] = num;
        return true;
    }

    /**
     * Remove um número do tabuleiro
     */
    public boolean removeNumber(int row, int col) {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
            System.out.println("Posição inválida! Use números de 1 a 9.");
            return false;
        }

        board[row][col] = 0;
        return true;
    }

    /**
     * Verifica se o jogo foi completado
     */
    public boolean isGameComplete() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Verifica se a solução está correta
     */
    public boolean isSolutionCorrect() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] != solution[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Exibe dica para o jogador
     */
    public void showHint() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == 0) {
                    System.out.println("Dica: Tente colocar o número " + solution[i][j] + 
                                     " na posição (" + (i + 1) + ", " + (j + 1) + ")");
                    return;
                }
            }
        }
    }

    /**
     * Loop principal do jogo
     */
    public void playGame() {
        System.out.println("=== BEM-VINDO AO JOGO DE SUDOKU ===");
        System.out.println("Instruções:");
        System.out.println("- Digite 'add linha coluna numero' para adicionar um número");
        System.out.println("- Digite 'remove linha coluna' para remover um número");
        System.out.println("- Digite 'hint' para obter uma dica");
        System.out.println("- Digite 'quit' para sair do jogo");
        System.out.println("- Use números de 1 a 9 para posições e valores");

        while (true) {
            displayBoard();
            
            if (isGameComplete()) {
                if (isSolutionCorrect()) {
                    System.out.println("\n🎉 PARABÉNS! Você completou o Sudoku corretamente! 🎉");
                    break;
                } else {
                    System.out.println("\n❌ O tabuleiro está completo, mas há erros. Verifique sua solução.");
                }
            }

            System.out.print("\nDigite seu comando: ");
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("quit")) {
                System.out.println("Obrigado por jogar!");
                break;
            } else if (input.equals("hint")) {
                showHint();
            } else if (input.startsWith("add ")) {
                String[] parts = input.split(" ");
                if (parts.length == 4) {
                    try {
                        int row = Integer.parseInt(parts[1]) - 1;
                        int col = Integer.parseInt(parts[2]) - 1;
                        int num = Integer.parseInt(parts[3]);
                        
                        if (makeMove(row, col, num)) {
                            System.out.println("Número adicionado com sucesso!");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Formato inválido! Use: add linha coluna numero");
                    }
                } else {
                    System.out.println("Formato inválido! Use: add linha coluna numero");
                }
            } else if (input.startsWith("remove ")) {
                String[] parts = input.split(" ");
                if (parts.length == 3) {
                    try {
                        int row = Integer.parseInt(parts[1]) - 1;
                        int col = Integer.parseInt(parts[2]) - 1;
                        
                        if (removeNumber(row, col)) {
                            System.out.println("Número removido com sucesso!");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Formato inválido! Use: remove linha coluna");
                    }
                } else {
                    System.out.println("Formato inválido! Use: remove linha coluna");
                }
            } else {
                System.out.println("Comando não reconhecido. Use 'add', 'remove', 'hint' ou 'quit'.");
            }
        }
        
        scanner.close();
    }

    /**
     * Método principal
     */
    public static void main(String[] args) {
        SudokuGame game = new SudokuGame();
        game.playGame();
    }
}

