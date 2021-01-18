# Genetics Application
## The central dogma of molecular biology made easy. 

Users will be able to input one of the following: 
1. DNA
2. RNA
 
This application, if DNA given as an input, transcribes the DNA into RNA and further translate the RNA
  into a protein. If RNA is the first input the app can either derive the DNA it came from *or* further translate it
  . Furthermore, a user can choose to introduce changes/mutations into the DNA or RNA, and the app will be able to
   inform of changes, if any, to the amino acid sequence. Lastly, if two DNA or RNA strands given, the
    application will highlight what differs in their sequences. The concept of transcription and translation is an early
    topic introduced to students in high school /college and, although the concept is not too challenging, it can
     become quite daunting, time-consuming, and error-prone to repeatedly go through, at least it was for me, which
      is why I am interested in building this application. Additionally, not only is this applicable to
       students learning about transcription and translation, but also has direct application to all current cutting
        edge genetics research on a smaller scale. Thus, users may include any student or scientist who have DNA or
         RNA sequences they are looking to further explore.
         
##User Stories
* As a user, I want to be able to add DNA and RNA to my collection
* As a user, I want to be able to translate any piece of DNA to RNA
* As a user, I want to be able to transcribe any piece of RNA to an amino acid sequence
* As a user, I want to be able to introduce a mutation in a piece of DNA or RNA which also updates it in my collection
* As a user, I want to be able to save my list of DNA and RNA strands to a file
* As a user, I want to have an option to reload my list of DNA and RNA strands from a file when starting the application
* As a user, if I do not load a file before saving new strands, any previous files will be overwritten 

#Phase 4: Task 2
The CodonToAmino uses the Map interface. DnaList and RnaList class' addStrandToList method have robust design
. RnaStrand and DnaStrand class' addToStrand method have robust design. 

#Phase 4: Task 3
The progression of the code is logical on the UML diagram, and I would likely not change very much but one large
 improvement that could be made is refactoring/ factoring out duplicate code. For example in GeneticsApplication the
  panel for DNA and RNA strands look very similar with only minor differences but code is written fully for both. A
   similar issue is present for DnaStrand and RnaStrand as well as DnaList and RnaList. This improvement would help
    to improve coupling in the system. 

#Citations
Data persistence added using the following as a template:  https://github.students.cs.ubc.ca/CPSC210
/JsonSerializationDemo.git