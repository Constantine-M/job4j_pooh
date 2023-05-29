package ru.job4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Данный класс описывает режим queue.
 *
 * <p>В режиме queue имеется одна
 * очередь и много получателей.
 * Если в очередь приходят несколько
 * получателей, то они поочередно
 * получают сообщения из очереди.
 * Каждое сообщение в очереди может
 * быть получено только одним получателем.
 */
public class QueueService implements Service {

    /**
     * Данное поле описывает очередь.
     * Ключом в этой карте является
     * название очереди. Значение -
     * непосредственно очередь.
     */
    private final Map<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();

    /**
     * Данный метод обрабатывает запрос,
     * производит описанные ниже действия
     * и возвращает ответ.
     *
     * <p>В случае с запросом типа "POST"
     * отправитель посылает запрос на добавление
     * данных с указанием очереди {@link Req sourceName}
     * и значением параметра {@link Req param}.
     * Сообщение помещается в конец очереди.
     * Если очереди нет в сервисе, то нужно
     * создать новую и поместить в нее сообщение.
     * 1.Проверил наличие очереди.
     * 2.Если нет - создаем, помещаем сообщение.
     *
     * <p>В случае с запросом типа "GET"
     * получатель посылает запрос на получение
     * данных с указанием очереди. Сообщение
     * забирается из начала очереди и удаляется.
     *
     * @param req запрос.
     * @return ответ {@link Resp}.
     */
    @Override
    public Resp process(Req req) {
        String resultText = "";
        String status = "";
        if ("POST".equals(req.httpRequestType())) {
            if (queue.get(req.getSourceName()) == null) {
                queue.putIfAbsent(req.getSourceName(), new ConcurrentLinkedQueue<>());
                queue.get(req.getSourceName()).offer(req.getParam());
            } else {
                queue.get(req.getSourceName()).offer(req.getParam());
            }
        }
        if ("GET".equals(req.httpRequestType())) {
            resultText = queue.getOrDefault(req.getSourceName(), new ConcurrentLinkedQueue<>()).poll();
            if (resultText == null) {
                resultText = "";
            }
        }
        status = getStatusCode(resultText);
        return new Resp(resultText, status);
    }

    /**
     * Данный метод позволяет получить
     * status code.
     *
     * <p>На данный момент я не могу решить,
     * какой показатель должен быть ключевым
     * и решил проверять наличие сообщения
     * длиной > 0.
     *
     * <p>Т.к. ошибок много, то изначально
     * выглядит неплохим решением вынести
     * подобную проверку отдельно. Рассматривался
     * вариант передачи в метод объекта {@link Req},
     * но пока что это лишнее на мой взгляд.
     * @param resultParam наличие сообщения в "ответе".
     * @return код ошибки.
     */
    private String getStatusCode(String resultParam) {
        return resultParam == null ? "204" : "200";
    }

    public static void main(String[] args) {
        Map<String, ConcurrentLinkedQueue<String>> queue = new ConcurrentHashMap<>();
        queue.putIfAbsent("1", new ConcurrentLinkedQueue<>());
        queue.get("1").offer("first");
        ConcurrentLinkedQueue<String> defaultQueue = new ConcurrentLinkedQueue<>();
        defaultQueue.offer("second");
        String result1 = queue.getOrDefault("1", defaultQueue).poll();
        String result2 = queue.getOrDefault("1", defaultQueue).poll();
        System.out.println(result1);
        System.out.println(result2); /*возвращает null вместо дефолта*/
    }
}
