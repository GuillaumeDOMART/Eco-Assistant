import {useCallback, useEffect, useState} from 'react';
import Box from '@mui/material/Box';
import Stepper from '@mui/material/Stepper';
import Step from '@mui/material/Step';
import StepLabel from '@mui/material/StepLabel';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import {useNavigate} from "react-router-dom";
import {Col, Container, Row, Spinner} from "react-bootstrap";
import {useForm} from "react-hook-form";
import Phase from "./Phase";

const steps = ["Planification", "Developpement", "Test", "Déploiement", "Maintenance"];

const test = {
    "TEST": [
        {
            "questionId": 29,
            "intitule": "Veux-tu remplir cette phase?",
            "phase": "TEST",
            "type": "QCM",
            "dependance": -1,
            "reponses": [
                {
                    "questionSuivId": 40,
                    "reponseId": 42,
                    "intitule": "NON"
                },
                {
                    "questionSuivId": 30,
                    "reponseId": 41,
                    "intitule": "OUI"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 30,
            "intitule": "Combien de jours dure cette phase ?",
            "phase": "TEST",
            "type": "NUMERIC",
            "dependance": 42,
            "reponses": [
                {
                    "questionSuivId": 31,
                    "reponseId": 43,
                    "intitule": "Veuillez entrer un entier"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 31,
            "intitule": "Combien de collaborateurs travaillent sur cette phase ?",
            "phase": "TEST",
            "type": "NUMERIC",
            "dependance": 42,
            "reponses": [
                {
                    "questionSuivId": 32,
                    "reponseId": 44,
                    "intitule": "Veuillez entrer un entier"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 32,
            "intitule": "Combien de jours de télé-travail vos collaborateur ont pour cette phase ?",
            "phase": "TEST",
            "type": "NUMERIC",
            "dependance": 42,
            "reponses": [
                {
                    "questionSuivId": 33,
                    "reponseId": 45,
                    "intitule": "Veuillez entrer un entier"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 33,
            "intitule": "Connais-tu à peu près la distance de trajet de tes collaborateurs ?",
            "phase": "TEST",
            "type": "QCM",
            "dependance": 42,
            "reponses": [
                {
                    "questionSuivId": 34,
                    "reponseId": 46,
                    "intitule": "OUI"
                },
                {
                    "questionSuivId": 35,
                    "reponseId": 47,
                    "intitule": "NON"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 34,
            "intitule": "Quelle est la distance moyenne de trajet de tes collaborateurs pour aller au travail (en km) ?",
            "phase": "TEST",
            "type": "NUMERIC",
            "dependance": 47,
            "reponses": [
                {
                    "questionSuivId": 35,
                    "reponseId": 48,
                    "intitule": "Veuillez entrer un entier"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 35,
            "intitule": "Combien viennent en voiture ?",
            "phase": "TEST",
            "type": "NUMERIC",
            "dependance": 41,
            "reponses": [
                {
                    "questionSuivId": 36,
                    "reponseId": 49,
                    "intitule": "Veuillez entrer un entier"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 36,
            "intitule": "Combien viennent en vélo ou à pied ?",
            "phase": "TEST",
            "type": "NUMERIC",
            "dependance": 41,
            "reponses": [
                {
                    "questionSuivId": 37,
                    "reponseId": 50,
                    "intitule": "Veuillez entrer un entier"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 37,
            "intitule": "Combien viennent en transport en commun ?",
            "phase": "TEST",
            "type": "NUMERIC",
            "dependance": 41,
            "reponses": [
                {
                    "questionSuivId": 38,
                    "reponseId": 51,
                    "intitule": "Veuillez entrer un entier"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 38,
            "intitule": "Combien de PC fixes utilises-tu pour cette phase ?",
            "phase": "TEST",
            "type": "NUMERIC",
            "dependance": 41,
            "reponses": [
                {
                    "questionSuivId": 39,
                    "reponseId": 52,
                    "intitule": "Veuillez entrer un entier"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 39,
            "intitule": "Combien de PC portables utilises-tu pour cette phase ?",
            "phase": "TEST",
            "type": "NUMERIC",
            "dependance": 41,
            "reponses": [
                {
                    "questionSuivId": 40,
                    "reponseId": 53,
                    "intitule": "Veuillez entrer un entier"
                }
            ],
            "reponse": null
        }
    ],
    "HORS_PHASE": [
        {
            "questionId": 1,
            "intitule": "Votre projet nécessite-t-il des trajets en avion ?",
            "phase": "HORS_PHASE",
            "type": "QCM",
            "dependance": -1,
            "reponses": [
                {
                    "questionSuivId": 3,
                    "reponseId": 2,
                    "intitule": "NON"
                },
                {
                    "questionSuivId": 2,
                    "reponseId": 1,
                    "intitule": "OUI"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 2,
            "intitule": "Combien de km en avion sont effectués pour le projet ?",
            "phase": "HORS_PHASE",
            "type": "NUMERIC",
            "dependance": 1,
            "reponses": [
                {
                    "questionSuivId": 3,
                    "reponseId": 3,
                    "intitule": "Veuillez entrer un entier"
                }
            ],
            "reponse": null
        }
    ],
    "DEVELOPPEMENT": [
        {
            "questionId": 14,
            "intitule": "Veux-tu remplir cette phase?",
            "phase": "DEVELOPPEMENT",
            "type": "QCM",
            "dependance": -1,
            "reponses": [
                {
                    "questionSuivId": 29,
                    "reponseId": 18,
                    "intitule": "NON"
                },
                {
                    "questionSuivId": 15,
                    "reponseId": 17,
                    "intitule": "OUI"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 15,
            "intitule": "Combien de jours dure cette phase ?",
            "phase": "DEVELOPPEMENT",
            "type": "NUMERIC",
            "dependance": 17,
            "reponses": [
                {
                    "questionSuivId": 16,
                    "reponseId": 19,
                    "intitule": "Veuillez entrer un entier"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 16,
            "intitule": "Combien de collaborateurs travaillent sur cette phase ?",
            "phase": "DEVELOPPEMENT",
            "type": "NUMERIC",
            "dependance": 17,
            "reponses": [
                {
                    "questionSuivId": 17,
                    "reponseId": 20,
                    "intitule": "Veuillez entrer un entier"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 17,
            "intitule": "Combien de jours de télé-travail vos collaborateur ont pour cette phase ?",
            "phase": "DEVELOPPEMENT",
            "type": "NUMERIC",
            "dependance": 17,
            "reponses": [
                {
                    "questionSuivId": 18,
                    "reponseId": 21,
                    "intitule": "Veuillez entrer un entier"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 18,
            "intitule": "Connais-tu à peu près la distance de trajet de tes collaborateurs ?",
            "phase": "DEVELOPPEMENT",
            "type": "QCM",
            "dependance": 17,
            "reponses": [
                {
                    "questionSuivId": 19,
                    "reponseId": 23,
                    "intitule": "NON"
                },
                {
                    "questionSuivId": 19,
                    "reponseId": 22,
                    "intitule": "OUI"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 19,
            "intitule": "Quelle est la distance moyenne de trajet de tes collaborateurs pour aller au travail (en km) ?",
            "phase": "DEVELOPPEMENT",
            "type": "NUMERIC",
            "dependance": 23,
            "reponses": [
                {
                    "questionSuivId": 20,
                    "reponseId": 24,
                    "intitule": "Veuillez entrer un entier"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 20,
            "intitule": "Combien viennent en voiture ?",
            "phase": "DEVELOPPEMENT",
            "type": "NUMERIC",
            "dependance": 17,
            "reponses": [
                {
                    "questionSuivId": 21,
                    "reponseId": 25,
                    "intitule": "Veuillez entrer un entier"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 21,
            "intitule": "Combien viennent en vélo ou à pied ?",
            "phase": "DEVELOPPEMENT",
            "type": "NUMERIC",
            "dependance": 17,
            "reponses": [
                {
                    "questionSuivId": 22,
                    "reponseId": 26,
                    "intitule": "Veuillez entrer un entier"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 22,
            "intitule": "Combien viennent en transport en commun ?",
            "phase": "DEVELOPPEMENT",
            "type": "NUMERIC",
            "dependance": 17,
            "reponses": [
                {
                    "questionSuivId": 23,
                    "reponseId": 27,
                    "intitule": "Veuillez entrer un entier"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 23,
            "intitule": "Combien de PC fixes utilises-tu pour cette phase ?",
            "phase": "DEVELOPPEMENT",
            "type": "NUMERIC",
            "dependance": 17,
            "reponses": [
                {
                    "questionSuivId": 24,
                    "reponseId": 28,
                    "intitule": "Veuillez entrer un entier"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 24,
            "intitule": "Combien de PC portables utilises-tu pour cette phase ?",
            "phase": "DEVELOPPEMENT",
            "type": "NUMERIC",
            "dependance": 17,
            "reponses": [
                {
                    "questionSuivId": 25,
                    "reponseId": 29,
                    "intitule": "Veuillez entrer un entier"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 25,
            "intitule": "Quel langage utilises-tu majoritairement pour le back ?",
            "phase": "DEVELOPPEMENT",
            "type": "QCM",
            "dependance": 17,
            "reponses": [
                {
                    "questionSuivId": 26,
                    "reponseId": 34,
                    "intitule": "Autre"
                },
                {
                    "questionSuivId": 26,
                    "reponseId": 33,
                    "intitule": "Python"
                },
                {
                    "questionSuivId": 26,
                    "reponseId": 32,
                    "intitule": "Java"
                },
                {
                    "questionSuivId": 26,
                    "reponseId": 31,
                    "intitule": "C++"
                },
                {
                    "questionSuivId": 26,
                    "reponseId": 30,
                    "intitule": "C"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 26,
            "intitule": "Combien de lignes de code ?",
            "phase": "DEVELOPPEMENT",
            "type": "NUMERIC",
            "dependance": 17,
            "reponses": [
                {
                    "questionSuivId": 27,
                    "reponseId": 35,
                    "intitule": "Veuillez entrer un entier"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 27,
            "intitule": "Quel langage utilises-tu majoritairement pour le front ?",
            "phase": "DEVELOPPEMENT",
            "type": "QCM",
            "dependance": 17,
            "reponses": [
                {
                    "questionSuivId": 28,
                    "reponseId": 39,
                    "intitule": "Autre"
                },
                {
                    "questionSuivId": 28,
                    "reponseId": 38,
                    "intitule": "TypeScript"
                },
                {
                    "questionSuivId": 28,
                    "reponseId": 37,
                    "intitule": "JavaScript"
                },
                {
                    "questionSuivId": 28,
                    "reponseId": 36,
                    "intitule": "PHP"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 28,
            "intitule": "Combien de lignes de code ?",
            "phase": "DEVELOPPEMENT",
            "type": "NUMERIC",
            "dependance": 17,
            "reponses": [
                {
                    "questionSuivId": 29,
                    "reponseId": 40,
                    "intitule": "Veuillez entrer un entier"
                }
            ],
            "reponse": null
        }
    ],
    "DEPLOIEMENT": [
        {
            "questionId": 40,
            "intitule": "Veux-tu remplir cette phase?",
            "phase": "DEPLOIEMENT",
            "type": "QCM",
            "dependance": -1,
            "reponses": [
                {
                    "questionSuivId": 55,
                    "reponseId": 55,
                    "intitule": "NON"
                },
                {
                    "questionSuivId": 41,
                    "reponseId": 54,
                    "intitule": "OUI"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 41,
            "intitule": "Combien de jours dure cette phase ?",
            "phase": "DEPLOIEMENT",
            "type": "NUMERIC",
            "dependance": 55,
            "reponses": [
                {
                    "questionSuivId": 42,
                    "reponseId": 56,
                    "intitule": "Veuillez entrer un entier"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 42,
            "intitule": "Combien de collaborateurs travaillent sur cette phase ?",
            "phase": "DEPLOIEMENT",
            "type": "NUMERIC",
            "dependance": 55,
            "reponses": [
                {
                    "questionSuivId": 43,
                    "reponseId": 57,
                    "intitule": "Veuillez entrer un entier"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 43,
            "intitule": "Combien de jours de télé-travail vos collaborateur ont pour cette phase ?",
            "phase": "DEPLOIEMENT",
            "type": "NUMERIC",
            "dependance": 55,
            "reponses": [
                {
                    "questionSuivId": 44,
                    "reponseId": 58,
                    "intitule": "Veuillez entrer un entier"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 44,
            "intitule": "Connais-tu à peu près la distance de trajet de tes collaborateurs ?",
            "phase": "DEPLOIEMENT",
            "type": "QCM",
            "dependance": 55,
            "reponses": [
                {
                    "questionSuivId": 45,
                    "reponseId": 59,
                    "intitule": "OUI"
                },
                {
                    "questionSuivId": 46,
                    "reponseId": 60,
                    "intitule": "NON"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 45,
            "intitule": "Quelle est la distance moyenne de trajet de tes collaborateurs pour aller au travail (en km) ?",
            "phase": "DEPLOIEMENT",
            "type": "NUMERIC",
            "dependance": 60,
            "reponses": [
                {
                    "questionSuivId": 46,
                    "reponseId": 61,
                    "intitule": "Veuillez entrer un entier"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 46,
            "intitule": "Combien viennent en voiture ?",
            "phase": "DEPLOIEMENT",
            "type": "NUMERIC",
            "dependance": 54,
            "reponses": [
                {
                    "questionSuivId": 47,
                    "reponseId": 62,
                    "intitule": "Veuillez entrer un entier"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 47,
            "intitule": "Combien viennent en vélo ou à pied ?",
            "phase": "DEPLOIEMENT",
            "type": "NUMERIC",
            "dependance": 54,
            "reponses": [
                {
                    "questionSuivId": 48,
                    "reponseId": 63,
                    "intitule": "Veuillez entrer un entier"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 48,
            "intitule": "Combien viennent en transport en commun ?",
            "phase": "DEPLOIEMENT",
            "type": "NUMERIC",
            "dependance": 54,
            "reponses": [
                {
                    "questionSuivId": 49,
                    "reponseId": 64,
                    "intitule": "Veuillez entrer un entier"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 49,
            "intitule": "Combien de PC fixes utilises-tu pour cette phase ?",
            "phase": "DEPLOIEMENT",
            "type": "NUMERIC",
            "dependance": 54,
            "reponses": [
                {
                    "questionSuivId": 50,
                    "reponseId": 65,
                    "intitule": "Veuillez entrer un entier"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 50,
            "intitule": "Combien de PC portables utilises-tu pour cette phase ?",
            "phase": "DEPLOIEMENT",
            "type": "NUMERIC",
            "dependance": 54,
            "reponses": [
                {
                    "questionSuivId": 51,
                    "reponseId": 66,
                    "intitule": "Veuillez entrer un entier"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 51,
            "intitule": "Utilises-tu un DataCenter ?",
            "phase": "DEPLOIEMENT",
            "type": "QCM",
            "dependance": 54,
            "reponses": [
                {
                    "questionSuivId": 52,
                    "reponseId": 67,
                    "intitule": "OUI"
                },
                {
                    "questionSuivId": 55,
                    "reponseId": 68,
                    "intitule": "NON"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 52,
            "intitule": "Combien d’énergie votre Datacenter consomme-t-il (en kWh) ?",
            "phase": "DEPLOIEMENT",
            "type": "NUMERIC",
            "dependance": 68,
            "reponses": [
                {
                    "questionSuivId": 53,
                    "reponseId": 69,
                    "intitule": "Veuillez entrer un entier"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 53,
            "intitule": "Sais-tu comment est produite l énergie qui alimente majoritairement ton DataCenter ?",
            "phase": "DEPLOIEMENT",
            "type": "QCM",
            "dependance": 68,
            "reponses": [
                {
                    "questionSuivId": 54,
                    "reponseId": 70,
                    "intitule": "OUI"
                },
                {
                    "questionSuivId": 55,
                    "reponseId": 71,
                    "intitule": "NON"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 54,
            "intitule": "Quelle énergie alimente majoritairement ton DataCenter ?",
            "phase": "DEPLOIEMENT",
            "type": "QCM",
            "dependance": 71,
            "reponses": [
                {
                    "questionSuivId": 55,
                    "reponseId": 74,
                    "intitule": "ENERGIE RENOUVELABLE"
                },
                {
                    "questionSuivId": 55,
                    "reponseId": 73,
                    "intitule": "FOSSILE"
                },
                {
                    "questionSuivId": 55,
                    "reponseId": 72,
                    "intitule": "NUCLEAIRE"
                }
            ],
            "reponse": null
        }
    ],
    "MAINTENANCE": [
        {
            "questionId": 55,
            "intitule": "Veux-tu remplir cette phase?",
            "phase": "MAINTENANCE",
            "type": "QCM",
            "dependance": -1,
            "reponses": [
                {
                    "questionSuivId": -1,
                    "reponseId": 76,
                    "intitule": "NON"
                },
                {
                    "questionSuivId": 56,
                    "reponseId": 75,
                    "intitule": "OUI"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 56,
            "intitule": "Combien de jours dure cette phase ?",
            "phase": "MAINTENANCE",
            "type": "NUMERIC",
            "dependance": 76,
            "reponses": [
                {
                    "questionSuivId": 57,
                    "reponseId": 77,
                    "intitule": "Veuillez entrer un entier"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 57,
            "intitule": "Combien de collaborateurs travaillent sur cette phase ?",
            "phase": "MAINTENANCE",
            "type": "NUMERIC",
            "dependance": 76,
            "reponses": [
                {
                    "questionSuivId": 58,
                    "reponseId": 78,
                    "intitule": "Veuillez entrer un entier"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 58,
            "intitule": "Combien de jours de télé-travail vos collaborateur ont pour cette phase ?",
            "phase": "MAINTENANCE",
            "type": "NUMERIC",
            "dependance": 76,
            "reponses": [
                {
                    "questionSuivId": 59,
                    "reponseId": 79,
                    "intitule": "Veuillez entrer un entier"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 59,
            "intitule": "Connais-tu à peu près la distance de trajet de tes collaborateurs ?",
            "phase": "MAINTENANCE",
            "type": "QCM",
            "dependance": 76,
            "reponses": [
                {
                    "questionSuivId": 60,
                    "reponseId": 81,
                    "intitule": "NON"
                },
                {
                    "questionSuivId": 60,
                    "reponseId": 80,
                    "intitule": "OUI"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 60,
            "intitule": "Quelle est la distance moyenne de trajet de tes collaborateurs pour aller au travail (en km) ?",
            "phase": "MAINTENANCE",
            "type": "NUMERIC",
            "dependance": 81,
            "reponses": [
                {
                    "questionSuivId": 61,
                    "reponseId": 82,
                    "intitule": "Veuillez entrer un entier"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 61,
            "intitule": "Combien viennent en voiture ?",
            "phase": "MAINTENANCE",
            "type": "NUMERIC",
            "dependance": 75,
            "reponses": [
                {
                    "questionSuivId": 62,
                    "reponseId": 83,
                    "intitule": "Veuillez entrer un entier"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 62,
            "intitule": "Combien viennent en vélo ou à pied ?",
            "phase": "MAINTENANCE",
            "type": "NUMERIC",
            "dependance": 75,
            "reponses": [
                {
                    "questionSuivId": 63,
                    "reponseId": 84,
                    "intitule": "Veuillez entrer un entier"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 63,
            "intitule": "Combien viennent en transport en commun ?",
            "phase": "MAINTENANCE",
            "type": "NUMERIC",
            "dependance": 75,
            "reponses": [
                {
                    "questionSuivId": 64,
                    "reponseId": 85,
                    "intitule": "Veuillez entrer un entier"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 64,
            "intitule": "Combien de PC fixes utilises-tu pour cette phase ?",
            "phase": "MAINTENANCE",
            "type": "NUMERIC",
            "dependance": 75,
            "reponses": [
                {
                    "questionSuivId": 65,
                    "reponseId": 86,
                    "intitule": "Veuillez entrer un entier"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 65,
            "intitule": "Combien de PC portables utilises-tu pour cette phase ?",
            "phase": "MAINTENANCE",
            "type": "NUMERIC",
            "dependance": 75,
            "reponses": [
                {
                    "questionSuivId": -1,
                    "reponseId": 87,
                    "intitule": "Veuillez entrer un entier"
                }
            ],
            "reponse": null
        }
    ],
    "PLANIFICATION": [
        {
            "questionId": 3,
            "intitule": "Veux-tu remplir cette phase ?",
            "phase": "PLANIFICATION",
            "type": "QCM",
            "dependance": -1,
            "reponses": [
                {
                    "questionSuivId": 14,
                    "reponseId": 5,
                    "intitule": "NON"
                },
                {
                    "questionSuivId": 4,
                    "reponseId": 4,
                    "intitule": "OUI"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 4,
            "intitule": "Combien de jours dure cette phase ?",
            "phase": "PLANIFICATION",
            "type": "NUMERIC",
            "dependance": 4,
            "reponses": [
                {
                    "questionSuivId": 5,
                    "reponseId": 6,
                    "intitule": "Veuillez entrer un entier"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 5,
            "intitule": "Combien de collaborateurs travaillent sur cette phase ?",
            "phase": "PLANIFICATION",
            "type": "NUMERIC",
            "dependance": 4,
            "reponses": [
                {
                    "questionSuivId": 6,
                    "reponseId": 7,
                    "intitule": "Veuillez entrer un entier"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 6,
            "intitule": "Combien de jours de télé-travail vos collaborateur ont pour cette phase ?",
            "phase": "PLANIFICATION",
            "type": "NUMERIC",
            "dependance": 4,
            "reponses": [
                {
                    "questionSuivId": 7,
                    "reponseId": 8,
                    "intitule": "Veuillez entrer un entier"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 7,
            "intitule": "Connais-tu à peu près la distance de trajet de tes collaborateurs ?",
            "phase": "PLANIFICATION",
            "type": "QCM",
            "dependance": 9,
            "reponses": [
                {
                    "questionSuivId": 8,
                    "reponseId": 9,
                    "intitule": "OUI"
                },
                {
                    "questionSuivId": 9,
                    "reponseId": 10,
                    "intitule": "NON"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 8,
            "intitule": "Quelle est la distance moyenne de trajet de tes collaborateurs pour aller au travail (en km) ?",
            "phase": "PLANIFICATION",
            "type": "NUMERIC",
            "dependance": 4,
            "reponses": [
                {
                    "questionSuivId": 9,
                    "reponseId": 11,
                    "intitule": "Veuillez entrer un entier"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 9,
            "intitule": "Combien viennent en voiture ?",
            "phase": "PLANIFICATION",
            "type": "NUMERIC",
            "dependance": 4,
            "reponses": [
                {
                    "questionSuivId": 10,
                    "reponseId": 12,
                    "intitule": "Veuillez entrer un entier"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 10,
            "intitule": "Combien viennent en vélo ou à pied ?",
            "phase": "PLANIFICATION",
            "type": "NUMERIC",
            "dependance": 4,
            "reponses": [
                {
                    "questionSuivId": 11,
                    "reponseId": 13,
                    "intitule": "Veuillez entrer un entier"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 11,
            "intitule": "Combien viennent en transport en commun ?",
            "phase": "PLANIFICATION",
            "type": "NUMERIC",
            "dependance": 4,
            "reponses": [
                {
                    "questionSuivId": 12,
                    "reponseId": 14,
                    "intitule": "Veuillez entrer un entier"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 12,
            "intitule": "Combien de PC fixes utilises-tu pour cette phase ?",
            "phase": "PLANIFICATION",
            "type": "NUMERIC",
            "dependance": 4,
            "reponses": [
                {
                    "questionSuivId": 13,
                    "reponseId": 15,
                    "intitule": "Veuillez entrer un entier"
                }
            ],
            "reponse": null
        },
        {
            "questionId": 13,
            "intitule": "Combien de PC portables utilises-tu pour cette phase ?",
            "phase": "PLANIFICATION",
            "type": "NUMERIC",
            "dependance": 4,
            "reponses": [
                {
                    "questionSuivId": 14,
                    "reponseId": 16,
                    "intitule": "Veuillez entrer un entier"
                }
            ],
            "reponse": null
        }
    ]
}

/**
 * The component representing the Stepper
 * @returns {JSX.Element}
 * @constructor
 */
function StepperComponent() {
    const [activeStep, setActiveStep] = useState(0);
    const [errorApiGetQuestionnaire, setErrorApiGetQuestionnaire] = useState(null);
    const [isLoaded, setIsLoaded] = useState(false);
    const [data, setData] = useState({})
    const [selectedAnswers, setSelectedAnswers] = useState([])
    const {register, handleSubmit, reset} = useForm();
    const navigate = useNavigate();

    /**
     * Go to the next step
     */
    const handleNext = useCallback(
        () => {
            setActiveStep((prevActiveStep) => prevActiveStep + 1);
            reset()
        },
        [reset]
    );

    /**
     * Go back to the previous step
     */
    const handleBack = useCallback(
        () => {
            setActiveStep((prevActiveStep) => prevActiveStep - 1);
            reset()
        },
        [reset]
    );

    /**
     * Reset the stepper
     */
    const handleReset = useCallback(
        () => {
            const projectId = sessionStorage.getItem("project")
            sessionStorage.removeItem("project")
            navigate(`/result?id=${projectId}`)
        },
        [navigate]
    );

    const handleQuit = useCallback(
        () => {
            navigate("/profil");
        },
        [navigate]
    )

    const handleChange = useCallback(
        (newAnswer) => {
            selectedAnswers.forEach(value => {
                if(value.question === newAnswer.question)
                    selectedAnswers.splice(selectedAnswers.indexOf(value), 1)
            })
            selectedAnswers.push(newAnswer)
            setSelectedAnswers(selectedAnswers)
        }, [selectedAnswers, setSelectedAnswers]
    )

    /**
     * Send responses to the backEnd when Next button is pressed
     * @param dataList
     */
    const onSubmit = (dataList) => {
        const projectId = sessionStorage.getItem("project")
        const sendToBack = {}
        const responses = []
        for (const [key, value] of Object.entries(dataList)) {
            const tuple = {}
            tuple.questionId = key;
            tuple.entry = value;
            responses.push(tuple)
        }
        sendToBack.projetId = projectId;
        sendToBack.reponses = responses;

        const token = sessionStorage.getItem("token")

        const myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");
        myHeaders.append("Authorization", `Bearer ${token}`);

        const requestOptions = {
            method: 'POST',
            headers: myHeaders,
            body: JSON.stringify(sendToBack),
            redirect: 'follow'
        };

        fetch("/api/reponsesDonnees", requestOptions)
            .then(response => response.text())

        if (activeStep === steps.length - 1)
            navigate(`/result?id=${projectId}`)
        else
            handleNext();
    }

    // useEffect( () => {
    //     const token = sessionStorage.getItem("token")
    //     const id = sessionStorage.getItem("project")
    //     const options = {
    //         method: 'POST',
    //         body: JSON.stringify({id}),
    //         headers: {
    //             'Authorization': `Bearer ${token}`,
    //             'Content-Type': "application/json"
    //         },
    //     };
    //     fetch("/api/questions", options)
    //         .then(res => res.json())
    //         .then(
    //             (result) => {
    //                 setIsLoaded(true);
    //                 console.log(result)
    //                 setData(result);
    //             },
    //             (error) => {
    //                 setIsLoaded(true);
    //                 setErrorApiGetQuestionnaire(error);
    //             }
    //         )
    // }, [])
    //
    // if (errorApiGetQuestionnaire) {
    //     return <div>Error: {errorApiGetQuestionnaire.message}</div>;
    // } else if (!isLoaded) {
    //     return (<>
    //             <div>Loading...</div>
    //             <Spinner animation="grow" variant="success"/>
    //         </>
    //     );
    // } else {
    let currentPhase = test.PLANIFICATION;
    switch (activeStep) {
        case 0:
            currentPhase = test.PLANIFICATION
            break;
        case 1:
            currentPhase = test.DEVELOPPEMENT
            break;
        case 2:
            currentPhase = test.TEST
            break;
        case 3:
            currentPhase = test.DEPLOIEMENT
            break;
        case 4:
            currentPhase = test.MAINTENANCE
            break;
        default:
            break;
    }
    return (
        <Container fluid>
            <Row>
                <Box className="mt-3">
                    <Stepper activeStep={activeStep} alternativeLabel>
                        {steps.map((label) => {
                            const stepProps = {};
                            const labelProps = {};
                            return (
                                <Step key={label} {...stepProps}>
                                    <StepLabel {...labelProps}>{label}</StepLabel>
                                </Step>
                            );
                        })}
                    </Stepper>
                </Box>
                <Col></Col>
                <form onSubmit={handleSubmit(onSubmit)}
                      style={{paddingLeft: '120px', paddingRight: '120px', marginTop: '20px'}}
                      className="navbar-nav-scroll mt-4 col-8"
                >
                    {currentPhase.map(question => {
                            const check = selectedAnswers.map(answer => {
                                if (answer.questionId === question.dependance) {
                                    return true;
                                }
                            })
                            if (question.dependance === -1 || check === true) {
                                console.log(selectedAnswers)
                                return (
                                    <Phase key={question.questionId}
                                           register={register}
                                           value={question}
                                           handleChange={handleChange}
                                    />)
                            }
                        }
                    )}
                    <Box className="">
                        {activeStep === steps.length ? (
                            <>
                                <Typography sx={{mt: 2, mb: 1}}>
                                    All steps completed - you&apos;re finished
                                </Typography>
                                <Box sx={{display: 'flex', flexDirection: 'row', pt: 2}}>
                                    <Box sx={{flex: '1 1 auto'}}/>
                                    <Button onClick={handleReset}>Reset</Button>
                                </Box>
                            </>
                        ) : (
                            <Box sx={{display: 'flex', flexDirection: 'row', pt: 2}}>
                                <Button
                                    color="inherit"
                                    disabled={activeStep === 0}
                                    onClick={handleBack}
                                    sx={{mr: 1}}
                                >
                                    Back
                                </Button>
                                <Box sx={{flex: '1 1 auto'}}/>

                                <Button type={"submit"}>
                                    {activeStep === steps.length - 1 ? 'Finish' : 'Next'}
                                </Button>
                            </Box>
                        )}
                    </Box>
                </form>
                <Col>
                    <Button className="align-bottom" onClick={handleQuit}>Quitter</Button>
                </Col>
            </Row>
        </Container>

    );
    // }
}

export default StepperComponent