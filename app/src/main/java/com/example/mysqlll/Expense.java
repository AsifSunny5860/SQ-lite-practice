package com.example.mysqlll;

class Expense {
    private  int id;
    private  String Etype;
    private String Eamount;
    private long Edate;
    private String Etime;


    public Expense(int id, String etype, String eamount, long edate, String etime) {
        this.id = id;
        Etype = etype;
        Eamount = eamount;
        Edate = edate;
        Etime = etime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEtype() {
        return Etype;
    }

    public void setEtype(String etype) {
        Etype = etype;
    }

    public String getEamount() {
        return Eamount;
    }

    public void setEamount(String eamount) {
        Eamount = eamount;
    }

    public long getEdate() {
        return Edate;
    }

    public void setEdate(int edate) {
        Edate = edate;
    }

    public String getEtime() {
        return Etime;
    }

    public void setEtime(String etime) {
        Etime = etime;
    }
}
