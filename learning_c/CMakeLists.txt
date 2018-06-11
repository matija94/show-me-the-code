cmake_minimum_required(VERSION 3.10)
project(learning_c C)

set(CMAKE_C_STANDARD 11)

include_directories(src)

add_executable(learning_c
        src/calc.h
        src/calendar.c
        src/cat.c
        src/compareTwoFiles.c
        src/define_proc.c
        src/doubles.c
        src/frequency_chart.c
        src/getch.c
        src/getop.c
        src/ints.c
        src/longestLine.c
        src/main.c
        src/memory.c
        src/minprintf.c
        src/my_grep.c
        src/remove_comment.c
        src/reverse_polish_calc.c
        src/sorting.c
        src/sortLines.c
        src/stack.c
        src/strings.c
        src/table_lookup.c
        src/upperOrLower.c
        src/wordcountC.c
        src/wordounctGeneral.c)
