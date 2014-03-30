=============================
proiect_idp
=============================
Sandulescu Dragos	341C3
Teris Petrut		342C3
=============================

Tema 1


IMPLEMENTARE GUI

	Pachetele "common" si "gui" ofera o structura de date si metode de 
reprezentare a informatiei, intr-o forma grafica, ce vor fi utilizate de
componenta mediator (in viitor), momentan clasa Main va simula comportamentul
acesteia (pe baza pachetului "test").


COMMON

	IMediator ofera o interfata de comunicare cu mediatorul, pentru componenta
grafica a programului.
	InfoTransfer ofera o structurare a datelor referitoare la un anumit
transfer, cu posibilitatea modificarii acestora.
	Main este o clasa temporara falosita pentru rularea aplicatiei, in absenta
mediatorului.


GUI

	FileTransfer se ocupa de organizarea datelor pentru tabelul de transferuri
(care ofera informatii despre starea stransferurilor la momentul actual),
ofera posibilitatea adaugarii de noi transferuri, resetare stare transfer.
	
	GuiAPI mentine si coordoneaza fluxul de informatii ce afecteaza direct
componenta grafica, cum ar fi listele de utilizatri, de fisiere, informatii
transferuri, informatii despre utilizatorul local.
	
	GuiCore este componenta principala de desenare. Raspunzatoare pentru toate
componentele grafice, construite pe baza informatiei primite de la "GuiAPI",
cu exceptia componentei "progres bar", pentru care mai foloseste un renderer.
	
	ProgressBarRendere folosit de GuiCore pentru a customiza si afisa
componenta "progress bar" in tabelul de transferuri.
	
	Status mentine starile in care se poate afla un transfer, la un anumit
moment de timp.


TESTARE

	Pentru testare am folosit clasa MockupMediator care este un mockup pentru
mediator. In cadrul acestei clase se creeaza 3 SwingWorkeri NewPackageWorker,
NewTransferWorker,NewUserWorker folositi pentru simularea produceri unor
evenimente si sa raspunda la actiunile  utilizatorului asupra interfetei
grafice.

	In continuare voi detalia pentru fiecare SwingWorker. 
	NewUserWorker  verifica daca sunt transferuri neterminate si incrementeaza
progresul unui transfer ales random si va sta apoi o perioada de timp random
dupa care bucla se reia. Practic el simuleaza aparitia unui pachet. 
	NewTransferWorker initiza cereri de fisiere de la alti utilizatori.
Folosind acelasi principiu se seteaza la fiecare pas in bucla un timp random de
sleep,  apoi se alege o destinatie si fisier random si folosind functia publish
a swingworkerului se adauga folosind api-ul interfetei grafice transferul. In
final ultimul worker  NewUserWorker este folosit tot la intervale timp random 
ca sa faca urmatoarele 3 actiuni modifica lista de fisiere, adauga un 
utilizator cu noi  fisiere, sau sterge un utilizator. Acest worker la intervale
de timp random, public un integer, iar in functia proces in functie de acest
numar  se alege una dintre cele 3 optiuni.

=============================





