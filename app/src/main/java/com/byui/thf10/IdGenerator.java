package com.byui.thf10;

public class IdGenerator {
    private static int id;
    private static IdGenerator idGenerator = new IdGenerator();

    private IdGenerator(){id = 0;}
    private void setId(int id){
        this.id = id;
    }
    private static void increment(int increment){
        idGenerator.setId(idGenerator.id + increment);
    }
    private static int getId(){return id;}
    public static int  generateID(){
        idGenerator.increment(1);
        return idGenerator.id;
    }


}
