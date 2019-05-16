package com.fsoft.khoahn.model;

import com.fsoft.khoahn.common.annotation.CellBindByName;

import lombok.Data;

@Data
public class WorkLogImportExportContent {

    @CellBindByName(column = "#")
    private String no;

    @CellBindByName(column = "Date")
    private String logDate;

    @CellBindByName(column = "Components")
    private String component;

    @CellBindByName(column = "Phase")
    private String phase;

    @CellBindByName(column = "Anken/RedmineTicket")
    private String anken;

    @CellBindByName(column = "Screen/Module")
    private String screen;

    @CellBindByName(column = "Task description")
    private String description;

    @CellBindByName(column = "Type of work")
    private String typeOfWork;

    @CellBindByName(column = "Actual Effort  (h)")
    private String actualEffort;

    @CellBindByName(column = "Quantity")
    private String quantity;

    @CellBindByName(column = "Unit")
    private String unit;

    @CellBindByName(column = "Issue")
    private String issue;

    @CellBindByName(column = "Notes")
    private String notes;

    private String task;

}
