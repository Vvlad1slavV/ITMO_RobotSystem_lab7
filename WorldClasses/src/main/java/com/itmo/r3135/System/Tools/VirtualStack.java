package com.itmo.r3135.System.Tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeMap;

/**
 * Класс для извлечения команд из скрипта для последующего выполнения.
 */
public class VirtualStack {
    private ArrayList<File> activeScriptList;
    private ArrayList<String> commandStack;
    private File currentFile;
    private ArrayList<String> lastRemove;
    private TreeMap<ArrayList<File>, String> derevo;


    {
        activeScriptList = new ArrayList<>();
        commandStack = new ArrayList<>(100000);
        lastRemove = new ArrayList<>();
        derevo = new  TreeMap<>();
    }

    /**
     * Класс для извлечения команд из скрипта.
     */
    protected ArrayList stackGenerate(String scriptAddress) throws IOException {
        int i = 0;
        if (checkFile(scriptAddress) == null) {
            return commandStack;
        }
        commandStack.addAll(i, readFile(checkFile(scriptAddress)));
        int stackSize = commandStack.size();
        while (i < stackSize) {
            if (commandCheck(commandStack.get(i))) {
                scriptAddress = getAddressScript(commandStack.get(i));
                commandStack.remove(commandStack.get(i));
                while (getExecuteFromLastFile(activeScriptList.get(activeScriptList.size() - 1)).equals(lastRemove)) {
                    lastRemove.clear();
                    lastRemove.add(activeScriptList.get(activeScriptList.size()-1).getAbsolutePath());
                    activeScriptList.remove(activeScriptList.size() - 1);

                }
                if (checkFile(scriptAddress) != null) {
                    insertScript(readFile(checkFile(scriptAddress)), i);
                }
                stackSize = commandStack.size();
            } else i++;
        }
        return commandStack;
    }

    private LinkedList readFile(File script) throws IOException {
        currentFile = script;
        LinkedList<String> CommandList = new LinkedList<>();
        if (activeScriptList.indexOf(script) == -1) {
            activeScriptList.add(script);
            lastRemove.clear();
            try (BufferedReader scriptReader = new BufferedReader(new FileReader(script))) {
                System.out.println("Анализ файла " + script.getAbsolutePath());
                String scriptCommand = scriptReader.readLine();
                while (scriptCommand != null) {
                    if (commandCheck(scriptCommand)) {
                        scriptCommand = relativeToAbsolutePath(scriptCommand);
                    }
                    CommandList.addLast(scriptCommand);
                    scriptCommand = scriptReader.readLine();
                }
            }
        } else {
            System.out.println("Обнаружен цикл!");
            lastRemove.add(script.getAbsolutePath());
            //activeScriptList.remove(activeScriptList.size() - 1);
        }
        return CommandList;
    }

    private ArrayList getExecuteFromLastFile(File script) throws IOException {
        ArrayList<String> CommandList = new ArrayList<>();

        try (BufferedReader scriptReader = new BufferedReader(new FileReader(script))) {
            String scriptCommand = scriptReader.readLine();
            while (scriptCommand != null) {
                if (commandCheck(scriptCommand)) {
                    scriptCommand = relativeToAbsolutePath(scriptCommand);
                    CommandList.add(getAddressScript(scriptCommand));
                }
                scriptCommand = scriptReader.readLine();
            }
        }
        return CommandList;
    }

    /**
     * Метод заменяет абсолютный путь на относителный
     */
    private String relativeToAbsolutePath(String nextExecute) {
        String nextFilePath = nextExecute.trim().split(" ", 2)[1];
        if (nextFilePath != null) {
            File nextFile = new File(nextFilePath);
            if (!nextFile.isAbsolute()) {
                String newExecute = "execute_script " + currentFile.getAbsolutePath().replace(currentFile.getName(), nextFile.getPath());
                return newExecute;
            }
        }
        return nextExecute;
    }

    /**
     * Метод проверки файла возвращает null, если есть проблемы
     */
    private File checkFile(String addres) {
        File script = new File(addres);

        if (!script.exists() || !script.isFile()) {
            System.out.println(("Файл по указанному пути (" + script.getAbsolutePath() + ") не существует."));
            return null;
        }
        if (!script.canRead()) {
            System.out.println("Файл защищён от чтения.");
            return null;
        }
        if (script.length() == 0) {
            System.out.println("Скрипт не содержит команд.");
            return null;
        }
        return script;
    }

    private String getAddressScript(String command) {
        String[] trimScriptCommand;
        trimScriptCommand = command.trim().split(" ", 2);
        return trimScriptCommand[1];
    }

    private Boolean commandCheck(String command) {
        if (command != null) {
            String[] trimScriptCommand;
            trimScriptCommand = command.trim().split(" ", 2);
            if (trimScriptCommand[0].equals("execute_script"))
                return true;
        }
        return false;
    }

    private void insertScript(LinkedList commandList, Integer index) {
        commandStack.addAll(index, commandList);
    }


}