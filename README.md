#Проект "Періодичні видання"
***
##Опис сутностей
* ### Користувач:
Містить наступні поля:

    private Long id;
    private String email;
    private String password;
    private String name;
    private String surname;
    private Role role;
    private Long balance;
    private boolean isActive = true;
    private Long subscriptions;
    Set<Periodical> periodicals;

Інфо: Користувач має власний кабінет.
Користувач має сторінку редагування профілю де має змогу змінити поля
[ім'я | прізвище | пароль].
Існує дві ролі користувача системи.
1. Адміністратор:
   + [Створення | Редагування | Видалення] видань
   + [Блокування | Розблокування] читачів
2. Читач:
    + Поповненя балансу
    + [Підписка | Відписка] на періодичне видання.
    + Перегляд історії поповнення балансу

* ### Періодичне видання:
Містить наступні поля:

    private Long id;
    private String name;
    private Subject subject; //Тематика видання
    private Long price;
    private Long subscribers;
    private Set<User> users;

Відображення періодичних видань включає:
   + Пагінацію
   + Сортування за полем [назва | ціна | підписники]
   + Фільтрування за тематикою [Новини | Спорт | Наука]
   + Пошук за назвою

* ### Платіж:
Містить наступні поля:

      private Long id; 
      private Long sum;
      private User user;
      private LocalDateTime time;

Інфо: 
Сутність створена для зберігання інформації 
про всі поповнення балансу читачем. 

***

## Схема базы данных

![](./dbStructure.png)
