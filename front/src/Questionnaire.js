import React, {useEffect, useState} from "react";
import StepperComponent from "./Stepper";
import {useForm} from "react-hook-form";
import {Spinner} from "react-bootstrap";
import {Select} from "@mui/material";

const test = {"intitule": "Combien d heures codez-vous par semaine ?",
    "type": "NUMERIC",
    "phase": "DEVELOPPEMENT",
    "categorie": "FIRST",
    "reponses": [
        {
            "id": 1,
            "questionSuiv": {
                "intitule": "Utilisez-vous les méthodes agiles au sein de votre equipe ?",
                "type": "QCM",
                "phase": "PLANIFICATION",
                "categorie": "FIRST",
                "reponses": [
                    {
                        "id": 2,
                        "questionSuiv": {
                            "intitule": "Combiens de jours durre un sprint dans votre équipe",
                            "type": "NUMERIC",
                            "phase": "PLANIFICATION",
                            "categorie": "FIRST",
                            "reponses": [
                                {
                                    "id": 4,
                                    "questionSuiv": null,
                                    "intitule": "Veuillez entrer un entier",
                                    "constante": 20
                                }
                            ],
                            "visible": false
                        },
                        "intitule": "Oui",
                        "constante": 1
                    },
                    {
                        "id": 3,
                        "questionSuiv": null,
                        "intitule": "NON",
                        "constante": 1
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
        responsesArray[index] = [responses.intitule, responses.id]
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
    return {
        'reponseRequise': reponseRequise,
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
const QCM = React.forwardRef(({ onChange, name, question }, ref) => {
    const [id, setId] = useState(question.reponses[0][1])

    let handleId = (event) => {
        onChange(event)
        setValue
    }

    return (
        <div style={{marginTop: '20px'}}>
            <label>{question.intitule}</label><br/>
            <select name={name} ref={ref} onChange={handleId}>
                {question.reponses.map((data) => {
                    return <option value={data[1]} key={data[0]} label={data[0]}/>
                })}
            </select><br/>
        </div>
    )
})

/**
 * A component representing a NUMERIC question
 * @param value
 * @returns {JSX.Element}
 * @constructor
 */
const NUMERIC = ({question, register, id}) => {
    return (
        <div style={{marginTop: '20px'}}>
            <label>{question.intitule}</label><br/>
            {question.reponses[0][0]} : <input type={"number"} {...register(id.toString())}/><br/>
        </div>
    )
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
        alert(JSON.stringify(data));
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
                        if (value.type === 'QCM') {
                            return (
                                <QCM key={value.intitule}
                                     question={value}
                                     {...register(value.intitule)}
                                />
                            )
                        }
                        return (
                            <NUMERIC key={value.intitule}
                                     question={value}
                                     register={register}
                                     id={value.reponses[0][1]}
                            />

                        )
                    })}
                    <input type="submit" style={{marginTop: '20px'}}/>
                </form>
            </>
        );
    // }
}

export default Questionnaire