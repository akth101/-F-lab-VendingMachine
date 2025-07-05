class Person {
    String name;
    Person (String name) { this.name = name; }
}

public class Main {
    public static void main(String[] args) {
        Person me = new Person("pepe");
        System.out.println(me.name); 
        rename(me);
        System.out.println(me.name); 
    }

    static void rename(Person p) {
        p = new Person("Fido");
    }
}