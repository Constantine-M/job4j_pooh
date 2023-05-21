package ru.job4j;

/**
 * Данный класс описывает
 * ответ от сервера.
 */
public class Resp {

    /**
     * Данное поле описывает текст
     * в ответе от сервера.
     */
    private final String text;

    /**
     * Данное поле описывает
     * статус ответа.
     *
     * <p>Например 200 в случае
     * если запрос прошел и 204,
     * если нет данных.
     */
    private final String status;

    public Resp(String text, String status) {
        this.text = text;
        this.status = status;
    }

    public String text() {
        return text;
    }

    public String status() {
        return status;
    }
}
