import {NUMERIC, QCM} from "./QuestionType";
import React from "react";

/**
 * gestion affuchage d'une question
 * @param value question
 * @param register register du quiz
 * @param setSelectedAnswer
 * @returns {JSX.Element|null}
 * @constructor
 */
export default function Phase({value, register, handleChange}) {

    switch (value.type) {
        case 'QCM' :
            const onChange = (target) => {
                const select = value.reponses.find(value => value.intitule === target.target.value)
                let answer = {
                    "question": target.target.name,
                    "reponseId": select.reponseId
                }
                handleChange(answer)
            }

            return (
                <QCM key={value.intitule}
                     question={value}
                     {...register(value.questionId.toString())}
                     onChange={onChange}
                />
            )
        case 'NUMERIC' :
            return (
                <NUMERIC key={value.intitule}
                         question={value}
                         register={register}
                />
            )
        default :
            return null;
    }
}