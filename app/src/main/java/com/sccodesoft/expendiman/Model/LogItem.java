package com.sccodesoft.expendiman.Model;

public class LogItem {
    String Id,Name,Amount,Type;


    public LogItem(String id, String name, String amount, String type) {
        Id = id;
        Name = name;
        Amount = amount;
        Type = type;
    }

    public String getId() {
        return Id;
    }

    public String getType() {
        return Type;
    }

    public void setId(String id) {
        Id = id;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }
}
