package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.units.qual.C;

import java.util.List;

public class TeamsLoginTask {
    private static final Logger logger = LogManager.getLogger(TeamsLoginTask.class);

    public static void main(String[] args) {
       try{
           ConfigReader configReader = new ConfigReader();
           List <Scheduler> tasks = configReader.read();
           for (Scheduler task : tasks) {
               task.schedule();
           }
       }catch (Exception e){
           logger.error(e);
       }
    }

}

