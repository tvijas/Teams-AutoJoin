package org.example;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;

public class Scheduler {
    private final Config config;

    public Scheduler(Config config) {
        this.config = config;
    }

    public void schedule (){
        // Запускаем задачу, которая будет выполняться каждый понедельник в 6:00 GMT
        Timer timer = new Timer();
        // Устанавливаем временную зону на GMT (или любую другую, подходящую для вас)
        TimeZone gmtTimeZone = TimeZone.getTimeZone("GMT");
        Calendar calendar = Calendar.getInstance(gmtTimeZone);

        calendar.set(Calendar.DAY_OF_WEEK, config.getDay()); // Устанавливаем день недели
        calendar.set(Calendar.HOUR_OF_DAY, config.getHour()); // Устанавливаем часы
        calendar.set(Calendar.MINUTE, config.getMinute());      // Устанавливаем минуты
        calendar.set(Calendar.SECOND, 0);      // Устанавливаем секунды
        Date scheduledTime = calendar.getTime();

        // Если заданное время уже прошло сегодня, то устанавливаем на следующий понедельник
        if (scheduledTime.before(new Date())) {
            calendar.add(Calendar.DAY_OF_WEEK, config.getDay());
            scheduledTime = calendar.getTime();
        }
        // Устанавливаем задачу для повторения каждый понедельник в 6:00 по времени в GMT
        timer.scheduleAtFixedRate(new Task(config), scheduledTime, 7 * 24 * 60 * 60 * 1000); // 7 дней в неделе
    }
}
