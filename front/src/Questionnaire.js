import React from "react";
import StepperComponent from "./Stepper";

const test = {
    "intitule": "Sur la duree totale du projet, combiens de jours avez-vous passer sur place (au bureau) ?",
    "type": "NUMERIC",
    "phase": "HORS_PHASE",
    "categorie": "FIRST",
    "reponses": [
        {
            "questionSuiv": {
                "intitule": "Est-ce que vous vous déplacer à pied pour vous rendre sur place ?",
                "type": "QCM",
                "phase": "HORS_PHASE",
                "categorie": "FIRST",
                "reponses": [
                    {
                        "questionSuiv": {
                            "intitule": "Combiens de KM est-ce que vous parcourrez en voiture pour vous rendre sur place ?",
                            "type": "QCM",
                            "phase": "HORS_PHASE",
                            "categorie": "FIRST",
                            "reponses": [
                                {
                                    "questionSuiv": {
                                        "intitule": "Combiens de salariées ont été mobilisé pour la phase de développements ?",
                                        "type": "NUMERIC",
                                        "phase": "DEVELOPPEMENT",
                                        "categorie": "FIRST",
                                        "reponses": [
                                            {
                                                "questionSuiv": {
                                                    "intitule": "Combiens de jours la phase de développements a-t-elle durée?",
                                                    "type": "NUMERIC",
                                                    "phase": "DEVELOPPEMENT",
                                                    "categorie": "FIRST",
                                                    "reponses": [
                                                        {
                                                            "questionSuiv": null,
                                                            "intitule": "Veuillez entrer un entier",
                                                            "constante": 4
                                                        }
                                                    ],
                                                    "visible": true
                                                },
                                                "intitule": "Veuillez entrer un entier",
                                                "constante": 1
                                            }
                                        ],
                                        "visible": true
                                    },
                                    "intitule": "Veuillez entrer un entier",
                                    "constante": 0
                                }
                            ],
                            "visible": false
                        },
                        "intitule": "OUI",
                        "constante": 0
                    },
                    {
                        "questionSuiv": {
                            "intitule": "Combiens de jours la phase de développements a-t-elle durée?",
                            "type": "NUMERIC",
                            "phase": "DEVELOPPEMENT",
                            "categorie": "FIRST",
                            "reponses": [
                                {
                                    "questionSuiv": null,
                                    "intitule": "Veuillez entrer un entier",
                                    "constante": 4
                                }
                            ],
                            "visible": true
                        },
                        "intitule": "NON",
                        "constante": 0
                    }
                ],
                "visible": true
            },
            "intitule": "Veuillez entrer un entier",
            "constante": 1
        }
    ],
    "visible": true
}

/**
 * Return the different possible answers to the question
 * @param question
 * @returns {*[]}
 */
function getResponses(question) {
    const responsesArray = []
    question.reponses.forEach((responses, index) => {
        responsesArray[index] = responses.intitule
    })
    return responsesArray;
}

/**
 * Return an array containing informations about the given response
 * @param question
 * @param reponseRequise
 * @returns {(*|string|"beforeRead"|"read"|"afterRead"|"beforeMain"|"main"|"afterMain"|"beforeWrite"|"write"|"afterWrite")[]}
 */
function parseQuestion(question, reponseRequise) {
    return [reponseRequise, question.intitule, question.type, question.phase, question.categorie, getResponses(question)]
}

/**
 * Create an array containing the quiz from a json format
 * @param question
 * @param arrayReturn
 * @param index
 * @param reponseRequise
 * @returns {*[]|null}
 */
function buildQuiz(question, arrayReturn = [], index = 0, reponseRequise = null) {
    if (question === null)
        return null;
    arrayReturn[index] = parseQuestion(question, reponseRequise)
    question.reponses.forEach((value) => {
        buildQuiz(value.questionSuiv, arrayReturn, index + 1, value.intitule)
    })
    return arrayReturn;
}

/**
 * A component representing a QCM question
 * @param value
 * @returns {JSX.Element}
 * @constructor
 */
const QCM = (value) => {
    return (
        <>
            <label>{value.question[1]}</label><br/>
            <select>
                {value.question[5].map((data) => {
                    return <option value={data} key={data}> {data}</option>
                })}
            </select>
            <input type="submit" style={{marginLeft: '10px'}}/>
        </>
    )
}

/**
 * A component representing a NUMERIC question
 * @param value
 * @returns {JSX.Element}
 * @constructor
 */
const NUMERIC = (value) => {
    return (
        <>
            <label>{value.question[1]}</label><br/>
            {value.question[5][0]} : <input/>
            <input type="submit" style={{marginLeft: '10px'}}/>
        </>
    )
}

/**
 * The component representing the quiz
 * @returns {JSX.Element}
 * @constructor
 */
function Questionnaire() {

    return (
        <>
            <StepperComponent/>
            <form style={{paddingLeft: '120px', paddingRight: '120px', marginTop: '20px'}}>
                {buildQuiz(test).map((value) => {
                    if (value[2] === 'QCM') {
                        return (
                            <div key={value} style={{marginTop: '20px'}}>
                                <QCM question={value}/>
                            </div>
                        )}
                    return (
                        <div key={value} style={{marginTop: '20px'}}>
                            <NUMERIC question={value}/>
                        </div>
                    )

                })}
            </form>
        </>
    );
}

export default Questionnaire