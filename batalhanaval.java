package batalhanaval;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;

public class BatalhaNaval extends javax.swing.JFrame {

    // Declarando os componentes
    private javax.swing.JPanel painelJogador;
    private javax.swing.JPanel painelComputador;
    private javax.swing.JButton btnResetar;
    private javax.swing.JLabel lblStatus;

    // Botões das matrizes
    private javax.swing.JToggleButton[] botoesJogador = new javax.swing.JToggleButton[25];
    private javax.swing.JToggleButton[] botoesComputador = new javax.swing.JToggleButton[25];

    // Matrizes dos barcos
    private int[] barcosJogador = new int[25];
    private int[] barcosComputador = new int[25];

    // Quantidade de células dos barcos
    private int barcosDe3 = 1, barcosDe2 = 2, barcosDe1 = 2;

    // Flags e turnos
    private boolean jogoIniciado = false;
    private boolean turnoJogador = true;

    // Lista de ataques feitos pelo computador
    private ArrayList<Integer> ataquesComputador = new ArrayList<>();

    public BatalhaNaval() {
        initComponents();
        resetarJogo();
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new BatalhaNaval().setVisible(true);
        });
    }

    private void initComponents() {
        painelJogador = new javax.swing.JPanel();
        painelComputador = new javax.swing.JPanel();
        btnResetar = new javax.swing.JButton();
        lblStatus = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Batalha Naval");
        setResizable(false);

        // Configurando os painéis
        painelJogador.setLayout(new java.awt.GridLayout(5, 5));
        painelComputador.setLayout(new java.awt.GridLayout(5, 5));

        // Inicializando os botões da matriz do jogador
        for (int i = 0; i < 25; i++) {
            botoesJogador[i] = new javax.swing.JToggleButton();
            botoesJogador[i].setBackground(Color.CYAN);
            botoesJogador[i].addActionListener(e -> colocarBarcoJogador(i));
            painelJogador.add(botoesJogador[i]);
        }

        // Inicializando os botões da matriz do computador
        for (int i = 0; i < 25; i++) {
            botoesComputador[i] = new javax.swing.JToggleButton();
            botoesComputador[i].setBackground(Color.GRAY);
            botoesComputador[i].setEnabled(false);
            botoesComputador[i].addActionListener(e -> atacarComputador(i));
            painelComputador.add(botoesComputador[i]);
        }

        // Configuração do botão resetar
        btnResetar.setText("Resetar");
        btnResetar.addActionListener(evt -> resetarJogo());

        lblStatus.setText("Posicione seus barcos!");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(painelJogador, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(painelComputador, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(btnResetar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblStatus)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(painelJogador, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                    .addComponent(painelComputador, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnResetar)
                    .addComponent(lblStatus))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void resetarJogo() {
        for (int i = 0; i < 25; i++) {
            botoesJogador[i].setBackground(Color.CYAN);
            botoesJogador[i].setEnabled(true);
            botoesComputador[i].setBackground(Color.GRAY);
            botoesComputador[i].setEnabled(false);
            barcosJogador[i] = 0;
            barcosComputador[i] = 0;
        }
        barcosDe3 = 1;
        barcosDe2 = 2;
        barcosDe1 = 2;
        jogoIniciado = false;
        turnoJogador = true;
        ataquesComputador.clear();
        lblStatus.setText("Posicione seus barcos!");
        posicionarBarcosComputador();
    }

    private void colocarBarcoJogador(int index) {
        if (jogoIniciado) return;
        if (botoesJogador[index].getBackground() == Color.BLACK) return;

        if (barcosDe3 > 0) {
            botoesJogador[index].setBackground(Color.BLACK);
            barcosJogador[index] = 3;
            barcosDe3--;
        } else if (barcosDe2 > 0) {
            botoesJogador[index].setBackground(Color.BLACK);
            barcosJogador[index] = 2;
            barcosDe2--;
        } else if (barcosDe1 > 0) {
            botoesJogador[index].setBackground(Color.BLACK);
            barcosJogador[index] = 1;
            barcosDe1--;
        }

        if (barcosDe3 == 0 && barcosDe2 == 0 && barcosDe1 == 0) {
            jogoIniciado = true;
            for (javax.swing.JToggleButton botao : botoesComputador) {
                botao.setEnabled(true);
            }
            lblStatus.setText("Ataque a matriz do inimigo!");
        }
    }

    private void atacarComputador(int index) {
        if (!jogoIniciado || !turnoJogador) return;

        if (botoesComputador[index].getBackground() != Color.GRAY) return;

        if (barcosComputador[index] > 0) {
            botoesComputador[index].setBackground(Color.RED);
            barcosComputador[index] = 0;
            lblStatus.setText("Você acertou!");
        } else {
            botoesComputador[index].setBackground(Color.BLUE);
            lblStatus.setText("Você errou!");
        }

        turnoJogador = false;

        if (verificarVitoria(barcosComputador)) {
            JOptionPane.showMessageDialog(this, "Você venceu!");
            resetarJogo();
        } else {
            atacarJogador();
        }
    }

    private void atacarJogador() {
        Random rand = new Random();
        int ataque;
        do {
            ataque = rand.nextInt(25);
        } while (ataquesComputador.contains(ataque));
        ataquesComputador.add(ataque);

        if (barcosJogador[ataque] > 0) {
            botoesJogador[ataque].setBackground(Color.RED);
            barcosJogador[ataque] = 0;
            lblStatus.setText("Computador acertou!");
        } else {
            botoesJogador[ataque].setBackground(Color.BLUE);
            lblStatus.setText("Computador errou!");
        }

        turnoJogador = true;

        if (verificarVitoria(barcosJogador)) {
            JOptionPane.showMessageDialog(this, "Computador venceu!");
            resetarJogo();
        }
    }

    private boolean verificarVitoria(int[] barcos) {
        for (int barco : barcos) {
            if (barco > 0) return false;
        }
        return true;
    }

    private void posicionarBarcosComputador() {
        Random rand = new Random();
        int barcosDe3 = 1, barcosDe2 = 2, barcosDe1 = 2;

        while (barcosDe3 > 0 || barcosDe2 > 0 || barcosDe1 > 0) {
            int index = rand.nextInt(25);

            if (barcosComputador[index] == 0) {
                if (barcosDe3 > 0) {
                    barcosComputador[index] = 3;
                    barcosDe3--;
                } else if (barcosDe2 > 0) {
                    barcosComputador[index] = 2;
                    barcosDe2--;
                } else if (barcosDe1 > 0) {
                    barcosComputador[index] = 1;
                    barcosDe1--;
                }
            }
        }
    }
}
