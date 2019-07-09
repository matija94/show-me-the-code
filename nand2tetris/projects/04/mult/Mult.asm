// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)

// Put your code here.

@res
M=0 // res = 0

(LOOP) // loop

@R1
D=M
@END
D;JEQ // if ( r1 == 0) goto end

@R1
M=M-1 // r1--;

@R0
D=M
@res
D=D+M
M=D // res = res + R0

@LOOP
0;JMP // goto loop
(END)
@res
D=M
@R2
M=D
