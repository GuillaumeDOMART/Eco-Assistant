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
import Phase from "../../Views/Quiz/Phase";

const steps = ["Conception", "Developpement", "Test", "Production", "Maintenance"];

// const test = {
//     "DEVELOPPEMENT": [
//         {
//             "questionId": 13,
//             "intitule": "Combien de collaborateurs travaillent sur cette phase ?",
//             "phase": "DEVELOPPEMENT",
//             "type": "NUMERIC",
//             "categorie": "FIRST",
//             "reponses": [
//                 {
//                     "questionSuivId": 14,
//                     "intitule": "Veuillez entrer un entier"
//                 }
//             ]
//         },
//         {
//             "questionId": 14,
//             "intitule": "Connaissez-vous à peu près la distance de trajet de vos collaborateurs ?",
//             "phase": "DEVELOPPEMENT",
//             "type": "QCM",
//             "categorie": "FIRST",
//             "reponses": [
//                 {
//                     "questionSuivId": 15,
//                     "intitule": "OUI"
//                 },
//                 {
//                     "questionSuivId": 16,
//                     "intitule": "NON"
//                 }
//             ]
//         },
//         {
//             "questionId": 15,
//             "intitule": "Quelle est la distance moyenne de trajet de vos collaborateurs pour aller au travail ?",
//             "phase": "DEVELOPPEMENT",
//             "type": "NUMERIC",
//             "categorie": "FIRST",
//             "reponses": [
//                 {
//                     "questionSuivId": 16,
//                     "intitule": "Veuillez entrer un entier"
//                 }
//             ]
//         },
//         {
//             "questionId": 16,
//             "intitule": "Combien viennent en voiture ?",
//             "phase": "DEVELOPPEMENT",
//             "type": "NUMERIC",
//             "categorie": "FIRST",
//             "reponses": [
//                 {
//                     "questionSuivId": 17,
//                     "intitule": "Veuillez entrer un entier"
//                 }
//             ]
//         },
//         {
//             "questionId": 17,
//             "intitule": "Combien de PC utilisez-vous pour cette phase ?",
//             "phase": "DEVELOPPEMENT",
//             "type": "NUMERIC",
//             "categorie": "FIRST",
//             "reponses": [
//                 {
//                     "questionSuivId": 18,
//                     "intitule": "Veuillez entrer un entier"
//                 }
//             ]
//         },
//         {
//             "questionId": 18,
//             "intitule": "Utilisez-vous des machines virtuelles ?",
//             "phase": "DEVELOPPEMENT",
//             "type": "QCM",
//             "categorie": "FIRST",
//             "reponses": [
//                 {
//                     "questionSuivId": 19,
//                     "intitule": "NON"
//                 },
//                 {
//                     "questionSuivId": 19,
//                     "intitule": "OUI"
//                 }
//             ]
//         },
//         {
//             "questionId": 19,
//             "intitule": "Combien ?",
//             "phase": "DEVELOPPEMENT",
//             "type": "NUMERIC",
//             "categorie": "FIRST",
//             "reponses": [
//                 {
//                     "questionSuivId": 20,
//                     "intitule": "Autre"
//                 },
//                 {
//                     "questionSuivId": 20,
//                     "intitule": "Python"
//                 },
//                 {
//                     "questionSuivId": 20,
//                     "intitule": "Java"
//                 },
//                 {
//                     "questionSuivId": 20,
//                     "intitule": "C++"
//                 },
//                 {
//                     "questionSuivId": 20,
//                     "intitule": "C"
//                 }
//             ]
//         },
//         {
//             "questionId": 20,
//             "intitule": "Quel langage utilisez-vous pour développer en Back ?",
//             "phase": "DEVELOPPEMENT",
//             "type": "QCM",
//             "categorie": "FIRST",
//             "reponses": [
//                 {
//                     "questionSuivId": 21,
//                     "intitule": "Autre"
//                 },
//                 {
//                     "questionSuivId": 21,
//                     "intitule": "TypeScript"
//                 },
//                 {
//                     "questionSuivId": 21,
//                     "intitule": "JavaScript"
//                 },
//                 {
//                     "questionSuivId": 21,
//                     "intitule": "PHP"
//                 }
//             ]
//         },
//         {
//             "questionId": 21,
//             "intitule": "Quel langage utilisez-vous pour développer en Front ?",
//             "phase": "DEVELOPPEMENT",
//             "type": "QCM",
//             "categorie": "FIRST",
//             "reponses": [
//                 {
//                     "questionSuivId": 22,
//                     "intitule": "Veuillez entrer un entier"
//                 }
//             ]
//         }
//     ],
//     "TEST": [
//         {
//             "questionId": 22,
//             "intitule": "Combien de collaborateurs travaillent sur cette phase ?",
//             "phase": "TEST",
//             "type": "NUMERIC",
//             "categorie": "FIRST",
//             "reponses": [
//                 {
//                     "questionSuivId": 23,
//                     "intitule": "OUI"
//                 },
//                 {
//                     "questionSuivId": 24,
//                     "intitule": "NON"
//                 }
//             ]
//         },
//         {
//             "questionId": 23,
//             "intitule": "Connaissez-vous à peu prés la distance de trajet de vos collaborateurs ?",
//             "phase": "TEST",
//             "type": "QCM",
//             "categorie": "FIRST",
//             "reponses": [
//                 {
//                     "questionSuivId": 24,
//                     "intitule": "Veuillez entrer un entier"
//                 }
//             ]
//         },
//         {
//             "questionId": 24,
//             "intitule": "Quelle est la distance moyenne de trajet de vos collaborateurs pour aller au travail ?",
//             "phase": "TEST",
//             "type": "NUMERIC",
//             "categorie": "FIRST",
//             "reponses": [
//                 {
//                     "questionSuivId": 25,
//                     "intitule": "Veuillez entrer un entier"
//                 }
//             ]
//         },
//         {
//             "questionId": 25,
//             "intitule": "Combien viennent en voiture ?",
//             "phase": "TEST",
//             "type": "NUMERIC",
//             "categorie": "FIRST",
//             "reponses": [
//                 {
//                     "questionSuivId": 26,
//                     "intitule": "Veuillez entrer un entier"
//                 }
//             ]
//         },
//         {
//             "questionId": 26,
//             "intitule": "Combien de PC utilisez-vous pour cette phase ?",
//             "phase": "TEST",
//             "type": "NUMERIC",
//             "categorie": "FIRST",
//             "reponses": [
//                 {
//                     "questionSuivId": 27,
//                     "intitule": "Veuillez entrer un entier"
//                 }
//             ]
//         }
//     ],
//     "HORS_PHASE": [
//         {
//             "questionId": 1,
//             "intitule": "Votre projet, nécessite-t-il des trajets en avion ?",
//             "phase": "HORS_PHASE",
//             "type": "QCM",
//             "categorie": "FIRST",
//             "reponses": [
//                 {
//                     "questionSuivId": 2,
//                     "intitule": "OUI"
//                 },
//                 {
//                     "questionSuivId": 3,
//                     "intitule": "NON"
//                 }
//             ]
//         },
//         {
//             "questionId": 2,
//             "intitule": "Combien ?",
//             "phase": "HORS_PHASE",
//             "type": "NUMERIC",
//             "categorie": "FIRST",
//             "reponses": [
//                 {
//                     "questionSuivId": 3,
//                     "intitule": "Veuillez entrer un entier"
//                 }
//             ]
//         },
//         {
//             "questionId": 3,
//             "intitule": "Produisez-vous de l’énergie verte ?",
//             "phase": "HORS_PHASE",
//             "type": "QCM",
//             "categorie": "FIRST",
//             "reponses": [
//                 {
//                     "questionSuivId": 4,
//                     "intitule": "OUI"
//                 },
//                 {
//                     "questionSuivId": 5,
//                     "intitule": "NON"
//                 }
//             ]
//         },
//         {
//             "questionId": 4,
//             "intitule": "A quel pourcentage couvre t-elle vos dépenses énergétiques",
//             "phase": "HORS_PHASE",
//             "type": "NUMERIC",
//             "categorie": "FIRST",
//             "reponses": [
//                 {
//                     "questionSuivId": 5,
//                     "intitule": "Veuillez entrer un entier"
//                 }
//             ]
//         },
//         {
//             "questionId": 5,
//             "intitule": "Combien de jours de télétravail par semaine ?",
//             "phase": "HORS_PHASE",
//             "type": "NUMERIC",
//             "categorie": "FIRST",
//             "reponses": [
//                 {
//                     "questionSuivId": 6,
//                     "intitule": "Veuillez entrer un entier"
//                 }
//             ]
//         }
//     ],
//     "PLANIFICATION": [
//         {
//             "questionId": 6,
//             "intitule": "Combien de collaborateurs travaillent sur cette phase ?",
//             "phase": "PLANIFICATION",
//             "type": "NUMERIC",
//             "categorie": "FIRST",
//             "reponses": [
//                 {
//                     "questionSuivId": 7,
//                     "intitule": "Veuillez entrer un entier"
//                 }
//             ]
//         },
//         {
//             "questionId": 7,
//             "intitule": "Connaissez-vous à peu près la distance de trajet de vos collaborateurs ?",
//             "phase": "PLANIFICATION",
//             "type": "QCM",
//             "categorie": "FIRST",
//             "reponses": [
//                 {
//                     "questionSuivId": 8,
//                     "intitule": "OUI"
//                 },
//                 {
//                     "questionSuivId": 9,
//                     "intitule": "NON"
//                 }
//             ]
//         },
//         {
//             "questionId": 8,
//             "intitule": "Quelle est la distance moyenne de trajet de vos collaborateurs pour aller au travail ?",
//             "phase": "PLANIFICATION",
//             "type": "NUMERIC",
//             "categorie": "FIRST",
//             "reponses": [
//                 {
//                     "questionSuivId": 9,
//                     "intitule": "Veuillez entrer un entier"
//                 }
//             ]
//         },
//         {
//             "questionId": 9,
//             "intitule": "Combien viennent en voiture ?",
//             "phase": "PLANIFICATION",
//             "type": "NUMERIC",
//             "categorie": "FIRST",
//             "reponses": [
//                 {
//                     "questionSuivId": 10,
//                     "intitule": "Veuillez entrer un entier"
//                 }
//             ]
//         },
//         {
//             "questionId": 10,
//             "intitule": "Combien de pages de document avez-vous utilisé ?",
//             "phase": "PLANIFICATION",
//             "type": "NUMERIC",
//             "categorie": "FIRST",
//             "reponses": [
//                 {
//                     "questionSuivId": 11,
//                     "intitule": "Veuillez entrer un entier"
//                 }
//             ]
//         },
//         {
//             "questionId": 11,
//             "intitule": "Dématérialisez-vous vos documents ?",
//             "phase": "PLANIFICATION",
//             "type": "QCM",
//             "categorie": "FIRST",
//             "reponses": [
//                 {
//                     "questionSuivId": 12,
//                     "intitule": "OUI"
//                 },
//                 {
//                     "questionSuivId": 13,
//                     "intitule": "NON"
//                 }
//             ]
//         },
//         {
//             "questionId": 12,
//             "intitule": "Quel pourcentage de vos documents est dématérialisé?",
//             "phase": "PLANIFICATION",
//             "type": "NUMERIC",
//             "categorie": "FIRST",
//             "reponses": [
//                 {
//                     "questionSuivId": 13,
//                     "intitule": "Veuillez entrer un entier"
//                 }
//             ]
//         }
//     ],
//     "MAINTENANCE": [
//         {
//             "questionId": 37,
//             "intitule": "Faites-vous une phase de maintenance ?",
//             "phase": "MAINTENANCE",
//             "type": "QCM",
//             "categorie": "FIRST",
//             "reponses": []
//         }
//     ],
//     "DEPLOIEMENT": [
//         {
//             "questionId": 27,
//             "intitule": "Combien de collaborateurs travaillent sur cette phase ?",
//             "phase": "DEPLOIEMENT",
//             "type": "NUMERIC",
//             "categorie": "FIRST",
//             "reponses": [
//                 {
//                     "questionSuivId": 28,
//                     "intitule": "OUI"
//                 },
//                 {
//                     "questionSuivId": 29,
//                     "intitule": "NON"
//                 }
//             ]
//         },
//         {
//             "questionId": 28,
//             "intitule": "Connaissez-vous à peu près la distance de trajet de vos collaborateurs ?",
//             "phase": "DEPLOIEMENT",
//             "type": "QCM",
//             "categorie": "FIRST",
//             "reponses": [
//                 {
//                     "questionSuivId": 29,
//                     "intitule": "Veuillez entrer un entier"
//                 }
//             ]
//         },
//         {
//             "questionId": 29,
//             "intitule": "Quelle est la distance moyenne de trajet de vos collaborateurs pour aller au travail ?",
//             "phase": "DEPLOIEMENT",
//             "type": "NUMERIC",
//             "categorie": "FIRST",
//             "reponses": [
//                 {
//                     "questionSuivId": 30,
//                     "intitule": "Veuillez entrer un entier"
//                 }
//             ]
//         },
//         {
//             "questionId": 30,
//             "intitule": "Combien viennent en voiture ?",
//             "phase": "DEPLOIEMENT",
//             "type": "NUMERIC",
//             "categorie": "FIRST",
//             "reponses": [
//                 {
//                     "questionSuivId": 31,
//                     "intitule": "OUI"
//                 },
//                 {
//                     "questionSuivId": 35,
//                     "intitule": "NON"
//                 }
//             ]
//         },
//         {
//             "questionId": 31,
//             "intitule": "Combien de PC utilisez-vous pour cette phase ?",
//             "phase": "DEPLOIEMENT",
//             "type": "NUMERIC",
//             "categorie": "FIRST",
//             "reponses": [
//                 {
//                     "questionSuivId": 32,
//                     "intitule": "Veuillez entrer un entier"
//                 }
//             ]
//         },
//         {
//             "questionId": 32,
//             "intitule": "Avez-vous une base de données ?",
//             "phase": "DEPLOIEMENT",
//             "type": "QCM",
//             "categorie": "FIRST",
//             "reponses": [
//                 {
//                     "questionSuivId": 33,
//                     "intitule": "OUI"
//                 },
//                 {
//                     "questionSuivId": 35,
//                     "intitule": "NON"
//                 }
//             ]
//         },
//         {
//             "questionId": 33,
//             "intitule": "Quelle taille fait-elle ?",
//             "phase": "DEPLOIEMENT",
//             "type": "NUMERIC",
//             "categorie": "FIRST",
//             "reponses": [
//                 {
//                     "questionSuivId": 34,
//                     "intitule": "OUI"
//                 },
//                 {
//                     "questionSuivId": 35,
//                     "intitule": "NON"
//                 }
//             ]
//         },
//         {
//             "questionId": 34,
//             "intitule": "Est-elle stockée dans le cloud ?",
//             "phase": "DEPLOIEMENT",
//             "type": "QCM",
//             "categorie": "FIRST",
//             "reponses": [
//                 {
//                     "questionSuivId": 35,
//                     "intitule": "ENERGIE RENOUVLABLE"
//                 },
//                 {
//                     "questionSuivId": 35,
//                     "intitule": "GEOTHERMIQUE"
//                 },
//                 {
//                     "questionSuivId": 35,
//                     "intitule": "CHARBON"
//                 },
//                 {
//                     "questionSuivId": 35,
//                     "intitule": "HYDRAULIQUE"
//                 },
//                 {
//                     "questionSuivId": 35,
//                     "intitule": "NUCLEAIRE"
//                 }
//             ]
//         },
//         {
//             "questionId": 35,
//             "intitule": "Savez-vous quelle énergie alimente votre cloud ?",
//             "phase": "DEPLOIEMENT",
//             "type": "QCM",
//             "categorie": "FIRST",
//             "reponses": [
//                 {
//                     "questionSuivId": 36,
//                     "intitule": "OUI"
//                 },
//                 {
//                     "questionSuivId": -1,
//                     "intitule": "NON"
//                 }
//             ]
//         },
//         {
//             "questionId": 36,
//             "intitule": "Citez quelle énergie alimente votre Cloud ?",
//             "phase": "DEPLOIEMENT",
//             "type": "QCM",
//             "categorie": "FIRST",
//             "reponses": [
//                 {
//                     "questionSuivId": 37,
//                     "intitule": "Veuillez entrer un entier"
//                 }
//             ]
//         }
//     ]
// }

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
    const {register, handleSubmit, reset} = useForm();
    const navigate = useNavigate();

    const onSubmit = (dataList) => {
        alert(JSON.stringify(dataList))
        const projectId = sessionStorage.getItem("project")
        sessionStorage.removeItem("project")
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

    /**
     * Go to the next step
     */
    const handleNext = useCallback(
        () => {
            setActiveStep((prevActiveStep) => prevActiveStep + 1);
            reset()
        },
        [activeStep]
    );

    /**
     * Go back to the previous step
     */
    const handleBack = useCallback(
        () => {
            setActiveStep((prevActiveStep) => prevActiveStep - 1);
            reset()
        },
        []
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

    useEffect(() => {
        const token = sessionStorage.getItem("token")
        const options = {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
            },
        };
        fetch("/api/questions", options)
            .then(res => res.json())
            .then(
                (result) => {
                    setIsLoaded(true);
                    console.log(result)
                    setData(result);
                },
                (error) => {
                    setIsLoaded(true);
                    setErrorApiGetQuestionnaire(error);
                }
            )
    }, [])

    if (errorApiGetQuestionnaire) {
        return <div>Error: {errorApiGetQuestionnaire.message}</div>;
    } else if (!isLoaded) {
        return (<>
                <div>Loading...</div>
                <Spinner animation="grow" variant="success"/>
            </>
        );
    } else {
        let currentPhase = data.PLANIFICATION;
        switch (activeStep) {
            case 0:
                currentPhase = data.PLANIFICATION
                break;
            case 1:
                currentPhase = data.DEVELOPPEMENT
                break;
            case 2:
                currentPhase = data.TEST
                break;
            case 3:
                currentPhase = data.DEPLOIEMENT
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
                    <Row>
                        <Col></Col>
                        <form onSubmit={handleSubmit(onSubmit)}
                              style={{paddingLeft: '120px', paddingRight: '120px', marginTop: '20px'}}
                              className="navbar-nav-scroll mt-4"
                        >
                            {currentPhase.map(question => <Phase key={question.questionId} register={register}
                                                                 value={question}/>)}
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
                                    <>
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
                                    </>
                                )}
                            </Box>
                        </form>
                        <Col></Col>
                    </Row>
                </Row>
            </Container>

        );
    }
}

export default StepperComponent