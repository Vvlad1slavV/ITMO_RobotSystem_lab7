package com.itmo.r3135;

import com.itmo.r3135.World.Product;

import java.io.File;
import java.util.Date;
import java.util.HashSet;

public class Collection {
    private File jsonFile;
    private HashSet<Product> products;
    private Date dateInitialization;
    private Date dateSave;
    private Date dateChange;

    {
        products = new HashSet<>();
        dateInitialization = new Date();
        dateChange = new Date();
        dateSave = new Date();
    }

    public Collection(File jsonFile) {
        this.jsonFile = jsonFile;
    }


    public Collection() {
        this(null);
    }

    public Date getDateChange() {
        return dateChange;
    }

    public Date getDateSave() {
        return dateSave;
    }

    public File getJsonFile() {
        return jsonFile;
    }

    public HashSet<Product> getProducts() {
        return products;
    }

    public void uptadeDateChange() {
        this.dateChange = new Date();
    }

    public void setJsonFile(File jsonFile) {
        this.jsonFile = jsonFile;
    }

    public void updateDateInitialization() {
        this.dateInitialization = new Date();
    }

    public void updateDateSave() {
        this.dateSave = new Date();
    }
}
