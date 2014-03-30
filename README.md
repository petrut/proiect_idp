proiect_idp
===========

TESTARE


	Pentru testare am folosit clasa MockupMediator care este un mockup pentru 
mediator. In cadrul acestei clase se creeaza 3 SwingWorkeri NewPackageWorker,
NewTransferWorker,NewUserWorker folositi pentru simularea produceri unor evenimente
si sa raspunda la actiunile  utilizatorului asupra interfetei grafice.
In continuare voi detalia pentru fiecare SwingWorker. 
NewUserWorker  verifica daca sunt transferuri neterminate si incrementeaza progresul
unui transfer ales random si va sta apoi o perioada de timp random dupa care bucla se 
reia. Practic el simuleaza aparitia unui pachet.  NewTransferWorker initiza cereri
de fisiere de la alti utilizatori.  Folosind acelasi principiu se seteaza la fiecare 
pas in bucla un timp random de sleep,  apoi se alege o destinatie si fisier random si
folosind functia publish a swingworkerului se adauga folosind api-ul interfetei grafice
transferul. In final ultimul worker  NewUserWorker este folosit tot la intervale timp 
random ca sa faca urmatoarele 3 actiuni modifica lista de fisiere, adauga un utilizator
cu noi  fisiere, sau sterge un utilizator. Acest worker la intervale de timp random,
public un integer, iar in functia proces in functie de acest numar  se alege una 
dintre cele 3 optiuni.







