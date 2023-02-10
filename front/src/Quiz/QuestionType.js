import {useState} from "react";
import React from "react";

/**
 * A component representing a QCM question
 * @param value
 * @returns {JSX.Element}
 * @constructor
 */
export const QCM = React.forwardRef(({ onChange, name, question }, ref) => {

    let handleId = (event) => {
        onChange(event)
    }

    return (
        <div style={{marginTop: '20px'}}>
            <p>{question.intitule}</p>
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
export const NUMERIC = ({question, register, id}) => {
    return (
        <div style={{marginTop: '20px'}}>
            <label>{question.intitule}</label><br/>
            {question.reponses[0][0]} : <input type={"number"} {...register(id.toString())}/><br/>
        </div>
    )
}