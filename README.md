## Part I: Junit 5

1. Documentation page of junit 5 : [Junit 5](https://junit.org/junit5/docs/current/user-guide/#writing-tests-for-junit-5)

> JUnit 5 was launched in **2017** and is composed of several modules:
> JUnit 5 = JUnit `Platform` + JUnit `Jupiter` + JUnit `Vintage`

2. Some Assertions - jupiter :

> assertEquals(expected, actual)
> assertTrue(condition) /* boolean */
> assertFalse(condition) /* boolean */
> assertNull(object) /* object */
> assertNotNull(object) /* object */
> assertSame(expected, actual) /* compare reference */
> assertNotSame(expected, actual) /* compare reference */
> assertIterableEquals(expected, actual) /* compare collection */
> assertThrows(expected, block) /* throw exception */
> assertAll(block) /* execute multiple assertions */
```
assertAll(
 () -> assertEquals(expected, actual),
 () -> assertTrue(condition),
 () -> assertSame(expected, actual),
 () -> assertEquals(expected, actual),
 () -> assertEquals(expected, actual),
 () -> assertEquals(expected, actual)
```

3. Some Assertions - assertj :

> assertThat(actual).isEqualTo(expected) /* `or` assertThat(actual).isNotEqualTo(expected) */
> assertThat(actual).isNull() /* `or` assertThat(actual).isNotNull() */
> assertThat(actual).isInstanceOf(expectedClass) /* for exception */
> assertThat(actual).isNotInstanceOf(expectedClass)
> assertThat(actual).isIn(expected) /* for collection (we can use also isNotIn) */
> assertThat(actual).(expected)
> assertThat(actual).isBetween(expected, expected) /* for number : isCloseTo / isGreaterThan / isLessThan / ... */
> assertThat(actual).isEmpty() /* for collection */
> assertThat(actual).hasSize(expected) /* for collection */
> assertThat(actual).doesNotContain(expected) /* for collection : startsWith / endsWith */
> assertThat(actual).matches(expected) /* for string */
> assertThat(actual).doesNotMatch(expected) /* for string */

4. Setting up tearDown :

> @BeforeEach /* for each test */
> @AfterEach 
> @BeforeAll /* for all tests */
> @AfterAll

* Life cycle of tearDown is as follows : @BeforeAll > @BeforeEach > @Test > @AfterEach > @AfterAll

5. Some annotations from junit 5 :

> @DisplayName("Test name") /* for test name */
> @Nested /* in case that we have several test suite which start with the same name */
> @Disabled /* disable test */


## Part II: Mockito

> The difference between `@mock` and `@Mockbean` is that @Mockbean is a bean in the spring context and @mock is a mock object.
> So, the only difference between @MockBean and @Mock that one will inject the mock into the Spring ApplicationContext (Mockbean) and the other won't

1. Stub :

> when : asking for data
> verify : telling by doing

2. Matchers : we use matchers when we're not interested in the value of arguments.

> anyString() 
> eq() /* when we use argument matchers, we should make all parameters matchers */

3. BDD mockito style :

> We replace when by `given()`, and verify by `then()`
