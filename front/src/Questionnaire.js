import React from "react";
import StepperComponent from "./Stepper";

const test = {"intitule":"Combien d heures codez-vous par semaine ?",
    "type":"NUMERIC",
    "phase":"DEVELOPPEMENT",
    "categorie":"FIRST",
    "reponses":[
        {
            "questionSuiv":
                {
                    "intitule":"Utilisez-vous les méthodes agiles au sein de votre equipe ?",
                    "type":"QCM",
                    "phase":"PLANIFICATION",
                    "categorie":"FIRST",
                    "reponses":[
                        {
                            "questionSuiv":
                                {
                                    "intitule":"Combiens de jours durre un sprint dans votre équipe",
                                    "type":"NUMERIC",
                                    "phase":"PLANIFICATION",
                                    "categorie":"FIRST",
                                    "reponses":[
                                        {
                                            "questionSuiv":null,
                                            "intitule":"Veuillez entrer un entier",
                                            "constante":20
                                        }]},
                            "intitule":"Oui",
                            "constante":1
                        },
                        {
                            "questionSuiv":null,
                            "intitule":"NON",
                            "constante":1
                        }]},
            "intitule":"Veuillez entrer un entier","constante":1
        }]
}

function buildQuestionnaire(test, arrayReturn = [], index = 0, reponseRequise = null) {
    if (test === null)
        return;
    arrayReturn[index] = parseQuestion(test, reponseRequise)
    test.reponses.map((value) => {
        buildQuestionnaire(value.questionSuiv, arrayReturn, index + 1, value.intitule)
    })
    return arrayReturn;
}

function getResponses(question) {
    const responsesArray = []
    question.reponses.map((responses, index) => {
        responsesArray[index] = responses.intitule

    })
    return responsesArray;
}

function parseQuestion(question, reponseRequise) {
    return [reponseRequise, question.intitule, question.type, question.phase, question.categorie, getResponses(question)]
}

const QCM = (value) => {
    return (
        <>
            <label>{value.question[1]}</label><br/>
            <select>
                {value.question[5].map((value) => {
                    return <option value={value} key={value}> {value}</option>
                })}
            </select>
            <input type="submit" style={{marginLeft: '10px'}}/>
        </>
    )
}

const NUMERIC = (value) => {
    return (
        <>
            <label>{value.question[1]}</label><br/>
            {value.question[5][0]} : <input/>
            <input type="submit" style={{marginLeft: '10px'}}/>
        </>
    )
}

function Questionnaire() {
    // const { register, handleSubmit } = useForm();
    // const [questionnaire, onQuestionnaire] = useState([])

    // const onSubmit = (data) => {
    //     alert(JSON.stringify(data));
    // };

    return (
        <>
            <StepperComponent/>
            <form style={{paddingLeft: '120px', paddingRight: '120px', marginTop: '20px'}}>
                {buildQuestionnaire(test).map((value) => {
                    if (value[2] === 'QCM') {
                        return (
                            <div key={value} style={{marginTop: '20px'}}>
                                <QCM question={value}/>
                            </div>
                        )}
                    {
                        return (
                            <div key={value} style={{marginTop: '20px'}}>
                                <NUMERIC question={value}/>
                            </div>
                        )
                    }
                })}
            </form>
        </>
    );
}

export default Questionnaire