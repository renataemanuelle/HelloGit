package Java2D;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class CanvasPanel extends JPanel implements Runnable, KeyListener {

    double pXNuvem = 400; //coordenada X nuvem
    boolean testeNuvemEsquerda = true; //indica se a nuvem está indo para esquerda
    boolean testeAbelhaEsquerda = true; //indica se a abelha está indo para esquerda
    double pXAbelha = 320; //coordenada X abelha
    double pYAbelha = 400; //coordenada Y abelha
    private Image abelha; //imagem abelha
    Image[] abelhaDireita; //vetor de imagens da abelha para a direita
    Image[] abelhaEsquerda; //vetor de imagens da abelha para a esquerda
    int indiceAbelha = 0; //índice correspondente ao vetor de imagens da abelha
    double contframes = 0; //contador para atualização do índiceAbelha
    
    //teclas
    private final boolean[] key_states = new boolean[256];

    //atribuição cores
    Color branco = new Color(255, 255, 255);
    Color verde = new Color(40, 155, 0);
    Color azulclaro = new Color(152, 209, 250);
    Color azulescuro = new Color(9, 9, 100);
    Color marrom = new Color(120, 76, 0);
    Color laranja = new Color(255, 136, 0);
    Color amarelo = new Color(255, 242, 0);
    Color cinza = new Color(100, 100, 100);
    Color preto = new Color(0, 0, 0);
    Color rosa = new Color(213, 55, 138);

    public CanvasPanel() {

        //inicializa o vetor de imagens
        abelhaDireita = new Image[4];
        abelhaEsquerda = new Image[4];
        abelhaDireita[0] = new ImageIcon(this.getClass().getResource("/Imagens/BeeRight1.png")).getImage();
        abelhaDireita[1] = new ImageIcon(this.getClass().getResource("/Imagens/BeeRight2.png")).getImage();
        abelhaDireita[2] = new ImageIcon(this.getClass().getResource("/Imagens/BeeRight3.png")).getImage();
        abelhaDireita[3] = new ImageIcon(this.getClass().getResource("/Imagens/BeeRight4.png")).getImage();
        abelhaEsquerda[0] = new ImageIcon(this.getClass().getResource("/Imagens/BeeLeft1.png")).getImage();
        abelhaEsquerda[1] = new ImageIcon(this.getClass().getResource("/Imagens/BeeLeft2.png")).getImage();
        abelhaEsquerda[2] = new ImageIcon(this.getClass().getResource("/Imagens/BeeLeft3.png")).getImage();
        abelhaEsquerda[3] = new ImageIcon(this.getClass().getResource("/Imagens/BeeLeft4.png")).getImage();
        
        //carrega a tela inicial
        load();
    }

    /**
     * RECEBE COMO PARAMETRO  A TECLA DIGITADA E MODIFICA A VARIÁVEL CORRESPONDENTE NO VETOR DE TECLAS
     * @param e TECLA DIGITADA
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }
    
    
    /**
     * RECEBE COMO PARAMETRO  A TECLA PRESSIONADA E MODIFICA A VARIÁVEL CORRESPONDENTE NO VETOR DE TECLAS
     * @param e TECLA PRESSIONADA
     */
    @Override
    public void keyPressed(KeyEvent e) {
        key_states[e.getKeyCode()] = true;
    }

    /**
     * RECEBE COMO PARAMETRO  A TECLA LIBERADA E MODIFICA A VARIÁVEL CORRESPONDENTE NO VETOR DE TECLAS
     * @param e TECLA LIBERADA
     */
    @Override
    public void keyReleased(KeyEvent e) {
        key_states[e.getKeyCode()] = false;
    }

    /**
     * RESPONSÁVEL POR REPINTAR A TELA
     * @param g GRAPHICS
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    @Override
    public void run() {
        double btime, dtime = 0;
        btime = System.currentTimeMillis();
        while (true) {
            update(dtime / 1000);
            repaint();
            dtime = (System.currentTimeMillis() - btime);
            btime = System.currentTimeMillis();
        }
    }
    
    /**
     * CARREGA A TELA INICIAL
     */
    private void load() {
        setBackground(azulclaro);
        abelha = new ImageIcon(this.getClass().getResource("/Imagens/BeeLeft1.png")).getImage();
        setDoubleBuffered(true);
        setFocusable(true);
        new Thread(this).start();
        addKeyListener(this);
    }

    /**
     * ATUALIZA A TELA
     * @param dt TEMPO EM MILLISEGS
     */
    private void update(double dt) {

        //movimentação nuvem
        if (testeNuvemEsquerda == true && pXNuvem > -240) {
            pXNuvem = pXNuvem - (50 * dt);
        } else if (pXNuvem <= -230) {
            testeNuvemEsquerda = false;
        }
        if (testeNuvemEsquerda == false && pXNuvem < 800) {
            pXNuvem = pXNuvem + (50 * dt);
        } else if (pXNuvem > 800) {
            testeNuvemEsquerda = true;
        }

        //muda fundo
        if (pXNuvem > -140 && pXNuvem < 120) {
            setBackground(azulescuro);
        } else {
            setBackground(azulclaro);
        }

        atualizaIndice(dt);
        
        //abelha voa para direita
        if (key_states[KeyEvent.VK_RIGHT]) {
            testeAbelhaEsquerda = false;
            if (pXAbelha < 685) {
                pXAbelha = pXAbelha + (200 * dt);
            }
        }

        //abelha voa para esquerda
        if (key_states[KeyEvent.VK_LEFT]) {
            testeAbelhaEsquerda = true;
            if (pXAbelha > 0) {
                pXAbelha = pXAbelha - (200 * dt);
            }
        }
        
        //abelha voa para cima
        if (key_states[KeyEvent.VK_UP]) {
            if (pYAbelha > 0) {
                pYAbelha = pYAbelha - (200 * dt);
            }
        }
        
        //abelha voa para baixo
        if (key_states[KeyEvent.VK_DOWN]) {
            if (pYAbelha < 485) {
                pYAbelha = pYAbelha + (200 * dt);
            }
        }
    }

    /**
     * ATUALIZA O INDICE DO VETOR CORRESPONDENTE A IMAGEM DA ABELHA
     * @param dt TEMPO EM MILLISEGS
     */
    private void atualizaIndice(Double dt) {
        contframes = contframes + (1000 * dt);
        if (contframes > 150) {
            indiceAbelha++;
            if (indiceAbelha > 3) {
                indiceAbelha = 0;
            }
            contframes = 0;
        }
        if(testeAbelhaEsquerda) abelha = new ImageIcon(abelhaEsquerda[indiceAbelha]).getImage();
        else abelha = new ImageIcon(abelhaDireita[indiceAbelha]).getImage();
    }

    
    /**
     * DESENHA AS IMAGENS NA TELA
     * @param g GRAPHICS
     */
    
    private void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        //chao
        g2d.setColor(verde);
        g2d.fillRect(0, 500, 800, 100);
        
        //apiário
        g2d.setColor(laranja);
        g2d.fillRect(230, 360, 110, 120);
        g2d.fillRect(230, 360, 110, 8);
        g2d.fillRect(315, 480, 15, 20);
        g2d.fillRect(240, 480, 15, 20);
        g2d.setColor(marrom);
        g2d.fillRect(230, 417, 110, 3);
        g2d.fillRect(230, 477, 110, 3);
        g2d.fillRect(230, 360, 110, 8);
        g2d.fillRoundRect(272, 425, 26, 8, 10, 10);
        g2d.fillRoundRect(272, 371, 26, 8, 10, 10);
        //telhado
        int xtPoints[] = {220, 350, 285};
        int ytPoints[] = {360, 360, 310};
        g2d.setColor(laranja);
        g2d.fillPolygon(xtPoints, ytPoints, 3);
        
        //flor branca
        g2d.setColor(marrom);
        g2d.drawArc(590, 450, 20, 50, 90, 180);
        //pétalas
        g2d.setColor(branco);
        g2d.fillOval(565, 420, 28, 28);
        g2d.fillOval(590, 420, 28, 28);
        g2d.fillOval(600, 440, 28, 28);
        g2d.fillOval(580, 455, 28, 28);
        g2d.fillOval(558, 442, 28, 28);
        //miolo
        g2d.setColor(amarelo);
        g2d.fillOval(580, 435, 30, 30);
        
        //flor rosa
        g2d.setColor(marrom);
        g2d.drawArc(490, 450, 20, 50, 90, -180);
        //pétalas
        g2d.setColor(rosa);
        g2d.fillOval(475, 420, 28, 28);
        g2d.fillOval(500, 420, 28, 28);
        g2d.fillOval(510, 440, 28, 28);
        g2d.fillOval(490, 455, 28, 28);
        g2d.fillOval(468, 442, 28, 28);
        //miolo
        g2d.setColor(amarelo);
        g2d.fillOval(487, 435, 30, 30);
        
        //sol
        g2d.setColor(amarelo);
        int diam = 90;
        int tamd = 10;
        int tamr = 20;
        int xs = 100;
        int ys = 70;
        g2d.drawLine((xs - tamd), (ys - tamd), (xs + diam + tamd), (ys + diam + tamd));
        g2d.drawLine((xs - tamr), (ys + diam / 2), (xs + diam + tamr), (ys + diam / 2));
        g2d.drawLine((xs - tamd), (ys + diam + tamd), (xs + diam + tamd), (ys - tamd));
        g2d.drawLine((xs + diam / 2), (ys - tamr), (xs + diam / 2), (ys + diam + tamr));
        g2d.fillOval(xs, ys, diam, diam);

        //nuvem
        g2d.setColor(branco);
        g2d.fillOval((int) pXNuvem, 60, 110, 110);
        g2d.fillOval((int)(pXNuvem + 80), 60, 110, 110);
        g2d.fillOval((int)(pXNuvem + 160), 60, 110, 110);
        g2d.fillOval((int)(pXNuvem + 240), 60, 110, 110);

        //abelha
        g2d.drawImage(abelha, (int) pXAbelha, (int) pYAbelha, this);
    }

}
