package info.xopowo.console;

/**
 * Consoleクラスに入力された文字列を取得するための
 * インタフェースです．
 */
public interface IConsoleListener {
    /**
     * コンソールにEnterキーが入力されたときに起動します．
     *
     * @param line   最後にEnterを入力した位置から現在のカーソル位置までの文字列
     * @param source 呼出し元のConsoleクラスのインスタンス
     */
    void lineAccepted(String line, Console source);
}
