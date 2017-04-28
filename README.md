# 2. Pratkikumsaufgabe Software-Architektur Sommer 2017 

## Allgemein
Heroku-Link: https://huberneumeier0shareit.herokuapp.com/

Team:
- Tobias Huber
- Andreas Neumeier

## REST-API Übersicht
Bisher existieren zwei verschiedene Ressourcetypen / Medien, Bücher (books) und CDs (discs).
Beide Typen leiten sich also von der Klasse Medium ab, wobei sie sich lediglich im Titel gleich sind.
### Medium (medium)
Attribute:
- title, Titel des Mediums
### Buch (book)
Attribute:
- isbn, eindeutige Identifikationsnummer ([ISBN-13](https://de.wikipedia.org/wiki/Internationale_Standardbuchnummer#ISBN-13))
- author, Autor eines Buches

Schnittstellen-URI | Funktion
------------------ | --------
[http://host/shareit/media/books](https://huberneumeier0shareit.herokuapp.com/shareit/media/books) | \[POST\] - neues Buch anlegen
[http://host/shareit/media/books](https://huberneumeier0shareit.herokuapp.com/shareit/media/books) | \[GET\] - alle Bücher auslesen
[http://host/shareit/media/books/{isbn}](https://huberneumeier0shareit.herokuapp.com/shareit/media/books/put_isbn_here) | \[PUT\] - angegebenes Buch ändern
[http://host/shareit/media/books/{isbn}](https://huberneumeier0shareit.herokuapp.com/shareit/media/books/put_isbn_here) | \[GET\] - angegebenes Buch auslesen
### CD (disc)
Attribute:
- barcode, eindeutige Identifikationsnummer ([Barcode](https://de.wikipedia.org/wiki/European_Article_Number))
- director, Direktor der CD
- fsk, Altersbeschränkung für die CD

Schnittstellen-URI | Funktion
------------------ | --------
[http://host/shareit/media/discs](https://huberneumeier0shareit.herokuapp.com/shareit/media/discs) | \[POST\] - neue CD anlegen
[http://host/shareit/media/discs](https://huberneumeier0shareit.herokuapp.com/shareit/media/discs) | \[GET\] - alle CDs auslesen
[http://host/shareit/media/discs/{barcode}](https://huberneumeier0shareit.herokuapp.com/shareit/media/discs/put_barcode_here) | \[PUT\] - angegebene CD ändern
[http://host/shareit/media/discs/{barcode}](https://huberneumeier0shareit.herokuapp.com/shareit/media/discs/put_barcode_here) | \[GET\] - angegebene CD auslesen
## Genauere Beschreibung der REST-API
### _POST_-Methode
Erstellt ein neues Medium.
#### Ein neues Buch erstellen:
##### Anfrage an _([http://host/shareit/media/books](https://huberneumeier0shareit.herokuapp.com/shareit/media/books))_
```
{
    "title": "test title",
    "author": "test author",
    "isbn": "9781566199094"
}
```
##### Anfrage an _([http://host/shareit/media/discs](https://huberneumeier0shareit.herokuapp.com/shareit/media/discs))_
```
{
    "barcode": "5449000096241",
    "title": "test title",
    "director": "test director",
    "fsk": 0
}
```
##### Antwort _(erfolgreiches Erstellen)_
```  
{
    "code": 201,
    "status": "CREATED",
    "message": "resource created"
}
```
##### Antwort _(fehlerhaftes Erstellen)_
Beim Anlegen eines neuen Mediums werden die Usereingaben vor dem eigentlichen Anlegen geprüft. Hierbei können einige Fehler auftreten, welche alle einen 400 - Bad Request - Error auslösen.
- Ungültige ISBN bzw ungültiger Barcode
- ISBN bzw Barcode bereits verwendet
- Fehlende Eingabe (Titel, Autor bzw Director, FSK)
```  
{
    "code": 400,
    "status": "BAD REQUEST",
    "message": "not a valid request"
}
```
### _GET_-Methode
Ruft ein oder mehrere Medien ab.  
_(Anhand vom Beispiel 'Buch', 'CD' funktioniert identisch.)_
#### Alle Bücher abrufen: Aufruf von [http://host/shareit/media/books](https://huberneumeier0shareit.herokuapp.com/shareit/media/books)
##### Antwort _(Bücher vorhanden)_
```  
[
    {
        "title": "book1",
        "author": "author1",
        "isbn": "9781566199094"
    },
    {
        "title": "book2",
        "author": "author2",
        "isbn": "9783827317100"
    }
]
```
##### Antwort _(keine Bücher vorhanden)_
```  
[]
```
#### Ein Buch abrufen: Aufruf von [http://host/shareit/media/books/9781566199094](https://huberneumeier0shareit.herokuapp.com/shareit/media/books/9781566199094)
##### Antwort _(Buch vorhanden)_
```  
{
    "title": "test title",
    "author": "test author",
    "isbn": "9781566199094"
}
```
##### Antwort _(Buch nicht vorhanden)_
```
{
    "code": 404,
    "status": "NOT_FOUND",
    "message": "resource not found"
}
```
### _PUT_-Methode
Ändert ein Medium, bzw ersetzt die gespeicherten Daten durch die per JSON übertragenen.  
_(Anhand vom Beispiel 'Buch', 'CD' funktioniert identisch.)_
##### Anfrage an _([http://host/shareit/media/books/9781566199094](https://huberneumeier0shareit.herokuapp.com/shareit/media/books/9781566199094))_
Um Änderungen am gespeicherten Medium vorzunehmen muss das übergebene JSON nur die zu ändernden Attribute enthalten. Die Angabe der ISBN ist hierbei immer optional, sie darf sich allerdings niemals von der in der URL angegebenen ISBN unterscheiden.
```  
{
    "title": "book1",
    "author": "author1",
    "isbn": "9781566199094"
}
```
##### Antwort _(erfolgreiche Änderung)_
```  
{
    "code": 202,
    "status": "ACCEPTED",
    "message": "accepted"
}
```
##### Antwort _(fehlerhafte Änderung)_
Eine Modifizierung wird nicht durchgeführt wenn...
- Titel oder Autor bzw Director oder FSK leer sind
- angegebene ISBN bzw Barcode nicht mit der in der URL übereinstimmen
```
{
    "code": 304,
    "status": "NOT_MODIFIED",
    "message": "resource not modified"
}
```