package USLP02_08.controller;

import USLP02_08.domain.Fertirrega;
import USLP02_08.domain.Setor;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class RegistarRegaAutomatica {

    public static void start(ArrayList<Setor> data, ArrayList<String> timeSchedule, ArrayList<Fertirrega> fertirregas){
        String fileName = "CadernoDeCampo"+ LocalDate.now().getMonth()+".txt";

        Map<LocalDateTime, List<Setor>> map = new HashMap<>();
        Map<LocalDateTime, List<Fertirrega>> mapFer = new HashMap<>();
        List<LocalDateTime> wateringEndTimes = new ArrayList<>();
        for (int i = 1; i <31 ; i++) {
            for (String time:timeSchedule) {
                for (Setor parcela: ControladorRega.checkDay(data,i)) {
                    LocalTime wateringTime = ControladorRega.convertToLocalTime(time);
                    LocalTime finalTimeOfWatering = wateringTime.plusMinutes(Long.parseLong(parcela.time));
                    LocalDateTime localDateTime =LocalDateTime.of(LocalDate.now().getYear(),LocalDate.now().getMonth(),i, finalTimeOfWatering.getHour(), finalTimeOfWatering.getMinute());
                    wateringEndTimes.add(localDateTime);
                    map.putIfAbsent(localDateTime,new ArrayList<>());
                    map.get(localDateTime).add(parcela);
                }
                for (Fertirrega fertirrega: ControladorRega.checkDayFertirrega(fertirregas,i)) {
                    LocalTime wateringTime = ControladorRega.convertToLocalTime(time);
                    LocalTime finalTimeOfWatering = wateringTime.plusMinutes(Long.parseLong(fertirrega.time));
                    LocalDateTime localDateTime =LocalDateTime.of(LocalDate.now().getYear(),LocalDate.now().getMonth(),i, finalTimeOfWatering.getHour(), finalTimeOfWatering.getMinute());
                    wateringEndTimes.add(localDateTime);
                    mapFer.putIfAbsent(localDateTime,new ArrayList<>());
                    mapFer.get(localDateTime).add(fertirrega);
                }
            }
        }
        Collections.sort(wateringEndTimes);


        LocalDateTime today = LocalDateTime.now();
        LocalDateTime dayBefore = LocalDateTime.now();
        List<LocalDateTime> listToSchedule = new ArrayList<>();
        for (LocalDateTime localDateTime:wateringEndTimes) {
            if (localDateTime.equals(today) || localDateTime.isAfter(today)){
                listToSchedule.add(localDateTime);
            }else dayBefore = localDateTime;
        }
        listToSchedule.add(dayBefore);
        Collections.sort(listToSchedule);
        List<LocalDateTime> newListToSchedule = removeDuplicatesUsingStream(listToSchedule);

        Timer timer = new Timer();

        for (LocalDateTime localDateTime1:newListToSchedule) {

            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    try (BufferedWriter writer = new BufferedWriter(new FileWriter("resources/USLP02/" + fileName))) {

                        Set<LocalDateTime> showSet = map.keySet();
                        List<LocalDateTime> showList = new ArrayList<>(showSet);
                        Collections.sort(showList);
                        for (LocalDateTime localDateTime : showList) {
                            if (localDateTime.isBefore(localDateTime1) || localDateTime.equals(localDateTime1)){
                                for (Setor p:map.get(localDateTime)) {
                                    writer.write(String.format("Operação de rega concluida no dia '%s' às '%s:%s' no sector '%s' de duração '%s' min \n", localDateTime.getDayOfMonth(), localDateTime.getHour(), localDateTime.getMinute(),p.name,p.time));
                                }
                                if (mapFer.containsKey(localDateTime)){
                                    for (Fertirrega f:mapFer.get(localDateTime)) {
                                        writer.write(String.format("Operação de fertirrega concluida no dia '%s' às '%s:%s' no sector '%s' de duração '%s' min, com o mix '%s'\n", localDateTime.getDayOfMonth(), localDateTime.getHour(), localDateTime.getMinute(),f.name,f.time,f.mix));
                                    }
                                }
                            }
                            else break;
                        }
                        //System.out.println("\nInformação do Caderno de Campo atualizado no ficheiro:'" + fileName + "' com sucesso!");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            timer.schedule(task, convertToDate(localDateTime1));
        }

        System.out.println("\nA registar automaticamente as regas, verifique o ficheiro:'" + fileName + "'!");
    }

    // Convert LocalDateTime to Date
    private static Date convertToDate(LocalDateTime localDateTime) {
        return java.sql.Timestamp.valueOf(localDateTime);
    }

    private static List<LocalDateTime> removeDuplicatesUsingStream(List<LocalDateTime> originalList) {
        return originalList.stream().distinct().toList();
    }











}
