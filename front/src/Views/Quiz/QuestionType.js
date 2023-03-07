import React, {useCallback} from "react";
import {FormControlLabel, Radio, RadioGroup} from "@mui/material";

/**
 * A component representing a QCM question
 * @param value
 * @returns {JSX.Element}
 * @constructor
 */
export const QCM = React.forwardRef(({onChange, name, question, onVisibility}, ref) => {

    const handleChange = useCallback((event) => {
        onChange(event);
        onVisibility(event)
    }, [onVisibility, onChange])
    if (question.reponse === null) {
        return (
            <div style={{marginTop: '20px'}} className="shadow-lg rounded p-3">
                <h5>{question.intitule}</h5>

                <RadioGroup className="mx-5">
                    {question.reponses.map((data) => {
                        return (
                            <FormControlLabel
                                value={data.intitule}
                                key={data.intitule}
                                label={data.intitule}
                                control={
                                    <Radio ref={ref} name={name} onChange={handleChange}/>
                                }
                            />
                        )
                    })}<br/>
                </RadioGroup>
            </div>
        )
    }
    return (
        <div style={{marginTop: '20px'}} className="shadow-lg rounded p-3">
            <h5>{question.intitule}</h5>
            <RadioGroup className="mx-5" defaultValue={question.reponse.reponse.intitule}>
                {question.reponses.map((data) => {
                    return (
                        <FormControlLabel
                            value={data.intitule}
                            key={data.intitule}
                            label={data.intitule}
                            control={
                                <Radio ref={ref} name={name} onChange={handleChange}/>
                            }
                        />
                    )
                })}<br/>
            </RadioGroup>
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
    if (question.reponse === null) {
        return (
            <div style={{marginTop: '20px'}} className="shadow-lg rounded">
                <div className="mx-5 p-3">
                    <h5>{question.intitule}</h5><br/>
                    {question.reponses[0].intitule} : <input
                    type={"number"}
                    {...register(question.questionId.toString())}
                    min={0}
                    defaultValue={question.reponse}
                /><br/>
                </div>
            </div>
        )
    }
    return (
        <div style={{marginTop: '20px'}} className="shadow-lg rounded">
            <div className="mx-5 p-3">
                <h5>{question.intitule}</h5><br/>
                {question.reponses[0].intitule} : <input
                type={"number"}
                {...register(question.questionId.toString())}
                min={0}
                defaultValue={question.reponse.entry}
            /><br/>
            </div>
        </div>
    )
}