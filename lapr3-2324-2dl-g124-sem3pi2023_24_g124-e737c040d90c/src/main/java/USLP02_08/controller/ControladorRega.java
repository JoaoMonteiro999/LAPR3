package USLP02_08.controller;

import USLP02_08.domain.Fertirrega;
import USLP02_08.domain.Setor;
import USLP02_08.ui.utils.Utils;

import java.io.*;
import java.time.DateTimeException;
import java.time.LocalTime;
import java.util.*;

public class ControladorRega {

    public static ArrayList<Setor> data = new ArrayList<>();
    public static ArrayList<String> timeSchedule = new ArrayList<>();
    public static ArrayList<Fertirrega> fertirregas = new ArrayList<>();
    public static boolean fertirregaCorrection = false;
    public static boolean written = false;
    public static int smallestDuration;




    private static boolean validateData() {
        if (data.isEmpty()) {
            System.out.print("Selecione o ficheiro primeiro!\n");
            return false;
        }
        return true;
    }
    public static void startAutomatedWatering() {
        if (validateData())
            RegistarRegaAutomatica.start(data, timeSchedule,fertirregas);
    }

    public static void checkWater() {
        if (validateData()) {
            System.out.print("Insira o dia: ");
            String day = Utils.getNumberAsString();
            System.out.print("Insira a hora: ");
            String hour = Utils.getHourAsString();
            System.out.print("Insira o minuto: ");
            String min = Utils.getMinuteAsString();
            System.out.println();

            LocalTime time = LocalTime.of(Integer.parseInt(hour), Integer.parseInt(min));
            String fileName = "tempoDeRega.txt";
            isItWatering(day, time, hour,min,fileName);
        }
    }

    private static void isItWatering(String day, LocalTime time, String hour,String min,String fileName) {
        ArrayList<Setor> wateredOnThisDay = checkDay(data, Integer.parseInt(day));
        ArrayList<Setor> wateredOnThisDayAndHour = checkHour(wateredOnThisDay, timeSchedule,time);
        ArrayList<Setor> newList= eraseSameSetor(wateredOnThisDayAndHour);
        ArrayList<Fertirrega> newFertirregas = checkDayFertirrega(fertirregas,Integer.parseInt(day));
        ArrayList<Fertirrega> newDayHourFertirregas = checkHourFertirrega(newFertirregas,timeSchedule,time);

        printData(newList,time);
        printDataFertirrega(newDayHourFertirregas,time);

        writeDataToFile(newList,newDayHourFertirregas ,time, day, hour, min,fileName);
    }

    private static ArrayList<Setor> eraseSameSetor(ArrayList<Setor> wateredOnThisDayAndHour) {
        // Create a new ArrayList
        ArrayList<Setor> newList = new ArrayList<>();

        // Traverse through the first list
        for (Setor element : wateredOnThisDayAndHour) {

            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) {

                newList.add(element);
            }
        }

