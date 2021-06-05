package com.tomcode.agent;

import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

public class YamlParser {
    public static void main(String[] args) throws IOException {
        Yaml yaml = new Yaml();
        String classResourcePath = YamlParser.class.getName().replaceAll("\\.", "/") + ".class";
        URL resource = ClassLoader.getSystemClassLoader().getResource(classResourcePath);

        String urlString = resource.toString();
        System.out.println(resource.toString());

        int prefixLength = "file:".length();
        String classLocation = urlString.substring(
                prefixLength, urlString.length() - classResourcePath.length());
        System.out.println(classLocation);
        FileInputStream ymlf = new FileInputStream(classLocation + "my.yaml");
        Object load = yaml.load(ymlf);

        System.out.println(load.getClass());
        ymlf.close();
    }
}
