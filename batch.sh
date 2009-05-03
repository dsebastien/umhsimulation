#!/bin/bash
for tempsSimul in 1000 10000
do
for trt in 0 0.5 1 #temps de trt
do
for nbHotes in 10 50 100 200 500
do
for tempsInterEnvoi in  200 500 1000 50 100
do
for tailleBuf in 100 1000 100 20 5000
do
for timeout in  200 250 500 1000 80 150
do
java -jar simul.jar --agentsTempsTraitementMessage $trt --hotesTimeoutReemissionMessages $timeout --agentsTailleMaxBuffer $tailleBuf --agentsNombreHotes $nbHotes --hotesTempsMaxInterEnvois $tempsInterEnvoi --duree $tempsSimul
done
done
done
done
done
done