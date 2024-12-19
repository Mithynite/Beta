package example.com.model.person;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {
    
    @Test
void shouldThrowIllegalArgumentExceptionWhenNameIsNull() {
    assertThrows(IllegalArgumentException.class, () -> new Person(null, "Doe"));
}

@Test
void shouldThrowIllegalArgumentExceptionWhenNameIsEmptyString() {
    assertThrows(IllegalArgumentException.class, () -> new Person(" ", "Doe"));
}

@Test
void shouldThrowIllegalArgumentExceptionWhenSurnameContainsNonLetterCharacters() {
    String invalidSurname = "Doe123";
    assertThrows(IllegalArgumentException.class, () -> new Person("John", invalidSurname));
}

@Test
void shouldThrowIllegalArgumentExceptionWhenSurnameIsEmptyString() {
    assertThrows(IllegalArgumentException.class, () -> new Person("John", " "));
}

@Test
void shouldThrowIllegalArgumentExceptionWhenSurnameIsNull() {
    assertThrows(IllegalArgumentException.class, () -> new Person("John", null));
}

@Test
void shouldThrowIllegalArgumentExceptionWhenNameContainsNonLetterCharacters() {
    String invalidName = "John123";
    assertThrows(IllegalArgumentException.class, () -> new Person(invalidName, "Doe"));
}

@Test
void shouldReturnCorrectNameWhenGetNameIsCalled() {
    String expectedName = "John";
    Person person = new Person(expectedName, "Doe");
    String actualName = person.getName();
    assertEquals(expectedName, actualName);
}

@Test
void shouldReturnCorrectSurnameWhenGetSurnameIsCalled() {
    String expectedSurname = "Doe";
    Person person = new Person("John", expectedSurname);
    String actualSurname = person.getSurname();
    assertEquals(expectedSurname, actualSurname);
}

@Test
void shouldHandleCaseSensitivityWhenValidatingNamesAndSurnames() {
    String nameWithMixedCase = "JoHn";
    String surnameWithMixedCase = "DoE";
    Person person = new Person(nameWithMixedCase, surnameWithMixedCase);
    assertEquals("John", person.getName());
    assertEquals("Doe", person.getSurname());
}
}