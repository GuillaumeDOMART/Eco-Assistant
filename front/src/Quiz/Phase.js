import {NUMERIC, QCM} from "./QuestionType";
import React from "react";

/**
 * gestion affuchage d'une question
 * @param value question
 * @param register register du quiz
 * @returns {JSX.Element|null}
 * @constructor
 */
export default function Phase({value, register}){
    switch (value.type) {
        case 'QCM' :
            return (
                <QCM key={value.intitule}
                     question={value}
                     {...register(value.questionId.toString())}
                />
            )
        case 'NUMERIC' :
            return (
                <NUMERIC key={value.intitule}
                         question={value}
                         register={register}
                />
            )
        default : return null;
    }
}