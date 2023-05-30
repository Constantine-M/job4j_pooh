package ru.job4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Данный класс описывает режим topic.
 *
 * <p>В данном режиме для каждого
 * потребителя своя будет уникальная
 * очередь с данными, в отличие
 * от режима "queue"
 */
public class TopicService implements Service {

    /**
     * Данное поле описывает хранилище
     * топиков. В этой структуре
     * 1-ым ключом будет являться уже
     * название топика (weather например),
     * а 2-ым ключом внутри карты будет
     * являться название очереди. Напомню,
     * что очередь у каждого клиента своя.
     * Т.е. например очереди client1 или
     * client888 и.т.д.
     */
    private final Map<String, ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>> topics = new ConcurrentHashMap<>();

    /**
     * Данный метод обрабатывает запрос,
     * производит описанные ниже действия
     * и возвращает ответ.
     *
     * <p>В случае с запросом типа "POST"
     * 1.Если топика нет в сервисе, то пропускаем.
     * 2.Если топик есть, то проходимся по
     * всей карте ConcurrentHashMap<String, ConcurrentLinkedQueue<String>>
     * и помещаем сообщение в каждую очередь,
     * т.к. очереди для каждого получателя
     * индивидуальны.
     *
     * <p>В случае с запросом типа "GET"
     * 1.Если топик отсутствует, то создаем
     * новый топик.
     * 2.Поле param в {@link Req} является
     * еще и своеобразным идентификатором
     * очереди получателя.
     * 3.Если этой очереди в сервисе нет -
     * создаем эту очередь.
     * 4.Получаем сообщение из конкретной
     * очереди.
     * 5.Если содержимое сообщения != null,
     * то возвращаем его.
     *
     * @param req запрос {@link Req}.
     * @return ответ {@link Resp}.
     */
    @Override
    public Resp process(Req req) {
        String text = "";
        String status = "";
        if ("POST".equals(req.httpRequestType())) {
            if (getTopic(req) != null) {
                getTopic(req).forEach((k, v) -> v.offer(req.getParam()));
            }
        }
        if ("GET".equals(req.httpRequestType())) {
            topics.putIfAbsent(req.getSourceName(), new ConcurrentHashMap<>());
            if (getTopic(req).get(req.getParam()) == null) {
                getTopic(req).putIfAbsent(req.getParam(), new ConcurrentLinkedQueue<>());
            }
            text = topics.getOrDefault(req.getSourceName(), new ConcurrentHashMap<>())
                    .get(req.getParam())
                    .poll();
        }
        status = getStatusCode(text);
        return new Resp(text == null ? "" : text, status);
    }

    private ConcurrentHashMap<String, ConcurrentLinkedQueue<String>> getTopic(Req req) {
        return topics.get(req.getSourceName());
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
}
