# Obligatorisk oppgave 2 i Algoritmer og Datastrukturer

Denne oppgaven er en innlevering i Algoritmer og Datastrukturer. 

# Arbeidsfordeling

Oppgaven er levert av følgende studenter:
* Niman Ahmed Yusuf, S239921, s239921@oslomet.no

Jeg har brukt git til å dokumentere arbeidet mitt.  
Ettersom jeg arbeider alene så har jeg utført de obligatoriske oppgavene for en person.
Jeg har brukt ggit til å lagre besvarelsene mine.

# Beskrivelse av oppgaveløsning (maks 5 linjer per oppgave)

* Oppgave 1: antall() skal bare returnere antall variabelen.
tom()- metoden returnere true om antall/listen inneholder 0 elementer, og false dersom listen/antall har ett element eller mer
DobbeltlenketListe(T[] a) konstruktøren skal sjekke for null verdier. Følger teksten i oppgaven og initialiserer hode og hale basert på om listen er tom eller ikke. Tar ikk med null-verdier i initialiseringen.

* Oppgave 2: 
a)
toString()- følger jeg bare oppgaveteksten og printer ut verdiene og legger dem i []-ved å bruke string builder. OmvendtString()-metoden gjør akkurat det samme, bortsett fra at jeg begynner på halen og itererer bakover og printer ut verdiene.

b)
leggInn(T verdi)-metoden sjekker først at det ikke er noen null verdi.
Sjekker først om listen er tom, for da er denne verdien første verdi i listen.
Hvis ikke så legges den bakerst.

* Oppgave 3: 
a)
finnNode(int indeks)-metoden er en privat hjelpemetode hvor jeg bruker en if-else statement for å finne ut av hvor noden jeg leter etter finnes. bruker deretter en for-loop for å finne frem til korrekt node på gitt indeks.
hent(int indeks)- metoden bruker hjelpemetoden til å hente nodens verdi.
oppdater(int indeks, T nyverdi)-metoden bruker finnNode-hjelpemetoden og erstatter den nåværende verdien med ny verdi på gitt indeks.

b)
subliste-lager en ny dobbeltlenketliste med fra og til variablene og legger inn verdiene i original listen inn i sub listen.

* Oppgave 4:
bruker en for-loop til å løpe igjennom hele listen og returnerer indeksen til verdi om den er funnet. hvis den ikke er funnet i listen så returneres -1.
inneholder(T verdi)-metoden så benytter jeg indeksTil-metoden for å sjekke om den eksisterer i listen. Dersom den ikke gjør det og jeg får returnert tallet -1, så returnerer metoden false, men dersom den finnes returnerer metoden true.

* Oppgave 5: 
I denne metoden så bruker jeg if-else statements for å sjekke hvor den nye verdien skal plasseres. Er listen tom så bruker jeg leggInn-metoden jeg kodet tidligere. Er indeks erlik 0, så legger jeg den nye verdien som hode og hvis den er i mellom hode og hale, så bruker jeg hjelpemetoden finnNode og legger til ny node med ny verdi og peker riktig

* Oppgave 6: 
bruker if-else statements i begge fjern metodene. I fjern(T verdi) itererer først til jeg finner noden, og bruker if-else statements til å fjerne noden korrekt mtp. referansene som må legges inn riktig.
fjern(int indeks) sjekker først om antallet i listen er 1, for da er det hode som erlik hale som fjernes og vi tar vare på verdien. Hvis ikke så sjekker vi om det er hode eller hale som skal fjernes, og hvis ikke så bruker jeg hjelpemetoden finnNode for å finne noden og fjerne noden.

* Oppgave 8:
a)
sjekker først om iteratorendringer er ulikt fra antall endringer, hvis de er ulike så kaster jeg en ConcurrentModificationException slik som beskrevet i oppgaven.
følger deretter oppgavebeskrivelsen og sjekker om hasNext er true eller false og kaster exception om den er false.

b)
følger oppgaveteksten og returnerer en ny instans av DobbeltLenketListeIterator

c)
gjør minimalt med endring og bruker finnNode til å sette verdi til "denne"-variabelen.

d)
Lager en ny instans av DobbeltLenketListeIterator og sender inn indeks som parameter og returnerer instansen av iteratoren.


kodet i tillegg nullstill()-metoden for å få korrekt test-dekning på oppgave 8
