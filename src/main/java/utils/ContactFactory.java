package utils;

import dto.Contact;
import net.datafaker.Faker;

public class ContactFactory {
    static Faker faker= new Faker();

    public static Contact positiveContact(){
        return Contact.builder()
                .name(faker.name().firstName())
                .lastName(faker.name().lastName())
                .phone(faker.number().digits(13))
                .email(faker.internet().emailAddress())
                .address(faker.address().fullAddress())
                .description("My friends")
                .build();
    }

    public static Contact positiveContactOnlyRequiredFields(){
        return Contact.builder()
                .name(faker.name().firstName())
                .lastName(faker.name().lastName())
                .phone(faker.number().digits(13))
                .email(faker.internet().emailAddress())
                .address(faker.address().fullAddress())
                .description("")
                .build();
    }

    public static Contact emptyContact(){
        return Contact.builder()
                .name("")
                .lastName("")
                .phone("")
                .email("")
                .address("")
                .description("")
                .build();
    }
}
