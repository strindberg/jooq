package jooqsimple.model;

import java.util.*;

public record Person(Long id,
                     String firstName,
                     String lastName,
                     List<Address> addresses) {
}
