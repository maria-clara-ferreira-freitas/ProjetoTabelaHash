import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        TabelaHashProjeto hashTable = null;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Selecione a função de hash:");
        System.out.println("1 - Multiplication");
        System.out.println("2 - Division");
        System.out.println("3 - MidSquare");
        System.out.println("4 - Folding");
        System.out.print("Opção: ");

        int opcao = scanner.nextInt();

        switch (opcao) {
            case 1:
                hashTable = new TabelaHashMultiplication();
                break;
            case 2:
                hashTable = new TabelaHashDivision();
                break;
            case 3:
                hashTable = new TabelaHashMidSquare();
                break;
            case 4:
                hashTable = new TabelaHashFolding();
                break;
            default:
                System.out.println("Opção inválida!");
                System.exit(1);
        }

        String arquivo = "data/random_numbers3.txt";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(arquivo));
            String line = "";

            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(" ");

                long start = System.nanoTime();

                for (String token : tokens) {
                    try {
                        int chave = Integer.parseInt(token);
                        hashTable.add(chave);
                    } catch (NumberFormatException e) {
                        System.out.println("Token inválido: " + token);
                    }
                }

            }

            System.out.println("Número total de colisões: " + hashTable.getColisoes());
            reader.close();

        } catch (IOException ioe) {
            System.out.println("Erro ao ler a entrada do arquivo: " + ioe.getMessage());
        }
    }
}
