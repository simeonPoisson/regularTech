package ru.regulartech.officeObjects;

import ru.regulartech.GUI.GetOfficeObjectNameDialog;
import ru.regulartech.SQL.ReportDAO;
import ru.regulartech.graphical.GraphObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

/**
 * Create by Votrin Andrey (votrin.andrey@caesber.ru).
 * DATE: 18.04.2014
 * TIME: 21:41
 */
public abstract class OfficeObject extends GraphObject implements baseActions {
    protected Integer id;
    protected Integer officeObjectTypeId;
    protected String name;
    protected String description;
    protected BigDecimal IndentureNumber;
    protected Integer status;

    protected Image image;

    public static final int OFFICE_OBJECT_PC        = 1;
    public static final int OFFICE_OBJECT_FURNITURE = 2;
    public static final int OFFICE_OBJECT_COMMON    = 3;
    public static final int OFFICE_OBJECT_ROUTER    = 4;
    public static final int OFFICE_OBJECT_PRINTER   = 5;
    public static final int OFFICE_OBJECT_MONITOR   = 6;
    public static final int OFFICE_OBJECT_LAPTOP    = 7;

    public static final Integer ALL_IS_OK = 1;
    public static final Integer BROKEN = 2;
    public static final Integer ON_DIAGNOSTIC = 3;

    public ObjectRightClickMenu objectRightClickMenu;
    private Integer officeObjectStatusId;

    public OfficeObject() {
        this.description = "no_description";
    }

    protected OfficeObject(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public void getNameDialog() {
        new GetOfficeObjectNameDialog(this);
        //setName("New OfficeObject");
    }

    @Override
    public void setOfficeObjectStatusId(Integer status) {
        this.officeObjectStatusId = status;
    }
    @Override
    public Integer getOfficeObjectStatusId() {
        return officeObjectStatusId;
    }

    public static OfficeObject create(Integer id, String name, int officeObjectType){
        switch(officeObjectType){
            case OFFICE_OBJECT_LAPTOP:
                ReportDAO.createOfficeObject(OFFICE_OBJECT_LAPTOP, id, name);
                return new Laptop(id, name);
            case OFFICE_OBJECT_MONITOR:
                ReportDAO.createOfficeObject(OFFICE_OBJECT_MONITOR, id, name);
                return new Monitor(id, name);
            case OFFICE_OBJECT_ROUTER:
                ReportDAO.createOfficeObject(OFFICE_OBJECT_ROUTER, id, name);
                return new Router(id, name);
            case OFFICE_OBJECT_PC:
                ReportDAO.createOfficeObject(OFFICE_OBJECT_PC, id, name);
                return new PC(id, name);
            case OFFICE_OBJECT_PRINTER:
                ReportDAO.createOfficeObject(OFFICE_OBJECT_PRINTER, id, name);
                return new Printer(id, name);
            case OFFICE_OBJECT_FURNITURE:
                ReportDAO.createOfficeObject(OFFICE_OBJECT_FURNITURE, id, name);
                return new Table(id, name);
        }
        return null;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public abstract Integer getType();

    public Integer getOfficeObjectTypeId() {
        return officeObjectTypeId;
    }
    public void setOfficeObjectTypeId(Integer officeObjectTypeId) {
        this.officeObjectTypeId = officeObjectTypeId;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getIndentureNumber() {
        return IndentureNumber;
    }
    public void setIndentureNumber(BigDecimal indentureNumber) {
        IndentureNumber = indentureNumber;
    }

    @Override
    public void changeRoom(){

    }
    @Override
    public  void changeStatus(Integer status){
        this.status = status;
    }

    public Image getObjectImage(){
        return image;
    }
    public void setObjectImage(Image image){
        this.image = image;
    }

    public void setBroken() {
        changeStatus(BROKEN);
        ReportDAO.setStatus(this, BROKEN);
    }

    public ObjectRightClickMenu getObjectRightClickMenu() {
        return objectRightClickMenu;
    }
    public void setObjectRightClickMenu(ObjectRightClickMenu objectRightClickMenu) {
        this.objectRightClickMenu = objectRightClickMenu;
    }

    public void hideObjectRightClickMenu() {
        if(objectRightClickMenu != null)
            objectRightClickMenu.setVisible(false);
    }

    public static OfficeObject loadOfficeObject(int id, String name, int officeObjectType) {
        switch(officeObjectType){
            case OFFICE_OBJECT_LAPTOP:
                return new Laptop(id, name);
            case OFFICE_OBJECT_MONITOR:
                return new Monitor(id, name);
            case OFFICE_OBJECT_ROUTER:
                return new Router(id, name);
            case OFFICE_OBJECT_PC:
                return new PC(id, name);
            case OFFICE_OBJECT_PRINTER:
                return new Printer(id, name);
            case OFFICE_OBJECT_FURNITURE:
                return new Table(id, name);
        }
        return null;
    }




    public abstract static class ObjectRightClickMenu extends  JPopupMenu{
        private JMenuItem markAsBroken;
        private OfficeObject officeObject;

        protected ObjectRightClickMenu() {
            markAsBroken = new JMenuItem("Уведомить о поломке");

            markAsBroken.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    getOfficeObject().setBroken();
                }
            });
            add(markAsBroken);
        }

        public void showMenu(OfficeObject officeObject, int x_coord, int y_coord) {
            setOfficeObject(officeObject);
            setLocation(x_coord, y_coord);
            setVisible(true);
        }

        public void setOfficeObject(OfficeObject officeObject) {
            this.officeObject = officeObject;
        }

        public OfficeObject getOfficeObject() {
            return officeObject;
        }
    }
}
