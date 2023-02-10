import React, {useEffect, useState} from "react";
import StepperComponent from "../Stepper/Stepper";
import {useForm} from "react-hook-form";
import {NUMERIC, QCM} from "./QuestionType";
import {Spinner} from "react-bootstrap";

const test = {
    "questionId": 1,
    "intitule": "Sur la duree totale du projet, combiens de jours avez-vous passer sur place (au bureau) ?",
    "type": "NUMERIC",
    "phase": "HORS_PHASE",
    "categorie": "FIRST",
    "reponses": [
        {
            "questionSuiv": {
                "questionId": 2,
                "intitule": "Est-ce que vous vous déplacer à pied pour vous rendre sur place ?",
                "type": "QCM",
                "phase": "HORS_PHASE",
                "categorie": "FIRST",
                "reponses": [
                    {
                        "questionSuiv": {
                            "questionId": 3,
                            "intitule": "Combiens de KM est-ce que vous parcourrez en voiture pour vous rendre sur place ?",
                            "type": "QCM",
                            "phase": "HORS_PHASE",
                            "categorie": "FIRST",
                            "reponses": [
                                {
                                    "questionSuiv": {
                                        "questionId": 4,
                                        "intitule": "Combiens de salariées ont été mobilisé pour la phase de développements ?",
                                        "type": "NUMERIC",
                                        "phase": "DEVELOPPEMENT",
                                        "categorie": "FIRST",
                                        "reponses": [
                                            {
                                                "questionSuiv": {
                                                    "questionId": 5,
                                                    "intitule": "Combiens de jours la phase de développements a-t-elle durée?",
                                                    "type": "NUMERIC",
                                                    "phase": "DEVELOPPEMENT",
                                                    "categorie": "FIRST",
                                                    "reponses": [
                                                        {
                                                            "questionSuiv": null,
                                                            "intitule": "Veuillez entrer un entier",
                                                            "constante": 4,
                                                            "id": 7
                                                        }
                                                    ],
                                                    "visible": true
                                                },
                                                "intitule": "Veuillez entrer un entier",
                                                "constante": 1,
                                                "id": 6
                                            }
                                        ],
                                        "visible": true
                                    },
                                    "intitule": "Veuillez entrer un entier",
                                    "constante": 0,
                                    "id": 5
                                }
                            ],
                            "visible": false
                        },
                        "intitule": "OUI",
                        "constante": 0,
                        "id": 3
                    },
                    {
                        "questionSuiv": {
                            "questionId": 5,
                            "intitule": "Combiens de jours la phase de développements a-t-elle durée?",
                            "type": "NUMERIC",
                            "phase": "DEVELOPPEMENT",
                            "categorie": "FIRST",
                            "reponses": [
                                {
                                    "questionSuiv": null,
                                    "intitule": "Veuillez entrer un entier",
                                    "constante": 4,
                                    "id": 7
                                }
                            ],
                            "visible": true
                        },
                        "intitule": "NON",
                        "constante": 0,
                        "id": 4
                    }
                ],
                "visible": true
            },
            "intitule": "Veuillez entrer un entier",
            "constante": 1,
            "id": 2
        }
    ],
    "visible": true
}

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
        } else {
        }
        question.reponses.forEach((value) => {
            buildQuiz(value.questionSuiv, arrayReturn)
        })
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

    const onSubmit = (data) => {
        let projectId = '1'; // A MODIFIER
        let sendToBack = {}
        let responses = []
        for(const [key, value] of Object.entries(data)) {
            let tuple = {}
            tuple['questionId'] = key;
            tuple['entry'] = value;
            responses.push(tuple)
        }
        sendToBack['projetId'] = projectId;
        sendToBack['reponses'] = responses;
        console.log(sendToBack)
        alert(JSON.stringify(data));

        const myHeaders = new Headers();
        myHeaders.append("Content-Type", "application/json");

        const requestOptions = {
            method: 'POST',
            headers: myHeaders,
            body: sendToBack,
            redirect: 'follow'
        };

        fetch("http://localhost/api/reponsesDonnee", requestOptions)
            .then(response => response.text())
            .then(result => console.log(result))
            .catch(error => console.log('error', error));
    };

    // useEffect(() => {
    //     fetch("http://localhost/api/questions")
    //         .then(res => res.json())
    //         .then(
    //             (result) => {
    //                 setIsLoaded(true);
    //                 setData(result);
    //             },
    //             (error) => {
    //                 setIsLoaded(true);
    //                 setErrorApiGetQuestionnaire(error);
    //             }
    //         )
    // }, [])

    // if (errorApiGetQuestionnaire) {
    //     return <div>Error: {errorApiGetQuestionnaire.message}</div>;
    // } else if (!isLoaded) {
    //     return (<>
    //             <div>Loading...</div>
    //             <Spinner animation="grow" variant="success"/>
    //         </>
    //     );
    // } else {
        return (
            <>
                <StepperComponent/>
                <form onSubmit={handleSubmit(onSubmit)}
                      style={{paddingLeft: '120px', paddingRight: '120px', marginTop: '20px'}}>
                    {buildQuiz(test).map((value) => {
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
                            default : return;
                        }
                    })}
                    <input type="submit" style={{marginTop: '20px'}}/>
                </form>
            </>
        );
    // }
}

export default Questionnaire