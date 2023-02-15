import {NUMERIC, QCM} from "./QuestionType";
import React from "react";

export default function Phase({value, register}){
    console.log("value" + value)
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