import React, {useEffect, useState} from "react";
import {useForm} from "react-hook-form";
import {Spinner} from "react-bootstrap";
import {useNavigate} from "react-router-dom";
import Phase from "./Phase";


/**
 * The component representing the quiz
 * @returns {JSX.Element}
 * @constructor
 */
function Questionnaire(activeStep) {
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
            // .then(result => console.log(result))
            // .catch(error => console.log('error', error));

        navigate("/result?id=1")
    }

    useEffect(() => {
        const token = sessionStorage.getItem("token")
        const options = {
            method: 'GET',
            headers: {
                'Authorization': `Bearer ${token}`
            },
        };
        fetch("/api/questions",options)
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
       let currentPhase;
       switch(activeStep.activeStep) {
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
           default:
               currentPhase = data.MAINTENANCE
               break;
       }
        return (
                <form onSubmit={handleSubmit(onSubmit)}
                      style={{paddingLeft: '120px', paddingRight: '120px', marginTop: '20px'}}>
                    {currentPhase.map(question =>
                        <Phase key={question.questionId} register={register} value={question}/>
                    )}
                    <input type="submit" style={{marginTop: '20px'}}/>
                </form>
        );
    }
}

export default Questionnaire