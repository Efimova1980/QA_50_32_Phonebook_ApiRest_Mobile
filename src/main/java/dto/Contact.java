package dto;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode

//contact from phonebook
public class Contact {
     @EqualsAndHashCode.Exclude
     private String id;
     private String name;
     private String lastName;
     private String email;
     private String phone;
     private String address;
     private String description;
}


