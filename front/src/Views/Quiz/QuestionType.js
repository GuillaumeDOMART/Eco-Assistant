import React from "react";
import {FormCheck} from "react-bootstrap";

/**
 * A component representing a QCM question
 * @param value
 * @returns {JSX.Element}
 * @constructor
 */
export const QCM = React.forwardRef(({ onChange, name, question }) => {
    return (
        <div style={{marginTop: '20px'}}>
            <p>{question.intitule}</p>
            {/*<input type={"checkbox"} name={name} ref={ref} onChange={onChange}>*/}
                {question.reponses.map((data) => {
                    return  (
                        <FormCheck
                        inline
                        name={question.intitule}
                        type={"radio"}
                        value={data.intitule}
                        key={data.intitule}
                        label={data.intitule}/>
                    )
                })}<br/>
        </div>
    )
})

QCM.displayName = 'QCM';

/**
 * A component representing a NUMERIC question
 * @param value
 * @returns {JSX.Element}
 * @constructor
 */
export const NUMERIC = ({question, register}) => {
    return (
        <div style={{marginTop: '20px'}}>
            <label>{question.intitule}</label><br/>
            {question.reponses[0].intitule} :<input type={"number"}
                                               {...register(question.questionId.toString())}
                                               defaultValue={0}
                                               min={0}
            /><br/>
        </div>
    )
}