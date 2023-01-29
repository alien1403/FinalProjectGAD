package com.company.services;

import com.company.user.Customer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CsvReaderService {

    private static CsvReaderService instance;

    public static CsvReaderService getInstance(){
        if(instance == null){
            instance = new CsvReaderService();
        }
        return instance;
    }

    public List<String[]> readCustomersFromCsv()
    {
        List<String[]> stringList = new ArrayList<>();
        String line = "";
        String splitBy = ",";
        try {
            //parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader("src\\com\\company\\resources\\customers.csv"));
            br.readLine(); // read header
            while ((line = br.readLine()) != null)
            //returns a Boolean value
            {
                String[] customer = line.split(splitBy);
                stringList.add(customer);
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return stringList;
    }

    public List<String[]> readAdminsFromCsv()
    {
        List<String[]> stringList = new ArrayList<>();
        String line = "";
        String splitBy = ",";
        try {
            //parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader("src\\com\\company\\resources\\admins.csv"));
            br.readLine(); // read header
            while ((line = br.readLine()) != null)
            //returns a Boolean value
            {
                String[] admin = line.split(splitBy);
                stringList.add(admin);
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return stringList;
    }
    public List<String[]> readStandardCardsFromCsv()
    {
        List<String[]> stringList = new ArrayList<>();
        String line = "";
        String splitBy = ",";
        try {
            //parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader("src\\com\\company\\resources\\standardcards.csv"));
            br.readLine(); // read header
            while ((line = br.readLine()) != null)
            //returns a Boolean value
            {
                String[] card = line.split(splitBy);
                stringList.add(card);
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return stringList;
    }

    public List<String[]> readPremiumCardsFromCsv()
    {
        List<String[]> stringList = new ArrayList<>();
        String line = "";
        String splitBy = ",";
        try {
            //parsing a CSV file into BufferedReader class constructor
            BufferedReader br = new BufferedReader(new FileReader("src\\com\\company\\resources\\premiumcards.csv"));
            br.readLine(); // read header
            while ((line = br.readLine()) != null)
            //returns a Boolean value
            {
                String[] card = line.split(splitBy);
                stringList.add(card);
            }
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return stringList;
    }
}
