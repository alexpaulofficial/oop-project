<div align="center">
<img widht="500" height="500" src=".github/logo.png">
</div>

# Introduzione
Twitter Project è una REST API con Interfaccia Grafica che permette di elaborare l'engagement (Likes e Retweets) di Tweet scaricati tramite l’[API di Twitter](https://developer.twitter.com/en/docs/tweets/search/api-reference/get-search-tweets). Una volta scaricati e memorizzati i Tweet è possibile [filtrarli](#filtri) ed è possibile ottenere delle [statistiche](#formato-delle-statistiche).

In dettaglio l’applicazione permette di:
*	Restituire i metadati del Tweet, in formato JSON, ovvero l’elenco degli attributi della classe Tweet e il loro tipo
*	Restituire tutti i dati scaricati in formato JSON
*	Restituire i dati <filtrati> per Likes, Retweets, Data in formato JSON
*	Restituire delle statistiche su dati filtrati o su tutti i dati

# Tabella dei contenuti

- [Introduzione](#introduzione)
- [Rotte dell'applicazione](#rotte-dellapplicazione)
- [Formato dati](#formato-dati)
  - [Formato dei Tweet restituiti](#formato-dei-tweet-restituiti)
  - [Formato delle statistiche](#formato-delle-statistiche)
- [Filtri](#filtri)
- [Front-end](#front-end)
- [UML](#uml)

# Rotte dell'applicazione 
Di seguito tutte le richieste possibili tramite chiamate all’indirizzo http://localhost:8080

> **GET** /

[Interfaccia Grafica](#front-end)

> **GET** /metadata

Rotta in cui è possibile accedere ai Meta Data della classe Tweet

> **GET**  /data

Restituisce un JSON di tutti i dati inseriti. Se la lista è vuota viene lanciata un’eccezione (GetTweetException) link

> **POST** /data

E’ possibile caricare un JSON di dati passato per body. 
**ATTENZIONE!** Naturalmente deve essere inserito un JSON ben formattato (se hai dei dubbi visita questo sito: https://jsonformatter.curiousconcept.com/ o simili). Nel caso in cui non fossero presenti i vari campi del Tweet richiesti (è possibile vedere quali sono tramite MetaData) **verranno memorizzati come 0/vuoti!**

> **POST** /data/twitter

Scarica i dati dall’API di Twitter utilizzando l’URL completo inserito nel body (Es. https://wd4hfxnxxa.execute-api.us-east-2.amazonaws.com/dev/api/1.1/search/tweets.json?from=realDonaldTrump=&count=50)

> **POST** /data/filter
Indirizzo per la richiesta di filtraggio dati (vedi [Filtri](#filtri))
> **POST** /data/stats
> **GET** /

Il motivo della scelta di fare molte **POST** è dovuto dal fatto che viene passato un body. Dal punto di vista della natura della chiamata stessa sarebbe più corrretto, oltre la fato che una richiesta GET con Jquery non prevede body.

# Formato dati

## Formato dei Tweet restituiti
Esempio di un solo Tweet:
```
{
    "created_at": "Sat Jun 13 15:32:41 +0000 2020",
        "id": 1271827840556728320,
        "text": "RT Testo del Tweet",
        "retweet_count": 6,
        "favorite_count": 8
}
```
In particolare:
*	**created_at** data di creazione del Tweet in formato `EEE MMM dd HH:mm:ss ZZZZZ yyyy`
*	**id** codice ID identificativo di ogni Tweet
*	**text** testo del Tweet
*	**retweet_count** numero dei Retweets per ogni Tweet
*	**favorite_count** numero dei Likes per ogni Tweet

## Formato Statistiche
Le statistiche vengono restituite come JSON in questo modo:
```
    {
        "Media dei Retweets": 2039.0,
        "Minimo dei Likes": 4906.0,
        "Deviazione Standard dei Likes": 1656.1476,
        "Media dei Likes": 7134.0,
        "Massimo dei Likes": 9420.0,
        "Massimo dei Retweets": 2462.0,
        "Varianza dei Likes": 2742825.0,
        "Minimo dei Retweets": 1459.0,
        "Deviazione Standard dei Retweets": 359.4212,
        "Varianza dei Retweet": 129183.6
    }
```

# Filtri
Il filtro presente nel corpo della richiesta POST per filtrare i dati è una stringa in formato JSON, contenente degli oggetti dotati della seguente struttura:

```
{
    "filter_field": "likes",
    "filter_type": "$gt",
    "parameters": 10
}
```
**ATTENZIONE!** Il formato della Data deve essere di questo tipo: `MMM dd, yyyy, HH:mm:ss`
I valori presenti come dato su cui eseguire il filtro possono essere delle stringhe, dei valori numerici oppure (nel caso degli ultimi tre operatori sopra specificati) un array contenente più valori dello stesso tipo.

### Tabella con tutti i filtri disponibili
| Tipo | Descrizione |
|--|--|
| "$gt" | Maggiore di |
| "$gte" | Maggiore o uguale di |
| "$lt" | Minore di |
| "$lte" | Minore o uguale di |
| "$bt" | Compreso tra |

Il filtro "$bt" richiede due parametri. Se non vengono inseriti, viene lanciate l'eccezione **WrongFilterException**. 
**ATTENZIONE!** I parametri devono essere inseriti come JSON array, altrimenti parte l'eccezione di sopra. Il formato quindi deve essere del tipo

```
{
    "filter_field": "likes",
    "filter_type": "$bt",
    "parameters": [10,100]
}
```
#### Filtro Data

Per i filtri sulla data bisogna fare attenzione a scrivere bene il formato della stessa, altrimenti parte una **ParseException**. Questo è visibile dalla console di Eclipse, ma **NON da Postman o Interfaccia**. Lo aabbiamo isnerito nelle [Funzionalità aggiuntive implementabili](#funzionalità-aggiuntive-implementabili)

# Statistiche

Le statistiche possono essere richieste su tutti i dati o su dei dati filtrati allo stesso modo di come si filtrano i Tweet.

# Eccezioni

`getTweetException()`: è un’ eccezione che si verifica dal momento in cui non c’ è alcun dato salvato in precedenza e quindi ci sarebbe un errore perchè viene restituita dal programma una lista vuota, per cui appare all’ utente un messaggio con scritto: “La lista è vuota!”

# Front-end
L'Interfaccia Grafica è stata implementata tramite linguaggio HTML, CSS e Javascript "puri", senza utilizzo di particolari framework (Angular, React, ecc.). Per lo stile è stato usato Bootstrap e per la gestione dei dati JQuery. I grafici sono stati fatti con Chart.js

Tramite interfaccia grafica è possibile svolgere tutte le operazioni previste dalla nostra API. In tutte le schermate si può aggiungere un link API Twitter per scaricare i dati. Poi ogni pagina ha le proprie funzioni
## Dati

<div align="left">
<img src="FrontEnd/readme/dati.png">
</div>

Questa pagina semplicemente mostra tutti i dati scaricati. Quando viene inserito un link e i Tweet vengono scaricati correttamente, la pagina si riaggiorna da sola così da aggiornare la tabella. Per verificare se il numero dei Tweet scaricati è quello richiesto dal link, è stato 

## Filtri
![](FrontEnd/readme/filtri.gif)

In questa pagina è possibile applicare il filtro ai dati (per vedere tutti i filtri disponibili [clicca qui]())
Dopo aver premuto il tasto "Invia" la pagina si aggiorna e compare la tabella con l'elenco dei Tweet filtrati
## Statistiche
<div align="left">
<img src="FrontEnd/readme/statistiche.png">
</div>

# Diagrammi UML

## Class Diagram

### Package it.univpm.GiAle.twitterProj.app

<div align="left">
<img src="UMLDiagram/it.univpm.GiAle.twitterProj.app.png">
</div>
## Package it.univpm.GiAle.twitterProj.controller

<div align="left">
<img src="UMLDiagram/it.univpm.GiAle.twitterProj.controller.png">
</div>
### Package it.univpm.GiAle.twitterProj.model

<div align="left">
<img src="UMLDiagram/it.univpm.GiAle.twitterProj.model.png">
</div>
###	Package it.univpm.GiAle.twitterProj.filter

<div align="left">
<img src="UMLDiagram/it.univpm.GiAle.twitterProj.filters.png">
</div>

###	Package it.univpm.GiAle.twitterProj.exception

<div align="left">
<img src="UMLDiagram/it.univpm.GiAle.twitterProj.exception.png">
</div>

###	Package it.univpm.GiAle.twitterProj.service

<div align="left">
<img src="UMLDiagram/it.univpm.GiAle.twitterProj.service.png">
</div>
## Use Case Diagram
Il diagramma dei Casi d’ Uso mostra le azioni che l’ utente può svolgere.

<div align="left">
<img src="UMLDiagram/UseCase.png">
</div>

## Sequence Diagram

<div align="left">
<img src="UMLDiagram/Sequence Diagram.png">
</div>

# Autori

* **Alessio Paolucci ([Alex Paul](https://www.alexpaulofficial.com/))**
* **Gin Paolo Verdolini**

## Suddivisione lavori

Per quanto riguarda l’ organizzazione intra-gruppo possiamo dire  di essere stati molto in contatto mediante videochiamate e/o chiamate per cui c’è stato un continuo confrontarsi per ogni decisione presa nello sviluppo di questo progetto, tanto è vero che ci siamo completamente dimenticati di fare “commit” giornaliere proprio per questo sentirci quotidianamente.
Se dovessimo elencare gli elementi su cui ognuno di noi si è focalizzato maggiormente possiamo dire che:
*	**Paolucci** si è occupato della stesura del codice in generale concentrandosi poi sullo sviluppo dell’ interfaccia grafica (argomento che ha catturato il suo interesse)

*	**Verdolini** si è occupato maggiormente delle eccezioni, della struttura dei test e dei diagrammi UML

Questa README stessa la stiamo scrivendo insieme usando Word del pacchetto Office 365 fornitoci dall’Università che permette di collaborare ed apportare modifiche in simultanea allo stesso file.


