import {NUMERIC, QCM} from "./QuestionType";
import React, {useCallback} from "react";

/**
 * management display of a question
 * @param value question
 * @param register register du quiz
 * @param setSelectedAnswer
 * @returns {JSX.Element|null}
 * @constructor
 */
export default function Phase({value, register, onChange, detail}) {
    /**
     * doc
     * @param target because
     */
    const handleChange = useCallback(
        (target) => {
        onChange(target, value)
    }, [onChange, value]
    );
    switch (value.type) {
        case 'QCM' :
            return (
                <QCM key={value.intitule}
                     question={value}
                     register={register}
                     {...register(value.questionId.toString())}
                     onVisibility={handleChange}
                     detail={detail}
                />
            )
        case 'NUMERIC' :
            return (
                <NUMERIC key={value.intitule}
                         question={value}
                         register={register}
                         detail={detail}
                />
            )
        default :
            return null;
    }
}