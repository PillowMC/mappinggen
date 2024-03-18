package net.pillowmc.mappinggen;

import net.fabricmc.mappingio.format.proguard.ProGuardFileReader;
import net.fabricmc.mappingio.format.tiny.Tiny2FileReader;
import net.fabricmc.mappingio.format.tiny.Tiny2FileWriter;
import net.fabricmc.mappingio.tree.MemoryMappingTree;

import java.io.*;
import java.util.Arrays;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 3) {
            System.out.println("Usage: <this program> path/to/mojmap path/to/intermediary output/path");
        }
        var mojmap = args[0];
        var intermediary = args[1];
        var output = args[2];
        var tree = new MemoryMappingTree();
        ProGuardFileReader.read(new FileReader(mojmap), "srg", "official", tree);
        Tiny2FileReader.read(new FileReader(intermediary), tree);
        var zip = new ZipOutputStream(new FileOutputStream(output));
        zip.putNextEntry(new ZipEntry("mappings/mappings.tiny"));
        tree.accept(new Tiny2FileWriter(new OutputStreamWriter(zip), false));
        zip.close();
    }
}
