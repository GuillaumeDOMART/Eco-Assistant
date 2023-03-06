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
            const selected = []
            json.forEach(question => {
                console.log("je suis là")
                if(question.type === 'QCM' && question.response !== null) {
                    selected.push({
                        "question": question.questionId.toString(),
                        "reponseId": question.response.response.responseId
                    })

                }
            })
            await setSelectedAnswers(selected)
        } else {
            setIsLoaded(true);
            setErrorApiGetQuestionnaire("Erreur lors de la récupération du questionnaire");
        }

    }, [setIsLoaded, setData, setErrorApiGetQuestionnaire, activeStep, setSelectedAnswers])

    /**
     */
    const handleNext = useCallback(
        () => {
            setActiveStep((prevActiveStep) => prevActiveStep + 1);
            setSelectedAnswers([])
        },
        [setSelectedAnswers]
    );

    /**
     * Go back to the previous step
     */
    const handleBack = useCallback(
        () => {
            setActiveStep((prevActiveStep) => prevActiveStep - 1);
            setSelectedAnswers([])
        },
        [setSelectedAnswers]
    );

    const handleQuit = useCallback(
        () => {
            if(sessionStorage.getItem("guest")){
                navigate("/logout")
            }
            else {
                navigate("/profil")
            }
        },
        [navigate]
    )

    const handleChange = useCallback((target, value) => {
        const select = value.responses.find(val => val.entitled === target.target.value)
        const answer = {
            "question": target.target.name,
            "reponseId": select.responseId
        }

        selectedAnswers.forEach(val => {
            if (val.question === answer.question) {
                selectedAnswers.splice(selectedAnswers.indexOf(val), 1)
            }
        })
        setSelectedAnswers([...selectedAnswers, answer])
    }, [selectedAnswers])

    const getDependancy = useCallback((questionId) => {
        const dependance = data.find((value) => value.questionId.toString() === questionId).dependency
        if (dependance === -1)
            return true;
        return selectedAnswers.find(value => value.responseId === dependance)
    }, [data, selectedAnswers])

    /**
     * Send responses to the backEnd when Next button is pressed
     * @param dataList
     */
    const onSubmit = useCallback(
        async (dataList) => {
            const projectId = sessionStorage.getItem("project")
            const sendToBack = {}
            const responses = []
            for (const [key, value] of Object.entries(dataList)) {
                const tuple = {}
                tuple.questionId = key;
                if (value === null || !getDependancy(key))
                    tuple.entry = "";
                else
                    tuple.entry = value;
                responses.push(tuple)
            }
            sendToBack.projectId = projectId;
            sendToBack.responses = responses;

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

            await fetch("/api/reponsesDonnees", requestOptions)

        if (activeStep === steps.length - 1){

            const body = {}
            body.id= projectId
            const options = {
                method: 'PUT',
                headers: {
                    'Content-Type' : 'application/json',
                    'Authorization': `Bearer ${token}`
                },
                body: JSON.stringify(body)
            };
            fetch('/api/projet/finish', options)
                .then(res => {
                    if(res.status === 404 ){
                        navigate(`/profil`)
                    }
                    else{
                        navigate(`/result?id=${projectId}`)
                    }
                })



        }
        else{
            handleNext();
        }
        }, [handleNext, activeStep, navigate, getDependancy]
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