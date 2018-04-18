/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generator;

/**
 *
 * @author a.kirillov
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Generator {

    private static String print = "";
    private static ArrayList<Data> lst = new ArrayList<Data>();

    public static void main(String[] args) {
        ReadXMLFileDOM.readXML("settings.xml");
        readData("source-data.tsv");
        ReportPrint("example-report.txt");
    }

    public static void ReportPrint(String SavePath) {
        try {
            final BufferedWriter bw = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream(SavePath), "UTF-16"));
            int height = PrintHead();
            String result;
            for (int i = 0; i < lst.size(); i++) {
                result = builder(String.valueOf(lst.get(i).getNumber()),
                        lst.get(i).getDate(), lst.get(i).getUserName());
                height += result.replace("\r\n", "").length() + ReadXMLFileDOM.page.getWidth();
                if (height / ReadXMLFileDOM.page.getWidth() < ReadXMLFileDOM.page.getHeight()) {
                    print += (result);
                } else {
                    print += ("~\r\n");
                    height = PrintHead();
                    print += (result);
                }

                if (i < lst.size()) {
                    for (int j = 0; j < ReadXMLFileDOM.page.getWidth(); j++) {
                        print += ("-");
                    }
                }

                print += ("\r\n");
            }
            bw.write(print);
            bw.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static int PrintHead() {
        String result = builder(
                ReadXMLFileDOM.settings.get(0).getTitle(),
                ReadXMLFileDOM.settings.get(1).getTitle(),
                ReadXMLFileDOM.settings.get(2).getTitle()
        );
        
        print += (result);
        for (int i = 0; i < ReadXMLFileDOM.page.getWidth(); i++) {
            print += ("-");
        }
        print += "\r\n";
        return result.length() + ReadXMLFileDOM.page.getWidth() - 1;
    }

    public static String builder(String Column1, String Column2, String Column3) {
        Boolean FlNumber = false;
        Boolean FlDate = false;
        Boolean FlUsername = false;
        String build = "";
        while (true) {
            build += "| ";
            if (Column1.length() < ReadXMLFileDOM.settings.get(0).getWidth() + 1) {
                build += Column1;
                for (int i = 0; i < ReadXMLFileDOM.settings.get(0).getWidth() - Column1.length(); i++) {
                    build += " ";
                }
                FlNumber = true;
                Column1 = "";
            } else {

                build += Column1.subSequence(0, ReadXMLFileDOM.settings.get(0).getWidth());
                Column1 = Column1.substring(ReadXMLFileDOM.settings.get(0).getWidth());
                Column1 = Column1.trim();
            }
            build += " | ";

            if (Column2.length() < ReadXMLFileDOM.settings.get(1).getWidth() + 1) {
                build += Column2;
                for (int i = 0; i < ReadXMLFileDOM.settings.get(1).getWidth() - Column2.length(); i++) {
                    build += " ";
                }
                FlDate = true;
                Column2 = "";
            } else {

                build += Column2.subSequence(0, ReadXMLFileDOM.settings.get(1).getWidth());
                Column2 = Column2.substring(ReadXMLFileDOM.settings.get(1).getWidth());
                Column2 = Column2.trim();
            }
            build += " | ";

            if (Column3.length() < ReadXMLFileDOM.settings.get(2).getWidth() + 1) {
                build += Column3.trim();
                for (int i = 0; i < ReadXMLFileDOM.settings.get(2).getWidth() - Column3.length(); i++) {
                    build += " ";
                }
                build += " |\r\n";
                FlUsername = true;
                Column3 = "";

            } else {
                build += Column3.subSequence(0, ReadXMLFileDOM.settings.get(2).getWidth());
                Column3 = Column3.substring(ReadXMLFileDOM.settings.get(2).getWidth());
                build += " |\r\n";
                Column3 = Column3.trim();
            }
            if (FlNumber && FlDate && FlUsername == true) {
                break;
            }
        }
        return build;
    }

    public static void readData(String DataPath) {
        try {
            final BufferedReader br = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(DataPath), "UTF-16"));
            String nextString;

            while ((nextString = br.readLine()) != null) {
                Data d = new Data();
                d.setNumber(Integer.parseInt(nextString.split("\t")[0]));
                d.setDate(nextString.split("\t")[1]);
                d.setUserName(nextString.split("\t")[2]);
                lst.add(d);
            }
            br.close();
        } catch (UnsupportedEncodingException | FileNotFoundException ex) {
            Logger.getLogger(Generator.class.getName())
                    .log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Generator.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
}
