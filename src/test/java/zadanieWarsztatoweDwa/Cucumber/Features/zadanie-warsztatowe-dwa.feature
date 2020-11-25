Feature: Zmiana informacji uzytkownika
  Scenario: uzytkownik moze dodac nowy adres

    Given wlaczamy strone sklepu PrestaShop, mamy zarejestrowanego uzytkownika
    When logowanie na stworzonego uzytkownika
    And wybieramy do zakupu Hummingbird Printed Sweater
    And wybieramy rozmiar i liczbe sztuk, dodajemy produkt do koszyka przechodzimy do proceed to checkout
    And potwierdzenie adresu, wybranie metody obior
    And wybranie metody platności, klikniecie order with obligation to pay
    Then screenshot z potwierdzeniem zamówienia i kwota



