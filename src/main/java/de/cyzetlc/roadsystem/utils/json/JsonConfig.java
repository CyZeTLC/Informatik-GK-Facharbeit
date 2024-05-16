package de.cyzetlc.roadsystem.utils.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JsonConfig {
    private JSONObject object;
    private Gson gson;
    private File file;

    public JsonConfig(String file) {
        this(file, false);
    }

    public JsonConfig(JSONObject jsonObject) {
        this.gson = (new GsonBuilder()).setPrettyPrinting().create();
        this.object = jsonObject;
    }

    public JsonConfig(String file, boolean loadFromJar) {
        this.file = new File(file);
        this.gson = (new GsonBuilder()).setPrettyPrinting().create();
        if (!this.file.exists()) {
            if (loadFromJar) {
                String[] split = file.split("/");
                this.loadFromJar(split[split.length-1], file.replace(split[split.length-1], ""));
                try {
                    this.object = new JSONObject(new String(Files.readAllBytes(Paths.get(this.file.toURI())), String.valueOf(StandardCharsets.UTF_8)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    this.file.createNewFile();
                    this.object = new JSONObject();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                this.object = new JSONObject(new String(Files.readAllBytes(Paths.get(this.file.toURI())), String.valueOf(StandardCharsets.UTF_8)));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public <T> T load(Class<T> clazz) {
        return this.gson.fromJson(this.object.toString(), clazz);
    }

    public void save() {
        try {
            Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
            try {
                writer.write(this.object.toString());
            } finally {
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFromJar(String fileName, String toPath) {
        InputStream in = this.getClass().getResourceAsStream("/" + fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        File f = new File(toPath, "/" + fileName);

        List<String> lines = new ArrayList<String>();

        if (!f.exists()) {
            try {
                f.createNewFile();

                String current = reader.readLine();

                while (current != null) {
                    lines.add(current);
                    current = reader.readLine();
                }

                FileWriter fw = new FileWriter(f);

                for (String str : lines) {
                    fw.write(str);
                    fw.write(System.getProperty("line.separator"));
                }

                fw.flush();
                fw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public JSONObject getObject() {
        return object;
    }

    public File getFile() {
        return file;
    }
}
