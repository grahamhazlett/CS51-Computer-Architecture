# CS51-Computer-Architecture


This repository contains my work from a class called CS51 - Computer Architecture at Dartmouth. This course covered various topics in computer architecture, such as digital logic, microcode operations, assembly language programming, input/output operations, caching, and pipelining.

The course involved 9 major homework assignments, and over the course of these problem sets, I completed a microprocessor circuit implementation for the y86 architecture. (more info on y86 can be found [here](https://y86tutoring.wordpress.com/y86-ia/)). These circuits were constructed and simulated in Logisim, a free logical simulator. These homeworks also involved writing assembly code for the microarchitecture, compiling it into hex, uploading it into the program memory, and running the program in the simulated circuit.
Other homework assignments involved developing digital circuits, studying analyzing runtime with caching, and studying pipelining.

# y86 Microprocessor Implementation

The following documents my final y86 microprocessor implementation. My final implementation of this microarchitecture is able to run compiled y86 assembly code, is able to perform all of the y86 instructions found [here](https://y86tutoring.files.wordpress.com/2012/10/y86-instructions-linked1.png), and is able to perform three more instructions past the standard y86 instruction set: OPLi, enter_os, and exit_os.

The circuitry of the final iteration of my y86 microprocessor implementation can be seen below:

![](Final%20y86%20Documentation/dpath.png)

Here, the datapath of this machine can be seen, primarily in the upper left portion of the image. This datapath is controlled by a finite state machine located towards the bottom left of the design, which consists of ROM modules to direct the control lines above depending on the state, and a microsequencer to direct the next state of the FSM. Additionally, the RAM modules, I/O interfaces, and OS privilege components are located towards the right of the design.


The final .circ file containing the circuit design can be found under [/Final y86 Documentation/y86.circ](Final%20y86%20Documentation/y86.circ), and the total count of components for the design can be found under [/Final y86 Documentation/Components.xlsx](Final%20y86%20Documentation/Components.xlsx) Additionally, the FSM ROM microcode can be found in the .xlsx file under [/Final y86 Documentation/FinalFSM.xlsx](Final%20y86%20Documentation/FinalFSM.xlsx). Finally, the internal logic for each of the larger components within the design (such as the microsequnecer, the regreader, etc) can be found under [/Final y86 Documentation](Final%20y86%20Documentation).

Incremental answers to each of the homework assignments, and milestones for the y86 microprocessor development are contained within the HW directories; a summary of the answers to each question of each homework assignment is in the .txt file in each respective directory.
