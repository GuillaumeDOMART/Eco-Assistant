import {useCallback, useEffect, useState} from 'react';
import Button from '@mui/material/Button';
import {useNavigate} from "react-router-dom";
import {Col, Container, Row, Spinner} from "react-bootstrap";
import {useForm} from "react-hook-form";
import StepForm from "./StepForm";
import StepBox from "./StepBox";

const steps = ["Hors_Phase", "Planification", "Developpement", "Test", "Deploiement", "Maintenance"];

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


    const handlePhase = useCallback(async () => {
        const token = sessionStorage.getItem("token")
        const id = sessionStorage.getItem("project")
        setSelectedAnswers([])
        const myHeaders = new Headers();
        myHeaders.append("Authorization", `Bearer ${token}`);
        myHeaders.append("Content-Type", "application/json");

        const raw = JSON.stringify({
            "phase": steps[activeStep].toUpperCase(),
            id
        });

        const requestOptions = {
            method: 'POST',
            headers: myHeaders,
            body: raw,
            redirect: 'follow'
        };

        const response = await fetch("/api/questions/phase", requestOptions)
        if (response.ok) {
            const json = await response.json();
            setIsLoaded(true);
            setData(json);
        } else {
            setIsLoaded(true);
            setErrorApiGetQuestionnaire("Erreur lors de la récupération du questionnaire");
        }

    }, [setIsLoaded, setData, setErrorApiGetQuestionnaire, activeStep])

    /**
     */
    const handleNext = useCallback(
        () => {
            setActiveStep((prevActiveStep) => prevActiveStep + 1);
            handlePhase().then(r => r)
        },
        [handlePhase]
    );

    /**
     * Go back to the previous step
     */
    const handleBack = useCallback(
        () => {
            setActiveStep((prevActiveStep) => prevActiveStep - 1);
            handlePhase().then(r => r)
        },
        [handlePhase]
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
    const onSubmit = useCallback(
        (dataList) => {
            alert(JSON.stringify(dataList))
            const projectId = sessionStorage.getItem("project")
            const sendToBack = {}
            const responses = []
            for (const [key, value] of Object.entries(dataList)) {
                const tuple = {}
                tuple.questionId = key;
                if (value === null)
                    tuple.entry = "";
                else
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
        }, [handleNext, activeStep, navigate]
    )

    useEffect(() => {
        handlePhase().then(() => reset())
    }, [handlePhase, reset])

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
                <StepBox
                    activeStep={activeStep}
                    steps={steps}
                />
                <Col></Col>
                <StepForm
                    steps={steps}
                    activeStep={activeStep}
                    data={data}
                    selectedAnswers={selectedAnswers}
                    handleSubmit={handleSubmit}
                    handleChange={handleChange}
                    handleBack={handleBack}
                    register={register}
                    onSubmit={onSubmit}
                />
                <Col>
                    <Button className="align-bottom" onClick={handleQuit}>Quitter</Button>
                </Col>
            </Row>
        </Container>
    );
}

export default StepperComponent