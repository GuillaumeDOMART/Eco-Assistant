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
export default function Phase({value, register, onChange}) {

    switch (value.type) {
        case 'QCM' :
            const handleChange = (target) => {
                onChange(target, value)
            }
            return (
                <QCM key={value.intitule}
                     question={value}
                     {...register(value.questionId.toString())}
                     onChange={handleChange}
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