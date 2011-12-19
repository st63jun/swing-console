package sample;

import info.xopowo.console.*;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * コンソールに入力された文字列をひたすらエコーするサンプル
 */
public class ConsoleSample implements Runnable {

    public static void main(String[] args) {
	ConsoleSample sample = new ConsoleSample();
	new Thread(sample).start();
    }

    public void run() {
	JFrame frame = new JFrame("Console Component Sample");
	Console console = new Console();
	
	console.addConsoleListener(new IConsoleListener() {
            @Override
            public void lineAccepted(String line, Console source) {
		source.println("echo: " + line);
                source.print("> ");
            }
	});

	console.println("Hello, World.");
	console.print("> ");

	frame.add(console);
	frame.setSize(600, 450);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
