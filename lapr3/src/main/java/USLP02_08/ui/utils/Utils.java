package USLP02_08.ui.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Paulo Maio <pam@isep.ipp.pt>
 */
public class Utils {

    static public String readLineFromConsole(String prompt) {
        try {
            System.out.println("\n" + prompt);

            InputStreamReader converter = new InputStreamReader(System.in);
            BufferedReader in = new BufferedReader(converter);

            return in.readLine();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    static public int readIntegerFromConsole(String prompt) {
        do {
            try {
                String input = readLineFromConsole(prompt);

                int value = Integer.parseInt(input);

                return value;
            } catch (NumberFormatException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (true);
    }

    static public double readDoubleFromConsole(String prompt) {
        do {
            try {
                String input = readLineFromConsole(prompt);

                double value = Double.parseDouble(input);

                return value;
            } catch (NumberFormatException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (true);
    }

    static public Date readDateFromConsole(String prompt) {
        do {
            try {
                String strDate = readLineFromConsole(prompt);

                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");

                Date date = df.parse(strDate);

                return date;
            } catch (ParseException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (true);
    }

    static public boolean confirm(String message) {
        String input;
        do {
            input = Utils.readLineFromConsole("\n" + message + "\n");
        } while (!input.equalsIgnoreCase("s") && !input.equalsIgnoreCase("n"));

        return input.equalsIgnoreCase("s");
    }

    static public Object showAndSelectOne(List list, String header) {
        showList(list, header);
        return selectsObject(list);
    }

    static public int showAndSelectIndex(List list, String header) {
        showList(list, header);
        return selectsIndex(list);
    }

    static public void showList(List list, String header) {
        System.out.println(header);

        int index = 0;
        for (Object o : list) {
            index++;

            System.out.println(index + ". " + o.toString());
        }
    }

    static public Object selectsObject(List list) {
        String input;
        Integer value;
        do {
            input = Utils.readLineFromConsole("Type your option: ");
            value = Integer.valueOf(input);
        } while (value < 0 || value > list.size());

        if (value == 0) {
            return null;
        } else {
            return list.get(value - 1);
        }
    }

    static public int selectsIndex(List list) {
        String input;
        Integer value;
        do {
            input = Utils.readLineFromConsole("Selecione uma opção: ");
            value = Integer.valueOf(input);
        } while (value < 0 || value > list.size());

        return value - 1;
    }

    static public int showAndSelectIndex2(List list, String header) {
        showList(list, header);
        return readValidIntInInterval(list.size());
    }
    static public int showAndSelectIndex3(List list, String header) {
        showList(list, header);
        return readValidIntInInterval2(list.size());
    }
    public static int readValidIntInInterval2(int valorMax) {
        Scanner scanner = new Scanner(System.in);
        int input = -1;

        while (input < 0 || input > valorMax) {

            System.out.println("\nO valor deve ser maior ou igual a 0 e menor ou igual a " + valorMax + " :");

            try {
                input = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Valor Inválido");
                scanner.nextLine();
                input = -1;
            }
        }

        return input;
    }

    public static int readValidPositiveInt() {
        Scanner scanner = new Scanner(System.in);
        int input;

        do {
            System.out.println("\nO valor deve ser maior que 0 :");

            try {
                input = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Valor Inválido");
                input = 0;
            }

        } while (input <= 0);

        return input;
    }

    public static int readValidIntInInterval(int valorMax) {
        Scanner scanner = new Scanner(System.in);
        int input = 0;

        while (input <= 0 || input > valorMax) {

            System.out.println("\nO valor deve ser maior que 0 e menor ou igual a " + valorMax + " :");

            try {
                input = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Valor Inválido");
                scanner.nextLine();
                input = 0;
            }
        }

        return input;
    }


    public static double readValidPositiveDoubleWithLabel(String label) {
        System.out.print(label);

        Scanner scanner = new Scanner(System.in);
        double input;

        do {
            System.out.println("\nO valor deve ser maior que 0 :");

            try {
                input = scanner.nextDouble();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Valor Inválido");
                scanner.nextLine();
                input = 0;
            }

        } while (input <= 0);

        return input;
    }

    public static double readValidDoubleInInterval(double valorMax) {
        Scanner scanner = new Scanner(System.in);
        double input = 0;

        while (input <= 0 || input > valorMax) {

            System.out.println("\nO valor deve ser maior que 0 e menor ou igual a " + valorMax + ": (Utilizar vírgula em valores decimais Ex: 0,1)");

            try {
                input = scanner.nextDouble();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Valor Inválido");
                scanner.nextLine();
                input = 0;
            }
        }
        return input;
    }

    public static Date readValidDateWithLabel(String label) {
        System.out.println(label);
        Scanner scanner = new Scanner(System.in);
        boolean flag = false;
        Date date = null;

        while (!flag) {
            System.out.println("A data deve encontrar-se no seguinte formato (dd-MM-yyyy)");

            try {
                String strDate = scanner.next();

                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                df.setLenient(false);

                date = df.parse(strDate);
                flag = true;
            } catch (ParseException e) {
                System.out.println("Data Inválida.");
                flag = false;
            }
        }

        return date;
    }


    public static Scanner sc = new Scanner(System.in);
    public static String getNumberAsString() {
        do {
            try {
                String num = sc.nextLine();
                int a = Integer.parseInt(num);
                if (a < 0 || a > 30)
                    throw new Exception();
                return num;
            } catch (Exception e) {
                System.out.print("Número Inválido\n");
            }
        } while (true);
    }

    public static String getHourAsString() {
        do {
            try {
                String num = sc.nextLine();
                int a = Integer.parseInt(num);
                if (a < 0 || a > 24)
                    throw new Exception();
                return num;
            } catch (Exception e) {
                System.out.print("Número Inválido\n");
            }
        } while (true);
    }

    public static String getMinuteAsString() {
        do {
            try {
                String num = sc.nextLine();
                int a = Integer.parseInt(num);
                if (a < 0 || a > 59)
                    throw new Exception();
                return num;
            } catch (Exception e) {
                System.out.print("Número Inválido\n");
            }
        } while (true);
    }









}

