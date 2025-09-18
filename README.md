Dokumentacja Aplikacji: Portal Ogłoszeń Nieruchomości

I. Wprowadzenie i Główne Funkcjonalności

Portal Ogłoszeń Nieruchomości to aplikacja internetowa stworzona w technologii Java przy użyciu frameworka Spring Boot. Aplikacja umożliwia użytkownikom przeglądanie, dodawanie oraz zarządzanie ogłoszeniami sprzedaży nieruchomości. Posiada również wbudowany panel administracyjny do zarządzania użytkownikami i ogłoszeniami.
Główne Funkcjonalności:
System Uwierzytelniania: Użytkownicy mogą zakładać konta (rejestracja) i logować się do systemu. Proces ten jest zabezpieczony, a hasła są szyfrowane.
Przeglądanie Ogłoszeń: Każdy odwiedzający (zarówno zalogowany, jak i niezalogowany) może przeglądać listę wszystkich dostępnych ogłoszeń oraz wchodzić na podstrony ze szczegółami.
Dodawanie Ogłoszeń: Zalogowani użytkownicy mogą dodawać nowe ogłoszenia sprzedaży nieruchomości poprzez dedykowany formularz.
Panel Administratora: Specjalny użytkownik z rolą ADMIN ma dostęp do panelu, w którym może edytować i usuwać konta wszystkich użytkowników oraz zarządzać wszystkimi ogłoszeniami w systemie.
Login : admin hasło: admin123
Kalkulator Kredytowy: Dodatkowe narzędzie pozwalające na szybkie obliczenie przybliżonej miesięcznej raty kredytu hipotecznego.

II. Architektura Aplikacji
Aplikacja została zbudowana w oparciu o sprawdzony wzorzec architektoniczny MVC (Model-View-Controller) oraz architekturę warstwową.
Model: Reprezentuje dane aplikacji oraz logikę biznesową. W naszym projekcie są to klasy encji (np. User, Advertisement) w pakiecie model, które mapowane są na tabele w bazie danych.
View (Widok): Warstwa prezentacji, odpowiedzialna za wyświetlanie danych użytkownikowi. Została zrealizowana przy użyciu silnika szablonów Thymeleaf, a pliki HTML znajdują się w katalogu src/main/resources/templates.
Controller (Kontroler): Pośredniczy między Modelem a Widokiem. Odbiera żądania od użytkownika (HTTP), przetwarza je, wywołuje odpowiednie operacje na danych (korzystając z warstwy serwisowej), a na koniec zwraca odpowiedni widok z danymi do wyświetlenia.
Architektura Warstwowa:
1. Warstwa Prezentacji (Kontrolery): Obsługuje interakcję z użytkownikiem.
2. Warstwa Logiki Biznesowej (Serwisy): Zawiera główną logikę aplikacji (np. proces rejestracji, walidację danych). Oddziela kontrolery od bezpośredniej komunikacji z bazą danych.
3. Warstwa Dostępu do Danych (Repozytoria): Odpowiada za komunikację z bazą danych. Dzięki Spring Data JPA operacje te są w dużej mierze zautomatyzowane.


III. Opis Klas i Komponentów
Plik Główny
NieruchomosciApplication.java: Główna klasa, która uruchamia całą aplikację Spring Boot. Zawiera również komponent CommandLineRunner, który przy starcie aplikacji tworzy domyślne konto administratora.
Pakiet config
SecurityConfig.java: Centralny punkt konfiguracji Spring Security. Definiuje m.in.:
Sposób szyfrowania haseł (PasswordEncoder).
Reguły autoryzacji – które strony są publiczne, a które wymagają logowania lub roli administratora.
Konfigurację formularza logowania i procesu wylogowywania.
Pakiet model
User.java: Encja JPA reprezentująca użytkownika w bazie danych. Zawiera takie pola jak username, password, email oraz zbiór ról.
Advertisement.java: Encja JPA dla ogłoszenia. Przechowuje wszystkie informacje o nieruchomości, takie jak tytuł, cena, lokalizacja. Zawiera relację @ManyToOne z encją User, wskazującą autora ogłoszenia. Adnotacja @Lob na niektórych polach pozwala przechowywać bardzo długie teksty.
Role.java: Typ wyliczeniowy (enum) definiujący możliwe role w systemie (USER, ADMIN).
Pakiet repository
UserRepository.java, AdvertisementRepository.java: Interfejsy dziedziczące po JpaRepository. Dzięki Spring Data JPA nie musimy pisać implementacji – framework sam dostarcza gotowe metody do operacji na bazie danych (np. save(), findById(), findAll(), deleteById()).
Pakiet dto (Data Transfer Object)
Cel: Klasy te służą jako pośrednicy do przenoszenia danych z formularzy (widoków) do kontrolerów. Dzięki nim nie ujawniamy wewnętrznej struktury naszych encji i możemy łatwo dodawać reguły walidacji.
UserRegistrationDto.java: Przechowuje dane z formularza rejestracji.
AdvertisementDto.java: Przechowuje dane z formularza dodawania/edycji ogłoszenia.
Pakiet service
UserService / UserServiceImpl.java: Serwis odpowiedzialny za logikę biznesową związaną z użytkownikami. Realizuje m.in. proces zapisu nowego użytkownika (wraz z szyfrowaniem hasła) oraz zarządzanie użytkownikami w panelu admina.
CustomUserDetailsService.java: Specjalistyczny serwis wymagany przez Spring Security. Jego jedynym zadaniem jest wczytanie danych użytkownika na podstawie jego nazwy podczas próby logowania.
Pakiet controller
AuthController.java: Obsługuje żądania związane z uwierzytelnianiem: wyświetlanie i przetwarzanie formularzy logowania (/login) oraz rejestracji (/register).
AdvertisementController.java: Zarządza stronami publicznymi dotyczącymi ogłoszeń: wyświetla listę wszystkich ogłoszeń (/ogloszenia), szczegóły pojedynczego ogłoszenia oraz obsługuje formularz dodawania nowego ogłoszenia.
AdminController.java: Kontroler dedykowany dla panelu administratora. Wszystkie jego metody są dostępne pod adresem /admin/** i zabezpieczone tak, aby dostęp do nich miał tylko użytkownik z rolą ADMIN.
CalculatorController.java: Prosty kontroler obsługujący logikę kalkulatora kredytowego.

IV. Przykładowy Przepływ Danych: Rejestracja Użytkownika
1. Użytkownik wchodzi na stronę /register.
2. AuthController obsługuje żądanie GET i zwraca widok register.html wraz z pustym obiektem UserRegistrationDto.
3. Użytkownik wypełnia formularz i klika "Zarejestruj". Przeglądarka wysyła żądanie POST na adres /register z danymi z formularza oraz tokenem CSRF.
4. AuthController odbiera żądanie POST. Adnotacja @Valid uruchamia walidację danych w obiekcie UserRegistrationDto.
5. Jeśli dane są niepoprawne, kontroler ponownie zwraca widok register.html, tym razem z informacjami o błędach.
6. Jeśli dane są poprawne, kontroler wywołuje metodę userService.saveUser(userDto).
7. UserService tworzy nową encję User, mapuje do niej dane z DTO, szyfruje hasło i zapisuje nowego użytkownika w bazie danych za pomocą userRepository.save().
8. Po pomyślnym zapisie AuthController przekierowuje użytkownika na stronę logowania z komunikatem o sukcesie.
