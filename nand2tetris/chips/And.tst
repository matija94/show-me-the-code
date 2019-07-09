load And.hdl,
output-file And.out,
output-list a b out;

set a %B0, set b %B0, eval, output;
set a %B0, set b %B1, eval, output;
set a %B1, set b %B0, eval, output;
set a %B1, set b %B1, eval, output;

