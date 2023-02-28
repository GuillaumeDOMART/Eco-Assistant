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

const steps = ["Hors_Phase", "Planification", "Developpement", "Test", "Deploiement", "Maintenance"];

/**
 * FormConponnents
 * @param activeStep activeStep
 * @param data data
 * @param selectedAnswers selectedAnswers
 * @param handleSubmit handleSubmit
 * @param handleChange handleChange
 * @param handleBack handleBack
 * @param register register
 * @param onSubmit onSubmit
 * @returns {JSX.Element} oui
 * @constructor
 */
function StepForm({activeStep, data, selectedAnswers, handleSubmit, handleChange, handleBack, register, onSubmit}) {
    return (
        <form onSubmit={handleSubmit(onSubmit)}
              className="navbar-nav-scroll mt-4 col-8"
              style={{paddingLeft: '120px', paddingRight: '120px', marginTop: '20px'}}
        >
            {data.map(question => {
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
                    return (
                        <>
                        </>
                    );
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
    );
}

/**
 * components
 * @param activeStep activeStep
 * @returns {JSX.Element} truc
 * @constructor
 */
function StepBox(activeStep) {
    return (
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
    );
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
    const [phase, setPhase] = useState("HORS_PHASE");
    const navigate = useNavigate();


    const handlePhase = useCallback(() => {
        setPhase(steps[activeStep])
        setSelectedAnswers([])
        const token = sessionStorage.getItem("token")
        const id = sessionStorage.getItem("project")
        const myHeaders = new Headers();
        myHeaders.append("Authorization", `Bearer ${token}`);
        myHeaders.append("Content-Type", "application/json");

        const raw = JSON.stringify({
            "phase": phase.toUpperCase(),
            id
        });

        const requestOptions = {
            method: 'POST',
            headers: myHeaders,
            body: raw,
            redirect: 'follow'
        };

        fetch("/api/questions/phase", requestOptions)
            .then(res => res.json())
            .then(
                (result) => {
                    setIsLoaded(true);
                    setData(result);
                },
                (error) => {
                    setIsLoaded(true);
                    setErrorApiGetQuestionnaire(error);
                }
            )
    }, [setIsLoaded, setData, setErrorApiGetQuestionnaire, setPhase, activeStep, phase])

    /**
     * Go to the next step
     */
    const handleNext = useCallback(
        () => {
            setActiveStep((prevActiveStep) => prevActiveStep + 1);
            handlePhase()
            reset()
        },
        [reset, handlePhase]
    );

    /**
     * Go back to the previous step
     */
    const handleBack = useCallback(
        () => {
            setActiveStep((prevActiveStep) => prevActiveStep - 1);
            handlePhase()
            reset()
        },
        [reset, handlePhase]
    );

    const handleQuit = useCallback(
        () => {
            navigate("/profil");
        },
        [navigate]
    )

    const handleChange = useCallback((target, value) => {
        const select = value.reponses.find(val => val.intitule === target.target.value)
        const answer = {
            "question": target.target.name,
            "reponseId": select.reponseId
        }

        selectedAnswers.forEach(val => {
            if (val.question === answer.question)
                selectedAnswers.splice(selectedAnswers.indexOf(val), 1)
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
        handlePhase()
    }, [handlePhase])

    if (errorApiGetQuestionnaire) {
        return <div>Error: {errorApiGetQuestionnaire.message}</div>;
    } else if (!isLoaded) {
        return (<>
                <div>Loading...</div>
                <Spinner animation="grow" variant="success"/>
            </>
        );
    }
    return (
        <Container fluid>
            <Row>
                <StepBox activeStep={activeStep}/>
                <Col></Col>
                <StepForm activeStep={activeStep} data={data} selectedAnswers={selectedAnswers}
                          handleSubmit={handleSubmit} handleChange={handleChange} handleBack={handleBack}
                          register={register} onSubmit={onSubmit}/>
                <Col>
                    <Button className="align-bottom" onClick={handleQuit}>Quitter</Button>
                </Col>
            </Row>
        </Container>
    );
}

export default StepperComponent