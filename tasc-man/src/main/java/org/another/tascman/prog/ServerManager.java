package org.another.tascman.prog;


import org.another.tascman.model.AnderTask;
import org.another.tascman.model.TaskName;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.http.HttpRequest.BodyPublishers;


import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ServerManager implements Runnable {

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
                add("dTN - delete task name, удаление происходит по id, так же удаляются все подзадачи");
                add("dAT - delete ander task, удаление происходит по id");
                add("dAllTN - all delete task name");
                add("dAllAT - all delete ander task");
                add("gTAT - Получить все подзадачи по главной задаче");
                add("ptTN - patch запрос для изменения имени главной задачи");
                add("ptAT - patch запрос для изменнения поздазачи");
                add("StopS - завершение работы приложения и сервера");

            }
        };

        RestTemplate restTemplate = new RestTemplate();


        while (isRunning) {
            String input = scanner.nextLine();
            if (input.equals("gTN")) {
                /**
                 * Получение данных из сущности taskName
                 */
                RestTemplate gTN = new RestTemplate();
                ResponseEntity<List<TaskName>> responseGTN = gTN.exchange(
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
                RestTemplate gAT = new RestTemplate();
                ResponseEntity<List<AnderTask>> responseGAT = gAT.exchange(
                        "http://localhost:8080/api/gAT",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<AnderTask>>() {}
                );

                List<AnderTask> gATResult = responseGAT.getBody();

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
                    System.out.println("Сушность TaskName успешно добавлена");
                } catch (HttpClientErrorException e) {
                    System.err.println("Ошибка добавление сущности TaskName: " + e.getRawStatusCode() + " " + e.getStatusText());
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
                    System.out.println("Сушность AnderTask успешно добавлена");
                } catch (HttpClientErrorException e) {
                    System.err.println("Ошибка добавление сущности AnderTask: " + e.getRawStatusCode() + " " + e.getStatusText());
                }

            } else if (input.equals("dTN")) {
                /**
                 * Удаление task name по id, так же происходит удаление всех связванных подзадач
                 */
                RestTemplate dTN = new RestTemplate();
                System.out.println("Ведите id задачи");
                Long id = scanner.nextLong();

                String url = "http://localhost:8080/api/dlTN?id=" + id;

                try {
                    // Выполнение запроса DELETE
                    dTN.delete(url);
                    System.out.println("Сущность TaskName успешно удалена");
                } catch (HttpClientErrorException ex) {
                    System.err.println("Ошибка удаления сущности TaskName: " + ex.getRawStatusCode() + " " + ex.getStatusText());
                }

            } else if (input.equals("dAT")) {
                /**
                 * Удадение поздзадач по id
                 */
                RestTemplate dAT = new RestTemplate();
                System.out.println("Ведите id подзадачи");
                Long id = scanner.nextLong();

                String url = "http://localhost:8080/api/dlAT?id=" + id;

                try {
                    // Выполнение запроса DELETE
                    dAT.delete(url);
                    System.out.println("Сущность AnderTask успешно удалена");
                } catch (HttpClientErrorException ex) {
                    System.err.println("Ошибка удаления сущности AnderTask: " + ex.getRawStatusCode() + " " + ex.getStatusText());
                }
            } else if (input.equals("dAllTN")) {
                /**
                 * Удаление всх записей task name, а также всех связанных подзадач
                 */
                RestTemplate dAllTN = new RestTemplate();
                System.out.println("Будут удалены все главные задачи включя подзадачи");

                String url = "http://localhost:8080/api/dAllTN";

                try {
                    dAllTN.delete(url);
                    System.out.println("Успешно удалены все главные записи");
                } catch (HttpClientErrorException e) {
                    System.err.println("Ошибка удаления: " + e.getRawStatusCode() + " " + e.getStatusText());
                }
            } else if (input.equals("dAllAT")) {
                /**
                 * Удаление всех подзадач
                 */
                RestTemplate dAllAT = new RestTemplate();
                System.out.println("Будут удалены все подзадачи");

                String url = "http://localhost:8080/api/dAllAT";

                try {
                    dAllAT.delete(url);
                    System.out.println("Успешно удалены все подзадачи");
                } catch (HttpClientErrorException e) {
                    System.err.println("Ошибка удаления: " + e.getRawStatusCode() + " " + e.getStatusText());
                }
            } else if (input.equals("gTAT")) {
                RestTemplate gTAT = new RestTemplate();
                System.out.println("Введите id главной задачи");
                Long id = scanner.nextLong();

                String url = "http://localhost:8080/api/gTAT?id=" + id;

                ResponseEntity<List<AnderTask>> responseGTAT = gTAT.exchange(
                        url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<AnderTask>>() {}
                );

                List<AnderTask> gTATResult = responseGTAT.getBody();

                for (AnderTask gTan : gTATResult) {
                    System.out.println(gTan.toStringTaskNameForId());
                }

            } else if (input.equals("StopS")) {
                System.out.println("Завершение работы приложения...");
                stop();
                System.exit(0);
            } else if (input.equals("ptTN")) {
                System.out.println("Введите имя главной задачи: ");
                String oldTaskName = scanner.nextLine();

                RestTemplate gTAT = new RestTemplate();

                String url = "http://localhost:8080/api/gTND?taskName=" + oldTaskName;

                ResponseEntity<List<TaskName>> responseGTAT = gTAT.exchange(
                        url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<TaskName>>() {}
                );

                List<TaskName> gTATResult = responseGTAT.getBody();

                int statusCode = responseGTAT.getStatusCode().value();

                HttpClient client = HttpClient.newBuilder()
                        .version(HttpClient.Version.HTTP_2)
                        .connectTimeout(Duration.ofSeconds(10))
                        .build();

                if (statusCode == 200) {
                    System.out.println("Задача существует");

                    System.out.println("Введите нове имя для здачи");
                    String newTaskName = scanner.nextLine();

                    String patchURL = "http://localhost:8080/api/ptTN";
                    URI uri = URI.create(patchURL);

                    //Создание json запроса
                    String json = "{" + "\n" +
                            "\"oldName\":" + "\"" + oldTaskName + "\"," + "\n" +
                            "\"newName\":" + "\"" + newTaskName + "\"" + "\n" +
                            "}";

                    try {
                        //Перадача тела запроса
                        HttpRequest requestPTNT = HttpRequest.newBuilder()
                                .uri(uri)
                                .timeout(Duration.ofSeconds(1))
                                .header("Content-Type", "application/json")
                                .method("PATCH", BodyPublishers.ofString(json))
                                .build();

                        //Отправка запроса
                        HttpResponse<String> responsePTNT = client.send(requestPTNT, BodyHandlers.ofString());

                        if (responsePTNT.statusCode() == 202) {
                            System.out.println("Status code: " + responsePTNT.statusCode() + " - update");
                        } else {
                            System.out.println("Status code: " + responsePTNT.statusCode() + " - no update " + responsePTNT.body()) ;
                        }

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    System.out.println("Задача не была найдена: " + statusCode);
                }

            } else if (input.equals("ptAT")) {
                RestTemplate ptAT = new RestTemplate();
                System.out.println("Будут выведены все подзадачи для вашего удобства");

                String url = "http://localhost:8080/api/gAT";

                ResponseEntity<List<AnderTask>> response = restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<AnderTask>>() {}
                );

                List<AnderTask> tasks = response.getBody();
                if (tasks != null) {
                    for (AnderTask task : tasks) {
                        System.out.println(task.toStringTaskNameForId());
                    }
                }

                //Создание клиента
                HttpClient client = HttpClient.newBuilder()
                                .version(HttpClient.Version.HTTP_2)
                                        .connectTimeout(Duration.ofSeconds(10))
                                                .build();

                System.out.println("Введите id подзадачи для её изменения:");
                Long id = scanner.nextLong();

                scanner.nextLine();

                System.out.println("Введите новый текст подзадачи:");
                String newTaskText = scanner.nextLine();

                String patchUrl = "http://localhost:8080/api/ptAT";
                URI uri = URI.create(patchUrl);
                // Создание json запроса
                String json = "{" + "\n" + "\"id\":" + id + "," +
                        "\n" + "\"subtaskText\":" + "\"" + newTaskText + "\"" +
                        "\n" + "}";

                try {
                    //Передача тела запроса
                    HttpRequest requestPatchTN = HttpRequest.newBuilder()
                            .uri(uri)
                            .timeout(Duration.ofSeconds(1))
                            .header("Content-Type", "application/json")
                            .method("PATCH", BodyPublishers.ofString(json))
                            .build();

                    //Отправка запроса
                    HttpResponse<String> responsePTAT = client.send(requestPatchTN, BodyHandlers.ofString());

                    if (responsePTAT.statusCode() == 202) {
                        System.out.println("Status code: " + responsePTAT.statusCode() + " - update");
                    } else {
                        System.out.println("Status code: " + responsePTAT.statusCode() + " - no update" + responsePTAT.body());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("Произошла ошибка: " + e.getMessage());
                }
            } else {
                for (String com : commands) {
                    System.out.println(com);
                }
            }
        }
        scanner.close();
    }
}
