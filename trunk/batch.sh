#!/bin/bash
tempsSimul=100000
trt=0.5 ##temps de trt
nbHotes=10
tempsInterEnvoi=179
tailleBuf=10
timeout=179
perteBrutale=0.00
routage=2000
delaisEntite=5
dureeInit=1000
tauxAutreAgent=0.75
hoteTemptsTrt=0.5
java -jar simulation.jar \
--agentsNombreHotes $nbHotes \
--agentsTailleMaxBuffer $tailleBuf \
--agentsTauxPerteBrutale $perteBrutale \
--agentsTempsInterEnvoiInfosRoutage $routage \
--agentsTempsTraitementMessage $trt \
--hotesTimeoutReemissionMessages $timeout \
--hotesTempsMaxInterEnvois $tempsInterEnvoi \
--duree $tempsSimul \
--delaiEntreEntites $delaisEntite \
--dureeInitialisation $dureeInit \
--hotesTauxMessagesVersAutreAgent $tauxAutreAgent \
--hotesTempsTraitementMessage $hoteTemptsTrt \
--periodiciteAffichageStats 0.01
