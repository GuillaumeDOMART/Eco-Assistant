import {useCallback, useEffect, useState} from 'react';
import Box from '@mui/material/Box';
import Stepper from '@mui/material/Stepper';
import Step from '@mui/material/Step';
import StepLabel from '@mui/material/StepLabel';
import Button from '@mui/material/Button';
import {useNavigate} from "react-router-dom";
import {Col, Container, Row, Spinner} from "react-bootstrap";
import {useForm} from "react-hook-form";
import Phase from "./Phase";

const steps = ["Planification", "Developpement", "Test", "Déploiement", "Maintenance"];

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

    const handleQuit = useCallback(
        () => {
            navigate("/profil");
        },
        [navigate]
    )

    const handleChange = useCallback((target, value) => {
        const select = value.reponses.find(value => value.intitule === target.target.value)
        let answer = {
            "question": target.target.name,
            "reponseId": select.reponseId
        }

        selectedAnswers.forEach(value => {
            if (value.question === answer.question)
                selectedAnswers.splice(selectedAnswers.indexOf(value), 1)
        })
        setSelectedAnswers([...selectedAnswers, answer])
    }, [selectedAnswers])


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

    useEffect(() => {
        const token = sessionStorage.getItem("token")
        const id = sessionStorage.getItem("project")
        const options = {
            method: 'POST',
            body: JSON.stringify({id}),
            headers: {
                'Authorization': `Bearer ${token}`,
                'Content-Type': "application/json"
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
            case 4:
                currentPhase = data.MAINTENANCE
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
                                const check = selectedAnswers.find(answer => {
                                    return question.dependance === answer.reponseId;
                                })
                                if (question.dependance === -1 || check) {
                                    return (
                                        <Phase key={question.questionId}
                                               register={register}
                                               value={question}
                                               onChange={handleChange}
                                        />)
                                }
                            }
                        )}
                        <Box className="">
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
                        </Box>
                    </form>
                    <Col>
                        <Button className="align-bottom" onClick={handleQuit}>Quitter</Button>
                    </Col>
                </Row>
            </Container>

        );
    }
}

export default StepperComponent