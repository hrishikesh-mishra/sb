package com.hrishikeshmishra.sb.springbatch;

import org.springframework.batch.item.ItemProcessor;

/**
 * Created by hrishikesh.mishra on 09/09/16.
 */
public class PersonItemProcessor implements ItemProcessor<Person, Person> {



    @Override
    public Person process(final Person person) throws Exception {
        final String firstName = person.getFirstName().toUpperCase();
        final String lastName = person.getLastName().toUpperCase();

        final Person transformedPerson = new Person(firstName, lastName);

        System.out.println("Converting (" + person + ") into (" + transformedPerson + ")");

        return transformedPerson;
    }
}
