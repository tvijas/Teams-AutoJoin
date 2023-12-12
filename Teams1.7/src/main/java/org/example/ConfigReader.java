package org.example;

import org.checkerframework.checker.units.qual.C;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ConfigReader {
    public ArrayList<Scheduler> read () throws URISyntaxException {
        ArrayList <Scheduler> tasks = new ArrayList<>();
        // Получаем URI текущего местоположения JAR-файла
        URI jarLocation = TeamsLoginTask.class.getProtectionDomain().getCodeSource().getLocation().toURI();

        // Преобразуем URI в Path
        Path jarPath = Paths.get(jarLocation).toAbsolutePath();

        // Получаем путь к директории JAR-файла
        Path jarDirectory = jarPath.getParent();

        // Собираем путь к файлу config.txt в той же директории, что и JAR-файл
        String configFilePath = jarDirectory.resolve("config.txt").toString();


        try {
            FileReader fileReader = new FileReader(configFilePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                Config config = new Config();
                config.setEmail(line);
                config.setPassword(bufferedReader.readLine());
                config.setRoomName(bufferedReader.readLine());
                config.setDay(Integer.parseInt(bufferedReader.readLine()));
                config.setHour(Integer.parseInt(bufferedReader.readLine()));
                config.setMinute(Integer.parseInt(bufferedReader.readLine()));
                config.setDuration(Integer.parseInt(bufferedReader.readLine()));
                tasks.add(new Scheduler(config));
            }

            bufferedReader.close();
            fileReader.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return tasks;
    }
}
