/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package zelone.engineTester;

/**
 *
 * @author Zelone
 */
public class DualRun {

    public static void main(String[] args) {
        Runnable rr = new Runnable() {
            @Override
            public void run() {
                MainGameLoop.main(args);
            }
        };
        Thread t = new Thread(rr);
        Thread tt = new Thread(rr);
        t.start();
        tt.start();
        try {
            t.join();
            tt.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        MainGameLoop.main(args);

    }

}
