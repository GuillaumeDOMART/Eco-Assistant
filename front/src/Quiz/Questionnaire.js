import React, {useEffect, useState} from "react";
import StepperComponent from "../Stepper/Stepper";
import {useForm} from "react-hook-form";
import {NUMERIC, QCM} from "./QuestionType";
import {Spinner} from "react-bootstrap";
import {useNavigate} from "react-router-dom";

    /**
     * Check if the question is in the array
     * @param question
     * @param questionArray
     * @returns {boolean}
     */
    function questionInList(question, questionArray) {
        let accepted = true;
        questionArray.forEach(value => {
            if (question.questionId === value.questionId){
                accepted =  false;
            }
        })
        return accepted;
    }

    /**
     * Return the different possible answers to the question
     * @param question
     * @returns {*[]}
     */
    function getResponses(question) {
        const responses = []
        question.reponses.forEach((response, index) => {
            responses[index] = response.intitule
        })
        return responses;
    }

    /**
     * Return an array containing informations about the given response
     * @param question
     * @returns {(*|string|"beforeRead"|"read"|"afterRead"|"beforeMain"|"main"|"afterMain"|"beforeWrite"|"write"|"afterWrite")[]}
     */
    function parseQuestion(question) {
        return {
            'questionId': question.questionId,
            'intitule' : question.intitule,
            'type' : question.type,
            'phase' : question.phase,
            'categorie' : question.categorie,
            'reponses' : getResponses(question)
        }
    }

    /**
     * Create an array containing the quiz from a json format
     * @param question
     * @param arrayReturn
     * @returns {*[]|null}
     */
    function buildQuiz(question, arrayReturn = []) {
        if (question === null) {
            return null;
        }
        if (questionInList(question, arrayReturn)) {
            arrayReturn.push(parseQuestion(question))
        }
        question.reponses.forEach((value) => {
            buildQuiz(value.questionSuiv, arrayReturn)
        })
        console.log(arrayReturn)
        return arrayReturn;
    }

/**
 * The component representing the quiz
 * @returns {JSX.Element}
 * @constructor
 */
function Questionnaire() {
    const [errorApiGetQuestionnaire, setErrorApiGetQuestionnaire] = useState(null);
    const [isLoaded, setIsLoaded] = useState(false);
    const [data, setData] = useState({})
    const {register, handleSubmit} = useForm();
    const navigate = useNavigate();

    /**
     * Create and send the json used in the backend
     * @param dataList
     */
    const onSubmit = (dataList) => {
        const projectId = '1'; // A MODIFIER
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

        const myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");

        const requestOptions = {
            method: 'POST',
            headers: myHeaders,
            body: JSON.stringify(sendToBack),
            redirect: 'follow'
        };

        fetch("/api/reponsesDonnees", requestOptions)
            .then(response => response.text())
            // .then(result => console.log(result))
            // .catch(error => console.log('error', error));

        navigate("/result")
    }

    useEffect(() => {
        fetch("/api/questions")
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
        return (
            <>
                <StepperComponent/>
                <form onSubmit={handleSubmit(onSubmit)}
                      style={{paddingLeft: '120px', paddingRight: '120px', marginTop: '20px'}}>
                    {data.map((value) => {
                        switch (value.type) {
                             case 'QCM' :
                                 return (
                                        <QCM key={value.intitule}
                                             question={value}
                                             {...register(value.questionId.toString())}
                                        />
                            )
                            case 'NUMERIC' :
                                return (
                                    <NUMERIC key={value.intitule}
                                             question={value}
                                             register={register}
                                    />
                                )
                            default : return null;
                        }
                    })}
                    <input type="submit" style={{marginTop: '20px'}}/>
                </form>
            </>
        );
    }
}

export default Questionnaire