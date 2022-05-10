package nesne.proje;

import nesne.proje.pojo.User;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception{
        IAuthenticator authenticator = new Authenticator(new DAO());
        Scanner sc = new Scanner(System.in);
        User user = null;
        while (user == null) {
            clearScreen();
            String username;
            String password;
            System.out.println("Kullanici adi ve sifre giriniz");
            System.out.print("Kullanici adi: ");
            username = sc.nextLine();
            System.out.print("sifre: ");
            password = sc.nextLine();
            if (authenticator.isRegistered(username, password)) {
                user = new User(username, password);
            } else {
                System.out.println("Yalnis sifre yada kullanici adi");
            }
        }
        SmartDevice device = new SmartDevice.Builder().addEyleyici(new Eyleyici()).addSicaklikAlgilayici(new SicaklikAlgilayici()).build();
        AgArayuz arayuz = new AgArayuz(device, user);
        device.sougtucuAc();
        Thread.sleep(500);
        System.out.println("Sicaklik: " + arayuz.sicaklikGonder());
        while (true) {
            System.out.println("S -> Sicaklik oku\nA -> sogutucu ac\nK -> sogutucu kapat\nE -> program kapat");
            System.out.print("lutfen seciniz: ");
            String cmd = sc.nextLine();
            switch (cmd) {
                case "S", "s" -> System.out.println("Sicaklik: " + arayuz.sicaklikGonder());
                case "A", "a" -> arayuz.sougtucuAc();
                case "K", "k" -> arayuz.sougtucuKapat();
                case "e", "E" -> System.exit(0);
            }
        }
    }

    private static void clearScreen() {
        try{
            String operatingSystem = System.getProperty("os.name"); //Check the current operating system

            if(operatingSystem.contains("Windows")){
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
                Process startProcess = pb.inheritIO().start();
                startProcess.waitFor();
            } else {
                ProcessBuilder pb = new ProcessBuilder("clear");
                Process startProcess = pb.inheritIO().start();

                startProcess.waitFor();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
