package temp;

import java.io.*;

/**
 * @Author: Seth
 * @Description:
 * @Date: Created in 15:31 2020/4/3
 */
class Person implements Serializable {
    public int id;
    public String name;
    public Job job;
    public Person(int id, String name, Job job) {
        this.id = id;
        this.name = name;
        this.job = job;
    }

    @Override
    public String toString() {
        String str = "id= " + id + ", name= " + name + ", job: {salary: " + job.salary
                + ", major: " + job.major + "}";
        return str;
    }
}

class Job implements Serializable {
    public int salary;
    public String major;
    public Job(int salary, String major) {
        this.salary = salary;
        this.major = major;
    }
}

public class TestCopy {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("temp.dat"));
//        ObjectInputStream input = new ObjectInputStream(new FileInputStream("temp.dat"));
//
//        Person person1 = new Person(16, "Hong", new Job(666, "software"));
//        output.writeObject(person1);
//        output.close();
//
//        Person copy_person1 = (Person)input.readObject();
//        input.close();
//
//        System.out.println(copy_person1);   // id= 16, name= Hong, job: {salary: 666, major: software}
//        person1.name = "Ming";
//        person1.id = 99;
//        person1.job.salary = 9999;
//        System.out.println(copy_person1);   // id= 16, name= Hong, job: {salary: 666, major: software}
        Thread thread = new Thread(() -> {System.out.println(1);});
        thread.start();
    }
}
