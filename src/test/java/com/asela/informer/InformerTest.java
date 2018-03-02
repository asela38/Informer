package com.asela.informer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.asela.informer.Descriptors;
import com.asela.informer.Informer;
import com.asela.informer.StringConditions;

/*
 * 
 * 
 * 
 * 
 */
public class InformerTest {

    @Test
    public void test() {
        Address adress = new Address(Arrays.asList("Mahahunupitiya", "Negombo"), 12);
        Address adress1 = new Address(Arrays.asList("Mahahunupitiya1", "Negombo1"), 121);
        Address adress2 = new Address(Arrays.asList("Mahahunupitiya2", "Negombo2"), 122);
        Person person = new Person("Asela", adress, new int[] { 10, 11, 12 });
        adress.setPerson(person);

        Map<String, Address> addresses = new HashMap<String, Address>();
        addresses.put("One", adress);
        addresses.put("Two", adress1);
        addresses.put("Three", adress2);
        person.setAdditionalAddresses(addresses);

        Informer.newInformer().getInformation(person);

        // Informer.getBuilder().depth(3)
        // .addMethodCondition(StringConditions.or(StringConditions.equals("getName"),
        // StringConditions.startsWith("getA")))
        // .putDescriptor(Address.class, new Descriptor<Address>() {
        //
        // @Override
        // public String describe(Address address) {
        // return address.getNumber() + " " + address.getLines();
        // }
        //
        // })
        // //.putDescriptor(Address.class,
        // Descriptors.getToStringDescriptor(Address.class))
        // .build().getInformation(person);
        //
        ////
        // Informer.getBuilder().depth(3)
        // .addMethodCondition(StringConditions.startsWith("get"))
        // .build().getInformation(person);

        // Informer.getBuilder()
        // .depth(3)
        // .addMethodCondition(
        // StringConditions.startsWith("get"))
        // .putDescriptor(Address.class,
        // Descriptors.getEmptyDescriptor(Address.class))
        // .build().getInformation(person);

        Informer.getBuilder().depth(3).addMethodCondition(StringConditions.startsWith("get"))
                .putDescriptor(Address.class, Descriptors.getEmptyDescriptor(Address.class)).build()
                .getInformation(person);

    }

    // Informer.getBuilder().addMethodCondition(StringConditions.startsWith("get")).build();

}

class Person {
    String               name;
    Address              adress;
    int[]                scores;
    Map<String, Address> additionalAddresses;

    public Map<String, Address> getAdditionalAddresses() {
        return additionalAddresses;
    }

    public void setAdditionalAddresses(Map<String, Address> additionalAddresses) {
        this.additionalAddresses = additionalAddresses;
    }

    public Person(String name, Address adress, int[] scores) {
        super();
        this.name = name;
        this.adress = adress;
        this.scores = scores;
    }

    public Person(String name, Address adress) {
        super();
        this.name = name;
        this.adress = adress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAdress() {
        return adress;
    }

    public void setAdress(Address adress) {
        this.adress = adress;
    }

    public int[] getScores() {
        return scores;
    }

    public void setScores(int[] scores) {
        this.scores = scores;
    }

}

class Address {

    List<String> lines;
    int          number;
    Person       person;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Address(List<String> lines, int number) {
        super();
        this.lines = lines;
        this.number = number;
    }

    public List<String> getLines() {
        return lines;
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

}