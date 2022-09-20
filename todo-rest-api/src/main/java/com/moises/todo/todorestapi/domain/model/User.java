package com.moises.todo.todorestapi.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Random;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "user_code", unique = true, updatable = false)
    private String userCode;

    @Column(name = "activation_code", updatable = false)
    private String activationCode;

    @Column(name = "is_active")
    private boolean isActive = false;

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "username", nullable = false, unique = true, updatable = false)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @CreationTimestamp
    @Column(name = "created_on", nullable = false, columnDefinition = "datetime")
    private OffsetDateTime createdOn;

    @UpdateTimestamp
    @Column(name = "updated_on", nullable = false, columnDefinition = "datetime")
    private OffsetDateTime updatedOn;

    @OneToOne
    @JoinColumn(name = "todo_directory_id", foreignKey = @ForeignKey(name = "FK_TODO_DIRECTORY_ID_USER"))
    private TodoDirectory todoDirectory;


    //    Methods to help validate the password
    private boolean passwordMatch(String password) {
        return getPassword().equals(password);
    }

    public boolean passwordDoesNotMatch(String password) {
        return !passwordMatch(password);
    }

    //    Methods to generate the user's unique code
    @PrePersist
    private void userCodeGenerator() {

        String generatedUserCode = codeGenerator(8);
        setUserCode(generatedUserCode);

        String generatedActivationCode = codeGenerator(4);
        setActivationCode(generatedActivationCode.toUpperCase());

    }

    private String codeGenerator(int codeLength) {

        // 55 char string. There is no o, O, l, i, I, 0, and 1 to avoid confusion.
        String charString = "ABCDEFGHJKLMNPQRSTUVWXYZ" +
                "abcdefghjkmnpqrstuvwxyz" +
                "23456789";

        // The length will be passed as parameter
        int numOfCodeChar = codeLength;

        Random random = new Random();

        StringBuilder code = new StringBuilder();

        for (int i = 0; i < numOfCodeChar; i++) {
            code.append(charString.charAt(random.nextInt(charString.length())));
        }

        // Get the generated code and store it in a String that will be returned
        String generatedCode = String.valueOf(code);

        return generatedCode;
    }

    //    Method to activate the account
    public void activateAccount() {

        this.isActive = true;
    }


}
