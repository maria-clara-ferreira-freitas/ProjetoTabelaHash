import java.io.*;

public class Main {
    public static void main(String[] args) {
        TabelaHashMultiplication hashTable = new TabelaHashMultiplication(3000, 0.85);
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