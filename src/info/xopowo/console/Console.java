package info.xopowo.console;

import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.text.BadLocationException;
import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

/**
 * コンソール風のテキスト表示コンポーネントです．
 * 
 * {@link IConsoleListener}インタフェースを使用することで
 * コンソールに入力した文字列を取得することが可能です．
 */
public class Console extends JComponent {

    private Console me;
    private int markedPosition;
    private JTextArea textArea;
    private JScrollPane scrollPane;
    private ArrayList<IConsoleListener> consoleListeners = new ArrayList<IConsoleListener>();

    public Console() {

        me = this;

        setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.addKeyListener(new ConsoleKeyListener());
        markedPosition = textArea.getCaretPosition();

        scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scrollPane, BorderLayout.CENTER);
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    /**
     * コンソールに文字列を出力します．
     *
     * @param str
     */
    public void print(String str) {
        textArea.append(str);
        textArea.setCaretPosition(textArea.getText().length());
        markedPosition = textArea.getCaretPosition();
    }

    public void println(String str) {
        print(str + "\n");
    }

    public void println() {
        print("\n");
    }

    /**
     * コンソールをクリアします．
     */
    public void clear() {
        textArea.setText("");
        markedPosition = textArea.getCaretPosition();
    }

    /**
     * ConsoleListenerを登録します．
     *
     * @param listener
     */
    public void addConsoleListener(IConsoleListener listener) {
        consoleListeners.add(listener);
    }

    private class ConsoleKeyListener implements KeyListener {
        @Override
        public void keyTyped(KeyEvent keyEvent) {

        }

        @Override
        public void keyPressed(KeyEvent keyEvent) {
            if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                textArea.append("\n");
                String line = "";
                try {
                    if (textArea.getCaretPosition() < markedPosition) {
                        textArea.setCaretPosition(textArea.getText().length());
                    }
                    line = textArea.getText(markedPosition, textArea.getCaretPosition() - markedPosition);
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
                for (IConsoleListener listener : consoleListeners) {
                    listener.lineAccepted(line, me);
                }
                keyEvent.consume();
            }

        }

        @Override
        public void keyReleased(KeyEvent keyEvent) {
            if (textArea.getCaretPosition() < markedPosition) {
                textArea.setCaretPosition(textArea.getText().length());
                keyEvent.consume();
            }
        }
    }
}
