import java.util.ArrayList;
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        TabelaHashMultiplication hashTable = new TabelaHashMultiplication(3000, 0.85);
        String arquivo = "data/random_numbers3.txt"; // ou o caminho completo, como "/caminho/do/arquivo/random_numbers.txt"

        try {
            BufferedReader reader = new BufferedReader(new FileReader(arquivo));
            String line = "";

            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(" ");

                // Medindo tempo de execução
                long start = System.nanoTime();

                // Loop para adicionar os números na tabela de hash
                for (String token : tokens) {
                    try {
                        int chave = Integer.parseInt(token); // Converte o token para número inteiro
                        hashTable.add(chave); // Adiciona à tabela de hash
                    } catch (NumberFormatException e) {
                        // Ignora caso algum token não seja um número válido
                        System.out.println("Token inválido: " + token);
                    }
                }

            }

            // Exibe o número total de colisões após o processamento do arquivo
            System.out.println("Número total de colisões: " + hashTable.getColisoes());

            // Fecha o BufferedReader após ler o arquivo
            reader.close();

        } catch (IOException ioe) {
            System.out.println("Erro ao ler a entrada do arquivo: " + ioe.getMessage());
        }
    }
}