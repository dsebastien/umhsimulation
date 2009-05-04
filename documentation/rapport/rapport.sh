#!/bin/sh
echo "Génération du rapport (PDF)"
cd tex
latex rapport.tex
wait
dvips -t a4 -Ppdf rapport.dvi
wait
ps2pdf rapport.ps
wait
echo "Rapport généré"
okular rapport.pdf
exit
