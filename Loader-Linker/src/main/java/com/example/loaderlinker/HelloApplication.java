package com.example.loaderlinker;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;


public class HelloApplication extends Application {
    static ObservableList<Table> data = FXCollections.observableArrayList();
    public  static void SIC() throws IOException {
        FileReader file = new FileReader("input.txt");
        BufferedReader read = new BufferedReader(file);
        String s = read.readLine();
        String t="";
        String[] y = s.split(" ");
        if (!y[2].endsWith("0")) {
            t =y[2].substring(1,5)+"0";
        }
        else{
            t=y[2];
        }
        int a = Integer.parseInt(t, 16);
        int b = Integer.parseInt(y[3], 16);
        b=b+a;
        ArrayList<String> addresses = new ArrayList<>();
        int i = 0;
        while (a < b) {
            addresses.add(Integer.toHexString(a).toUpperCase(Locale.ROOT));
            a += 16;
            i++;
        }
        ArrayList<String> codes = new ArrayList<>();
        int n=0;
        int CurrentAddr = Integer.parseInt(addresses.get(0),16);
        while (!(s = read.readLine()).startsWith("E")) {
            String[] str=s.split(" ");
            if (Integer.parseInt(str[1],16)!=CurrentAddr){
                int x=Integer.parseInt(str[1],16)-CurrentAddr;
                n+=x;
                for(int m=0;m<x;m++)
                    codes.add("XX");
                CurrentAddr=Integer.parseInt(str[1],16);
            }
            String ObjectCodes = s.replaceAll(" ", "");
            ObjectCodes = ObjectCodes.substring(9);
            for (int d = 0; d < ObjectCodes.length(); d += 2) {
                codes.add(ObjectCodes.substring(d, Math.min(ObjectCodes.length(), d+2)));
            }
            n+=Integer.parseInt(str[2],16);
            CurrentAddr+=Integer.parseInt(str[2],16);
        }
        file.close();

        while((codes.size()%16)!=0){
            codes.add("XX");
        }
        int x=0;
        for (int h = 0; h < codes.size()-15 ; h+=16) {
            Table table =new Table(addresses.get(x),codes.get(h),codes.get(h+1),codes.get(h+2),codes.get(h+3)
                    ,codes.get(h+4),codes.get(h+5),codes.get(h+6),codes.get(h+7),codes.get(h+8),codes.get(h+9)
                    ,codes.get(h+10),codes.get(h+11),codes.get(h+12),codes.get(h+13),codes.get(h+14),codes.get(h+15));
            x++;
            data.add(table);
        }
        launch();
    }
    public  static void SICXE() throws IOException {
        FileInputStream file = new FileInputStream("input.txt");
        Scanner read = new Scanner(file);
        ArrayList<String> addresses = new ArrayList<>();
        ArrayList<String> codes = new ArrayList<>();
        ArrayList<String>Sections=new ArrayList<>();
        ArrayList<String>Definitions=new ArrayList<>();
        System.out.println("Enter the start address");
        Scanner in = new Scanner(System.in);
        String Start = in.next();
        String Current =Start;
        String Dlw2ty="";
        String s = read.nextLine();

        FileWriter output1 = new FileWriter("external.txt");
        output1.write(s+"\n");


        while (read.hasNext()) {
            String[] y = s.split(" ");
            if (s.startsWith("H")){
                Dlw2ty=Current;
                Sections.add(y[1]);//esm elsection
                Sections.add(Current);//bedayet elsection
                Current=Integer.toHexString(Integer.parseInt(y[3],16)+Integer.parseInt(Current,16)).toUpperCase(Locale.ROOT);
            }
            else if(s.startsWith("D")){
                for (int x=1;x< y.length;x+=2){
                    Definitions.add(y[x]);
                    Definitions.add(Integer.toHexString(Integer.parseInt(y[x+1],16)+Integer.parseInt(Dlw2ty,16)).toUpperCase(Locale.ROOT));
                }
                Definitions.add("/");
            }

            s = read.nextLine();
            output1.write(s+"\n");
        }
        Sections.add(Current);
        String t = "";
        if (!Sections.get(1).endsWith("0")) {
            t = Sections.get(1).substring(1, 5) + "0";
        } else {
            t = Sections.get(1);
        }
        int a = Integer.parseInt(t, 16);
        int b = Integer.parseInt(Sections.get(Sections.size()-1), 16);
        int i = 0;
        while (a < b) {
            addresses.add(Integer.toHexString(a).toUpperCase(Locale.ROOT));
            a += 16;
            i++;
        }
        int y=0;
        FileWriter output = new FileWriter("Ext_Sym_Table.txt");
        output.write("Prog_Name" + "\t" + "Symbols" + "\t" + "Address" + "\t" + "Length"+"\n");
        for (int x=0;x<Sections.size()-2;x+=2){
            if(x ==Sections.size()-3){
                output.write(Sections.get(x)+"\t\t"+Sections.get(x+1)+"\t"+
                        Integer.toHexString(Integer.parseInt(Sections.get(x+2),16)-Integer.parseInt(Sections.get(x+1),16)).toUpperCase(Locale.ROOT)+"\n");
            }
            else {
                output.write(Sections.get(x) + "\t\t" + Sections.get(x + 1) + "\t" +
                        Integer.toHexString(Integer.parseInt(Sections.get(x + 3), 16) - Integer.parseInt(Sections.get(x + 1), 16)).toUpperCase(Locale.ROOT) + "\n");
            }
            while(!Definitions.get(y).equals("/")){
                output.write("\t\t"+Definitions.get(y)+"\t"+Definitions.get(y+1)+"\n");
                y+=2;
            }
            y++;
        }
        output1.close();
        output.close();
        file.close();

        FileReader file2=new FileReader("external.txt");
        Scanner readd = new Scanner(file2);
        int n=0;
        String sectionAddr="";
        int j=1;
        int CurrentAddr = Integer.parseInt(addresses.get(0),16);
        String ss =readd.nextLine();
        ArrayList<String>Modifications=new ArrayList<>();
        int flag=0;
        while (readd.hasNext()) {
            if (ss.startsWith("H")){
                sectionAddr=Sections.get(j);
                j+=2;
            }
            if (ss.startsWith("T")){
                String[] str=ss.split(" ");
                int Addr=Integer.parseInt(str[1],16)+Integer.parseInt(sectionAddr,16);
                if (Addr!=CurrentAddr){
                    int x=Addr-CurrentAddr;
                    n+=x;
                    for(int m=0;m<x;m++)
                        codes.add("XX");
                    CurrentAddr=Addr;
                }
                String ObjectCodes = ss.replaceAll(" ", "");
                ObjectCodes = ObjectCodes.substring(9);
                for (int d = 0; d < ObjectCodes.length(); d += 2) {
                    codes.add(ObjectCodes.substring(d, Math.min(ObjectCodes.length(), d+2)));
                }
                n+=Integer.parseInt(str[2],16);
                CurrentAddr+=Integer.parseInt(str[2],16);
            }

            else if(ss.startsWith("M")){
                String[] str=ss.split(" ");
                int add=Integer.parseInt(sectionAddr,16)+Integer.parseInt(str[1],16);
                Modifications.add(Integer.toHexString(add));
                Modifications.add(str[2]);
                Modifications.add(str[3]);
                for(int nn=0;nn<Definitions.size();nn++){
                    if(str[4].equals(Definitions.get(nn))){
                        Modifications.add(Definitions.get(nn+1));
                        flag=1;
                    }
                }
                if(flag==0){
                    for(int nn=0;nn<Sections.size();nn++){
                        if(str[4].equals(Sections.get(nn))) {
                            Modifications.add(Sections.get(nn+1));
                        }
                    }
                }
                flag=0;
            }
            ss =readd.nextLine();
        }

        file2.close();
        while((codes.size()%16)!=0){
            codes.add("XX");
        }

        String ra="";
        for(int r=0;r<Modifications.size()-3;r+=4){
           int em=Integer.parseInt(Modifications.get(r),16)-Integer.parseInt(addresses.get(0),16);
           if (!((r-4)>0 && Modifications.get(r).equals(Modifications.get(r-4)))){
                ra = codes.get(em) + codes.get(em + 1) + codes.get(em + 2);
            }

           if(Modifications.get(r+1).equals("05")){
               char ma= ra.charAt(0);
               ra=ra.substring(1);
               if(Modifications.get(r+2).equals("+")){
                   ra=Long.toHexString(Long.parseLong(ra,16)+Integer.parseInt(Modifications.get(r+3),16));
               }
              else if(Modifications.get(r+2).equals("-")){
                   ra=Integer.toHexString(Integer.parseInt(ra,16)-Integer.parseInt(Modifications.get(r+3),16));
               }
              if (ra.length()<5){
               while (ra.length()!=5){
                   ra="0"+ra;
               }}
               char mo=ra.charAt(0);
               if (!((r+4)<Modifications.size() && Modifications.get(r).equals(Modifications.get(r+4)))) {
                   if (ra.length()>5){
                       String d=ra.substring(ra.length()-5)+""+mo;
                       codes.set(em, d.toUpperCase(Locale.ROOT));
                       codes.set(em + 1, ra.substring(ra.length()-4, ra.length()-2).toUpperCase(Locale.ROOT));
                       codes.set(em + 2, ra.substring(ra.length()-2).toUpperCase(Locale.ROOT));
                   }
                   else{String d=ma+""+mo;
                       codes.set(em, d.toUpperCase(Locale.ROOT));
                       codes.set(em + 1, ra.substring(1, 3).toUpperCase(Locale.ROOT));
                       codes.set(em + 2, ra.substring(3, 5).toUpperCase(Locale.ROOT));
                   }
               }
           }
           else if(Modifications.get(r+1).equals("06")){
               if(Modifications.get(r+2).equals("+")){
                      ra=Long.toHexString(Long.parseLong(ra,16)+Integer.parseInt(Modifications.get(r+3),16));
               }
               else if(Modifications.get(r+2).equals("-")){
                   ra=Integer.toHexString(Integer.parseInt(ra,16)-Integer.parseInt(Modifications.get(r+3),16));

               }
               if(ra.length()<6){
               while (ra.length()!=6){
                   ra="0"+ra;
               }}
               if (!((r+4)<Modifications.size() && Modifications.get(r).equals(Modifications.get(r+4)))) {
                   if (ra.length()>6){
                       codes.set(em, ra.substring(ra.length()-6, ra.length()-4).toUpperCase(Locale.ROOT));
                       codes.set(em + 1, ra.substring(ra.length()-4, ra.length()-2).toUpperCase(Locale.ROOT));
                       codes.set(em + 2, ra.substring(ra.length()-2).toUpperCase(Locale.ROOT));
                   }
                   else{
                       codes.set(em, ra.substring(0, 2).toUpperCase(Locale.ROOT));
                       codes.set(em + 1, ra.substring(2, 4).toUpperCase(Locale.ROOT));
                       codes.set(em + 2, ra.substring(4, 6).toUpperCase(Locale.ROOT));
                   }
               }
            }
        }
        int x=0;
        for (int h = 0; h < codes.size()-15 ; h+=16) {
            Table table = new Table(addresses.get(x), codes.get(h), codes.get(h + 1), codes.get(h + 2), codes.get(h + 3)
                    , codes.get(h + 4), codes.get(h + 5), codes.get(h + 6), codes.get(h + 7), codes.get(h + 8), codes.get(h + 9)
                    , codes.get(h + 10), codes.get(h + 11), codes.get(h + 12), codes.get(h + 13), codes.get(h + 14), codes.get(h + 15));
            x++;
            data.add(table);
        }

            launch();
    }
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 875, 500);
        stage.setTitle("Naftared en de el Memory");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) throws IOException {
        System.out.println("What is the type of your input ? SIC or SICXE ");
        Scanner in = new Scanner(System.in);
        String input = in.next();
        if (input.equals("SIC"))
            SIC();
         else if (input.equals("SICXE")) {
            SICXE();
        }
        else
            System.out.println("Please enter valid program type :(");

    }

}