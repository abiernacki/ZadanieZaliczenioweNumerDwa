Feature: Zmiana informacji uzytkownika
  Scenario Outline: uzytkownik moze dodac nowy adres

    Given wlaczamy strone sklepu PrestaShop, mamy zarejestrowanego uzytkownika
    When logowanie na stworzonego uzytkownika
    And wybieramy do zakupu Hummingbird Printed Sweater
    And wybieramy <rozmiar> i 5, dodajemy produkt do koszyka przechodzimy do proceed to checkout
    And potwierdzenie adresu, wybranie metody obioru
    And wybranie metody platności, klikniecie order with obligation to pay
    Then screenshot z potwierdzeniem zamówienia i kwota

    Examples:
      |rozmiar|
      |S      |
      |M      |
      |L      |
      |XL     |


