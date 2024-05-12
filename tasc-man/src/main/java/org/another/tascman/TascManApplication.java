package org.another.tascman;

import org.another.tascman.model.AnderTask;
import org.another.tascman.model.TaskName;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

@SpringBootApplication
public class TascManApplication implements Runnable {

    private boolean isRunning;

    public void start() {
        isRunning = true;
        new Thread(this).start();
    }

    public void stop() {
        isRunning = false;
    }

    @Override
    public void run() {

        Scanner scanner = new Scanner(System.in);
        List<String> commands = new ArrayList<>() {
            {
                add("gTN - get task name");
                add("gAT - get ander task");
                add("pTN - post task name");
                add("pAT - post ander task, перед выполнением команды рекомендуется вызвать gTN");
            }
        };

        final RestTemplate restTemplate = new RestTemplate();


        while (isRunning) {
            String input = scanner.nextLine();
            if (input.equals("gTN")) {
                /**
                 * Получение данных из сущности taskName
                 */
                final RestTemplate gTN = new RestTemplate();
                final ResponseEntity<List<TaskName>> responseGTN = gTN.exchange(
                        "http://localhost:8080/api/gTN",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<TaskName>>() {}
                );

                final List<TaskName> gTNResult = responseGTN.getBody();

                for (TaskName tn : gTNResult) {
                    System.out.println(tn.toString());
                }
            } else if (input.equals("gAT")) {
                /**
                 * Получение данных из сущности anderTask
                 */
                final RestTemplate gAT = new RestTemplate();
                final ResponseEntity<List<AnderTask>> responseGAT = gAT.exchange(
                        "http://localhost:8080/api/gAT",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<AnderTask>>() {}
                );

                final List<AnderTask> gATResult = responseGAT.getBody();

                for (AnderTask an : gATResult) {
                    System.out.println(an.toString());
                }
            } else if (input.equals("pTN")) {
                System.out.println("Введите имя задачи:");
                String postData = scanner.nextLine();
                // Создаем объект TaskName
                TaskName newTaskName = new TaskName();
                newTaskName.setTaskName(postData); // Устанавливаем значение поля taskName

                try {
                    // Отправляем POST-запрос на сервер для добавления новой сущности TaskName
                    ResponseEntity<TaskName> responsePTN = restTemplate.postForEntity(
                            "http://localhost:8080/api/pTN",
                            newTaskName,
                            TaskName.class
                    );
                    // Получаем добавленную сущность TaskName из ответа сервера
                    TaskName createdTaskName = responsePTN.getBody();
                 System.out.println("Сушность TaskName c id" + createdTaskName.getId() +  "успешно добавлена");
                } catch (HttpClientErrorException e) {
                    System.out.println("Ошибка добавление сущности TaskName: " + e.getRawStatusCode() + " " + e.getStatusText());
                }
            } else if (input.equals("pAT")) {
                System.out.println("Введите id главной задачи");
                Long taskId = scanner.nextLong();

                scanner.nextLine();

                final RestTemplate PAT = new RestTemplate();

                // Создаем объект TaskName
                TaskName taskName = new TaskName();
                taskName.setId(taskId);

                System.out.println("Введите подзадачу");
                String textAnderTask = scanner.nextLine();
                // Создаем новый объект AnderTask с желаемыми данными
                AnderTask newAnderTask = new AnderTask();
                newAnderTask.setSubtaskText(textAnderTask); // Устанавливаем значение поля subtaskText
                newAnderTask.setTaskName(taskName); // Устанавливаем связь с объектом TaskName

                try {
                    // Отправляем POST-запрос на сервер для добавления новой сущности AnderTask
                    ResponseEntity<AnderTask> responsePAT = PAT.postForEntity(
                            "http://localhost:8080/api/pAT",
                            newAnderTask,
                            AnderTask.class
                    );
                    // Получаем добавленную сущность AnderTask из ответа сервера
                    AnderTask createdAnderTask = responsePAT.getBody();
                    System.out.println("Сушность AnderTask c id" + createdAnderTask.getId() +  "успешно добавлена");
                } catch (HttpClientErrorException e) {
                    System.out.println("Ошибка добавление сущности AnderTask: " + e.getRawStatusCode() + " " + e.getStatusText());
                }

            } else if (input.equals("dTN")) {
                RestTemplate dTN = new RestTemplate();
                System.out.println("Ведите id задачи");
                Long id = scanner.nextLong();

                String url = "http://localhost:8080/api/dlTN?id=" + id;

                try {
                    // Выполнение запроса DELETE
                    dTN.delete(url);
                    System.out.println("Сущность TaskName с id " + id + " успешно удалена");
                } catch (HttpClientErrorException ex) {
                    System.err.println("Ошибка удаления сущности TaskName: " + ex.getRawStatusCode() + " " + ex.getStatusText());
                }

            } else if (input.equals("dAT")) {
                RestTemplate dAT = new RestTemplate();
                System.out.println("Ведите id подзадачи");
                Long id = scanner.nextLong();

                String url = "http://localhost:8080/api/dlAT?id=" + id;

                try {
                    // Выполнение запроса DELETE
                    dAT.delete(url);
                    System.out.println("Сущность AnderTask с id " + id + " успешно удалена");
                } catch (HttpClientErrorException ex) {
                    System.err.println("Ошибка удаления сущности AnderTask: " + ex.getRawStatusCode() + " " + ex.getStatusText());
                }
            } else {
                for (String com : commands) {
                    System.out.println(com);
                }
            }
        }
        scanner.close();
    }

    public static void main(String[] args) {
        SpringApplication.run(TascManApplication.class, args);

        TascManApplication tascManApplication = new TascManApplication();
        tascManApplication.start();
    }
}
