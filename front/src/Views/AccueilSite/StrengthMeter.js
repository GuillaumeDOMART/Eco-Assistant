import React, {useCallback, useState} from "react";
import "./AccueilSite.css";
import {TextField, Tooltip} from "@mui/material";

/**
 * Component that represente a strengh meter for passwords, it includes the textfield password and the error handling of it
 *
 * @param register
 * @param fieldErrors
 * @returns {JSX.Element}
 * @constructor
 */
const StrengthMeter = ({register, fieldErrors}) => {

    const [password, setPassword] = useState("");
    const [progress, setProgress] = useState({strength: 0, val: ""});

    /**
     * Function to check the password and update the progress bar
     *
     * @return the function
     */
    const pwdChecker = useCallback((event) => {
        const passwd = event.target.value;
        const pwdCheck = [false, false, false, false, false];
        const validateRegex = [".{8,}", "[A-Z]", "[a-z]", "[0-9]", "[\\W_]"]

        for (let i = 0; i < validateRegex.length; i++) {
            const regex = new RegExp(validateRegex[i]);
            if (regex.test(passwd)) {
                pwdCheck[i] = true;
            }
        }
        switch (pwdCheck.filter(v => v === true).length) {
            case 1:
                setProgress({strength: 1, val: "faible"});
                break;
            case 2:
                setProgress({strength: 2, val: "passable"});
                break;
            case 3:
                setProgress({strength: 3, val: "correct"});
                break;
            case 4:
                setProgress({strength: 3, val: "bon"});
                break;
            case 5:
                setProgress({strength: 4, val: "fort"});
                break;
            default:
                setProgress({strength: 0, val: ""});
        }
        setPassword(passwd);
    }, []);

    return (
        <>
            <Tooltip
                title={"Le mot de passe doit être composé de 8 caractères minimum dont au moins 1 minuscule, 1 MAJUSCULE, 1 chiffre et 1 caractère spécial"}
                arrow>
                <TextField label="Mot de passe" type="password" variant="standard"
                           className="textfield " {...register("password")} value={password} onChange={pwdChecker}
                           required
                           error={!Boolean(fieldErrors.password)}
                           helperText={fieldErrors.password}/>
            </Tooltip><br/>

            <progress
                className={`pwd-checker-bar strength-${progress.val}`}
                value={progress.strength}
                max="4"
            />
        </>
    );
};
export default StrengthMeter;