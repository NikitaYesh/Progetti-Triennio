import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<String> partiti = new ArrayList<>();
        List<Integer> voti = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("Votazioni.csv"));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parti = line.split(";");

// Controllo che la riga abbia 3 elementi, altrimenti salto elaborazione
                if (parti.length == 3) {
                    String partito = parti[1];
                    int voto = Integer.parseInt(parti[2]);

                    int index = -1;
// Cerco il partito nella lista
                    for (int i = 0; i < partiti.size(); i++) {
                        if (partiti.get(i).equals(partito)) {
                            index = i;
                        }
                    }

                    if (index == -1) {
// Partito non trovato, aggiungo nuovo partito e voti
                        partiti.add(partito);
                        voti.add(voto);
                    } else {
// Partito trovato, aggiorno i voti
                        int votiAttuali = voti.get(index);
                        voti.set(index, votiAttuali + voto);
                    }
                }
            }
            reader.close();

// Calcolo totale voti
            int totale = 0;
            for (int i = 0; i < voti.size(); i++) {
                totale += voti.get(i);
            }

// Ordino i dati (bubble sort)
            for (int i = 0; i < partiti.size() - 1; i++) {
                for (int j = i + 1; j < partiti.size(); j++) {
                    int votiI = voti.get(i);
                    int votiJ = voti.get(j);
                    String pI = partiti.get(i);
                    String pJ = partiti.get(j);

                    if (votiJ > votiI || (votiJ == votiI && pJ.compareTo(pI) < 0)) {
// Scambio partiti
                        String tempP = partiti.get(i);
                        partiti.set(i, partiti.get(j));
                        partiti.set(j, tempP);
// Scambio voti
                        int tempV = voti.get(i);
                        voti.set(i, voti.get(j));
                        voti.set(j, tempV);
                    }
                }
            }

// Stampo la tabella
            System.out.println("\nPARTITO VOTI PERCENTUALE");
            System.out.println("----------------------------------");
            for (int i = 0; i < partiti.size(); i++) {
// Uso Math.round per arrotondare la percentuale calcolata
                int perc = (int) Math.round(voti.get(i) * 100.0 / totale);
                System.out.printf("%-10s %5d %9s\n", partiti.get(i), voti.get(i), perc + "%");
            }
            System.out.println("----------------------------------");
            System.out.printf("%-10s %5d %9s\n", "Totale", totale, "100%");

// Scrivo su file
            BufferedWriter writer = new BufferedWriter(new FileWriter("Risultati.csv"));
            for (int i = 0; i < partiti.size(); i++) {
                int perc = (int) Math.round(voti.get(i) * 100.0 / totale);
                writer.write(partiti.get(i) + ";" + voti.get(i) + ";" + perc);
                writer.newLine();
            }
            writer.close();

        } catch (IOException e) {
            System.out.println("Errore: " + e.getMessage());
        }
    }
}
