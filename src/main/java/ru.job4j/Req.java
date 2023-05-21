package ru.job4j;

/**
 * Данный класс служит для
 * парсинга входящего запроса.
 */
public class Req {

    /**
     * Данное поле описывает тип запроса.
     */
    private final String httpRequestType;

    /**
     * Данное поле описывает режим работы:
     * queue или topic.
     */
    private final String poohMode;

    /**
     * Данное поле описывает имя очереди
     * или топика.
     */
    private final String sourceName;

    /**
     * Данное поле описывает содержимое запроса
     * или является идентификатором очереди
     * получателя.
     */
    private final String param;

    public Req(String httpRequestType, String poohMode, String sourceName, String param) {
        this.httpRequestType = httpRequestType;
        this.poohMode = poohMode;
        this.sourceName = sourceName;
        this.param = param;
    }

    /**
     * Данный метод парсит запрос
     * и собирает его в объект {@link Req}.
     *
     * <p>Когда мы делаем запрос с помощью cURL:
     * curl -X POST -d "temperature=18" http://localhost:9000/queue/weather
     * здесь ключ -Х (--request) позволяет указать
     * тип HTTP-запроса (GET/POST/PUT).
     * Ключ -d (-data) указывают curl
     * выполнить HTTP POST-запрос (запрос в
     * виде списка имя=значение).
     *
     * <p>В данном случае быстрее было
     * сделать именно через {@link String#split}.
     * 1.Разбили контекст по строкам и
     * взяли оттуда текст/параметр для
     * запроса типа POST.
     * 2.Разбили первую строку по "пробелу"
     * и взяли оттуда тип запроса.
     * 3.Разбили вторую часть первой строки
     * по символу "/" и взяли оттуда
     * режим работы и имя очереди/топика.
     *
     * @param content непосредственно запрос.
     * @return объект-запрос {@link Req}.
     */
    public static Req of(String content) {
        String param = "";
        String[] splitByNewLine = content.split("\\R");
        String[] splitFirstLineBySpace = splitByNewLine[0].split(" ");
        String[] splitBySlash = splitFirstLineBySpace[1].split("/");
        String reqType = splitFirstLineBySpace[0];
        String poohMode = splitBySlash[1];
        String sourceName = splitBySlash[2];
        if ("POST".equals(reqType)) {
            param = splitByNewLine[splitByNewLine.length - 1];
        }
        if ("GET".equals(reqType) && splitBySlash.length > 3) {
            param = splitBySlash[3];
        }
        return new Req(reqType, poohMode, sourceName, param);
    }

    public String httpRequestType() {
        return httpRequestType;
    }

    public String getPoohMode() {
        return poohMode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getParam() {
        return param;
    }
}
