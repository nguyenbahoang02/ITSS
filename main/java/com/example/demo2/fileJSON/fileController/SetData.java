package com.example.demo2.fileJSON.fileController;

import com.example.demo2.Model.Officer.OfficerTimeSheet;
import com.example.demo2.Model.Request;
import com.example.demo2.Model.Worker.Worker;
import com.example.demo2.Model.Worker.WorkerTimeSheet;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SetData {
    public static void writeRequestToFile(List<Request> data) {
        try {
            FileWriter fw = new FileWriter("src\\main\\java\\com\\example\\demo2\\fileJSON\\request.json");
            fw.write("[\n");
            for (int i = 0; i < data.size(); i++) {
                fw.write(data.get(i).toString());
                if (i != data.size() - 1)
                    fw.write(",\n");
                else
                    fw.write("\n");
            }
            fw.write("\n]");
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public static void writeOfficerTimeSheetToFile(List<OfficerTimeSheet> data) {
        try {
            FileWriter fw = new FileWriter("src\\main\\java\\com\\example\\demo2\\fileJSON\\officerTimeSheet.json");
            fw.write("[\n");
            for (int i = 0; i < data.size(); i++) {
                fw.write(data.get(i).toString());
                if (i != data.size() - 1)
                    fw.write(",\n");
                else
                    fw.write("\n");
            }
            fw.write("\n]");
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public static void writeWorkerTimeSheetToFile(List<WorkerTimeSheet> data) {
        try {
            FileWriter fw = new FileWriter("src\\main\\java\\com\\example\\demo2\\fileJSON\\officerTimeSheet.json");
            fw.write("[\n");
            for (int i = 0; i < data.size(); i++) {
                fw.write(data.get(i).toString());
                if (i != data.size() - 1)
                    fw.write(",\n");
                else
                    fw.write("\n");
            }
            fw.write("\n]");
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