        // return the new list
        return newList;
    }

    private static void printData(ArrayList<Setor> wateredOnThisDayAndHour, LocalTime time) {
        if (wateredOnThisDayAndHour.isEmpty())
            System.out.print("Não está a ser regado!\n");
        else
            for (Setor p : wateredOnThisDayAndHour)
                System.out.printf("Sector: %s, Tempo Restante: %d min\n", p.name, getTimeLeft(p, time));
    }
    private static void printDataFertirrega(ArrayList<Fertirrega> wateredOnThisDayAndHour, LocalTime time) {
        if (wateredOnThisDayAndHour.isEmpty())
            System.out.print("Não está a ser realizada nenhuma fertirrega!\n");
        else
            for (Fertirrega p : wateredOnThisDayAndHour)
                System.out.printf("Sector: %s, Tempo Restante: %d min, Mix: %s\n", p.name, getTimeLeft(new Setor(p.name,p.time,p.periodicity), time),p.mix);
    }

    private static void writeDataToFile(ArrayList<Setor> wateredOnThisDayAndHour,ArrayList<Fertirrega> newDayHourFertirregas,LocalTime time, String day, String hour, String min, String fileName) {
                                                                        // "src/main/resources/USLP02/" <- to test |  "resources/USLP02/"
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("resources/USLP02/"+fileName))) {
            if (wateredOnThisDayAndHour.isEmpty())
                writer.write("Não está a ser regado!\n");
            else {
                writer.write(String.format("Day: %s Hour: %s:%s\n",day,hour,min));
                for (Setor p : wateredOnThisDayAndHour)
                    writer.write(String.format("Sector: %s, Tempo Restante: %d min\n", p.name, getTimeLeft(p, time)));

                for (Fertirrega f:newDayHourFertirregas) {
                    writer.write(String.format("Sector: %s, Tempo Restante: %d min, Mix: %s\n", f.name, getTimeLeft(new Setor(f.name,f.time,f.periodicity), time),f.mix));
                }
            }
            System.out.println("\nInformação guardada no ficheiro:'"+fileName+"' com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static int getTimeLeft(Setor p, LocalTime time) {
        int remaining = 0;

        for (String s : timeSchedule) {
            LocalTime startTime = convertToLocalTime(s);
            LocalTime endTime = convertToLocalTime(s).plusMinutes(Integer.parseInt(p.time));
            if (startTime.minusMinutes(1).isBefore(time) && endTime.isAfter(time))
                remaining = endTime.getMinute() - time.getMinute();
        }
        return remaining;
    }

    static LocalTime convertToLocalTime(String timeSchedule) {
        String[] arr = timeSchedule.split(":");
        return LocalTime.of(Integer.parseInt(arr[0]), Integer.parseInt(arr[1]));
    }

    public static ArrayList<Setor> checkHour(ArrayList<Setor> parcelasRegadasNoDia, ArrayList<String> horasDeRega, LocalTime timeToCheck) {
        ArrayList<Setor> parcelasRegadasNaHora = new ArrayList<>();
        ArrayList<LocalTime> lt = new ArrayList<>();

        for (String s: horasDeRega)
            lt.add(convertToLocalTime(s));

        for (LocalTime wateringTime: lt) {
            for (Setor parcela:parcelasRegadasNoDia) {
                long duration = Long.parseLong(parcela.time);
                LocalTime finalTimeOfWatering = wateringTime.plusMinutes(duration);
                if (timeToCheck.isAfter(wateringTime.minusMinutes(1)) && timeToCheck.isBefore(finalTimeOfWatering)) parcelasRegadasNaHora.add(parcela);
            }
        }
        return parcelasRegadasNaHora;
    }
    public static ArrayList<Fertirrega> checkHourFertirrega(ArrayList<Fertirrega> parcelasRegadasNoDia, ArrayList<String> horasDeRega, LocalTime timeToCheck) {
        ArrayList<Fertirrega> parcelasRegadasNaHora = new ArrayList<>();
        ArrayList<LocalTime> lt = new ArrayList<>();

        for (String s: horasDeRega)
            lt.add(convertToLocalTime(s));

        for (LocalTime wateringTime: lt) {
            for (Fertirrega fertirrega:parcelasRegadasNoDia) {
                long duration = Long.parseLong(fertirrega.time);
                LocalTime finalTimeOfWatering = wateringTime.plusMinutes(duration);
                if (timeToCheck.isAfter(wateringTime.minusMinutes(1)) && timeToCheck.isBefore(finalTimeOfWatering)) parcelasRegadasNaHora.add(fertirrega);
            }
        }
        return parcelasRegadasNaHora;
    }

    public static ArrayList<Setor> checkDay(ArrayList<Setor> parcelasIniciais, int dia){
        ArrayList<Setor> parcelasRegadasNoDia = new ArrayList<>();
        for (Setor parcela: parcelasIniciais) {
            if (parcela.periodicity.equals("T")) parcelasRegadasNoDia.add(parcela);
            if (parcela.periodicity.equals("P") && dia % 2 ==0) parcelasRegadasNoDia.add(parcela);
            if (parcela.periodicity.equals("I") && dia % 2 !=0) parcelasRegadasNoDia.add(parcela);
            if (parcela.periodicity.equals("3") && ((dia - 1) % 3) == 0) parcelasRegadasNoDia.add(parcela);
        }
        return parcelasRegadasNoDia;
    }

    public static void selectFile() {
        try {
            System.out.print("\nNome do ficheiro: ");
            String filename = Utils.sc.nextLine();
            timeSchedule.clear();
            written = false;
            fertirregaCorrection = false;
            data = readCSV(filename);
            if (data.isEmpty())
                System.out.print("Ficheiro inválido\n");
            else {
                System.out.println("Ficheiro selecionado corretamente!");
                createPlan();
                createFertirregaPlan();
            }

        }catch (IndexOutOfBoundsException e) {
            System.out.println("Definição incorreta de sector!");
        }catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (DateTimeException e) {
            System.out.println("Tempo de rega inválido, deverá ser entre 00:00 e 23:59");
        }catch (Exception e) {
            System.out.println("Nome do ficheiro inválido");
        }
    }

    private static ArrayList<Setor> readCSV(String filename) {
        ArrayList<Setor> arr = new ArrayList<>();
        ArrayList<Fertirrega> arrFer = new ArrayList<>();

        Scanner fileReader = null;

        try {
            fileReader = new Scanner(new File("resources/USLP02/"+filename));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        String[] times = fileReader.nextLine().split(",");
        if(validateTimeSchedule(times)) timeSchedule.addAll(List.of(times));
        if (timeSchedule.size() > 1) durationConflictTime();
        while (fileReader.hasNextLine()) {
            String[] line = fileReader.nextLine().split(",");

            if (line.length == 5){
                if (validateFertirrega(line,arr)){
                    arrFer.add(new Fertirrega(line[0], line[1], line[2],line[3],line[4]));
                    line = fertirregaToSetor(line);
                }
            }

            if (validateSector(line,arr)) arr.add(new Setor(line[0], line[1], line[2]));
        }

        fertirregas = arrFer;
        return arr;
    }

    private static boolean validateFertirrega(String[] line, ArrayList<Setor> arr) {

        if (line[0].isEmpty()) throw new IllegalArgumentException ("Deverá existir uma designação de sector");
        if (line[1].isEmpty()) throw new IllegalArgumentException ("Deverá existir um tempo de rega");
        if (line[2].isEmpty()) throw new IllegalArgumentException ("Deverá existir uma periodicidade");
        if (line[3].isEmpty()) throw new IllegalArgumentException ("Deverá existir um mix");
        if (line[4].isEmpty()) throw new IllegalArgumentException ("Deverá existir uma recorrência");
        if (Integer.parseInt(line[1]) < 1) throw new IllegalArgumentException ("Deverá existir um tempo de rega acima de 0");
        if (!line[2].equals("T") && !line[2].equals("P") && !line[2].equals("I") && !line[2].equals("3"))
            throw new IllegalArgumentException ("Deverá existir uma periodicidade igual a 'T','P','I' ou '3'");
        if (Integer.parseInt(line[4]) < 1 && Integer.parseInt(line[4]) > 29)
            throw new IllegalArgumentException ("Deverá existir uma recorrência entre 1 e 30");
        if (line.length != 5) throw new IllegalArgumentException ("Deverá existir apenas designação de sector, tempo de rega, periodicidade, mix e recorrência");
        if (hasDuplicatesSectors(arr,line)) throw new IllegalArgumentException("Não deverá existir designações de sector iguais!");
        if (Integer.parseInt(line[1]) > smallestDuration) throw new IllegalArgumentException("Existem setores com tempo de rega inválidos devido ao intervalo entre horas de rega.");

        return true;
    }

    private static void durationConflictTime() {

        ArrayList<LocalTime> times = new ArrayList<>();
        for (String s: timeSchedule) {
            LocalTime localTime = convertToLocalTime(s);
            times.add(localTime);
        }
        Collections.sort(times);

        //get a diferenca menor entre os tempos
        if (times.isEmpty()) throw new IllegalArgumentException("Indique uma hora de rega!");

        int smallestDifference = 1500;

        for (int i = 0; i < times.size() - 1; i++) {
            LocalTime current = times.get(i);
            LocalTime next = times.get(i + 1);

            // Calculate the time difference
            int diffMinutes = (int) java.time.Duration.between(current, next).toMinutes();

            // Check if it is the smallest difference so far
            if (diffMinutes < smallestDifference) {
                smallestDifference = diffMinutes;
            }
        }
        smallestDuration = smallestDifference;
    }

    private static String[] fertirregaToSetor(String[] line){
        String[] newLine = new String[line.length-2];
        System.arraycopy(line, 0, newLine, 0, (newLine.length ));
        return newLine;
    }


    private static boolean validateSector(String[] line,ArrayList<Setor> arr) {

        if (line[0].isEmpty()) throw new IllegalArgumentException ("Deverá existir uma designação de sector");
        if (line[1].isEmpty()) throw new IllegalArgumentException ("Deverá existir um tempo de rega");
        if (line[2].isEmpty()) throw new IllegalArgumentException ("Deverá existir uma periodicidade");
        if (Integer.parseInt(line[1]) < 1) throw new IllegalArgumentException ("Deverá existir um tempo de rega acima de 0");
        if (!line[2].equals("T") && !line[2].equals("P") && !line[2].equals("I") && !line[2].equals("3"))
            throw new IllegalArgumentException ("Deverá existir uma periodicidade igual a 'T','P','I' ou '3'");
        if (line.length != 3) throw new IllegalArgumentException ("Deverá existir apenas designação de sector, tempo de rega e periodicidade");
        if (hasDuplicatesSectors(arr,line)) throw new IllegalArgumentException("Não deverá existir designações de sector iguais!");
        if (Integer.parseInt(line[1]) > smallestDuration) throw new IllegalArgumentException("Não deverá existir designações de sector iguais!");

        return true;
    }
    private static boolean hasDuplicatesSectors(ArrayList<Setor> arr, String[] line) {
        for (Setor p : arr)
            if (p.name.equals(line[0])) return true;
        return false;
    }

    private static boolean validateTimeSchedule(String[] times) {

        if (times.length == 0) throw new IllegalArgumentException("Tempo de rega inválido, deverá existir um tempo de rega");
        for (String time:times)
            if (convertToLocalTime(time).isAfter(convertToLocalTime("23:59")) || convertToLocalTime(time).isBefore(convertToLocalTime("00:00"))){
                throw new IllegalArgumentException("Tempo de rega inválido, deverá ser entre 00:00 e 23:59");
            }
        if (hasDuplicates(times)) throw new IllegalArgumentException("Não deverá existir horas de rega iguais");


        return true;
    }

    private static boolean hasDuplicates(String[] array) {
        Set<String> set = new HashSet<>();
        for (String element : array)
            if (!set.add(element)) return true; // If the element is already in the set, it's a duplicate
        return false;// No duplicates found
    }

    private static void createPlan() {
        String fileName = "PlanoDeRega.txt";
                                                                                // "src/main/resources/USLP02/" <- to test |  "resources/USLP02/"
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("resources/USLP02/"+fileName))) {

            for (int i = 1; i < 31; i++) {
                writer.write(String.format("Dia: %d\nHora(s):", i));
                for (String timeScheduled:timeSchedule) {
                    writer.write(String.format(" %s;", timeScheduled));
                }
                ArrayList<Setor> dayList = checkDay(data, Integer.parseInt(String.valueOf(i)));
                if (dayList.isEmpty())
                    writer.write("\nNão existem regas planeadas para este dia\n");
                else{
                    writer.write("\n");
                    writer.write(" ----------------------------------\n");
                    writer.write(String.format("|%-12s|%-21s|%n", "Sector", "Duração de rega (min)"));
                    writer.write("|------------|---------------------|\n");
                    for (Setor p : dayList)
                        writer.write(String.format("|%-12s|%-21s|%n", p.name, p.time));
                    writer.write(" ----------------------------------\n");

                }

                writer.write("\n");
            }


            System.out.println("Informação do Plano de Rega guardada no ficheiro:'"+fileName+"' com sucesso!");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private static void createFertirregaPlan() {
        String fileName = "PlanoDeFertirrega.txt";
                                                                            // "src/main/resources/USLP02/" <- to test |  "resources/USLP02/"
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("resources/USLP02/"+fileName))) {

            for (int i = 1; i < 31; i++) {
                writer.write(String.format("Dia: %d\nHora(s):", i));
                for (String timeScheduled:timeSchedule) {
                    writer.write(String.format(" %s;", timeScheduled));
                }
                ArrayList<Fertirrega> dayList = checkDayFertirrega(fertirregas, Integer.parseInt(String.valueOf(i)));
                if (dayList.isEmpty())
                    writer.write("\nNão existem fertirregas planeadas para este dia\n");
                else{
                    writer.write("\n");
                    writer.write(" -----------------------------------------------\n");
                    writer.write(String.format("|%-12s|%-23s|%-10s|%n", "Sector", "Duração de rega (min)", "Mix"));
                    writer.write("|------------|-----------------------|----------|\n");
                    for (Fertirrega p : dayList) {
                        writer.write(String.format("|%-12s|%-23s|%-10s|%n", p.name, p.time, p.mix));
                    }
                    writer.write(" -----------------------------------------------\n");
                    if (fertirregaCorrection){
                        writer.write("Algumas datas de fertirrega não coincidem com datas de rega, corrija o fichero de entrada!\n");
                        written = true;
                    }
                    fertirregaCorrection = false;
                }

                writer.write("\n");
            }


            System.out.println("Informação do Plano de Fertirrega guardada no ficheiro:'"+fileName+"' com sucesso!");
            if (written) System.out.println("Algumas datas de fertirrega não coincidem com datas de rega, corrija o fichero de entrada!");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    public static ArrayList<Fertirrega> checkDayFertirrega(ArrayList<Fertirrega> fertirregas, int dia) {

        ArrayList<Fertirrega> parcelasRegadasNoDia = new ArrayList<>();
        for (Fertirrega fertirrega: fertirregas) {
        int rec = Integer.parseInt(fertirrega.recurrence);
            if (((dia) % rec) == 0){
                if (checkDayThisSector(fertirrega,dia)){
                    parcelasRegadasNoDia.add(fertirrega);
                }else fertirregaCorrection = true;
            }
        }

        return parcelasRegadasNoDia;
    }

    public static boolean checkDayThisSector(Fertirrega fertirrega, int dia){
        if (fertirrega.periodicity.equals("T")) return true;
        if (fertirrega.periodicity.equals("P") && dia % 2 ==0) return true;
        if (fertirrega.periodicity.equals("I") && dia % 2 !=0) return true;
        return fertirrega.periodicity.equals("3") && ((dia - 1) % 3) == 0;
    }



}
