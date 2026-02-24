---
theme: default
layout: intro
backgroundSize: 90%
fonts:
  sans: 'Inter'
  mono: 'JetBrains Mono'
---

# **SpringBoot - How to Test**

---

# **@SpringBootTest : Le test complet**

```java

@SpringBootTest
class MyApplicationTests {
  @Autowired
  private UserService userService;

  @Test
  void contextLoads() {
    // Tout le contexte Spring est chargé
  }
}
```

- ✅ Charge tout le contexte Spring
- ✅ Tous les beans sont disponibles
- ❌ Lent
- ❌ Tests d'intégration, pas unitaires

---
layout: image-left
image: resources/app_ball_service_test.png
backgroundSize: contain
---

# **Le problème**

```java

@SpringBootTest
class UserServiceTest {
  @Autowired
  private UserService userService;
  
  @Test
  void shouldCreateUser() {
    assertThat(userService.createUser());
    // Pourquoi charger toute l'appli ?
  }
}
```


---
layout: image-left
image: resources/app_ball_mock_bean.png
backgroundSize: contain
---

# **Le problème**

#### **Utilisation de `@MockBean`** et `@SpringBootTest`

- Nécessaire si on veut mock les retours de certains services
- Changement de contexte à chaque test ➡ perte de perfs (dû au système de caching de contexte)

<br>

### Solution : Test Slices 🎯

Ne charger que ce dont on a besoin !

---
layout: image-right
image: resources/sliced.png
backgroundSize: contain
---

# **Test Slices : Le concept**

##### **Principe** : Charger uniquement la couche à tester

| Annotation        | Charge             | Usage                 |
|-------------------|--------------------|-----------------------|
| `@SpringBootTest` | Tout               | Tests d'intégration   |
| `@WebMvcTest`     | Controllers + Web  | Tests de controllers  |
| `@DataJpaTest`    | JPA + DB           | Tests de repositories |

---

# **@WebMvcTest : Tester les Controllers**

```java

@WebMvcTest
class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  @Test
  void shouldGetUser() throws Exception {
    when(userService.findById(1L))
            .thenReturn(new User(1L, "John"));

    mockMvc.perform(get("/users/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("John"));
  }
}
```

---

# **@WebMvcTest : Ce qui est chargé**

### ✅ Chargé automatiquement

- `@Controller`, `@RestController`
- `@ControllerAdvice`
- Convertisseurs JSON (Jackson)
- Validators
- Security filters (si Spring Security)

### ❌ NON chargé

- `@Service`, `@Repository`, `@Component`
- Configuration métier
- Base de données

Possibilité de préciser la liste des controllers à instancier : `@WebMvcTest(UserController.class)`

---

# **@DataJpaTest : Tester les Repositories**

```java

@DataJpaTest
class UserRepositoryTest {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private TestEntityManager entityManager;

  @Test
  void shouldFindUserByEmail() {
    User user = new User("john@test.com", "John");
    entityManager.persistAndFlush(user);

    Optional<User> found = userRepository.findByEmail("john@test.com");

    assertThat(found).isPresent();
    assertThat(found.get().getName()).isEqualTo("John");
  }
}
```

---

# **@DataJpaTest : Ce qui est chargé**

<br>

### ✅ Chargé automatiquement

- Repositories JPA
- EntityManager, TestEntityManager (helper pour les tests)
- Transactions auto-rollback (contrairement à `@SpringBootTest`)

<br>

### ❌ NON chargé

- `@Service`
- `@Controller`

---

# **Autres Test Slices**

<br>

- `@JsonTest`
- `@RestClientTest`
- `@DataElasticsearchTest`
- ...

cf. https://docs.spring.io/spring-boot/appendix/test-auto-configuration/slices.html

---

# **Et pour les services ?**

- Mockito
- `@ExtendWith(MockitoExtension.class)`

## Pattern recommandé : La pyramide des tests

```
         /\
        /  \  Quelques tests @SpringBootTest
       /____\
      /      \
     / @Slice \ Beaucoup de tests de slices
    /__________\
   /            \
  /  Unit Tests  \ Majorité : tests unitaires purs
 /________________\
```

---

# **@SpringBootTest avec classes limitées**

### Le compromis : Charger seulement certains beans

```java
@SpringBootTest(classes = {
        UserService.class,
        UserRepository.class
})
class UserServiceIntegrationTest {

  @Autowired private UserService userService;

  @MockBean private EmailService emailService;

  @Test
  void shouldCreateUserWithRealDependencies() {
    // UserService et UserRepository sont réels
    // EmailService est mocké
  }
}

```
