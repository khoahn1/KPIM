package com.fsoft.khoahn.common.upload;

import java.io.Serializable;


/**
 * Created by xschen on 10/7/2016.
 */
public class DataColumn implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name = "";
    private int columnIndex;

    public DataColumn(){

    }

    public DataColumn(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public DataColumn makeCopy(){
        DataColumn c = new DataColumn(name);
        c.setColumnIndex(columnIndex);
        return c;
    }


    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public int getColumnIndex(){
        return columnIndex;
    }
}
