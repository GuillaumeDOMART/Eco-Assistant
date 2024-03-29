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
    const [detail, setDetail] = useState(false);
    const {register, handleSubmit, reset} = useForm();

    const navigate = useNavigate();

    const getMaxDependance = useCallback((dependance) => {
        if (dependance === -1)
            return false
        const check = selectedAnswers.find(answer => {
            return dependance === answer.reponseId;
        })
        if (check) {
            return true
        }
        const questionGet = data.find(question => {
            return question.reponses.find(value => {
                return value.reponseId === dependance
            })
        })
        if (questionGet === undefined)
            return false
        return getMaxDependance(questionGet.dependance)
    }, [data, selectedAnswers])


    const handlePhase = useCallback(async () => {
        const detailParam = new URLSearchParams(window.location.search).get('detail');
        setDetail(detailParam === "true")

        const token = sessionStorage.getItem("token")
        const id = sessionStorage.getItem("project")
        const myHeaders = new Headers();
        myHeaders.append("Authorization", `Bearer ${token}`);
        myHeaders.append("Content-Type", "application/json");

        const raw = JSON.stringify({
            "phase": steps[activeStep].toUpperCase(),
            id
        });

        if (!detailParam) {
            const requestOptionsCheckFinish = {
                method: 'GET',
                headers: myHeaders,
                redirect: 'follow'
            };

            const finishResponse = await fetch(`/api/projet/${id}/isfinish`, requestOptionsCheckFinish)

            if (!finishResponse.ok) {
                navigate("/")
                return;
            }
        }

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
                if (question.type === 'QCM' && question.reponse !== null) {
                    selected.push({
                        "question": question.questionId.toString(),
                        "reponseId": question.reponse.reponse.reponseId
                    })
                }
            })
            await setSelectedAnswers(selected)
        } else {
            setIsLoaded(true);
            setErrorApiGetQuestionnaire("Erreur lors de la récupération du questionnaire");
        }

    }, [setIsLoaded, setData, setErrorApiGetQuestionnaire, activeStep, setSelectedAnswers, navigate, detail])

    /**
     * Go to the next step
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

    /**
     * Leave the quiz
     * @type {(function(): void)|*}
     */
    const handleQuit = useCallback(
        () => {
            if (sessionStorage.getItem("guest")) {
                navigate("/logout")
            } else {
                navigate("/profil")
            }
        },
        [navigate]
    )

    /**
     * Return an array containing the id of the responses of the given question
     * @type {function(*): *[]}
     */
    const getResponses = useCallback((question) => {
        return question.reponses.map(response => {
            return response.reponseId
        })
    }, [])

    /**
     * Return the question associated with the given responseId
     * @type {function(*): *}
     */
    const getQuestionByResponseId = useCallback((responseId) => {
        return data.find(question => {
            return question.reponses.find(response => {
                return response.reponseId === responseId
            })
        })
    }, [data])

    /**
     * Check if the given question depends on the given questionMax
     * @type {(function(*, *): (boolean|boolean|*|undefined|undefined))|*}
     */
    const highestDepSelected = useCallback((questionMax, question) => {
        if (question.dependance === -1)
            return false;
        if (getResponses(questionMax).includes(question.dependance))
            return true
        const nextQuestion = getQuestionByResponseId(question.dependance)
        if (nextQuestion !== undefined)
            return highestDepSelected(questionMax, nextQuestion)
    }, [getResponses, getQuestionByResponseId])

    /**
     * Return the question associated with the given questionId
     */
    const getQuestion = useCallback((questionId) => {
        return data.find(value => {
            return value.questionId === questionId;
        })
    }, [data])

    /**
     * Update the array containing answers selected
     * @type {(function(*, *): Promise<void>)|*}
     */
    const handleChange = useCallback(async (target, value) => {
        //value = question selectionnée
        const select = value.reponses.find(val => val.intitule === target.target.value) //réponse selectionnée
        const answer = {
            "question": target.target.name, //id de la question
            "reponseId": select.reponseId // id de la réponse
        }
        const selectedAnswerCopy = [...selectedAnswers]
        selectedAnswers.forEach(val => {
            const questionVal = getQuestion(+val.question)
            if (val.question === answer.question || highestDepSelected(value, questionVal)) { // Si l'id de la question de la réponse selectionnée est le même que celui de la value dans Selected
                selectedAnswerCopy.splice(selectedAnswerCopy.indexOf(val), 1)
            }
        })
        await setSelectedAnswers([...selectedAnswerCopy, answer])
    }, [selectedAnswers, getQuestion, highestDepSelected])

    const getDependancy = useCallback((questionId) => {
        const dependance = data.find((value) => value.questionId.toString() === questionId).dependance
        if (dependance === -1)
            return true;
        return selectedAnswers.find(value => value.reponseId === dependance)
    }, [data, selectedAnswers])

    /**
     * Send responses to the backEnd when Next button is pressed
     * @param dataList
     */
    const onSubmit = useCallback(
        async (dataList) => {
            if (detail) {
                handleNext();
                return;
            }
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

            await fetch("/api/reponsesDonnees", requestOptions)

            if (activeStep === steps.length - 1) {

                const body = {}
                body.id = projectId
                const options = {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${token}`
                    },
                    body: JSON.stringify(body)
                };
                fetch('/api/projet/finish', options)
                    .then(res => {
                        if (res.status === 404) {
                            navigate(`/profil`)
                        } else {
                            navigate(`/result?id=${projectId}`)
                        }
                    })


            } else {
                handleNext();
            }
        }, [handleNext, activeStep, navigate, getDependancy, detail]
    )

    useEffect(() => {
        handlePhase().then(() => reset())

        if (!sessionStorage.getItem("project")) {
            navigate("/")
        }
    }, [handlePhase, reset, navigate])

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
                    detail={detail}
                />
                <Col>
                </Col>
            </Row>
            <Row>
                <Col>
                    <Button className="align-items-center mt-2" onClick={handleQuit}>Quitter</Button>
                </Col>
            </Row>
        </Container>
    );
}

export default StepperComponent