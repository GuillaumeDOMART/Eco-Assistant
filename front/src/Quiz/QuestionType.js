import React from "react";

/**
 * A component representing a QCM question
 * @param value
 * @returns {JSX.Element}
 * @constructor
 */
export const QCM = React.forwardRef(({ onChange, name, question }, ref) => {

    return (
        <div style={{marginTop: '20px'}}>
            <p>{question.intitule}</p>
            <select name={name} ref={ref} onChange={onChange}>
                {question.reponses.map((data) => {
                    return <option value={data} key={data} label={data}/>
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
export const NUMERIC = ({question, register}) => {
    return (
        <div style={{marginTop: '20px'}}>
            <label>{question.intitule}</label><br/>
            {question.reponses[0]} : <input type={"number"}
                                               {...register(question.questionId.toString())}
                                               defaultValue={0}
                                               min={0}
            /><br/>
        </div>
    )
}