=============================
proiect_idp
=============================

	Sandulescu Dragos	341C3
	Teris Petrut		342C3

Tema 1
-----------------------------


	IMPLEMENTARE GUI

Pachetele "common" si "gui" ofera o structura de date si metode de 
reprezentare a informatiei, intr-o forma grafica, ce vor fi utilizate de
componenta mediator (in viitor).


	COMMON

IMediator ofera momentan o interfata de comunicare pentru componenta grafica
a programului.
InfoTransfer ofera o structurare a datelor referitoare la un anumit transfer,
cu posibilitatea modificarii acestora.
Main este o clasa falosita pentru rularea aplicatiei, in viitor va instantia
componenta mediator.


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

	ANT

ant build;
ant run;
ant clean;

=============================


Tema2
-----------------------------



	LOGARE
log4j

	TESTARE
JUnit

	RULARE

	Se incarca date din structura de fisiere si directoare continuta de
"Users_info".


	ANT

ant build;

ant run					// default user = me
ant run -Dargs=mihai;	// current user = mihai
ant 					// default target = run_3_users(3 instante de program);

ant clean;

=============================



Modul Networking

Api : oferit mediatorului  
	start_server
		aceasta functie va porni un server pe un anumit port
		si in functie va insarcina classei ProccesMessage cu procesarea mesajelor
		mediator si procesarea mesajelor este decuplata de networking si pentru acest lucru am folosit interfete pentru clasele aferente
	- retrieve_file  functie ce declanseaza seria de mesaje
	pentru obtinerea unui fisier. Mesajul declansator
	este primul din succesiunea de mai jos.
	Aceste mesaje au urmatoare succesiune. Se trimite un
	un mesaj pentru aflarea numarului de chunckuri. Se primeste un raspuns. Iar apoi pt fiecare chunck  se trimite un request pt date si se primeste un response cu datele aferente.

clasa ProcessMessage
	Aceasta clasa verifica ce mesaj a sosit apoi in functie 
de acesta trimite de acesta trimite un request astfel ca acel
flow sa fie respectat.
	Fiecare din cele 4 mesaje care sunt transformate in binar de clasa MessageToByte.
	Informatie care se regasteste in fiecare mesaje este
	sursa,  destinatia si fisierul.
	Legatura dintre mediator si clasa Proccess Message este 
necesara pentru ca doar mediatorul cunoaste interfata grafica
si functiile apelate din mediator au rol doar  de actuliza 
o structura ce tine evidenta  transferurilor in mediator
si apoi de a apela o functie din api-ul interfatei grafice
ca sa actulizeze progresul transferului sau sa adauge un alt
transfer.

	


		




